package com.atguigu.yygh.order.service;

import java.util.Map;

public interface WeixinService {
    Map<String, Object> createNative(Long orderId);

    Map<String, String> queryPayStatus(long orderId);

    /***
     * 退款
     * @param orderId
     * @return
     */
    Boolean refund(Long orderId);

}
