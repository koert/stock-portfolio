import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {LoginService} from "./login.service";

@Injectable()
export class AuthorizationInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authorizationHeader = localStorage.getItem(LoginService.authorizationKey);
    if (authorizationHeader) {
      const cloned = req.clone({
        headers: req.headers.set("Authorization", authorizationHeader)
      });
      return next.handle(cloned);
    }
    else {
      return next.handle(req);
    }
  }
}
