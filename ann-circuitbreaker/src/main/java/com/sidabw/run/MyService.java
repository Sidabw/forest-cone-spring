package com.sidabw.run;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    int i = 0;
    @CircuitBreaker(name = "backendA", fallbackMethod = "getAFallback")
    public String getA(){
        //前三次失败，后面一直成功
        if (i++ > 2) {
            return "success-getA-service";
        }
        throw new RuntimeException("xxxx");
    }

    public String getAFallback(Exception e){
        return "success-getA-service-fallback";
    }
}
