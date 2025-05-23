package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.article.dtos.ArticleCommentDto;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.dtos.ArticleInfoDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.mess.ArticleVisitStreamMess;
import com.heima.model.wemedia.dtos.StatisticsDto;

import java.util.Date;

public interface ApArticleService extends IService<ApArticle> {

    /**
     * 根据参数加载文章列表
     * @param loadType [1为加载更多  2为加载最新]
     */
    ResponseResult<Object> load(Short loadType, ArticleHomeDto dto);

    /**
     * 保存app端相关文章
     * @param dto 文章信息
     */
    ResponseResult<Object> saveArticle(ArticleDto dto);

    /**
     * 加载文章列表
     * @param dto 文章参数
     * @param type  1 加载更多   2 加载最新
     * @param firstPage  true  是首页  flase 非首页
     */
    ResponseResult<Object> loadArticleList(ArticleHomeDto dto, Short type, boolean firstPage);

    /**
     * 更新文章的分值  同时更新缓存中的热点文章数据
     * @param mess 热点文章数据
     */
    void updateScore(ArticleVisitStreamMess mess);

    /**
     * 加载文章详情 数据回显
     * @param dto 文章详情参数
     */
    ResponseResult<Object> loadArticleBehavior(ArticleInfoDto dto);

    /**
     * 图文统计统计
     */
    ResponseResult<Object> queryLikesAndConllections(Integer wmUserId, Date beginDate, Date endDate);

    /**
     * 分页查询 图文统计
     */
    PageResponseResult newPage(StatisticsDto dto);

    /**
     * 查询文章评论统计
     */
    PageResponseResult findNewsComments(ArticleCommentDto dto);

}