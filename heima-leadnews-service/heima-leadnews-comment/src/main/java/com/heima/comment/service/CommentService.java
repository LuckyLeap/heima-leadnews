package com.heima.comment.service;

import com.heima.model.comment.dtos.CommentDto;
import com.heima.model.comment.dtos.CommentLikeDto;
import com.heima.model.comment.dtos.CommentSaveDto;
import com.heima.model.common.dtos.ResponseResult;

public interface CommentService {

    /**
     * 保存评论
     */
    ResponseResult<Object> saveComment(CommentSaveDto dto);

    /**
     * 点赞
     */
    ResponseResult<Object> like(CommentLikeDto dto);

    /**
     * 加载评论列表
     * @param dto 评论dto
     */
    ResponseResult<Object> findByArticleId(CommentDto dto);

}