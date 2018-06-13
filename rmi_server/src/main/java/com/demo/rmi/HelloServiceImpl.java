package com.demo.rmi;

import com.demo.rmi.IHelloService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImpl extends UnicastRemoteObject implements IHelloService {

    protected HelloServiceImpl() throws RemoteException {
        super();
    }

    public String sayHello(String message) throws RemoteException{
        return "Hello," + message;
    }

}
