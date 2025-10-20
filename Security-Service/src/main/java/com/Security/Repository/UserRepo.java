package com.Security.Repository;

import com.Security.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User,Long> {
    User findByUserName(String username);
}
