package com.rpc;

import com.rpc.annotation.RpcAnnotation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@RpcAnnotation(value = ISayHiService.class)
public class SayHiServiceImpl implements ISayHiService {

    public String sayHello(String message){
        return "Hello " + message + " for port 8080";
    }

}
