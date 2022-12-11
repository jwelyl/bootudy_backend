package com.ssafy.bootudy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper .Page;
import com.github.pagehelper.PageHelper;
import com.ssafy.bootudy.util.PageNavigationForPageHelper;

@Component
public class PagingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String page = request.getParameter("page");
        if(page==null) {
            page = "1";
        }
        PageHelper.startPage(Integer.parseInt(page), 10);
        
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
       Page<?> books = (Page<?>)modelAndView.getModel().get("books");
        
        PageNavigationForPageHelper helper = new PageNavigationForPageHelper(books, request.getContextPath()+"/book/list?page");
        modelAndView.addObject("books", helper);
    }
}