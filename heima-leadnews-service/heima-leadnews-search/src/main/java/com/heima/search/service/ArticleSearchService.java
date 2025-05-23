package com.heima.search.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;

import java.io.IOException;

public interface ArticleSearchService {

    /**
     * ES文章分页搜索
     */
    ResponseResult<Object> search(UserSearchDto userSearchDto) throws IOException;

}