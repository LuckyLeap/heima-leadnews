package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.NewsAuthDto;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;

public interface WmNewsService extends IService<WmNews> {
    /**
     * 查询文章
     * @param dto 查询条件
     */
    ResponseResult<Object> findAll(WmNewsPageReqDto dto);

    /**
     * 查询文章详情
     * @param id 文章id
     */
    ResponseResult<Object> findOne(Integer id);

    /**
     *  发布文章或保存草稿
     * @param dto 文章信息
     */
    ResponseResult<Object> submitNews(WmNewsDto dto);

    /**
     *  删除文章
     * @param id 文章id
     */
    ResponseResult<Object> delById(Integer id);

    /**
     *  上下架文章
     * @param dto 状态信息
     */
    ResponseResult<Object> downOrUp(WmNewsDto dto);

    /**
     * 查询文章列表
     * @param dto 查询条件
     */
    ResponseResult<Object> findList(NewsAuthDto dto);

    /**
     * 查询文章详情
     * @param id 文章id
     */
    ResponseResult<Object> findWmNewsVo(Integer id);

    /**
     * 文章审核，修改状态
     * @param status  2  审核失败  4 审核成功
     * @param dto 修改状态
     */
    ResponseResult<Object> updateStatus(Short status , NewsAuthDto dto);
}