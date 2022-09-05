package com.mf.config;

import com.mf.dto.WeblogDto;
import com.mf.service.WeblogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
@RequiredArgsConstructor
public class Tracking <T>{

    private final WeblogService<T> weblogService;

    private final LinkedList <T> queue = new LinkedList<>();
    private final ThreadPoolExecutor poolExecutor;

    public synchronized void put(T element){
        log.info("Sync [{}] to the tracking queue", element.toString());
        if (queue.size() >= 2 ) {
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
