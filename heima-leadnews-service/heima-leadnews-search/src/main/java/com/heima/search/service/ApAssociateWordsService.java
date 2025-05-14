package com.heima.search.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;

/**
 * 联想词表 服务类
 */
public interface ApAssociateWordsService {

    /**
     * 联想词
     * @param userSearchDto dto
     */
    ResponseResult<Object> findAssociate(UserSearchDto userSearchDto);

}