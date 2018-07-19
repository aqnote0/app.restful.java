
package com.aqnote.demo.restful;

import java.util.HashMap;
import java.util.Map;

/**
 * 类MyServiceImpl.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip Jan 13, 2016 11:24:38 AM
 */
public class MyServiceImpl implements MyService {

    @Override
    public Map<String, String> getServerInfo() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("timestamp", String.valueOf(System.currentTimeMillis()));
        result.put("wmi", "safsfasf");
        result.put("yy", "safsfasf");
        result.put("aa", "safsfasf");
        result.put("bb", "safsfasf");
        result.put("cc", "safsfasf");
        return result;
    }
}
