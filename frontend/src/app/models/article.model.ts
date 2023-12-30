import { Input } from "@angular/core";

export class Article {
    
  id?: any;
  articleId?: string;
  articleTitle?: string;
  articleContent?: string;
  genres?: string[] =[];
  username?: string;
  comments?: Comment[];

  constructor(
    id?: any,
    articleId?: string,
    articleTitle?: string,
    articleContent?: string,
    genres?: string[],
    username?: string,
    comments?: Comment[]
)
{
  this.id = id;
  this.articleId = articleId;
  this.articleTitle = articleTitle;
  this.articleContent = articleContent;
  this.genres = genres;
  this.username = username,
  this.comments = comments
}




}
