package org.blogg.Repository;

import org.blogg.Model.Category;
import org.blogg.Model.Post;
import org.blogg.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer>{

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    
    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key")String title);
}
