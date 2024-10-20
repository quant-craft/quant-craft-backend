package com.quant.craft.backend.domain.auth;

import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  @Value("${security.jwt.access-token.secret-key}")
  private String secretKeyOfAccessToken;

  @Value("${security.jwt.refresh-token.secret-key}")
  private String secretKeyOfRefreshToken;

  @Value("${security.jwt.access-token.expire-length}")
  private long validityInMillisecondsOfAccessToken;

  @Value("${security.jwt.refresh-token.expire-length}")
  private long validityInMillisecondsOfRefreshToken;

  public String createAccessToken(User user) {
    Claims claims = Jwts.claims().setSubject(user.getId().toString());
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMillisecondsOfAccessToken);
    return createJwtToken(claims, now, validity, secretKeyOfAccessToken);
  }

  public String createRefreshToken(User user) {
    Claims claims = Jwts.claims().setSubject(user.getId().toString());
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMillisecondsOfRefreshToken);
    return createJwtToken(claims, now, validity, secretKeyOfRefreshToken);
  }

  public void validateAccessToken(String accessToken) {
    validateToken(accessToken, secretKeyOfAccessToken);
  }

  public void validateRefreshToken(String refreshToken) {
    validateToken(refreshToken, secretKeyOfRefreshToken);
  }

  public String getAccessTokenPayload(String accessToken) {
    try {
      return Jwts.parser().setSigningKey(secretKeyOfAccessToken).parseClaimsJws(accessToken).getBody()
          .getSubject();
    } catch (MalformedJwtException e) {
      throw new UnauthorizedException("Invalid Access Token. e: " + e);
    }
  }

  private String createJwtToken(Claims claims, Date now, Date validity, String secretKey) {
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  private void validateToken(String token, String secretKey) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    } catch (JwtException | IllegalArgumentException e) {
      throw new UnauthorizedException("Invalid Token. e: " + e);
    }
  }
}
