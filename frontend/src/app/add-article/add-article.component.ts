import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-article',
  templateUrl: './add-article.component.html',
  styleUrls: ['./add-article.component.css']
})
export class AddArticleComponent implements OnInit {

  isSubmitted = false;
  public articleFormGroup!: FormGroup;
  isNewArticle = true;
  responseObj = {}

  constructor(private httpClient: HttpClient,private router:Router,private route:ActivatedRoute,private form:FormBuilder) { }

  //articleFormGroup
  get f(){return this.articleFormGroup.controls;}

  ngOnInit(){
    this.buildForm();
  }

  buildForm(){
    this.articleFormGroup = this.form.group({
      articleId: new FormControl(''),
      articleTitle: new FormControl(''),
      articleContent: new FormControl('')
    })
  }

prepareResForNewArticle(form: any){
  this.responseObj = {
    articleId : form.value.articleId,
    articleTitle: form.value.articleTitle,
    articleContent: form.value.articleContent,

  }
}

postApiCall(data: any){

  const body=JSON.stringify(data);
  console.log(body)
  const url = 'http://localhost:9000/articles'
  var token = "Bearer "+ localStorage.getItem('accessToken').replace('\"',"");
  token = token.replace('\"',"");

  console.log(token);

  const headers = 
    new HttpHeaders(
      {
        'Content-Type': 'application/json',
        'X-Requested-With': 'XMLHttpRequest',
        'Authorization' : token,
      }
    )

  return this.httpClient.post(url, body,{'headers':headers})

}




saveArticleDetails(form: any){
  this.prepareResForNewArticle(form);
  console.log('Add article details :: ',this.responseObj);
  this.postApiCall(this.responseObj).subscribe(data => {
    let res = JSON.parse(JSON.stringify(data))
    console.log('data', res.status)
    if(res.status == '200'){
        console.log('success',res);  
    }
    this.isSubmitted = false;
  })
}

}
