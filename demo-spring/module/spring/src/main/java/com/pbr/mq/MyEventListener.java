package com.pbr.mq;

import com.pbr.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Lawrence Peng
 */
@Component
@Slf4j
public class MyEventListener {

    @EventListener
    @Async
    public void processEvent(MyEvent event) {
        log.info(event.toString());

        CommonUtils.sleep(3);
    }

}
