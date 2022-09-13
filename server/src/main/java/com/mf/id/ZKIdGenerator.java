package com.mf.id;

import com.common.base.IdTypeEnum;
import lombok.RequiredArgsConstructor;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class ZKIdGenerator {
    private static final String ID_PATH = "/id";


    private final ZkClient zkClient;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");

    @PostConstruct
    private void init(){
        if (!zkClient.exists(ID_PATH)) {
            zkClient.createPersistent(ID_PATH);
        }
    }

    public long getNextId(IdTypeEnum idTypeEnum) {
        LocalDateTime now = LocalDateTime.now();
        String dataTime = dateTimeFormatter.format(now);

        String value = zkClient.createEphemeralSequential(ID_PATH + "/", Thread.currentThread().getName());

        long id = Long.parseLong(value.substring(ID_PATH.length() + 1));

        if (id >= 1000) {
            id = id % 1000;
        }

        String seq = StringUtils.leftPad(String.valueOf(id), 3, "0");
        return Long.parseLong(idTypeEnum.getCode() + dataTime + seq);

    }

}
