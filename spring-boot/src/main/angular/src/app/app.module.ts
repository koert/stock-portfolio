import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PortfolioComponent } from './portfolio/portfolio.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {ButtonModule} from "primeng/button";
import {CalendarModule} from "primeng/calendar";
import {ChartModule} from "primeng/chart";
import {DialogModule} from "primeng/dialog";
import {DropdownModule} from "primeng/dropdown";
import {FileUploadModule} from "primeng/fileupload";
import {InputTextModule} from "primeng/inputtext";
import {MenuModule} from "primeng/menu";
import {OverlayPanelModule} from "primeng/overlaypanel";
import {PanelModule} from "primeng/panel";
import {MessageService, SelectButtonModule, SidebarModule} from "primeng/primeng";
import {TableModule} from "primeng/table";
import {ToastModule} from "primeng/toast";
import {FormsModule} from "@angular/forms";
import { LoginComponent } from './login/login.component';
import {JwtModule} from "@auth0/angular-jwt";
import { JwtLoginComponent } from './jwt-login/jwt-login.component';
import {AuthorizationInterceptor} from "./security/AuthorizationInterceptor";
import {MomentModule} from "ngx-moment";
import { PriceDialogComponent } from './portfolio/price-dialog/price-dialog.component';

export function tokenGetter() {
  return localStorage.getItem("access_token");
}

@NgModule({
  declarations: [
    AppComponent,
    PortfolioComponent,
    LoginComponent,
    JwtLoginComponent,
    PriceDialogComponent
  ],
  imports: [
    BrowserModule, BrowserAnimationsModule, FormsModule, HttpClientModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        whitelistedDomains: ["example.com", "portfolio.zencode.nl"],
        blacklistedRoutes: ["example.com/examplebadroute/"]
      }
    }),
    AppRoutingModule,
    MomentModule,

    // PrimeNG
    ButtonModule, CalendarModule, ChartModule, DialogModule, DropdownModule, FileUploadModule, InputTextModule,
    MenuModule, OverlayPanelModule, PanelModule, SelectButtonModule, SidebarModule, TableModule, ToastModule

  ],
  providers: [
    // PrimeNg
    MessageService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthorizationInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
