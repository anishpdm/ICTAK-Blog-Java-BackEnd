package com.ictak.blogproject;

import com.ictak.blogproject.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Users, Long> {


    @Query(value = "SELECT * FROM users WHERE email=?1  and password=?2 ",nativeQuery = true)
    Users userlogin(String email,String password);


}
