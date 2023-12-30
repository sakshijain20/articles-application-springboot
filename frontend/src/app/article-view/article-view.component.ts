import { Component, Input, OnInit } from '@angular/core';
import { Article } from '../models/article.model';
import { ActivatedRoute } from '@angular/router';
import { ArticleService } from '../services/article.service';

@Component({
  selector: 'app-article-view',
  templateUrl: './article-view.component.html',
  styleUrls: ['./article-view.component.css']
})


export class ArticleViewComponent implements OnInit {
  

  article: Article = new Article();
  genres: string[] = [];
  comments: Comment[];

  constructor(private _activatedRoute: ActivatedRoute,
              private _articleService: ArticleService) { }

  ngOnInit() {

    this._activatedRoute.paramMap.subscribe(
      () => {
        this.getArticleInfo();
      }
    )
  }

  getArticleInfo(){
    //console.log(localStorage.getItem('cred'));
    //console.log(this._activatedRoute.snapshot.paramMap.get('id'));
    const articleId: string = this._activatedRoute.snapshot.paramMap.get('id');
    this._articleService.get(articleId).subscribe(
      data => {
        console.log(data);
        this.article = data;
        this.setGenresAndComments(this.article);
      }
    );
  }

  setGenresAndComments(article: Article){
    this.genres = article.genres;
    this.comments = article.comments
    console.log(article.comments);
    //console.log(this.genres); 
  }

}
