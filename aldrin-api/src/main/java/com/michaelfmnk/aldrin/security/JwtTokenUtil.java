package com.michaelfmnk.aldrin.security;

import com.michaelfmnk.aldrin.services.utils.TimeProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "aud";
    static final String CLAIM_KEY_CREATED = "iat";

    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";

    private TimeProvider timeProvider;

    @Autowired
    public JwtTokenUtil(TimeProvider timeProvider){
        this.timeProvider = timeProvider;
    }

    /**
     * with this secret jwt-token is signed
     */
    @Value("${jwt.secret}")
    private String secret = "mySecret";

    /**
     * how long jwt-token is valid (in seconds)
     */
    @Value("${jwt.expiration}")
    private Long expiration = 100000L;


    /**
     * Gets username from a given jwt-token
     * @param token jwt-token
     * @return Username from a token
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    public Integer getUserIdFromToken(String token) {
        return Integer.valueOf(getClaimFromToken(token, Claims::getId));
    }

    /**
     * @param token jwt-token
     * @param claimResolver function
     * @return claim by claimResolver
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }


    /**
     * @param token jwt-token
     * @return all Claims from a token
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }


    /**
     * Checks whether jwt-token is not expired.
     * Uses getExpirationDateFromToken() to get expiration date
     * Gets current time from TimeProvider and compares them
     *
     * @param token jwt-token
     * @return true if token is expired; false if token is not expired;
     */
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(timeProvider.now());
    }


    /**
     * @param token jwt-token
     * @return token's Expiration date
     */
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    /**
     * Checks whether the jwt-token is valid for a given user;
     * Casts UserDetails to JwtUser, gets username and issued-date;
     *
     * Token is valid if username in token equals JwtUsers's username,
     * token is not expired (see isTokenExpired()) and jwt-token is created
     * before the previous password reset date
     * @param token jwt-token
     * @param userDetails JwtUser
     * @return true if token is valid, false - not valid
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser jwtUser = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        return (
                username.equals(jwtUser.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(created, jwtUser.getLastPasswordResetDate())
        );
    }


    /**
     * @param token jwt-token
     * @return Date when token was created
     */
    private Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }


    /**
     * Checks whether the token was created before the lass password's reset date
     * @param created Date
     * @param lastPasswordReset Date
     * @return boolean
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }


    /**
     * Uses private method createToken() to generate new JWT-Token
     * Passes username & audience (as string) to createToken()
     * @param userDetails JwtUser
     * @return String - jwt-token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }


    /**
     * Creates new jwt token
     *
     * Uses calculateExpirationDate() to get expiration date, then uses
     * Uses JWTs builder to create new token.
     * Uses HS512 Signature algorithm;
     *
     * @param claims
     * @param subject username
     * @return String jwt-token
     */
    private String createToken(Map<String, Object> claims, String subject) {
        final Date createdDate = timeProvider.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        System.out.println("createToken " + createdDate);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }


    /**
     * Adds to the current date N (expiration) seconds;
     * @param createdDate Date now
     * @return expiration Date
     */
    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }


    /**
     * Checks whether token can be refreshed;
     * Conditions:
     *  - is created after the last password reset
     *  - token is not expired OR expiration can be ignored
     * @param token jwt-token
     * @param lastPasswordResetDate
     * @return
     */
    public boolean canTokenBeRefreshed(String token, Date lastPasswordResetDate) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordResetDate) &&
                (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }


    /**
     * Checks whether expiration can be ignored while refreshing token
     * @param token jwt
     * @return boolean
     */
    private boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }


    /**
     * Gets Audience as a string from a given jwt-token
     * @param token jwt-token
     * @return String - audience
     */
    private String getAudienceFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }


    /**
     * Refreshes jwt-token;
     * creates claims based on the old token, changes issuedDate and expiration date,
     * then builds new token;
     * @param token jwt-token
     * @return new jwt-token
     */
    public String refreshToken(String token) {
        final Date createdDate = timeProvider.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
