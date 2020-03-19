package com.vicyor.sso.interceptor;

import com.vicyor.sso.utils.HttpUtil;
import com.vicyor.sso.utils.SSOClientUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * 作者:姚克威
 * 时间:2020/3/18 16:36
 **/
public class SsoClientInterceptor implements HandlerInterceptor {

    /**
     * @param request
     * @param response
     * @param handler
     * @return true-> 不拦截 .  false 拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Boolean isLogin = (Boolean) session.getAttribute("isLogin");
        if (isLogin != null && isLogin) return true;

        //处理登录页回调请求
        String token = request.getParameter("token");
        if (!StringUtils.isEmpty(token)) {
            //拿到token去检查票据是否正确
            String httpURL = SSOClientUtil.SERVER_URL_PREFIX + "/verify";
            //验证参数
            HashMap<String, String> parms = new HashMap<>();
            parms.put("token",token);
            parms.put("app","taobao");
            String responseStr = HttpUtil.sendHttpRequest(httpURL, parms );
            if(responseStr.equals("OK")){
                session.setAttribute("isLogin",true);
            }
                return true;
        }
        //未登录去认证服务器查看是否登录
        // http://127.0.0.1:8000/server/checkLogin?redirectUrl=http://127.0.0.1:8001/taobao/...
        //redirectUrl为当前请求的地址
        SSOClientUtil.redirectToSSOURL(request, response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
