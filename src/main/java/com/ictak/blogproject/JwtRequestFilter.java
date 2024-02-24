package com.ictak.blogproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final SecretKey secretKey = JwtUtils.getHs512SecretKey();

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7).trim();  // Trim to remove leading/trailing whitespaces
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token.replace("Bearer ", ""));
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired");
        } catch (MalformedJwtException e) {
            System.out.println("Malformed or tampered token");
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature");
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
        }
        return false;
    }


    private Claims extractClaims(String jwt) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        String jwt = resolveToken(request);

        if (StringUtils.hasText(jwt)) {
            try {
                if (validateToken(jwt)) {
                    Claims claims = extractClaims(jwt);
                    String username = claims.getSubject();
                    String authorities = claims.get("authorities", String.class);

                    String formattedAuthority = "ROLE_ADMIN" ; // Add "ROLE_" prefix
            UserDetails userDetails = new User(username, "", Collections.singletonList(new SimpleGrantedAuthority(formattedAuthority)));

                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // Continue with the filter chain
                    chain.doFilter(request, response);
                } else {
                    // Token is not valid
                    handleInvalidTokenResponse(response);
                }
            } catch (Exception e) {
                // Token validation failed
                handleInvalidTokenResponse(response);
            }
        } else {
            // No token found
            chain.doFilter(request, response);
        }
    }

    private void handleInvalidTokenResponse(HttpServletResponse response) throws IOException {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        errorResponse.put("message", "Token validation failed");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }






}
