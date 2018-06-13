package com.rpc;

import com.rpc.annotation.RpcAnnotation;
import com.rpc.zookeeper.IRegisterCenter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServer {
    private IRegisterCenter registerCenter;
    private String serviceAddress;
    Map<String, Object> handlerMap = new HashMap<String, Object>();

    public RpcServer(IRegisterCenter registerCenter, String serviceAddress) {
        this.registerCenter = registerCenter;
        this.serviceAddress = serviceAddress;
    }

    public void bind(Object... services){
        for(Object service : services){
            RpcAnnotation rpcAnnotation = service.getClass().getAnnotation(RpcAnnotation.class);
            String serviceName = rpcAnnotation.value().getName();
            String version=rpcAnnotation.version();
            if(version!=null&&!version.equals("")){
                serviceName=serviceName+"-"+version;
            }
            handlerMap.put(serviceName,service);
        }
    }
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    public void publisher(){
        ServerSocket serverSocket = null;
        try {
            String[] addrs = serviceAddress.split(":");
            serverSocket = new ServerSocket(Integer.parseInt(addrs[1]));
            for(String interfaceName :handlerMap.keySet()){
                registerCenter.register(interfaceName,serviceAddress);
                System.out.println("注册服务成功:" + interfaceName + "->" + serviceAddress);
            }
            while(true){
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessorHandler(socket,handlerMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket !=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
