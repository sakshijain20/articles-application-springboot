package com.api.articles.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.articles.model.Article;
import com.api.articles.service.ArticleService;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
	
	@Autowired
	private ArticleService service;


    @GetMapping
    public ResponseEntity<List<Article>> getArticles() {
        return new ResponseEntity<List<Article>>(service.findAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Optional<Article>> getSingleMovie(@PathVariable String articleId){
    	
    	Optional<Article> articleData = service.findArticleByArticleId(articleId);

   	  if (articleData.isPresent()) {
   		return new ResponseEntity<Optional<Article>>(articleData, HttpStatus.OK);
   	  } 
   	  else {
   	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   	  }
        
    }
    
    @PostMapping
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
  	  
  	  try {
  		   Article _article = service.addArticle(article);
  		    return new ResponseEntity<>(_article, HttpStatus.CREATED);
  		  } catch (Exception e) {
  		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  		  }
      
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<Article> updateTutorial(@PathVariable("articleId") String id, @RequestBody Article article) {
  	  Optional<Article> articleData = service.findArticleByArticleId(id);

  	  if (articleData.isPresent()) {
  	    Article _article = articleData.get();
  	    _article.setArticleId(article.getArticleId());
  	    _article.setArticleTitle(article.getArticleTitle());
  	    _article.setArticleContent(article.getArticleContent());
  	    _article.setGenres(article.getGenres());
  	    
  	    return new ResponseEntity<>(service.addArticle(_article), HttpStatus.OK);
  	  } else {
  	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  	  }
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("articleId") String id) {
      
  	  try {
  		    service.deleteArticleByArticleId(id);
  		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  		  } catch (Exception e) {
  		    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  		  }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllArticles() {
      
  	  try {
  		    service.deleteAll();
  		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  		  } catch (Exception e) {
  		    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  		  }
    }

}
