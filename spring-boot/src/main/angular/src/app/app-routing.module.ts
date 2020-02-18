import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {PortfolioComponent} from "./portfolio/portfolio.component";
import {LoginComponent} from "./login/login.component";

const routes: Routes = [
  { path: "portfolio", component: PortfolioComponent },
  { path: "login", component: LoginComponent },
  { path: "", redirectTo: "/portfolio", pathMatch: "full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
