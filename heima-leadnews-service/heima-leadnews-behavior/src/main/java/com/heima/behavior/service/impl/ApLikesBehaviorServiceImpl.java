package com.heima.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.behavior.service.ApLikesBehaviorService;
import com.heima.common.redis.CacheService;
import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.common.constants.BehaviorConstants;
import com.heima.model.common.constants.HotArticleConstants;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.mess.UpdateArticleMess;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.thread.AppThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class ApLikesBehaviorServiceImpl implements ApLikesBehaviorService {

    private CacheService cacheService;
    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    private RabbitTemplate rabbitTemplate;
    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public ResponseResult<Object> like(LikesBehaviorDto dto) {
        //1.检查参数
        if (dto == null || dto.getArticleId() == null || checkParam(dto)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.是否登录
        ApUser user = AppThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        UpdateArticleMess mess = new UpdateArticleMess();
        mess.setArticleId(dto.getArticleId());
        mess.setType(UpdateArticleMess.UpdateArticleType.LIKES);

        //3.点赞  保存数据
        if (dto.getOperation() == 0) {
            Object obj = cacheService.hGet(BehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
            if (obj != null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "已点赞");
            }
            // 保存当前key
            log.info("保存当前key:{} ,{}, {}", dto.getArticleId(), user.getId(), dto);
            cacheService.hPut(BehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString(), JSON.toJSONString(dto));
            mess.setAdd(1);
        } else {
            // 删除当前key
            log.info("删除当前key:{}, {}", dto.getArticleId(), user.getId());
            cacheService.hDelete(BehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId().toString(), user.getId().toString());
            mess.setAdd(-1);
        }

        //发送消息，数据聚合
        rabbitTemplate.convertAndSend(HotArticleConstants.HOT_ARTICLE_SCORE_QUEUE, JSON.toJSONString(mess));

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 检查参数
     */
    private boolean checkParam(LikesBehaviorDto dto) {
        return dto.getType() > 2 || dto.getType() < 0 || dto.getOperation() > 1 || dto.getOperation() < 0;
    }
}