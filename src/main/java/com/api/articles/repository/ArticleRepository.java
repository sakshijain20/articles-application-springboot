package com.api.articles.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.api.articles.model.Article;

@Repository
public interface ArticleRepository extends MongoRepository<Article, ObjectId>{
	
	Optional<Article> findArticleByArticleId(String articleId);
}
