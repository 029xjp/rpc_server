package com.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class ProcessorHandler implements  Runnable {
    private Socket socket;
    private Map<String,Object> handlerMap;

    public ProcessorHandler(Socket socket, Map<String,Object> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    public void run() {
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            RpcRequest rpcRequest = (RpcRequest)inputStream.readObject();
            Object result = invoke(rpcRequest);
            //System.out.println(result);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);
            outputStream.flush();
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inputStream !=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(RpcRequest rpcRequest) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String methodName = rpcRequest.getMethodName();
        Object[] params = rpcRequest.getParams();
        Class<?>[] types = new Class[params.length];
        for(int i=0;i<params.length;i++){
            types[i] = params[i].getClass();
        }
        String serviceName = rpcRequest.getClassName();
        String version = rpcRequest.getVersion();
        if(version!=null&&!version.equals("")){
            serviceName=serviceName+"-"+version;
        }
        Object service = handlerMap.get(serviceName);
        Method method = service.getClass().getMethod(methodName,types);
        return method.invoke(service,params);
    }
}
