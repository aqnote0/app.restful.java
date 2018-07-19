package com.aqnote.demo.restful;

import java.util.Map;

import com.aqnote.shared.restful.result.Result;
import com.aqnote.shared.restful.RestfulBean;
import com.aqnote.shared.restful.spring.RestfulSpringAware;

/**
 * URL: see {@code URLGenTest}
 * 
 * @author madding.lip
 */
public class FindCertByCertIdRestFul implements RestfulBean<Map<String, String>> {

    @Override
    public Result<Map<String, String>> invoke(Map<String, String> requestMap) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        MyService service = (MyService) RestfulSpringAware.getBean("myService");

        Map<String, String> map = service.getServerInfo();
        result.setSuccess(true);
        result.setModel(map);
        return result;
    }
}
