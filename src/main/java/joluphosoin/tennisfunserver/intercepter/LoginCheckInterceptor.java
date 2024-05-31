package joluphosoin.tennisfunserver.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;


public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 세션에서 회원 정보 조회
        HttpSession session = request.getSession();

        String userId = (String) session.getAttribute("id");
        String businessId = (String) session.getAttribute("businessId");

        // 2. 회원 정보 체크
        if (userId == null && businessId == null) {
            response.sendRedirect("/auth/expired");
            return false;
        }


        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}