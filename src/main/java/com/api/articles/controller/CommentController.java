package com.api.articles.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.articles.model.Comment;
import com.api.articles.security.services.UserDetailsImpl;
import com.api.articles.security.services.UserDetailsServiceImpl;
import com.api.articles.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
	
	@Autowired
	private CommentService service;
	
	@Autowired
	private UserDetailsServiceImpl userService;
	
	@PostMapping()
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Comment> createComment(@Valid @RequestBody Map<String, String> payload) {

        return new ResponseEntity<Comment>(service.createComment(payload.get("commentBody"), payload.get("articleId")), HttpStatus.OK);
    }
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Comment> updateComment(@Valid @RequestBody Map<String, String> payload, @PathVariable ObjectId id) {
	
		 Optional<Comment> commentData = service.findCommentByCommentId(id);
		 //System.out.println(commentData);
		
		if (commentData.isPresent())
				if((userService.getCurrentUserName()).equals(commentData.get().getUsername())) 
				{
					 Comment _comment = commentData.get();
					 
					 //System.out.println(_comment.getComment());
					 //System.out.println(_comment.getId());
					 _comment.setComment(payload.get("commentBody"));
					 //System.out.println(_comment.getComment());
					_comment.setUpdated(LocalDateTime.now());
					 
					 return new ResponseEntity<>(service.updateComment(_comment, payload.get("articleId")), HttpStatus.OK);
					}
				else {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	 }

}
