package com.rpc;

import com.rpc.zookeeper.IRegisterCenter;
import com.rpc.zookeeper.RegiterCenterImpl;

import java.io.IOException;

public class ServerDemo {
    public static void main(String[] args) throws IOException {
        ISayHiService iSayHiService = new SayHiServiceImpl();
        IRegisterCenter registerCenter = new RegiterCenterImpl();
        RpcServer server = new RpcServer(registerCenter,"127.0.0.1:8080");
        server.bind(iSayHiService);
        server.publisher();
        System.in.read();
    }
}
