package com.heima.model.wemedia.dtos;

import lombok.Data;

@Data
public class WmNewsEnableDto {
    private Integer id;

    // 状态 0 下架 1 上架
    private Short enable;
}