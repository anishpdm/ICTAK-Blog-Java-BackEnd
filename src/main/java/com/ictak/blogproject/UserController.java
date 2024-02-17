package com.ictak.blogproject;

import com.ictak.blogproject.models.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;


    private final String secretKey = "ictacademy"; // Replace with a secure secret key

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody Users user) {
        userRepository.save(user);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginCheckUser(@RequestBody Users user) {
        Users userData = userRepository.userlogin(user.getEmail(), user.getPassword());
        Map<String, String> response = new HashMap<>();

        if (userData != null) {
            String token = Jwts.builder()
                    .setSubject(userData.getId().toString())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1 )) // 1 hours
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();

            response.put("status", "success");
            response.put("userId", userData.getId().toString());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }
}
