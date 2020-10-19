package com.company.designpattern.proxy.enforced;

/**
 * @author: yansu
 * @date: 2020/9/16
 */
public class Landlord implements RealestateService {
    private RealestateService service = null;
    @Override
    public void rent() {
        if (isProxy()) {
            System.out.println("house rented");
        } else {
            System.out.println("failed, broker only");
        }
    }

    @Override
    public RealestateService getProxy() {
        service = new Broker(this);
        return service;
    }

    private boolean isProxy() {
        return this.service != null;
    }
}
