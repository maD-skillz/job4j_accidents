package ru.job4j.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("from User where username like :name")
    User findByUsername(@Param("name") String username);

}
