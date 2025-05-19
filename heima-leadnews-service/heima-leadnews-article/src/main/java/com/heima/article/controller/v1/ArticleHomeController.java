package com.heima.article.controller.v1;

import com.heima.article.service.ApArticleService;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.common.constants.ArticleConstants;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleHomeController {

    private final ApArticleService apArticleService;
    @Autowired
    public ArticleHomeController(ApArticleService apArticleService) {
        this.apArticleService = apArticleService;
    }

    @PostMapping("/load")
    public ResponseResult<Object> load(@RequestBody ArticleHomeDto dto) {
        return apArticleService.loadArticleList(dto, ArticleConstants.LOADTYPE_LOAD_MORE, true);
    }

    @PostMapping("/loadmore")
    public ResponseResult<Object> loadMore(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(ArticleConstants.LOADTYPE_LOAD_MORE, dto);
    }

    @PostMapping("/loadnew")
    public ResponseResult<Object> loadNew(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(ArticleConstants.LOADTYPE_LOAD_NEW, dto);
    }
}