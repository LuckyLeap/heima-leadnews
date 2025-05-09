package com.heima.user.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;
import com.heima.user.service.ApUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
@Tag(name = "用户登录接口", description = "用户登录接口")
public class ApUserLoginController {

    private final ApUserService apUserService;
    @Autowired
    public ApUserLoginController(ApUserService apUserService) {
        this.apUserService = apUserService;
    }

    @PostMapping("/login_auth")
    @Operation(description = "用户登录")
    public ResponseResult<Object> login(@RequestBody LoginDto dto) {
        return apUserService.login(dto);
    }

}