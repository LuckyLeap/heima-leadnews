package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmLoginDto;
import com.heima.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final WmUserService wmUserService;
    @Autowired
    public LoginController(WmUserService wmUserService) {
        this.wmUserService = wmUserService;
    }

    @PostMapping("/in")
    public ResponseResult<Object> login(@RequestBody WmLoginDto dto){
        return wmUserService.login(dto);
    }

}