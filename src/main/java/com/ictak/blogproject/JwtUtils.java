package com.ictak.blogproject;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {

    private static final String SECRET_KEY = "ictak1234567890ictakacademyqshvasjkgkjagaskjgaskjkjkjkjgkjagakjs";

    public static SecretKey getHs512SecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


}
