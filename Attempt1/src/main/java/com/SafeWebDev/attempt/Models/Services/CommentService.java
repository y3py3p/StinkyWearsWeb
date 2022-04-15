package com.SafeWebDev.attempt.Models.Services;

import com.SafeWebDev.attempt.Models.Respositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.SafeWebDev.attempt.Models.Entities.*;

import java.util.List;

@Service
@Component
public class CommentService {

    @Autowired
    CommentRepository repo;

    public void addComment(Comment comment){
        repo.save(comment);
    }

    public List<Comment> getAll(){
        return repo.findAll();
    }
}
