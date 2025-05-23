package com.heima.wemedia.service;

import com.heima.model.article.dtos.ArticleCommentDto;
import com.heima.model.comment.dtos.CommentConfigDto;
import com.heima.model.comment.dtos.CommentLikeDto;
import com.heima.model.comment.dtos.CommentManageDto;
import com.heima.model.comment.dtos.CommentRepaySaveDto;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;

public interface CommentManageService {

    /**
     * 查看文章评论列表
     * @param dto 文章评论分页条件
     */
    PageResponseResult findNewsComments(ArticleCommentDto dto);

    /**
     * 打开或关闭评论
     * @param dto 评论配置
     */
    ResponseResult<Object> updateCommentStatus(CommentConfigDto dto);

    /**
     * 查询评论列表
     */
    ResponseResult<Object> list(CommentManageDto dto);

    /**
     * 删除评论
     */
    ResponseResult<Object> delComment(String commentId);

    /**
     * 删除评论回复
     * @param commentRepayId 评论回复id
     */
    ResponseResult<Object> delCommentRepay(String commentRepayId);

    /**
     * 回复评论
     * @param dto 回复评论
     */
    ResponseResult<Object> saveCommentRepay(CommentRepaySaveDto dto);

    /**
     * 点赞
     * @param dto 评论点赞
     */
    ResponseResult<Object> like(CommentLikeDto dto);
}