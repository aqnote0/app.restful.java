package com.madding.shared.restful.result;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * JSONResult类描述：结果返回模型
 * 
 * @author madding.lip
 */
public class JSONResult extends Result<Object> {

    private static final long   serialVersionUID = -881008847714890154L;
    
    // 请求参数
    private Map<String, String> request;
    // 返回结果
    private Object              response;

    public JSONResult(){
        super();
    }

    public Map<String, String> getRequest() {
        return request;
    }

    public void setRequest(Map<String, String> request) {
        this.request = request;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public static void main(String[] args) {
        JSONResult result = new JSONResult();
        result.setSuccess(true);

        Map<String, String> map = new HashMap<String, String>();
        map.put("aaa", "aaa");
        map.put("bbb", "bbb");
        map.put("ccc", "ccc");
        result.setRequest(map);
    }
}
