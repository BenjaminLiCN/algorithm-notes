package com.company.designpattern.proxy.basic;

import com.company.designpattern.proxy.enforced.Landlord;

/**
 * @author: yansu
 * @date: 2020/9/16
 */
public interface RealestateService {
    void rent();

    static void main(String[] args) {
        //regular proxy
        Client client = new Client();
        RealestateService proxy = new Broker(client);
        proxy.rent();
    }
}
