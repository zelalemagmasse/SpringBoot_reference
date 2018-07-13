package com.zol.fsite.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.zol.fsite.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findUserClassByUserName(String userName);
    User findByUserName(String userName);
	User findByEmail(String email);
}
