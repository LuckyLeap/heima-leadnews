package com.heima.wemedia.controller.v1;

import com.heima.model.common.constants.WemediaConstants;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.NewsAuthDto;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController {

    private final WmNewsService wmNewsService;
    @Autowired
    public WmNewsController(WmNewsService wmNewsService) {
        this.wmNewsService = wmNewsService;
    }

    @PostMapping("/list")
    public ResponseResult<Object> findAll(@RequestBody WmNewsPageReqDto dto){
        return  wmNewsService.findAll(dto);
    }

    @PostMapping("/submit")
    public ResponseResult<Object> submitNews(@RequestBody WmNewsDto dto){
        return  wmNewsService.submitNews(dto);
    }

    @GetMapping("del_news/{id}")
    public ResponseResult<Object> delById(@PathVariable("id") Integer id){
        return  wmNewsService.delById(id);
    }

    @PostMapping("/down_or_up")
    public ResponseResult<Object> downOrUp(@RequestBody WmNewsDto dto){
        return  wmNewsService.downOrUp(dto);
    }

    @GetMapping("/one/{id}")
    public ResponseResult<Object> findOne(@PathVariable("id") Integer id){
        return wmNewsService.findOne(id);
    }

    @PostMapping("/list_vo")
    public ResponseResult<Object> findList(@RequestBody NewsAuthDto dto){
        return wmNewsService.findList(dto);
    }

    @GetMapping("/one_vo/{id}")
    public ResponseResult<Object> findWmNewsVo(@PathVariable("id") Integer id){
        return wmNewsService.findWmNewsVo(id);
    }

    @PostMapping("/auth_pass")
    public ResponseResult<Object> authPass(@RequestBody NewsAuthDto dto){
        return wmNewsService.updateStatus(WemediaConstants.WM_NEWS_AUTH_PASS, dto);
    }

    @PostMapping("/auth_fail")
    public ResponseResult<Object> authFail(@RequestBody NewsAuthDto dto){
        return wmNewsService.updateStatus(WemediaConstants.WM_NEWS_AUTH_FAIL, dto);
    }

}