package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService extends IService<WmMaterial> {

    /**
     * 图片上传
     * @param multipartFile 图片文件
     */
    ResponseResult<Object> uploadPicture(MultipartFile multipartFile);

    /**
     * 素材列表查询
     * @param dto 查询参数
     */
    ResponseResult<Object> findList( WmMaterialDto dto);

    /**
     * 素材删除
     * @param id 素材id
     */
    ResponseResult<Object> delById(Integer id);

    /**
     * 素材收藏
     * @param id 素材id
     */
    ResponseResult<Object> collect(Integer id);

    /**
     * 取消素材收藏
     * @param id 素材id
     */
    ResponseResult<Object> cancelCollect(Integer id);
}