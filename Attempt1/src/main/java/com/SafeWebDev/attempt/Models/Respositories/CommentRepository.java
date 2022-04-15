package com.SafeWebDev.attempt.Models.Respositories;

import com.SafeWebDev.attempt.Models.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
