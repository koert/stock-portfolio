import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {PortfolioComponent} from "./portfolio/portfolio.component";
import {LoginComponent} from "./login/login.component";
import {JwtLoginComponent} from "./jwt-login/jwt-login.component";
import {AuthorizationGuard} from "./security/authorization-guard.service";

const routes: Routes = [
  { path: "portfolio", component: PortfolioComponent, canActivate: [AuthorizationGuard] },
  { path: "jwt-login", component: JwtLoginComponent },
  { path: "login", component: LoginComponent },
  { path: "", redirectTo: "/portfolio", pathMatch: "full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
