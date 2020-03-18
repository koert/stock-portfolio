import { TestBed, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ButtonModule} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {DropdownModule} from "primeng/dropdown";
import {PanelModule} from "primeng/panel";
import {TableModule} from "primeng/table";
import {FormsModule} from "@angular/forms";
import {ToastModule} from "primeng/toast";
import {instance, mock, when} from "ts-mockito";
import {StockService} from "./stock.service";
import {Message, NotificationService} from "./notification.service";
import {MessageService} from "primeng/api";
import {of} from "rxjs";

describe('AppComponent', () => {

  let notificationService = mock(NotificationService);
  let messageService = mock(MessageService);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [BrowserModule, BrowserAnimationsModule, RouterTestingModule, FormsModule,
        ButtonModule, DialogModule, DropdownModule, PanelModule, TableModule, ToastModule],
      declarations: [
        AppComponent
      ],
      providers: [
        {provide: NotificationService, useValue: instance(notificationService)},
        {provide: MessageService, useValue: instance(messageService)}
      ],
    }).compileComponents();

    let message = new Message();
    when(notificationService.getMessageSubject()).thenReturn(of(message));
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'portfolio'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('portfolio');
  });

});
