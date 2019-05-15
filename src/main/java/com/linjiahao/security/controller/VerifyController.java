package com.linjiahao.security.controller;

import com.linjiahao.security.data.JsonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class VerifyController {
@Autowired
JsonMessage jsonMessage;

    @PostMapping("/gen_verify.json")
    public JsonMessage generateVerifyImage(HttpServletRequest request, HttpServletResponse response){
        try {

        }catch (Exception e){
            jsonMessage.setException(e);
        }
        return jsonMessage;
    }
}
