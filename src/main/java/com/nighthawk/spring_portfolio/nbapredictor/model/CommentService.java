package com.nighthawk.spring_portfolio.nbapredictor.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByGameName(String gameName) {
        return commentRepository.findByGameNameOrderByTimestampAsc(gameName);
    }
}
