package com.heima.wemedia.controller.v1;

import com.heima.model.article.dtos.ArticleCommentDto;
import com.heima.model.comment.dtos.CommentConfigDto;
import com.heima.model.comment.dtos.CommentLikeDto;
import com.heima.model.comment.dtos.CommentManageDto;
import com.heima.model.comment.dtos.CommentRepaySaveDto;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.wemedia.service.CommentManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment/manage")
public class CommentManageController {

    private CommentManageService commentManageService;
    @Autowired
    public void setCommentManageService(CommentManageService commentManageService) {
        this.commentManageService = commentManageService;
    }

    @PostMapping("/list")
    public ResponseResult<Object> list(@RequestBody CommentManageDto dto){
        return commentManageService.list(dto);
    }

    @DeleteMapping("/del_comment/{commentId}")
    public ResponseResult<Object> delComment(@PathVariable("commentId") String commentId){
        return commentManageService.delComment(commentId);
    }

    @DeleteMapping("/del_comment_repay/{commentRepayId}")
    public ResponseResult<Object> delCommentRepay(@PathVariable("commentRepayId") String commentRepayId){
        return commentManageService.delCommentRepay(commentRepayId);
    }

    @PostMapping("/find_news_comments")
    public PageResponseResult findNewsComments(@RequestBody ArticleCommentDto dto){
        return commentManageService.findNewsComments(dto);
    }

    @PostMapping("/update_comment_status")
    public ResponseResult<Object> updateCommentStatus(@RequestBody CommentConfigDto dto){
        return commentManageService.updateCommentStatus(dto);
    }

    @PostMapping("/comment_repay")
    public ResponseResult<Object> saveCommentRepay(@RequestBody CommentRepaySaveDto dto){
        return commentManageService.saveCommentRepay(dto);
    }

    @PostMapping("/like")
    public ResponseResult<Object> like(@RequestBody CommentLikeDto dto){
        return commentManageService.like(dto);
    }

}