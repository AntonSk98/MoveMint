import {bootstrapApplication} from '@angular/platform-browser';
import {RouteReuseStrategy, provideRouter, withPreloading, PreloadAllModules} from '@angular/router';
import {IonicRouteStrategy, provideIonicAngular} from '@ionic/angular/standalone';

import {routes} from './app/app.routes';
import {AppComponent} from './app/app.component';
import {APP_INITIALIZER, inject, provideAppInitializer} from "@angular/core";
import {AuthenticationService} from "./app/auth/authentication.service";
import {provideHttpClient} from "@angular/common/http";

bootstrapApplication(AppComponent, {
  providers: [
    {provide: RouteReuseStrategy, useClass: IonicRouteStrategy},
    provideIonicAngular(),
    provideHttpClient(),
    provideRouter(routes, withPreloading(PreloadAllModules))
  ],
});
