
package com.aqnote.shared.restful.test;

import com.aqnote.shared.restful.RestfulConstants;
import org.junit.Test;

import com.aqnote.shared.restful.util.MD5Util;
import com.aqnote.shared.restful.util.SortUtil;

import junit.framework.TestCase;

/**
 * 类URLGenTest.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip Jan 13, 2016 1:45:53 PM
 */
public class URLGenTest extends TestCase implements RestfulConstants {

    @Test
    public void testSign() {
        StringBuilder uri = new StringBuilder();
        uri.append("timestamp=").append(System.currentTimeMillis()).append("&");
        uri.append("partner=").append("yudao").append("&");
        uri.append("service=").append("com.madding.restfull.findcertbycertid").append("&");
        uri.append("param1=").append(System.currentTimeMillis()).append("&");
        uri.append("param2=").append("asdfsaofhofasfsfd").append("&");

        String sortedURI = SortUtil.ascURI(uri.toString());
        String salt = "hell01234";
        String curSign = MD5Util.md5(sortedURI + salt);

        StringBuilder finalURL = new StringBuilder("http://localhost:8080/java.restful/gateway.do?");
        finalURL.append(uri);
        uri.append("_input_charset=").append("UTF-8").append("&");
        finalURL.append(SIGN).append("=").append(curSign);
        System.out.println(finalURL.toString());
        assertTrue(true);
    }
}
