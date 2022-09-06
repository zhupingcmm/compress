package com.mf.queue;

import com.mf.service.WeblogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class Tracking <T>{

    private final WeblogService<T> weblogService;
    private final LinkedList<T> queue = new LinkedList<>();
    @Qualifier("threadPoolExecutor")
    @Autowired
    private ThreadPoolExecutor poolExecutor;

    @Autowired
    @Qualifier("scheduledThreadPoolExecutor")
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    @PostConstruct
    public void init(){
        if (queue.isEmpty()) {
            scheduledThreadPoolExecutor.schedule(() -> weblogService.insertOneLog(queue.element()), 5, TimeUnit.SECONDS);
        }

    }

    public synchronized void put(T element){
        log.info("Sync [{}] to the tracking queue", element.toString());
        if (queue.size() >= 5 ) {
            log.info("tracking queue is full, need sync to db");
            for (int i = 0; i < queue.size(); i++) {
                poolExecutor.execute(() -> weblogService.insertOneLog(queue.poll()));
            }
        }
        queue.push(element);
    }



    public int getSize () {
        return queue.size();
    }

    public LinkedList<T> getQueue () {
        return queue;
    }
}