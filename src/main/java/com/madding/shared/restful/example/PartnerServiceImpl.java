package com.madding.shared.restful.example;

import com.madding.shared.restful.service.PartnerService;

/**
 * 类PartnerService.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip Nov 29, 2013 3:50:46 PM
 */
public class PartnerServiceImpl implements PartnerService {

    public String findSaltByPartnerId(String partnerId) {
        // TODO: 添加自己实现
        if ("yudao".equals(partnerId)) {
            return "hell01234";
        }
        return String.valueOf(System.currentTimeMillis());
    }

}
