import { Injectable } from '@angular/core';
import {Observable, Subject} from "rxjs";

export enum MessageType {
  Info, Warning, Error
}

export class Message {
  type: MessageType;
  title: string;
  text: string;
  timeout: number = 5000;

  static error(title: string, text: string, timeout?: number): Message {
    let message = new Message();
    message.type = MessageType.Error;
    message.title = title;
    message.text = text;
    if (timeout) {
      message.timeout = timeout;
    }
    return message;
  }

  static info(title: string, text: string): Message {
    let message = new Message();
    message.type = MessageType.Info;
    message.title = title;
    message.text = text;
    return message;
  }

  static warning(title: string, text: string, timeout?: number): Message {
    let message = new Message();
    message.type = MessageType.Warning;
    message.title = title;
    message.text = text;
    if (timeout) {
      message.timeout = timeout;
    }
    return message;
  }
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private notificatieSubject: Subject<Message> = new Subject<Message>();

  constructor() { }

  error(title: string, text: string, timeout?: number): void {
    this.notificatieSubject.next(Message.error(title, text, timeout));
  }

  info(title: string, text: string): void {
    this.notificatieSubject.next(Message.info(title, text));
  }

  warning(title: string, text: string, timeout?: number): void {
    this.notificatieSubject.next(Message.warning(title, text, timeout));
  }

  getMessageSubject(): Observable<Message> {
    return this.notificatieSubject.asObservable();
  }

}
