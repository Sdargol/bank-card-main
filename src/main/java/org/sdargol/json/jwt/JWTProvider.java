package org.sdargol.json.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.sdargol.utils.Log;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JWTProvider {
    private static final Logger LOGGER = Log.getLogger(JWTProvider.class.getName());
    private static final String secret = "secret";

    public String generateToken(String login){
        Date now = new Date();
        Date date = new Date(now.getTime() + (10 * 60 + 1000));

        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error validate token", e);
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
