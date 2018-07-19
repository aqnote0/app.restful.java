package com.aqnote.shared.restful.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 类SortUtil.java的实现描述：排序工具类，提供给多key的排序
 * 
 * @author madding.lip Dec 27, 2012 3:34:23 PM
 */
public class SortUtil {

    public static String ascMapToURI(final Map<String, String> kvMap) {
        if (kvMap == null) return "";
        Map<String, String> tkvMap = kvMap;
        List<String> keyList = new ArrayList<String>(tkvMap.keySet());

        Collections.sort(keyList, new KVComparator());
        String result = "";
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            if (StringUtils.isBlank(key)) continue;
            String value = kvMap.get(key);
            if (StringUtils.isBlank(value)) continue;
            result += key + "=" + value + "&";
        }
        return result.length() < 1 ? "" : result.substring(0, result.length() - 1);
    }

    public static String ascURI(final String uri) {
        if (StringUtils.isEmpty(uri)) return "";

        String[] kvArray = uri.split("&");
        Map<String, String> kvMap = new HashMap<String, String>();
        for (int i = 0; i < kvArray.length; i++) {
            String[] kv = kvArray[i].split("=");
            if (kv == null) continue;
            if (!StringUtils.isEmpty(kv[0])) {
                if (kv.length == 1) kvMap.put(kv[0], "");
                if (kv.length > 1) kvMap.put(kv[0], kv[1]);
            }
        }
        Map<String, String> tkvMap = kvMap;
        List<String> keyList = new ArrayList<String>(tkvMap.keySet());
        Collections.sort(keyList, new KVComparator());
        String result = "";
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            result += key + "=" + kvMap.get(key) + "&";
        }
        return result.substring(0, result.length() - 1);
    }

    static class KVComparator implements Comparator<String> {

        public int compare(String first, String second) {
            int firstLen = first.length();
            int secondLen = second.length();
            int minLen = firstLen > secondLen ? secondLen : firstLen;

            for (int i = 0; i < minLen; i++) {
                if (first.charAt(i) > second.charAt(i)) {
                    return 1;
                }

                if (first.charAt(i) < second.charAt(i)) {
                    return -1;
                }
            }

            if (firstLen == secondLen) {
                return 0;
            } else if (firstLen > secondLen) {
                return 1;
            } else {
                return -1;
            }
        }

    }
}
