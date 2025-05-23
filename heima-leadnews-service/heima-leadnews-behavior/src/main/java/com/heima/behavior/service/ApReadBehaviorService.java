package com.heima.behavior.service;

import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

public interface ApReadBehaviorService {

    /**
     * 保存阅读行为
     * @param dto 阅读行为数据
     */
    ResponseResult<Object> readBehavior(ReadBehaviorDto dto);

}