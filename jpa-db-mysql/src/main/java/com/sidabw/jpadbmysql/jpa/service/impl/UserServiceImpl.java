package com.sidabw.jpadbmysql.jpa.service.impl;

import com.sidabw.jpadbmysql.jpa.User;
import com.sidabw.jpadbmysql.jpa.UserRepository;
import com.sidabw.jpadbmysql.jpa.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author shaogz
 */
@Service
public class UserServiceImpl implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void queryAndUpdate(int random) throws InterruptedException {
        //两个请求同时进来，是不是第二个请求的find操作就直接阻塞了
        //答案，不是的，得是串行化的事务隔离级别才会在每个读的行上加锁
        //所以，第一次update的内容事务提交后，会被第二个update给覆盖。
        log.info("service under @Transactional come in, random:{}", random);
        Optional<User> byId = userRepository.findById(1);
        User user = byId.orElseThrow(RuntimeException::new);
        log.info("find by id finished, random:{}", random);
        TimeUnit.SECONDS.sleep(5);

        user.setName(user.getName() + "_" + random);
        userRepository.save(user);
        log.info("save by id finished, random:{}", random);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsertWitchOneException() throws Exception {
        //经过测试，数据不会入到数据库中
        userRepository.save(new User("sida_" + new Random().nextInt(1000), "aa"));
        log.info("save finished");
        TimeUnit.SECONDS.sleep(10);
        throw new RuntimeException("aa");
    }

}
