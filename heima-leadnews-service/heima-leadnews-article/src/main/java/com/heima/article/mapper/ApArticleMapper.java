package com.heima.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.article.dtos.ArticleCommentDto;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.vos.ArticleCommnetVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface ApArticleMapper extends BaseMapper<ApArticle> {

    // 加载文章列表
    List<ApArticle> loadArticleList(@Param("dto") ArticleHomeDto dto, @Param("type") Short type);

    // 查询文章列表
    List<ApArticle> findArticleListByLast5days(@Param("dayParam") Date dayParam);

    // 查询文章配置
    Map queryLikesAndConllections(@Param("wmUserId") Integer wmUserId, @Param("beginDate") Date beginDate, @Param("endDate")  Date endDate);

    // 查询文章评论
    List<ArticleCommnetVo> findNewsComments(@Param("dto") ArticleCommentDto dto);

    // 查询文章评论数量
    int findNewsCommentsCount(@Param("dto")  ArticleCommentDto dto);

}