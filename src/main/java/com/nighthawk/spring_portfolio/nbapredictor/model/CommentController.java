package com.nighthawk.spring_portfolio.nbapredictor.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/")
    public Comment postComment(@RequestBody Comment comment) {
        System.out.println("Received comment: " + comment);
        return commentService.saveComment(comment);
    }

    @GetMapping("/{gameName}")
    public List<Comment> getCommentsByGameName(@PathVariable String gameName) {
        return commentService.getCommentsByGameName(gameName);
    }

    
}
