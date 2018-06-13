package com.rpc;

import com.rpc.ISayHiService;
import com.rpc.annotation.RpcAnnotation;

@RpcAnnotation(value = ISayHiService.class)
public class SayHi2ServiceImpl implements ISayHiService {

    public String sayHello(String message) {
        return "Hello " + message + " for port 8081";
    }
}
