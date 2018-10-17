package com.yoyodine.helloworld;

import net.tretin.api.core.ApiException;

public interface HelloService {
    String getHelloMessage(String mood, String name) throws ApiException;
}
