package com.ictak.blogproject;


import com.ictak.blogproject.models.BlogPosts;
import com.ictak.blogproject.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BlogController {



    @Autowired
    private PostRepository postRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/createPost")
    public ResponseEntity<Map<String, String>> createPosts(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody BlogPosts blogPosts) {

        System.out.println(token);

        // Check if the user is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            // Access user information
       //     String username = authentication.getName();
          //  List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();

            // Perform token-specific operations if needed
            // ...

            // Proceed with creating the post
            postRepository.save(blogPosts);

            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            // User is not authenticated (token validation failed)
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Token validation failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


//    @CrossOrigin(origins = "http://localhost:3000" )
//    @PostMapping("/createPost")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<Map<String, String>> createPosts(
//            @RequestHeader(name = "Authorization") String token,
//            @RequestBody BlogPosts blogPosts) {
//
//        // Check if the user is authenticated
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication.isAuthenticated()) {
//            // Access user information
//            String username = authentication.getName();
//            List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
//
//            // Perform token-specific operations if needed
//            // ...
//
//            // Proceed with creating the post
//            postRepository.save(blogPosts);
//
//            Map<String, String> response = new HashMap<>();
//            response.put("status", "success");
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
//        } else {
//            // User is not authenticated (token validation failed)
//            Map<String, String> response = new HashMap<>();
//            response.put("status", "error");
//            response.put("message", "Token validation failed");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//        }
//    }

//    @CrossOrigin(origins = "http://localhost:3000" )
//    @PostMapping("/createPost")
//    public ResponseEntity<Map<String,String>> createPosts(@RequestHeader(name = "Authorization") String token, @RequestBody BlogPosts blogPosts){
//
//
//
//        postRepository.save(blogPosts);  // Insert
//
//        Map<String,String> response=new HashMap<>();
//        response.put("status","success");
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//
//
//    }


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
