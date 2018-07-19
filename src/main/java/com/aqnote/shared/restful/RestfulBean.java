package com.aqnote.shared.restful;

import java.util.Map;

import com.aqnote.shared.restful.result.Result;

/**
 * 类RestfulService.java的实现描述：restful风格的接口类
 * 
 * @author madding.lip Jan 10, 2014 4:15:37 PM
 */
public interface RestfulBean<T extends Object> {

    /** restful风格接口调用 */
    public Result<T> invoke(Map<String, String> requestMap);
}
