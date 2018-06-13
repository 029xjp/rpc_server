package com.rpc.zookeeper;

public interface IRegisterCenter {
    void register(String serviceName, String serviceAddress);
}
