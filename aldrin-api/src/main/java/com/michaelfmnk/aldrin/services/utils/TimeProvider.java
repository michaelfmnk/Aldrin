package com.michaelfmnk.aldrin.services.utils;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TimeProvider {
    private TimeProvider() {}
    public Date now(){
        return new Date();
    }

}
