package com.api.articles.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.articles.model.Article;
import com.api.articles.model.Comment;
import com.api.articles.model.User;
import com.api.articles.repository.ArticleRepository;
import com.api.articles.repository.CommentRepository;
import com.api.articles.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ArticleRepository articleRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	
	 public List<User> getAllUsers() {
	        return userRepository.findAll();
	    }
	 
	     
	 public List<Article> getAllArticlesByUserId(String userId) {
		 
		Optional<User> user = userRepository.findById(userId);
		List<Article> articles = user.get().getArticles();
		
		return articles;
		 
	 }
	
	 public String deleteAllArticlesByUserId(String userId) {
		 
			Optional<User> user = userRepository.findById(userId);
			List<Article> articles = user.get().getArticles();
			
			for(Article article: articles) {
				
				List<Comment> comments = article.getComments();
				for(Comment comment: comments) {
					Object id =comment.getId();
					System.out.println((ObjectId) id);
					commentRepo.deleteById((ObjectId) id);
					
				}
				
				articleRepo.deleteById(article.getId());
				
			}

	    	return "Deleted all articles for user: " + user.get().getUsername();
			 
		 }
	 
	 public String deleteUser(String id) {
		 
		 String user = userRepository.findById(id).get().getUsername();
		 userRepository.deleteById(id);		
		 
		 return "User: " +user + " deleted successfully";
	 }
	

}
