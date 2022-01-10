/**
 * Copyright (C), 2018-2020, zenki.ai
 * FileName: Demo1
 * Author:   feiyi
 * Date:     2020/8/21 5:37 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.jpa.db.mongo.template;

import com.mongodb.client.MongoClients;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author feiyi
 * @create 2020/8/21
 * @since 1.0.0
 */
public class Demo1 {

    private static final Log log = LogFactory.getLog(Demo1.class);


    @Test
    public void test1(){
        MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(), "test"));
        Person p = new Person("Joe", 34);

        // Insert is used to initially store the object into the database.
        mongoOps.insert(p);
        log.info("Insert: " + p);

        // Find
        p = mongoOps.findById(p.getId(), Person.class);
        log.info("Found: " + p);

        // Update
        mongoOps.updateFirst(query(where("name").is("Joe")), update("age", 35), Person.class);
        p = mongoOps.findOne(query(where("name").is("Joe")), Person.class);
        log.info("Updated: " + p);

        // Delete
//        mongoOps.remove(p);

        // Check that deletion worked
        List<Person> people =  mongoOps.findAll(Person.class);
        log.info("Number of people = : " + people.size());

    }
}
