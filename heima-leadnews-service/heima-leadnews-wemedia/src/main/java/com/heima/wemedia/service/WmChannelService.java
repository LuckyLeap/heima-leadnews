package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.ChannelDto;
import com.heima.model.wemedia.pojos.WmChannel;

public interface WmChannelService extends IService<WmChannel> {

    /**
     * 查询所有频道
     */
    ResponseResult<Object> findAll();

    /**
     * 保存
     * @param wmChannel 频道
     */
    ResponseResult<Object> insert(WmChannel wmChannel);

    /**
     * 查询
     */
    ResponseResult<Object> findByNameAndPage(ChannelDto dto);

    /**
     * 修改
     */
    ResponseResult<Object> update(WmChannel wmChannel);

    /**
     * 删除
     * @param id 频道id
     */
    ResponseResult<Object> delete(Integer id);

}