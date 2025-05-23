package com.heima.comment.service;

import com.heima.model.comment.dtos.CommentRepayDto;
import com.heima.model.comment.dtos.CommentRepayLikeDto;
import com.heima.model.comment.dtos.CommentRepaySaveDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * 评论回复
 */
public interface CommentRepayService {

    /**
     * 查看更多回复内容
     * @param dto 评论回复dto
     */
    ResponseResult<Object> loadCommentRepay(CommentRepayDto dto);

    /**
     * 保存回复
     */
    ResponseResult<Object> saveCommentRepay(CommentRepaySaveDto dto);

    /**
     * 点赞回复的评论
     */
    ResponseResult<Object> saveCommentRepayLike(CommentRepayLikeDto dto);

}