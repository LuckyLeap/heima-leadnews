package com.heima.wemedia.feign;

import com.heima.apis.wemedia.IWemediaClient;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.wemedia.service.WmChannelService;
import com.heima.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WemediaClient implements IWemediaClient {

    private WmChannelService wmChannelService;
    private WmUserService wmUserService;
    @Autowired
    public void setWmChannelService(WmChannelService wmChannelService, WmUserService wmUserService) {
        this.wmChannelService = wmChannelService;
        this.wmUserService = wmUserService;
    }

    @GetMapping("/api/v1/user/findByName/{name}")
    @Override
    public WmUser findWmUserByName(@PathVariable("name") String name){
        return wmUserService.findWmUserByName(name);
    }

    @GetMapping("/api/v1/channel/list")
    @Override
    public ResponseResult<Object> getChannels() {
        return wmChannelService.findAll();
    }

}