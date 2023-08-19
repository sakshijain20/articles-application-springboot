package com.api.articles.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document(collection = "articles")
public class Article {
	
	 @Id
	 private ObjectId id;
	 
	 @NotBlank
	 private String articleId;
	 
	 @NotBlank
	 @Size(max = 30)
	 private String articleTitle;
	 
	 @NotBlank
	 @Size(max = 250)
	 private String articleContent;
	 private List<String> genres;
	 
	 @JsonIgnore
	 private String username;

	@DocumentReference
	 private List<Comment> comments;
	 
	
	public Article() {}
	 
	 public Article(String articleId, String articleTitle, String articleContent, List<String> genres, String username) {
			super();
			this.articleId = articleId;
			this.articleTitle = articleTitle;
			this.articleContent = articleContent;
			this.genres = genres;
			this.username = username;
	}
	 

	public Article(Article articleBody, String username) {
		// TODO Auto-generated constructor stub
		this.id = articleBody.getId();
		this.articleId = articleBody.getArticleId();
		this.articleTitle = articleBody.getArticleTitle();
		this.articleContent = articleBody.getArticleContent();
		this.genres = articleBody.getGenres();
		this.username = username;
	}

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * @return the articleId
	 */
	public String getArticleId() {
		return articleId;
	}

	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	/**
	 * @return the articleTitle
	 */
	public String getArticleTitle() {
		return articleTitle;
	}

	/**
	 * @param articleTitle the articleTitle to set
	 */
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	/**
	 * @return the articleContent
	 */
	public String getArticleContent() {
		return articleContent;
	}

	/**
	 * @param articleContent the articleContent to set
	 */
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	/**
	 * @return the genres
	 */
	public List<String> getGenres() {
		return genres;
	}

	/**
	 * @param genres the genres to set
	 */
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
	
	 /**
		 * @return the comments
		 */
	public List<Comment> getComments() {
			return comments;
	}

		/**
		 * @param comments the comments to set
		 */
	public void setComments(List<Comment> comments) {
			this.comments = comments;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
		 
}
