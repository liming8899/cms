package cloud.onion.cms.service.impl;



import cloud.onion.cms.mapper.ConstructHistoryMapper;
import cloud.onion.cms.model.convertor.ArticleConvertor;
import cloud.onion.cms.model.convertor.ConstructHistoryConvertor;
import cloud.onion.cms.model.entity.ArticleInfo;
import cloud.onion.cms.model.entity.ConstructHistoryInfo;
import cloud.onion.cms.model.res.ConstructHistoryRes;
import cloud.onion.cms.service.IHistoryService;
import cloud.onion.core.constant.ReviewConst;
import cloud.onion.core.constant.StatusConst;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class HistoryServiceImpl extends ServiceImpl<ConstructHistoryMapper, ConstructHistoryInfo> implements IHistoryService {


    @Override
    public IPage<ConstructHistoryRes> pages(int currentPage, int pageSize, String keywords) {
        Page<ConstructHistoryInfo> pager = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<ConstructHistoryInfo> lambdaQuery = Wrappers.<ConstructHistoryInfo>lambdaQuery();
        lambdaQuery.eq(ConstructHistoryInfo::getStatus, StatusConst.NORMAL);
        lambdaQuery.eq(ConstructHistoryInfo::getIsReview, ReviewConst.YES);
        if (StringUtils.isNotBlank(keywords)) {
            lambdaQuery.like(ConstructHistoryInfo::getTitle,keywords);
        }
        lambdaQuery.orderByAsc(ConstructHistoryInfo::getHistoryTime);
        Page<ConstructHistoryInfo> poPage = super.page(pager,lambdaQuery);
//        List<ConstructHistoryInfo> records = poPage.getRecords();
//        for (ConstructHistoryInfo record : records) {
//            Date historyTime = record.getHistoryTime();
//            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
//            record.setHistoryTimeStr(simpleDateFormat.format(historyTime));
//        }
//        poPage.setRecords(records);
        return poPage.convert(ConstructHistoryConvertor.INSTANCE::fromHistory);
    }
}
