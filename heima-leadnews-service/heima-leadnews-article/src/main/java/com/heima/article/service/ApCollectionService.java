package com.heima.article.service;

import com.heima.model.article.dtos.CollectionBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

public interface ApCollectionService {
    /**
     * 收藏
     */
    ResponseResult<Object> collection(CollectionBehaviorDto dto);
}