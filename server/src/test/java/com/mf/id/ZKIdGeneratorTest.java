package com.mf.id;

import com.common.base.IdTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ZKIdGeneratorTest {

    @Autowired
    private ZKIdGenerator zkIdGenerator;

    @Test
    public void getNextId(){
        for (int i = 0; i <10 ; i++) {
            long id = zkIdGenerator.getNextId(IdTypeEnum.COMPRESS);
            log.info("id: {} ", id);
        }

    }
}
