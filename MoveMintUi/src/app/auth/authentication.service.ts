import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {AuthenticateUserCommand, RegisterUserCommand} from "./commands";
import {AuthenticationDto} from "./dtos";
import {take} from "rxjs";
import {Preferences} from "@capacitor/preferences";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private readonly host: string = environment.host;
  private readonly accessTokenKey: string = 'accessToken';
  private readonly refreshTokenKey: string = "refreshTo"

  constructor(private readonly _http: HttpClient) {

  }

  authenticate(command: AuthenticateUserCommand): void {
    this._http.post<AuthenticationDto>(`${this.host}/auth/login`, command)
      .pipe(take(1))
      .subscribe({
        next: (authTokenDto: AuthenticationDto) => {
          Preferences.set({
            key: this.accessTokenKey,
            value: JSON.stringify(authTokenDto.accessToken)
          }).catch(err => console.error('Failed to save access token', err));

          Preferences.set({
            key: this.refreshTokenKey,
            value: JSON.stringify(authTokenDto.refreshToken)
          }).catch(err => console.error('Failed to save refresh token', err))
        },
        error: (err) => {
          console.error('Authentication failed', err);
        }
      });
  }


  register(command: RegisterUserCommand): void {
    this._http.post(`${this.host}/auth/register`, command)
      .subscribe({
        error: err => console.error(`Registration error: ${err}`)
      })
  }

  refreshToken(): void {
  }

  isAuthenticated(): boolean {
    return false;
  }
}
