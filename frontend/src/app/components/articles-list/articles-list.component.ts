import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Article } from 'src/app/models/article.model';
import { ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-articles-list',
  templateUrl: './articles-list.component.html',
  styleUrls: ['./articles-list.component.css']
})
export class ArticlesListComponent implements OnInit {

  articles?: Article[];
  currentArticle?: Article;
  currentIndex = -1;

  constructor(private articleService : ArticleService, private router: Router) { }

  ngOnInit(): void {
    this.retrieveArticles();
  }
  
  retrieveArticles(): void {
    this.articleService.getAll()
      .subscribe(
        data => {
          this.articles = data;
          //console.log(data);
        },
        error => {
          console.log(error);
          alert("Error: " +error);
        });
  }

  refreshList(): void {
    this.retrieveArticles();
    this.currentArticle = undefined;
    this.currentIndex = -1;
  }
  
}
