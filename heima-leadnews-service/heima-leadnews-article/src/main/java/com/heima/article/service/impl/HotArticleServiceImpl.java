package com.heima.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.apis.wemedia.IWemediaClient;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.HotArticleService;
import com.heima.common.redis.CacheService;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.vos.HotArticleVo;
import com.heima.model.common.constants.ArticleConstants;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmChannel;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class HotArticleServiceImpl implements HotArticleService {

    private ApArticleMapper apArticleMapper;
    private CacheService cacheService;
    private IWemediaClient wemediaClient;
    @Autowired
    public void setApArticleMapper(ApArticleMapper apArticleMapper, CacheService cacheService, IWemediaClient wemediaClient) {
        this.apArticleMapper = apArticleMapper;
        this.cacheService = cacheService;
        this.wemediaClient = wemediaClient;
    }

    /**
     * 计算热点文章
     */
    @Override
    public void computeHotArticle() {
        //1.查询前5天的文章数据
        Date dateParam = DateTime.now().minusDays(50).toDate();
        List<ApArticle> apArticleList = apArticleMapper.findArticleListByLast5days(dateParam);

        //2.计算文章的分值
        List<HotArticleVo> hotArticleVoList = computeHotArticle(apArticleList);

        //3.为每个频道缓存30条分值较高的文章
        cacheTagToRedis(hotArticleVoList);
    }

    /**
     * 为每个频道缓存30条分值较高的文章
     * @param hotArticleVoList 热点文章
     */
    private void cacheTagToRedis(List<HotArticleVo> hotArticleVoList) {
        // 分组预处理
        Map<Integer, List<HotArticleVo>> channelArticlesMap = hotArticleVoList.stream()
                .collect(Collectors.groupingBy(HotArticleVo::getChannelId));

        // 添加防御性检查
        ResponseResult<Object> responseResult = wemediaClient.getChannels();
        if (responseResult == null || responseResult.getCode() != HttpStatus.OK.value()) return;

        // 类型安全转换
        List<WmChannel> wmChannels = Optional.ofNullable(responseResult.getData())
                .map(data -> JSON.parseArray(JSON.toJSONString(data), WmChannel.class))
                .orElse(Collections.emptyList());

        // 处理频道数据
        wmChannels.stream()
                .filter(channel -> channel != null && channel.getId() != null)
                .forEach(channel -> {
                    List<HotArticleVo> articles = channelArticlesMap.getOrDefault(channel.getId(),
                            Collections.emptyList());
                    sortAndCache(articles, ArticleConstants.HOT_ARTICLE_FIRST_PAGE + channel.getId());
                });

        // 处理推荐数据（独立过滤原始数据）
        List<HotArticleVo> defaultArticles = hotArticleVoList.stream()
                .filter(x -> x.getChannelId() == null || ArticleConstants.DEFAULT_TAG.equals(String.valueOf(x.getChannelId())))
                .collect(Collectors.toList());
        sortAndCache(defaultArticles, ArticleConstants.HOT_ARTICLE_FIRST_PAGE + ArticleConstants.DEFAULT_TAG);
    }

    /**
     * 排序并且缓存数据
     * @param hotArticleVos 文章集合
     * key：频道id   value：30条分值较高的文章
     */
    private void sortAndCache(List<HotArticleVo> hotArticleVos, String key) {
        hotArticleVos = hotArticleVos.stream().sorted(Comparator.comparing(HotArticleVo::getScore).reversed()).collect(Collectors.toList());
        if (hotArticleVos.size() > 30) {
            hotArticleVos = hotArticleVos.subList(0, 30);
        }
        cacheService.set(key, JSON.toJSONString(hotArticleVos));
    }

    /**
     * 计算文章分值
     * @param apArticleList 文章集合
     */
    private List<HotArticleVo> computeHotArticle(List<ApArticle> apArticleList) {
        List<HotArticleVo> hotArticleVoList = new ArrayList<>();

        if(apArticleList != null && !apArticleList.isEmpty()){
            for (ApArticle apArticle : apArticleList) {
                HotArticleVo hot = new HotArticleVo();
                BeanUtils.copyProperties(apArticle,hot);
                Integer score = computeScore(apArticle);
                hot.setScore(score);
                hotArticleVoList.add(hot);
            }
        }
        return hotArticleVoList;
    }

    /**
     * 计算文章的具体分值
     * @param apArticle 文章对象
     */
    private Integer computeScore(ApArticle apArticle) {
        int scere = 0;
        //点赞数 3
        if(apArticle.getLikes() != null){
            scere += apArticle.getLikes() * ArticleConstants.HOT_ARTICLE_LIKE_WEIGHT;
        }
        //阅读数 1
        if(apArticle.getViews() != null){
            scere += apArticle.getViews();
        }
        //评论数 5
        if(apArticle.getComment() != null){
            scere += apArticle.getComment() * ArticleConstants.HOT_ARTICLE_COMMENT_WEIGHT;
        }
        //收藏数 8
        if(apArticle.getCollection() != null){
            scere += apArticle.getCollection() * ArticleConstants.HOT_ARTICLE_COLLECTION_WEIGHT;
        }
        return scere;
    }
}