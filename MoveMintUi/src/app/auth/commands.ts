export interface RegisterUserCommand {
  identity: string;
  password: string;
  userDetails: UserDetailsParams;
}

export interface UserDetailsParams {
  name: string;
  dateOfBirth: string; // ISO format date (e.g., "1990-01-01")
  gender: 'MALE' | 'FEMALE';
  height: number;
  weight: number;
  timezone: string;
}

export interface AuthenticateUserCommand {
  identity: string;
  password: string
}
