package com.api.articles.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.articles.model.Comment;
import com.api.articles.service.CommentService;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
	
	@Autowired
	private CommentService service;
	
	@PostMapping()
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Comment> createComment(@RequestBody Map<String, String> payload) {

        return new ResponseEntity<Comment>(service.createComment(payload.get("commentBody"), payload.get("articleId")), HttpStatus.OK);
    }

}
