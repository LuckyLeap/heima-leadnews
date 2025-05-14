package com.heima.wemedia.interceptor;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.heima.model.wemedia.pojos.WmUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.heima.utils.thread.WmThreadLocalUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class WmTokenInterceptor implements HandlerInterceptor {
    private static final String HEADER_USER_ID = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 得到header中的信息
        String userId = request.getHeader(HEADER_USER_ID);
        if (userId != null && !userId.trim().isEmpty()) {
            try {
                int userIdInt = Integer.parseInt(userId);
                WmUser wmUser = new WmUser();
                wmUser.setId(userIdInt);
                WmThreadLocalUtils.setUser(wmUser);
                log.info("wmTokenFilter设置用户信息到threadlocal中，用户ID：{}", userIdInt);
            } catch (NumberFormatException e) {
                log.warn("无效的用户ID格式：{}", userId);
                // 可根据业务需求决定是否中断请求
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("清理threadlocal...");
        WmThreadLocalUtils.clear();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}