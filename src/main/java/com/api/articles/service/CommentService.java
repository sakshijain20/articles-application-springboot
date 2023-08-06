package com.api.articles.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.api.articles.model.Article;
import com.api.articles.model.Comment;
import com.api.articles.repository.CommentRepository;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository repository;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public Comment createComment(String commentBody, String articleId) {
		Comment comment = repository.insert(new Comment(commentBody, LocalDateTime.now(), LocalDateTime.now()));
		
		mongoTemplate.update(Article.class)
				.matching(Criteria.where("articleId").is(articleId))
				.apply(new Update().push("comments").value(comment))
				.first();
	    return comment;
	}

}
