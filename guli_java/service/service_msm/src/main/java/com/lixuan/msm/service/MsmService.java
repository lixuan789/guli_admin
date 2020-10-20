package com.lixuan.msm.service;

import java.util.Map;

public interface MsmService {
    /**
     * 发送验证码到阿里云，阿里云发送短信到指定的手机号
     * @param phone
     * @param param
     * @return
     */
    boolean send(String phone, Map<String, Object> param);
}
