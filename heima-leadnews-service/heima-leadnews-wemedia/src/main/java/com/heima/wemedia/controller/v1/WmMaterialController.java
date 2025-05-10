package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.wemedia.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/material")
public class WmMaterialController {

    private final WmMaterialService wmMaterialService;
    @Autowired
    public WmMaterialController(WmMaterialService wmMaterialService) {
        this.wmMaterialService = wmMaterialService;
    }


    @PostMapping("/upload_picture")
    public ResponseResult<Object> uploadPicture(MultipartFile multipartFile){
        return wmMaterialService.uploadPicture(multipartFile);
    }

    @PostMapping("/list")
    public ResponseResult<Object> findList(@RequestBody WmMaterialDto dto){
        return wmMaterialService.findList(dto);
    }

    @GetMapping("del_picture/{id}")
    public ResponseResult<Object> delById(@PathVariable("id") Integer id){
        return wmMaterialService.delById(id);
    }

    @GetMapping("collect/{id}")
    public ResponseResult<Object> collect(@PathVariable("id") Integer id){
        return wmMaterialService.collect(id);
    }

    @GetMapping("cancel_collect/{id}")
    public ResponseResult<Object> cancelCollect(@PathVariable("id") Integer id){
        return wmMaterialService.cancelCollect(id);
    }
}