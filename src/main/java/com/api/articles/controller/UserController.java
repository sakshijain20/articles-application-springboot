package com.api.articles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.articles.model.Article;
import com.api.articles.service.UserService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin_portal")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	public List<Article> getAllArticlesbyUserId(@PathVariable String id){
		return userService.getAllArticlesByUserId(id);
	}
	
	@DeleteMapping("/{id}")
    public String deleteAllArticlesByUserId(@PathVariable String id) {
		return userService.deleteAllArticlesByUserId(id);
	}
	
	@DeleteMapping("/delete_user/{id}")
	public String deleteUser(@PathVariable String id) {
		userService.deleteAllArticlesByUserId(id);
		return userService.deleteUser(id);
	}
	

}
