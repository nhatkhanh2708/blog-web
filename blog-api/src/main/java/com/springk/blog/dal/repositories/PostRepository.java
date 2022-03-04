package com.springk.blog.dal.repositories;

import com.springk.blog.dal.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContaining(String title);
    @Query("SELECT DISTINCT p FROM Post p JOIN p.categories pc WHERE pc.id = :id")
    List<Post> findByCategoryId(Long id);
}
