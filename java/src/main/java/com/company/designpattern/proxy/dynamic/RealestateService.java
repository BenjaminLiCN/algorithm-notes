package com.company.designpattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author: yansu
 * @date: 2020/9/16
 */
public interface RealestateService {
    void rent();

    static void main(String[] args) {
        Client client = new Client();
        InvocationHandler handler = new Broker(client);
        ClassLoader cl = client.getClass().getClassLoader();
        RealestateService proxy = (RealestateService)Proxy.newProxyInstance(cl, new Class[]{RealestateService.class}, handler);
        proxy.rent();
        
    }
}
