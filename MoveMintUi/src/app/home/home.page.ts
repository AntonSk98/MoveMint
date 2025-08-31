import { Component } from '@angular/core';
import {
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonCard,
  IonCardHeader,
  IonCardTitle, IonCardContent, IonItem, IonLabel, IonInput, IonButton
} from '@ionic/angular/standalone';
import {FirebaseMessaging, GetTokenOptions} from "@capacitor-firebase/messaging";
import {environment} from "../../environments/environment";
import {Capacitor} from "@capacitor/core";

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  imports: [IonHeader, IonToolbar, IonTitle, IonContent, IonCard, IonCardHeader, IonCardTitle, IonCardContent, IonItem, IonLabel, IonInput, IonButton],
})
export class HomePage {
  public token = "";

  constructor() {
    FirebaseMessaging.addListener("notificationReceived", (event) => {
      alert("notificationReceived: ");
    });
    FirebaseMessaging.addListener("notificationActionPerformed", (event) => {
      alert("notificationActionPerformed:");
    });
    if (Capacitor.getPlatform() === "web") {
      navigator.serviceWorker.addEventListener("message", (event: any) => {
        alert("serviceWorker message: ");
        const notification = new Notification('Notification!', {
          body: JSON.stringify(event.data),
        });
        notification.onclick = (event) => {
          alert("notification clicked: ");
        };
      });
    }
  }

  public async requestPermissions(): Promise<void> {
    await FirebaseMessaging.requestPermissions();
  }

  public async getToken(): Promise<void> {
    const options: GetTokenOptions = {
      vapidKey: environment.firebase.vapidKey,
    };
    if (Capacitor.getPlatform() === "web") {
      options.serviceWorkerRegistration =
        await navigator.serviceWorker.register("firebase-messaging-sw.js");
    }
    const { token } = await FirebaseMessaging.getToken(options);
    this.token = token;
  }
}
