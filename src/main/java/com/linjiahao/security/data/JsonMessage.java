package com.linjiahao.security.data;

import com.alibaba.fastjson.JSONObject;
import com.linjiahao.security.tools.YmlReader;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class JsonMessage {
    //指定页面该跳转的url
    private String url;
    //指定本次请求的状态
    private int status;
    //指定本次请求应弹出的消息
    private String message;
    //指定本次请求实际的json数据
    private JSONObject data;

    public void setException(Exception e) {
        String version = (String) YmlReader.load("project.version");
        setStatus(-1);
        if (version.equals("test")) {
            setMessage("服务错误");
        } else {
            setMessage(e.getMessage());
        }
        e.printStackTrace();
    }
}
