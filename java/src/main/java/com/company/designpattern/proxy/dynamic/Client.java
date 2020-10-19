package com.company.designpattern.proxy.dynamic;

import com.company.designpattern.proxy.basic.RealestateService;

/**
 * @author: yansu
 * @date: 2020/9/16
 */
public class Client implements RealestateService {
    @Override
    public void rent() {
        System.out.println("house rented");
    }
}
