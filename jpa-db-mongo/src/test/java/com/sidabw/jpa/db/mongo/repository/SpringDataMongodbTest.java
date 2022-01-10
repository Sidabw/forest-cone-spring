/**
 * Copyright (C), 2018-2020, zenki.ai
 * FileName: SpringDataMongodbTest
 * Author:   feiyi
 * Date:     2020/8/21 3:44 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.jpa.db.mongo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author feiyi
 * @create 2020/8/21
 * @since 1.0.0
 */
@SpringBootTest
public class SpringDataMongodbTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    public void test() {
        repository.deleteAll();
        // save a couple of customers
        repository.save(new Customer("Alice", "Smith"));
        repository.save(new Customer("Bob", "Smith"));
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();
        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println(customer);
        }
    }

}
