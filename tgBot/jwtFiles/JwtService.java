package TgBot.jwtFiles;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


import java.security.Key;
import java.util.*;
import java.util.function.Function;

//@Service
public class JwtService {
//
//    private static String SECRET_KEY = "3B104839A005112555732E54BB19E94DBDC3D325CC8318B005815043B17C0BA6";
//
//    public String extractUsername(String token) {
//        return extractClaims(token, Claims::getSubject);
//    }
//
//    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    public String generateToken(UserDetails userDetails) {
//        return generateToken(new HashMap<>(), userDetails);
//    }
//
//    public String generateToken(Map<String, Object> extractClaims,
//                                UserDetails userDetails) {
//        return Jwts
//                .builder()
//                .setClaims(extractClaims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
//                .signWith(getSignKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaims(token, Claims::getExpiration);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Key getSignKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
}
