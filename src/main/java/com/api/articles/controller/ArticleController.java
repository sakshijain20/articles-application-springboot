package com.api.articles.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.articles.model.Article;
import com.api.articles.payload.response.MessageResponse;
import com.api.articles.repository.ArticleRepository;
import com.api.articles.security.services.UserDetailsImpl;
import com.api.articles.security.services.UserDetailsServiceImpl;
import com.api.articles.service.ArticleService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
	
	@Autowired
	private ArticleService service;
	
	@Autowired
	private ArticleRepository repo;
	
	@Autowired
	private UserDetailsServiceImpl userService;
	
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Article>> getArticles() {
        return new ResponseEntity<List<Article>>(service.findAllArticles(), HttpStatus.OK);
    }

    @GetMapping("/{articleId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Optional<Article>> getSingleArticle(@PathVariable String articleId){
    	
    	Optional<Article> articleData = service.findArticleByArticleId(articleId);

   	  if (articleData.isPresent()) {
   		return new ResponseEntity<Optional<Article>>(articleData, HttpStatus.OK);
   	  } 
   	  else {
   	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   	  }
        
    }
    
    @PostMapping
    @PreAuthorize("hasRole('USER')" )
    public ResponseEntity<?> addArticle(@Valid @RequestBody Article article) {
    	
    	if(repo.existsByArticleId(article.getArticleId())){  
    		return ResponseEntity
    		          .badRequest()
    		          .body(new MessageResponse("Error: ArticleId already exist!"));
    	}
    	else {
	  	  try {
	  		   Article _article = service.addArticle(article,userService.getCurrentUserName());
	  		    return new ResponseEntity<>(_article, HttpStatus.CREATED);
	  		  } catch (Exception e) {
	  		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  		  }
    	}
      
    }

    @PutMapping("/{articleId}")
    @PreAuthorize("hasRole('USER')" )
    public ResponseEntity<Article> updateArticle(@PathVariable("articleId") String id,@Valid @RequestBody Article article)
    {
	  	Optional<Article> articleData = service.findArticleByArticleId(id);
	  	  
	  	  if (articleData.isPresent()) {
	  		  if(articleData.get().getUsername().equals(userService.getCurrentUserName())) {
		  	    Article _article = articleData.get();
		  	    _article.setArticleTitle(article.getArticleTitle());
		  	    _article.setArticleContent(article.getArticleContent());
		  	    _article.setGenres(article.getGenres());
		  	    
		  	    _article = service.updateArticle(_article);
		  	    
		  	    return new ResponseEntity<>(_article, HttpStatus.OK);
	  		  }
	  		  else {
	  			  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	  		  }
	  	  } 
	  	  else
	  	  {
	  	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	  	  }
    }

    @DeleteMapping("/{articleId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteArticle(@PathVariable("articleId") String id) {		
		Optional<Article> article = service.findArticleByArticleId(id);
		
		if( (userService.getCurrentUserName()).equals(article.get().getUsername()) ) 
		{
	  	  try {
	  		    service.deleteArticleByArticleId(id);
	  		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  		  } catch (Exception e) {
	  		    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	  		  }
		}
		else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')" )
    public ResponseEntity<HttpStatus> deleteAllArticles() {
      
  	  try {
  		    service.deleteAll();
  		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  		  } catch (Exception e) {
  		    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  		  }
    }

}
