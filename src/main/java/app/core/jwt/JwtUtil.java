package app.core.jwt;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.services.ClientType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    private String encodedSecretKey = "this+is+my+key+and+it+must+be+at+least+256+bits+long";
    private Key decodedSecretKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey),
            this.signatureAlgorithm);
    private JwtParser jwtParser = Jwts.parser().setSigningKey(this.decodedSecretKey);// .build();

    public String generateToken(Object user, ClientType clientType) {
        Map<String, Object> claims = new HashMap<>();
        String subject = null;
        String id = null;
        switch (clientType) {
            case Customer:
                id = String.valueOf(((Customer) user).getId());
                subject = ((Customer) user).getEmail();
                claims.put("firstName", ((Customer) user).getFirstName());
                claims.put("lastName", ((Customer) user).getLastName());
                claims.put("password", ((Customer) user).getPassword());
                break;
            case Company:
                id = String.valueOf(((Company) user).getId());
                subject = ((Company) user).getEmail();
                claims.put("name", ((Company) user).getName());
                claims.put("password", ((Company) user).getPassword());
                break;
            case Administrator:
                subject = "admin@admin.com";
                claims.put("password", "admin");
                break;
        }

        return createToken(claims, subject, id);
    }

    private String createToken(Map<String, Object> claims, String subject, String id) {

        Instant now = Instant.now();

        return Jwts.builder().setClaims(claims)

                .setId(id)

                .setSubject(subject)

                .setIssuedAt(Date.from(now))

                .setExpiration(Date.from(now.plus(10, ChronoUnit.HOURS)))

                .signWith(null, this.decodedSecretKey)

                .compact();
    }

    private Claims extractAllClaims(String token) throws ExpiredJwtException {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    /** returns the JWT subject - in our case the email address */
    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractId(String token) {
        return extractAllClaims(token).getId();
    }

    public String extractPassword(String token) {
        return (String) extractAllClaims(token).get("password");
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token) {
        try {
            extractAllClaims(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * returns true if the user (email) in the specified token equals the one in the
     * specified user details and the token is not expired
     */
    public boolean validateToken(String token, String subject) {
        final String userSubject = extractSubject(token);
        return (userSubject.equals(subject) && !isTokenExpired(token));
    }

}