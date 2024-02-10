package com.ictak.blogproject;

import com.ictak.blogproject.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {


    @Autowired
    private UserRepository userRepository;


    @CrossOrigin(origins = "http://localhost:3000" )
    @PostMapping("/signup")
    public ResponseEntity<Map<String,String>> createUser(@RequestBody Users user){

        userRepository.save(user);  // Insert

        Map<String,String> response=new HashMap<>();
        response.put("status","success");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);


    }



    @CrossOrigin(origins = "http://localhost:3000" )
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginCheckUser(@RequestBody Users user) {

        Users userData=userRepository.userlogin(user.getEmail(),user.getPassword());
        Map<String,String> response=new HashMap<>();


        if(userData!=null){
            response.put("status","success");
            response.put("userId",userData.getId().toString());
            return ResponseEntity.ok(response);
        }
        else{
            response.put("status","error");
            return ResponseEntity.ok(response);
        }

    }
}
