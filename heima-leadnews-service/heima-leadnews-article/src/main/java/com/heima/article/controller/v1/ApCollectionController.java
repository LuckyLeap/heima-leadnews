package com.heima.article.controller.v1;

import com.heima.article.service.ApCollectionService;
import com.heima.model.article.dtos.CollectionBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/collection_behavior")
public class ApCollectionController {

    private ApCollectionService apCollectionService;
    @Autowired
    public void setApCollectionService(ApCollectionService apCollectionService) {
        this.apCollectionService = apCollectionService;
    }

    @PostMapping
    public ResponseResult<Object> collection(@RequestBody CollectionBehaviorDto dto) {
        return apCollectionService.collection(dto);
    }

}