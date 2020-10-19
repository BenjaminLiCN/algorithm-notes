package com.company.designpattern.proxy.enforced;

/**
 * @author: yansu
 * @date: 2020/9/16
 */
public interface RealestateService {
    void rent();
    RealestateService getProxy();

    static void main(String[] args) {
        //landlord refuses unless broker
        RealestateService landlord = new Landlord();
        //refused
        landlord.rent();
        //yes
        landlord.getProxy().rent();
    }
}
