import {Routes} from '@angular/router';
import {HomePage} from "./home/home.page";
import {AuthGuard} from "./auth/auth.guard";

export const routes: Routes = [
  {
    path: 'home',
    component: HomePage,
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: HomePage
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
];
