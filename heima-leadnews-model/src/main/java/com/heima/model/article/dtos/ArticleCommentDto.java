package com.heima.model.article.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleCommentDto extends PageRequestDto {

    private String beginDate;
    private String endDate;
    private Integer wmUserId;

}