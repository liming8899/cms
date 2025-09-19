package cloud.onion.cms.config.i18n;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented //说明该注解将被包含在javadoc中
public @interface I18n {
    /**
     * 国际化文件名
     */
    String[] value();
}