package com.ictak.blogproject;


import com.ictak.blogproject.models.BlogPosts;
import com.ictak.blogproject.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BlogController {



    @Autowired
    private PostRepository postRepository;


    @CrossOrigin(origins = "http://localhost:3000" )
    @PostMapping("/createPost")
    public ResponseEntity<Map<String,String>> createPosts(@RequestBody BlogPosts blogPosts){

        postRepository.save(blogPosts);  // Insert

        Map<String,String> response=new HashMap<>();
        response.put("status","success");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);


    }


    @CrossOrigin(origins = "http://localhost:3000" )
    @PostMapping("/viewAllPosts")
    public ResponseEntity<List<Map<String,String>>> viewPosts() {

       List<Map<String,String>> data= postRepository.viewAllPosts();

       return ResponseEntity.ok(data);

    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/viewMyPost")
    public ResponseEntity<List<Map<String,String>>> viewMyPosts(@RequestBody Users user){

        System.out.println(user.getId().toString());
        List<Map<String,String>> data=postRepository.viewMyPosts(user.getId().toString());
        return ResponseEntity.ok(data);
    }



}
