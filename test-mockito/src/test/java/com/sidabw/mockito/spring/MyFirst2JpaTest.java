package com.sidabw.mockito.spring;

import com.sidabw.mockito.entity.Live;
import com.sidabw.mockito.repository.LiveRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

// @SpringBootTest

// 单纯的测试项目里只有MyFirst1JpaTest 和 MyFirst2JpaTest时，mvn clean package会启动几次spring项目，结论：1次，哇哦~
@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MyFirst2JpaTest {

    @Autowired
    private LiveRepository liveRepository;

    @Test
    void contextLoads22222() throws InterruptedException {
        Live live = new Live();
        live.setRoomId(123);
        live.setUserId(123);
        live.setStatus(123);
        live.setRecordVideoId(123);
        live.setRecordVideoStatus(123);
        live.setTemplateType(123);
        live.setSourceNodeId(123);
        live.setSourceType(123);

        liveRepository.save(live);
        System.out.println(live.getId());

        TimeUnit.SECONDS.sleep(10);
        System.out.println("finished2222222");
        Optional<Live> byId1 = liveRepository.findById(live.getId());
        if (byId1.isPresent()) {
            System.out.println(byId1);
        } else {
            System.out.println("not present2222");
        }
    }

}
