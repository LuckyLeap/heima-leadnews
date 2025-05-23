package com.heima.wemedia.service;

import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.StatisticsDto;

public interface WmStatisticsService {

    /**
     * 图文统计
     */
    ResponseResult<Object> newsDimension(String beginDate, String endDate);

    /**
     * 分页查询图文统计
     */
    PageResponseResult newsPage(StatisticsDto dto);

}