package com.ictak.blogproject;

import com.ictak.blogproject.models.BlogPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface PostRepository extends JpaRepository<BlogPosts, Long> {


    @Query(value = "SELECT  bp.`message`,u.name, u.email,u.phone FROM `blog_posts` bp JOIN users u ON u.id=bp.user_id ",nativeQuery = true)
    List<Map<String,String>> viewAllPosts();



    @Query(value = "SELECT  bp.`message`,u.name, u.email,u.phone FROM `blog_posts` bp JOIN users u ON u.id=bp.user_id WHERE u.id=?1",nativeQuery = true)
    List<Map<String,String>> viewMyPosts(String id);






}
