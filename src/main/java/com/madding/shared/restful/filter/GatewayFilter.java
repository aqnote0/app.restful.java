package com.madding.shared.restful.filter;

import static com.madding.shared.restful.log.LogFactory.gateway;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.madding.shared.restful.RestfulConstants;
import com.madding.shared.restful.RestfulResultCode;
import com.madding.shared.restful.result.JSONResult;
import com.madding.shared.restful.result.Result;
import com.madding.shared.restful.service.PartnerService;
import com.madding.shared.restful.service.RestfulBean;
import com.madding.shared.restful.spring.RestfulSpringAware;
import com.madding.shared.restful.util.MD5Util;
import com.madding.shared.restful.util.PageUtil;
import com.madding.shared.restful.util.SortUtil;

/**
 * 类GatewayFilter.java的实现描述：gateway Filter入口
 * 
 * @author madding.lip Dec 4, 2013 8:51:38 AM
 */
public class GatewayFilter implements Filter, RestfulConstants {

    private ApplicationContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        context = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain) throws IOException,
                                            ServletException {
        HttpServletRequest request = getWrapperRequest(servletRequest);
        HttpServletResponse response = getWrapperResponse(servletResponse);

        Map<String, String> requestMap = getReuqestMap(request);

        JSONResult jsonResult = new JSONResult();
        try {
            // 签名出错
            if (!validSign(request)) {
                jsonResult.setReason(RestfulResultCode.SIGN_IS_INVALID.getName());
                return;
            }

            // url过期
            if (isURLExpire(request)) {
                jsonResult.setReason(RestfulResultCode.URL_EXPIRE.getName());
                return;
            }

            String serviceName = requestMap.get(RESTFUL_SERVICE);
            RestfulBean<Object> service = RestfulSpringAware.getRestfulBean(serviceName);
            // 服务不存在
            if (service == null) {
                jsonResult.setReason(RestfulResultCode.SERVICE_NO_EXIST.getName());
                return;
            }

            Result<Object> serviceResponse = service.invoke(requestMap);

            if (!serviceResponse.isSuccess()) {
                jsonResult.setReason(serviceResponse.getReason());
                return;
            }

            jsonResult.setRequest(requestMap);
            jsonResult.setResponse(serviceResponse.getModel());
            jsonResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setReason(RestfulResultCode.SYSTEM_ERROR.getName());
        } finally {
            logReq(jsonResult, request);
            response.getWriter().println(jsonResult);
        }
    }

    @Override
    public void destroy() {
        context = null;
    }

    public void logReq(JSONResult jsonResult, HttpServletRequest request) {
        String req = getCompleteURL(request);
        String res = jsonResult.toString();
        String ip = PageUtil.getIpAddr(request);
        gateway.info("req:" + req + "; ip: " + ip + "; res:" + res);
    }

    private String getCompleteURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        if (request.getQueryString() != null) {
            requestURL.append("?").append(request.getQueryString());
        }

        Map<String, String> paramMap = getReuqestMap(request);
        String sortedURI = SortUtil.ascMapToURI(paramMap);
        if (StringUtils.isNotEmpty(sortedURI)) {
            requestURL.append("?").append(sortedURI);
        }

        return requestURL.toString();
    }

    // TODO: 目前啊约定签名采用md5，可考虑动态性
    public boolean validSign(HttpServletRequest request) {
        Map<String, String> paramMap = getReuqestMap(request);
        String sign = paramMap.get(SIGN);
        if (StringUtils.isBlank(sign)) {
            return false;
        }
        String partner = paramMap.get(PARTNER);
        if (StringUtils.isBlank(partner)) {
            return false;
        }
        PartnerService partnerService = (PartnerService) context.getBean("partnerService");
        String salt = partnerService.findSaltByPartnerId(partner);
        if (StringUtils.isBlank(salt)) {
            return false;
        }
        Map<String, String> filterMap = filterMap(paramMap);
        String sortedURI = SortUtil.ascMapToURI(filterMap);
        String curSign = MD5Util.md5(sortedURI + salt);
        return StringUtils.equals(sign, curSign);
    }

    private Map<String, String> filterMap(final Map<String, String> paramMap) {
        Map<String, String> filterMap = new HashMap<String, String>();
        filterMap.putAll(paramMap);
        filterMap.remove(_INPUT_CHARSET);
        filterMap.remove(SIGN);
        return filterMap;
    }

    // 可扩展，建议改成url一次有效
    public boolean isURLExpire(HttpServletRequest request) {
        String timestamp = request.getParameter(TIMESTAMP);
        if (StringUtils.isBlank(timestamp)) {
            return true;
        }

        long ts = 0;
        try {
            ts = Long.parseLong(timestamp);
        } catch (Exception e) {
            gateway.error("format ts error.");
            return true;
        }
        return Math.abs(System.currentTimeMillis() - ts) > URL_EXPIRE_TIME;
    }

    @SuppressWarnings("rawtypes")
    public Map<String, String> getReuqestMap(HttpServletRequest request) {
        Map<String, String> requestMap = new ConcurrentHashMap<String, String>();
        Enumeration keyEnum = request.getParameterNames();
        while (keyEnum.hasMoreElements()) {
            String key = (String) keyEnum.nextElement();
            String value = request.getParameter(key);
            requestMap.put(key, value);
        }
        return requestMap;
    }

    protected HttpServletRequest getWrapperRequest(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        return request;
    }

    protected HttpServletResponse getWrapperResponse(ServletResponse servletResponse) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        return response;
    }

}
