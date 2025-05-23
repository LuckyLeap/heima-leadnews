package com.heima.behavior.controller.v1;

import com.heima.behavior.service.ApUnlikesBehaviorService;
import com.heima.model.behavior.dtos.UnLikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/un_likes_behavior")
public class ApUnlikesBehaviorController {

    private ApUnlikesBehaviorService apUnlikesBehaviorService;
    @Autowired
    public void setApUnlikesBehaviorService(ApUnlikesBehaviorService apUnlikesBehaviorService) {
        this.apUnlikesBehaviorService = apUnlikesBehaviorService;
    }

    @PostMapping
    public ResponseResult<Object> unLike(@RequestBody UnLikesBehaviorDto dto) {
        return apUnlikesBehaviorService.unLike(dto);
    }

}