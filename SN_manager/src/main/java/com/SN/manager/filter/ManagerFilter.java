package com.SN.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    //表示过滤器在请求之前pre还是之后pose执行
    @Override
    public String filterType() {
        return "pre";
    }

    //多个过滤器执行的优先级，数字越小，优先级越大
    @Override
    public int filterOrder() {
        return 0;
    }

    //表示当前过滤器是否开启
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //过滤器内执行的操作 return任何object的值都表示继续执行
    //setsendzullResponse(false)表示不再继续执行
    @Override
    public Object run() throws ZuulException {
        //获得上下文信息
        RequestContext currentContext = RequestContext.getCurrentContext();
        //得到域对象
        HttpServletRequest request = currentContext.getRequest();


        //向网关发送请求时，首先会发送一个默认的额OPTIONS请求，这个请求是没有头信息的，第二个请求才是真正的请求
        //所以这里判断如果是默认请求的话就直接放行
        if(request.getMethod().equals("OPTIONS")){
            return null;
        }

        //如果当前发送的是登录请求也直接放行
        if(request.getRequestURL().indexOf("logn")>0){
            return null;
        }
        //得到请求头
        String header = request.getHeader("Authorization");
        if(header!=null && !"".equals(header)){
            if(header.startsWith("Bearer ")){
                String token = header.substring(7);
                try{
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if(roles.equals("admin")){
                        currentContext.addZuulRequestHeader("Authorizationed",header);
                        return null;
                    }
                    throw new RuntimeException("权限不足");
                }catch (Exception e){
                    currentContext.setSendZuulResponse(false);//终止运行
                }
            }
        }
        currentContext.setSendZuulResponse(false);//终止运行
        currentContext.setResponseStatusCode(403);
        currentContext.setResponseBody("权限不足");
        currentContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;

    }
}
