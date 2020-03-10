import { Component } from '@angular/core';
import {MessageType, NotificationService} from "./notification.service";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'portfolio';

  constructor(private notificationService: NotificationService, private messageService: MessageService) {
    this.notificationService.getMessageSubject().subscribe(notificationMessage => {
      let type: string = "success";
      let message: any;
      if (notificationMessage.type == MessageType.Error) {
        type = "error";
        message = {severity: 'error', summary: notificationMessage.title, detail: notificationMessage.text, life: notificationMessage.timeout};
      }
      if (notificationMessage.type == MessageType.Info) {
        type = "success";
        message = {severity: 'info', summary: notificationMessage.title, detail: notificationMessage.text, life: notificationMessage.timeout};
      }
      if (notificationMessage.type == MessageType.Warning) {
        type = "warning";
        message = {severity: 'warn', summary: notificationMessage.title, detail: notificationMessage.text, life: notificationMessage.timeout};
      }

      if (message) {
        this.messageService.add(message);
      }

    });
  }

}
