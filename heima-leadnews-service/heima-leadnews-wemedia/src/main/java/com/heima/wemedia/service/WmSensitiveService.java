package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.SensitiveDto;
import com.heima.model.wemedia.pojos.WmSensitive;

public interface WmSensitiveService extends IService<WmSensitive> {
    /**
     * 查询
     */
    ResponseResult<Object> list(SensitiveDto dto);

    /**
     * 新增
     */
    ResponseResult<Object> insert(WmSensitive wmSensitive);

    /**
     * 修改
     * @param wmSensitive wmSensitive
     */
    ResponseResult<Object> update(WmSensitive wmSensitive);

    /**
     * 删除
     * @param id id
     */
    ResponseResult<Object> delete(Integer id);
}