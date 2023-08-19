package com.api.articles.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.api.articles.model.Article;
import com.api.articles.model.User;
import com.api.articles.repository.ArticleRepository;
import com.api.articles.security.services.UserDetailsServiceImpl;

@Service
public class ArticleService {
	
	@Autowired
    private ArticleRepository repository;
	
	@Autowired
	private UserDetailsServiceImpl userService;
	
	@Autowired
	MongoTemplate mongoTemplate;

    public List<Article> findAllArticles() {
        return repository.findAll();
    }
    
    public Optional<Article> findArticleByArticleId(String articleId) {
        return repository.findArticleByArticleId(articleId);
    }
    
    public Article addArticle(Article article, String username){
    	Article _article = repository.insert(new Article(article.getArticleId(),article.getArticleTitle(),article.getArticleContent(),article.getGenres(), username));	
    	
    	mongoTemplate.update(User.class)
		.matching(Criteria.where("_id").is(userService.getCurrentUser().getId()))
		.apply(new Update().push("articles").value(_article))
		.first();
    	
    	return repository.save(_article);
    	
    }
    
    public Article updateArticle(Article article) {
    	
    	Article _article = repository.save(article);
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
