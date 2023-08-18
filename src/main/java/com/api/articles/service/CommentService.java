package com.api.articles.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.api.articles.model.Article;
import com.api.articles.model.Comment;
import com.api.articles.repository.CommentRepository;
import com.api.articles.security.services.UserDetailsImpl;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository repository;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public Comment createComment(String commentBody, String articleId) {
		
		Authentication auth = SecurityContextHolder. getContext(). getAuthentication();
    	UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
    	
		Comment comment = repository.insert(new Comment(userPrincipal.getUsername()
				,commentBody, LocalDateTime.now(), LocalDateTime.now()));
		
		mongoTemplate.update(Article.class)
				.matching(Criteria.where("articleId").is(articleId))
				.apply(new Update().push("comments").value(comment))
				.first();
	    return comment;
	}
	
	public Comment updateComment(Comment _comment, String articleId) {
			
			Comment comment = repository.save(_comment);
			return comment;
		}
		
	
	public void deleteAllCommentsbyArticleId(ObjectId id){
		 repository.deleteById((ObjectId) id);
	}

	public Optional<Comment> findCommentByCommentId(ObjectId id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

}
