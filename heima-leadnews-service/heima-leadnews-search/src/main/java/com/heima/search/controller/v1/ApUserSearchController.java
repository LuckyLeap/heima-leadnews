package com.heima.search.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.HistorySearchDto;
import com.heima.search.service.ApUserSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * APP用户搜索信息表 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/history")
public class ApUserSearchController{

    private ApUserSearchService apUserSearchService;
    @Autowired
    public void setApUserSearchService(ApUserSearchService apUserSearchService) {
        this.apUserSearchService = apUserSearchService;
    }

    @PostMapping("/load")
    public ResponseResult<Object> findUserSearch() {
        return apUserSearchService.findUserSearch();
    }

    @PostMapping("/del")
    public ResponseResult<Object> delUserSearch(@RequestBody HistorySearchDto historySearchDto) {
        return apUserSearchService.delUserSearch(historySearchDto);
    }
}