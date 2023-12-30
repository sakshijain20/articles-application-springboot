import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Article } from '../models/article.model';

const baseUrl = 'http://localhost:9000/articles';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Article[]> {
    return this.http.get<Article[]>(baseUrl);
  }

  get(articleId: string): Observable<Article> {
    //console.log(articleId);
    const articleDetailUrl = `${baseUrl}/${articleId}`;
    //console.log(localStorage.cred);
    var token = "Bearer "+ localStorage.getItem('accessToken').replace('\"',"");
    token = token.replace('\"',"");
    console.log(token); 
    return this.http.get<Article>(articleDetailUrl,{
      headers:
          new HttpHeaders(
            {
              'Content-Type': 'application/json',
              'X-Requested-With': 'XMLHttpRequest',
               'Authorization' : token
            }
          )
    });
  }
  }
