package com.michaelfmnk.aldrindocs.config;

import com.michaelfmnk.aldrindocs.services.StorageUtils;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StorageInitialization implements ApplicationListener<ApplicationPreparedEvent> {
    private final StorageUtils storageUtils;

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
        storageUtils.init();
    }
}
