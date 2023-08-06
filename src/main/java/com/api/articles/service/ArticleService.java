package com.api.articles.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.articles.model.Article;
import com.api.articles.repository.ArticleRepository;

@Service
public class ArticleService {
	
	@Autowired
    private ArticleRepository repository;

    public List<Article> findAllMovies() {
        return repository.findAll();
    }
    
    public Optional<Article> findArticleByArticleId(String articleId) {
        return repository.findArticleByArticleId(articleId);
    }
    
    public Article addArticle(Article article){
    	return repository.save(article);
    	
    }

	public void deleteArticleByArticleId(String id) {
		Optional<Article> article = repository.findArticleByArticleId(id);
		repository.deleteById(article.get().getId());
	}

	public void deleteAll() {
		repository.deleteAll();
	}
	
}
