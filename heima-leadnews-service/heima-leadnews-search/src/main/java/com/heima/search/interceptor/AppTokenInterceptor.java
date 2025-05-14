package com.heima.search.interceptor;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.thread.AppThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AppTokenInterceptor implements HandlerInterceptor {
    private static final String HEADER_USER_ID = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userIdStr = request.getHeader(HEADER_USER_ID);
        if (userIdStr != null && !userIdStr.isEmpty() && userIdStr.matches("\\d+")) {
            try {
                Integer userId = Integer.valueOf(userIdStr);
                ApUser apUser = new ApUser();
                apUser.setId(userId);
                AppThreadLocalUtils.setUser(apUser);
                log.info("appTokenFilter设置用户信息到threadlocal中，用户ID: {}", userId);
            } catch (NumberFormatException e) {
                log.warn("无效的用户ID格式：{}", userIdStr);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            log.info("清理threadlocal...");
            AppThreadLocalUtils.clear();
        } catch (Exception e) {
            log.error("清理threadlocal发生异常", e);
        }
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}