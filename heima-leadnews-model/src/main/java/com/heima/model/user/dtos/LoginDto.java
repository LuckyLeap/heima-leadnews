package com.heima.model.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginDto {

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "密码")
    private String password;
}