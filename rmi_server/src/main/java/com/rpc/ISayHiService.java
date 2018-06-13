package com.rpc;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISayHiService{
    public String sayHello(String message);
}
