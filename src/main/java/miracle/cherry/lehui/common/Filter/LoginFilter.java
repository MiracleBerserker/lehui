package miracle.cherry.lehui.common.Filter;


import miracle.cherry.lehui.common.service.UserService;
import miracle.cherry.lehui.common.tools.Result;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-16 14:31
 * @Modified:
 * @Description:
 */
public class LoginFilter implements Filter {


    @Resource
    UserService userService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("权限过滤器开启成功-------------------------------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println(request.getRequestURI());
       if(!request.getRequestURI().startsWith("/system/")){
           filterChain.doFilter(servletRequest,servletResponse);
       }else {
           if(request.getSession().getAttribute("user") != null){
               filterChain.doFilter(servletRequest,servletResponse);
           }else {
               PrintWriter printWriter = response.getWriter();
               printWriter.write(new Result(Result.FAIL,null,"nologin").toJson());
               printWriter.flush();
               printWriter.close();
           }

       }

    }

    @Override
    public void destroy() {
        System.out.println("权限过滤器销毁成功-------------------------------");
    }
}
