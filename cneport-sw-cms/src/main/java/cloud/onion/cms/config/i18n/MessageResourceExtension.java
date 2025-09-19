package cloud.onion.cms.config.i18n;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

/**
 * 配置国际化文件自动匹配获取
 */
@Slf4j
@Component("messageSource")
@Order(1)
public class MessageResourceExtension extends ResourceBundleMessageSource {

    /**
     * 指定的国际化文件目录
     */
    @Value(value = "${spring.messages.baseFolder}")
    private String baseFolder;
 
    /**
     * 父MessageSource指定的国际化文件
     */
    @Value(value = "${spring.messages.basename}")
    private String basename;
 
    public static String I18N_ATTRIBUTE = "i18n_attribute";

    @PostConstruct
    public void init() {
        log.info("init MessageResourceExtension...");
        if (!StringUtils.isEmpty(baseFolder)) {
            try {
                this.setBasenames(getAllBaseNames(baseFolder));
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        //设置父MessageSource
        ResourceBundleMessageSource parent = new ResourceBundleMessageSource();
        //是否是多个目录
        if (basename.indexOf(",") > 0) {
            parent.setBasenames(basename.split(","));
        } else {
            parent.setBasename(basename);
        }
        //设置文件编码
        parent.setDefaultEncoding("UTF-8");
        this.setParentMessageSource(parent);
        this.setDefaultEncoding("UTF-8");
    }

    @Override
    public void setDefaultEncoding(String defaultEncoding) {
        super.setDefaultEncoding(defaultEncoding);
    }

    /**
     * 获取国际化资源
     * @param code 资源code
     * @param locale 语言编码
     * @return
     */
    @Override
    protected String resolveCodeWithoutArguments(@NotNull String code, @NotNull Locale locale) {
        // 获取request中设置的指定国际化文件名
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Object attribute = attr.getAttribute(I18N_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        String[] i18File = null;
        if (attribute instanceof String) {
            i18File = new String[]{(String) attribute};
        } else if (attribute instanceof String[]) {
            i18File = (String[])attribute;
        }
        if (i18File != null && i18File.length > 0) {
            //获取在basenameSet中匹配的国际化文件名
            List<String> basename = new ArrayList<>();
            String[] baseNameArr = getBasenameSet().toArray(new String[]{});
            for (String aBaseNameArr : baseNameArr) {
                Arrays.stream(i18File).forEach(fileName -> {
                    if (StringUtils.endsWithIgnoreCase(aBaseNameArr, fileName)) {
                        basename.add(aBaseNameArr);
                    }
                });
            }
            if (basename.size() > 0) {
                for (String aBaseName : basename) {
                    //得到指定的国际化文件资源
                    ResourceBundle bundle = getResourceBundle(aBaseName, locale);
                    if (bundle != null) {
//                        if ("Releasetime".equals(code)) {
                            String result = getStringOrNull(bundle, code);
                            if (result != null) {
                                return getStringOrNull(bundle, code);
                            }
//                        }
                    }
                }
            }
        }
        //如果指定i18文件夹中没有该国际化字段,返回null会在ParentMessageSource中查找
        return null;
    }
 
    /**
     * 获取文件夹下所有的国际化文件名
     *
     * @param folderName 文件夹名
     * @return
     * @throws IOException
     */
    public String[] getAllBaseNames(final String folderName) throws IOException {
        URL url = Thread.currentThread().getContextClassLoader()
                .getResource(folderName);
        if (null == url) {
            throw new RuntimeException("无法获取资源文件路径");
        }
 
        List<String> baseNames = new ArrayList<>();
        if (url.getProtocol().equalsIgnoreCase("file")) {
            // 文件夹形式,用File获取资源路径
            File file = new File(url.getFile());
            if (file.exists() && file.isDirectory()) {
                baseNames = Files.walk(file.toPath())
                        .filter(path -> path.toFile().isFile())
                        .map(Path::toString)
                        .map(path -> path.substring(path.indexOf(folderName)))
                        .map(this::getI18FileName)
                        .distinct()
                        .collect(Collectors.toList());
            } else {
                log.error("指定的baseFile不存在或者不是文件夹");
            }
        } else if (url.getProtocol().equalsIgnoreCase("jar")) {
            // jar包形式，用JarEntry获取资源路径
            String jarPath = url.getFile().substring(url.getFile().indexOf(":") + 2, url.getFile().indexOf("!"));
            JarFile jarFile = new JarFile(new File(jarPath));
            List<String> baseJars = jarFile.stream()
                    .map(ZipEntry::toString)
                    .filter(jar -> jar.endsWith(folderName + "/")).collect(Collectors.toList());
            if (baseJars.isEmpty()) {
                log.info("不存在{}资源文件夹", folderName);
                return new String[0];
            }
 
            baseNames = jarFile.stream().map(ZipEntry::toString)
                    .filter(jar -> baseJars.stream().anyMatch(jar::startsWith))
                    .filter(jar -> jar.endsWith(".properties"))
                    .map(jar -> jar.substring(jar.indexOf(folderName)))
                    .map(this::getI18FileName)
                    .distinct()
                    .collect(Collectors.toList());
 
        }
        return baseNames.toArray(new String[0]);
    }
 
    /**
     * 把普通文件名转换成国际化文件名
     *
     * @param filename 文件名
     * @return
     */
    private String getI18FileName(String filename) {
        filename = filename.replace(".properties", "");
        for (int i = 0; i < 2; i++) {
            int index = filename.lastIndexOf("_");
            if (index != -1) {
                filename = filename.substring(0, index);
            }
        }
        return filename.replace("\\", "/");
    }
}