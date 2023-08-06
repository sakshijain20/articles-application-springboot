package com.api.articles.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.api.articles.model.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, ObjectId>{

}
