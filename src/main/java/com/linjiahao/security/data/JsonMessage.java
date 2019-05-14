package com.linjiahao.security.data;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class JsonMessage {
    //指定页面该跳转的url
    private String url;
    //指定本次请求的状态
    private int status;
    //指定本次请求应弹出的消息
    private String message;
    //指定本次请求实际的json数据
    private JSONObject data;
}
