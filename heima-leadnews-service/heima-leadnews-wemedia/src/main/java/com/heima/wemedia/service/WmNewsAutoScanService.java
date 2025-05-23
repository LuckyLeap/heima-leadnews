package com.heima.wemedia.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmNews;

public interface WmNewsAutoScanService {

    /**
     * 自媒体文章审核
     * @param id  自媒体文章id
     */
    void autoScanWmNews(Integer id);

    /**
     * 保存app文章数据
     * @param wmNews 自媒体文章
     */
    ResponseResult<Object> saveAppArticle(WmNews wmNews);

}