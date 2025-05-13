package com.heima.search.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.search.pojos.ApAssociateWords;

/**
 * 联想词表 服务类
 */
public interface ApAssociateWordsService extends IService<ApAssociateWords> {
    /**
     * 联想词
     * @param userSearchDto dto
     */
    ResponseResult<Object> findAssociate(UserSearchDto userSearchDto);
}