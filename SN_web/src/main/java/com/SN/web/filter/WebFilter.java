package com.SN.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //得到request上下文
        RequestContext context = RequestContext.getCurrentContext();
        //通过上下文得到request域
        HttpServletRequest request = context.getRequest();
        //得到头信息
        String header = request.getHeader("Authorization");
        //判断是否有头信息
        if(header!=null && !"".equals(header)){
            //把头信息继续往下传,这里有个小坑:addZuulRequestHeader的第一个参数如果用Authorization，在目标服务中将获取不到这个头，所以要换个名字
            context.addZuulRequestHeader("Authorizationed",header);
        }

        return null;
    }
}
