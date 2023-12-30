import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  signUpSuccess=false;
  public signUpCompForm!: FormGroup;
  responseObj = {}

  ngOnInit(): void {
    this.buildForm()
    localStorage.clear();
  }
  
  constructor(private router: Router, private form:FormBuilder, private httpClient: HttpClient) { }
  get f(){return this.signUpCompForm.controls;}

  buildForm(){

    this.signUpCompForm = this.form.group({
      username: new FormControl(''),
      email: new FormControl(''),
      //roles: new FormControl([]),
      password: new FormControl('')
    })
  }

  
  prepareResForLogin(form: any){
    var currentTime = new Date();
    this.responseObj = {
      username: form.value.username,
      email: form.value.email,
      //roles: form.value.roles,
      password: form.value.password
    }
    console.log('cred ', this.responseObj)
  }


  postApiCall(data: any){
    const headers = { 'content-type': 'application/json'}  
    const body=JSON.stringify(data);
    var url = 'http://localhost:9000/api/auth/signup'
    return this.httpClient.post(url, body,{'headers':headers})
  }
  
  signUp(form: any){
    var request = this.getValuesForSignUp(form)
    this.prepareResForLogin(form);
    //logic to check credentials on db

    this.postApiCall(this.responseObj).subscribe(data => {
      let res = JSON.parse(JSON.stringify(data))
      console.log('data',res.status)
      if(res.status == '200'){
          this.signUpSuccess = true;
          alert("User: \""+res.username+ "\" signed in successfuly!!");
          this.router.navigate(['sign-in']);
      }
      else{
        console.log("unsuccessful");
        this.signUpSuccess = false;
      }
    })
  }

  getValuesForSignUp(form: any){
    var signUpObj = {
      username: form.value.username,
      email: form.value.email,
      //roles: form.value.roles,
      password: form.value.password
    }
    return signUpObj
  }


}
