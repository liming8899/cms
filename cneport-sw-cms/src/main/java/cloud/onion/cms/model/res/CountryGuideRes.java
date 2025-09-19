package cloud.onion.cms.model.res;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
public class CountryGuideRes implements Serializable {

    /**
     * 文章Id
     */
    private String id;

    /**
     * 文章栏目Id
     */
    private String categoryId;

    /**
     * 国情指南展示名称-中文
     */
    private String title;

    /**
     * 国情指南展示名称-英文
     */
    private String enTitle;

    /**
     * 国情指南展示名称-俄文
     */
    private String ruTitle;

    /**
     * 本地新闻创建时间
     */
    private Date createTime;

    /**
     * 文章类型
     */
    private String articleType;

}
