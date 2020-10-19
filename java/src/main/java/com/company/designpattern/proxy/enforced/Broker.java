package com.company.designpattern.proxy.enforced;

/**
 * @author: yansu
 * @date: 2020/9/16
 */
public class Broker implements RealestateService {
    private RealestateService service;
    public Broker(RealestateService service) {
        this.service = service;
    }
    @Override
    public void rent() {
        System.out.println("this is broker engaging");
        this.service.rent();
        System.out.println("broker rented a house");
    }

    @Override
    public RealestateService getProxy() {
        return this;
    }
}
