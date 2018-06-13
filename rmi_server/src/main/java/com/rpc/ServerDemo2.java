package com.rpc;

import com.rpc.ISayHiService;
import com.rpc.RpcServer;
import com.rpc.SayHi2ServiceImpl;
import com.rpc.SayHiServiceImpl;
import com.rpc.zookeeper.IRegisterCenter;
import com.rpc.zookeeper.RegiterCenterImpl;

import java.io.IOException;

public class ServerDemo2 {
    public static void main(String[] args) throws IOException {
        ISayHiService iSayHiService = new SayHi2ServiceImpl();
        IRegisterCenter registerCenter = new RegiterCenterImpl();
        RpcServer server = new RpcServer(registerCenter,"127.0.0.1:8081");
        server.bind(iSayHiService);
        server.publisher();
        System.in.read();
    }
}
