package com.sidabw.jpadbmysql.jpa.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author shaogz
 */
public interface IUserService {


    void queryAndUpdate(int random) throws InterruptedException;

    void batchInsertWitchOneException() throws Exception;

}
