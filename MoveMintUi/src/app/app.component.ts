import {Component} from '@angular/core';
import {Capacitor} from '@capacitor/core';
import {IonApp, IonRouterOutlet} from '@ionic/angular/standalone';
import {environment} from "../environments/environment";
import {initializeApp} from "firebase/app";

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  imports: [IonApp, IonRouterOutlet],
})
export class AppComponent {
  constructor() {
    this.initializeFirebase();
  }

  public async initializeFirebase(): Promise<void> {
    if (Capacitor.isNativePlatform()) {
      return;
    }
    initializeApp(environment.firebase);
  }
}
