package com.sidabw.jpadbmysql.jpa;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
 * CRUD refers Create, Read, Update, Delete
 * @author shaogz
 */
public interface UserRepository extends CrudRepository<User, Integer> {

}