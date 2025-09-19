package cloud.onion.cms.mapper;

import cloud.onion.cms.model.entity.GeneralArticle;
import cloud.onion.cms.model.res.GeneralArticleRes;
import cloud.onion.cms.model.res.SearchRes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 允泽
 * @date 2022/8/7
 */
@Mapper
public interface GeneralArticleMapper extends BaseMapper<GeneralArticle> {
    @Select(value = "<script>" +
            "select * from (SELECT t.*,IFNULL(bai.en_title,xcwt.en_title) enTitle,IFNULL(bai.ru_title,xcwt.ru_title) ruTitle," +
            "IFNULL(bai.en_description,xcwt.en_summary) enDescription,IFNULL(bai.ru_description,xcwt.ru_summary) ruDescription," +
            "case when instr(t.title,'国别指南')>0 then 1 when instr(t.title,'国情图解')>0 then 2 else 3 end as order_num " +
            "FROM `bm_general_article` t " +
            "LEFT JOIN bm_article_info bai on (t.data_id = bai.id and bai.deleted = 1 and bai.is_review = 2) " +
            "LEFT JOIN xh_content_with_translate xcwt on (t.data_id = xcwt.id) " +
            "where " +
            "t.status=1 and t.deleted = 1 and t.menu_id is not null" +
            "<choose> <when test='keywords != null and keywords.trim() != \"\"'> and t.data_id IN (SELECT id FROM  bm_article_info WHERE is_review = '2' AND instr( title, #{keywords} ) > 0  and status = '1' AND deleted = '1') or t.data_id IN (SELECT id from shpt_info_content where eisDel = '0' AND instr( title, #{keywords} ) > 0 ) and instr(t.title,#{keywords}) >0 </when><otherwise> and 1=2</otherwise></choose>" +
            " ) a order by a.order_num,a.add_time desc" +
            "</script>")
    IPage<GeneralArticleRes> getPageList(Page<GeneralArticleRes> page, @Param(value = "keywords")String keywords);



    @Select(value = "<script>" +
            "select * from ("+
            "select CAST(a.data_id AS bigint) AS id,a.menu_id as menuId,a.title,a.enTitle,a.ruTitle,a.description,a.enDescription,a.ruDescription,a.add_time as addTime,a.status as status,order_num,1 as type,a.deleted as deleted,a.data_type dataType" +
            " from (SELECT t.*,IFNULL(bai.en_title,xcwt.en_title) enTitle,IFNULL(bai.ru_title,xcwt.ru_title) ruTitle,IFNULL(bai.en_description,xcwt.en_summary) enDescription," +
            "IFNULL(bai.ru_description,xcwt.ru_summary) ruDescription,case when instr(t.title,'国别指南')>0 then 1 when instr(t.title,'国情图解')>0 then 2 else 3 end as order_num FROM `bm_general_article` t " +
            " LEFT JOIN bm_article_info bai on (t.data_id = bai.id and bai.deleted = 1 and bai.is_review = 2) " +
            " LEFT JOIN xh_content_with_translate xcwt on t.data_id = xcwt.id where t.STATUS = 1 AND t.deleted = 1 AND t.data_id IN (SELECT id FROM  bm_article_info WHERE is_review = '2' AND instr( title, #{keywords} ) > 0  and status = '1' AND deleted = '1') or t.data_id IN (SELECT id from shpt_info_content where eisDel = '0' AND instr( title, #{keywords} ) > 0 ) " +
            "<choose> <when test='keywords != null and keywords.trim() != \"\"'> and instr(t.title,#{keywords}) >0 </when><otherwise> and 1=2</otherwise></choose> " +
            " ) a " +
            "UNION ALL " +
            "SELECT CAST(t1.id AS bigint) AS id,null as menuId,t1.name as title,t1.en_name enTitle,t1.ru_name ruTitle,t1.description,t1.en_description enDescription,t1.ru_description ruDescription,t1.create_time as addTime,t1.status as status,4 as order_num,2 as type,t1.deleted as deleted,1 as data_type " +
            "from `bm_b2b_product_info` t1 where t1.status=1 and t1.deleted = 1 and t1.is_review = 2 " +
            "<choose> <when test='keywords != null and keywords.trim() != \"\"'> and instr(t1.name,#{keywords}) >0 </when><otherwise> and 1=2</otherwise></choose> " +
            "UNION ALL " +
            "SELECT CAST(t2.id AS bigint) AS id,null as menuId,t2.title as title,t2.en_title enTitle,t2.ru_title ruTitle,null as description,null as enDescription,null as ruDescription,t2.create_time as addTime,t2.status as status,5 as order_num,3 as type,t2.deleted as deleted,1 as data_type " +
            "from `bm_b2b_purchase_info` t2 where t2.status=1 and t2.deleted = 1 and t2.is_review = 2 and t2.end_time > DATE_SUB(CURDATE(),INTERVAL 1 DAY)" +
            "<choose> <when test='keywords != null and keywords.trim() != \"\"'> and instr(t2.title,#{keywords}) >0 </when><otherwise> and 1=2</otherwise></choose> " +
            ") aa "+
            "order by order_num,addTime desc " +
            "</script>")
    IPage<SearchRes> getSeachPageList(Page<SearchRes> pager, @Param(value = "keywords")String keywords);
}
