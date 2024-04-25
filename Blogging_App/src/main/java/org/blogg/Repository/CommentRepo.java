package org.blogg.Repository;

import org.blogg.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

}
