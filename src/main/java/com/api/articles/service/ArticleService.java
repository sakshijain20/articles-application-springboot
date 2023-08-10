package com.api.articles.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.api.articles.model.Article;
import com.api.articles.model.User;
import com.api.articles.repository.ArticleRepository;
import com.api.articles.security.services.UserDetailsImpl;

@Service
public class ArticleService {
	
	@Autowired
    private ArticleRepository repository;
	
	@Autowired
	MongoTemplate mongoTemplate;

    public List<Article> findAllArticles() {
        return repository.findAll();
    }
    
    public Optional<Article> findArticleByArticleId(String articleId) {
        return repository.findArticleByArticleId(articleId);
    }
    
    public Article addArticle(Article article){
    	
    	 
    	Article _article = repository.insert(new Article(article.getArticleId(),article.getArticleTitle(),article.getArticleContent(),article.getGenres()));
    	Authentication auth = SecurityContextHolder. getContext(). getAuthentication();
    	UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
    	
    	mongoTemplate.update(User.class)
		.matching(Criteria.where("_id").is(userPrincipal.getId()))
		.apply(new Update().push("articles").value(_article))
		.first();
    	
    	return repository.save(_article);
    	
    }

	public void deleteArticleByArticleId(String id) {
		Optional<Article> article = repository.findArticleByArticleId(id);
		repository.deleteById(article.get().getId());
	}

	public void deleteAll() {
		repository.deleteAll();
	}
	
}
