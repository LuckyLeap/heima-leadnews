package com.heima.model.article.dtos;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Data
public class ArticleHomeDto {
    @Schema(description = "最大时间")
    Date maxBehotTime;
    @Schema(description = "最小时间")
    Date minBehotTime;
    @Schema(description = "文章类型 1为自动加载，2为上拉加载")
    Integer size;
    @Schema(description = "频道id")
    String tag;
}