package com.example.User.Service;

import com.example.User.Model.User;
import com.example.User.Repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    Repo repo;

    UserService(Repo repo){
        this.repo = repo;
    }


    @Autowired
    BCryptPasswordEncoder encoder;

    public User getUserById(long id) {
    return repo.findById(id).orElseThrow(() -> new RuntimeException("USER NOT FOUND"));
    }

    public String createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return "DONE";
    }

}
