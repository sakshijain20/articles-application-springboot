import { formatNumber } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {
  loginSuccess = false;
  public loginCompForm!: FormGroup;
  responseObj = {}

  ngOnInit(): void {
    this.buildForm()
    localStorage.clear();
  }
  

  constructor(private router: Router, private form:FormBuilder,private httpClient: HttpClient, private cookieService: CookieService){}
  get f(){return this.loginCompForm.controls;}
  
  buildForm(){

    this.loginCompForm = this.form.group({
      username: new FormControl(''),
      password: new FormControl('')
    })
  }

  prepareResForLogin(form: any){
    var currentTime = new Date();
    this.responseObj = {
      username: form.value.username,
      password: form.value.password
    }
    //console.log('cred ', this.responseObj)
  }


  postApiCall(data: any){
    const headers = { 'content-type': 'application/json'}  
    const body=JSON.stringify(data);
    var url = 'http://localhost:9000/api/auth/signin'
    return this.httpClient.post(url, body,{'headers':headers})
  }
  
  login(form: any){
    var request = this.getValuesForLogin(form)
    var date = new Date();

    this.prepareResForLogin(form);


    this.postApiCall(this.responseObj).subscribe(data => {
      let res = JSON.parse(JSON.stringify(data))
      //console.log('data',res.status)
      if(res.status == '200'){
          this.loginSuccess = true;
          var credObj = {
            isLoginSuccessful: 'yes',
            userId: request.username,
            password: request.password,
            ttl: date.getTime()+(10 * 60 * 1000),
            jwt: res.jwt,
            role: setRole(res)
          }
          var accessToken = res.jwt;
          localStorage.setItem('cred', JSON.stringify(credObj));
          localStorage.setItem('accessToken', JSON.stringify(accessToken));
          //console.log(res.jwt);
          console.log(credObj);
          this.router.navigate(['articles']);
      }
      else{
        console.log("unsuccessful");
        this.loginSuccess = false;
        localStorage.setItem('isLoginSuccessful','no')
      }
    })
  }

  getValuesForLogin(form: any){
    var loginObj = {
      username: form.value.username,
      password: form.value.password
    }
    return loginObj
  }


  onBtnClick(){
    console.log(">>> router", this.router);
    this.router.navigate(['sign-up']);
  }

}
function setRole(res: any) {
  if(res.role0 == 'ROLE_ADMIN'){
    return 'ROLE_ADMIN';
  }
  else{
    return 'ROLE_USER';
  }
}




