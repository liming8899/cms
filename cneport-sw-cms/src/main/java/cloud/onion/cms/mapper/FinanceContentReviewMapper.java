package cloud.onion.cms.mapper;

import cloud.onion.cms.model.res.FinanceAgencyRes;
import cloud.onion.cms.model.res.FinanceHomepageRes;
import cloud.onion.cms.model.res.FinanceProductRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
* @author ShenFM
* @description 针对表【bm_finance_content_review】的数据库操作Mapper
* @createDate 2023-07-07 10:44:58
* @Entity model.BmFinanceContentReview
*/
@Mapper
public interface FinanceContentReviewMapper {
    @Select(value = "<script>" +
            " select * from finance_homepage_reviewed fhr where fhr.isReview = 2 and fhr.status = 1 " +
            "</script>")
    List<FinanceHomepageRes> getFinanceHomePageList();

    @Select(value = "<script>" +
            " select * from finance_agency_reviewed far where far.isReview = 2 and far.status = 1 " +
            " <if test='homepageId != null'> and far.homepage_id = #{homepageId} </if> " +
            " <if test='agencyId != null'> and far.id = #{agencyId} </if> " +
            "</script>")
    List<FinanceAgencyRes> getFinanceAgencyList(Map<String, Long> params);

    @Select(value = "<script>" +
            " select * from finance_product_reviewed fpr where fpr.isReview = 2 and fpr.status = 1" +
            " <if test='agencyId != null'> and fpr.agency_id = #{agencyId} </if> " +
            " <if test='productId != null'> and fpr.id = #{productId} </if> " +
            "</script>")
    List<FinanceProductRes> getFinanceProductList(Map<String, Long> params);
}




