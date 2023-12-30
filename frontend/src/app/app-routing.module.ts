import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ArticlesListComponent } from './components/articles-list/articles-list.component';
import { SigninComponent } from './components/signin/signin.component';
import { HomeComponent } from './components/home/home.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { ArticleViewComponent } from './article-view/article-view.component';
import { AuthGuard } from './helper/auth.guard';
import { AddArticleComponent } from './add-article/add-article.component';


const routes: Routes = [
  { 
    path: 'articles',
    component: ArticlesListComponent
  },
  { 
    path: 'sign-in',
    component: SigninComponent },
  { 
    path: 'home', 
    component: HomeComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'sign-up', 
    component: SignUpComponent
  },
  { path: 'articles/:id',
   component: ArticleViewComponent,
   canActivate: [AuthGuard]
   },
   { path: 'add-article',
   component: AddArticleComponent,
   canActivate: [AuthGuard]
   }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
