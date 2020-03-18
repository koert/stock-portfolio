import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import {instance, mock} from "ts-mockito";
import {NotificationService} from "../notification.service";
import {Router} from "@angular/router";
import {LoginService} from "../security/login.service";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule} from "@angular/forms";
import {ButtonModule} from "primeng/button";
import {CalendarModule} from "primeng/calendar";
import {ChartModule} from "primeng/chart";
import {DialogModule} from "primeng/dialog";
import {DropdownModule} from "primeng/dropdown";
import {OverlayPanelModule} from "primeng/overlaypanel";
import {PanelModule} from "primeng/panel";
import {SelectButtonModule} from "primeng/primeng";
import {TableModule} from "primeng/table";

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let notificationService = mock(NotificationService);
  let router = mock(Router);
  let loginService = mock(LoginService);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [BrowserModule, BrowserAnimationsModule, FormsModule,
        ButtonModule, CalendarModule, ChartModule, DialogModule,
        DropdownModule, OverlayPanelModule, PanelModule, SelectButtonModule, TableModule],
      declarations: [ LoginComponent ],
      providers: [
        {provide: NotificationService, useValue: instance(notificationService)},
        {provide: Router, useValue: instance(router)},
        {provide: LoginService, useValue: instance(loginService)}
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
