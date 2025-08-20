export interface AuthenticationDto {
  accessToken: Token;
  refreshToken: Token;
}

export interface Token {
  token: string;
  expiresAt: number;
}

export function isTokenExpired(token: Token) {
  const bufferSeconds = 60;
  const currentTime = Math.floor(Date.now() / 1000);
  return token.expiresAt < (currentTime + bufferSeconds);
}
