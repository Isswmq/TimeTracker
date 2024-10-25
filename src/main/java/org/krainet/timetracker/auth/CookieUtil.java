package org.krainet.timetracker.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class CookieUtil {
    @Value("${access_token_name}")
    private String accessTokenName;

    @Value("${refresh_token_name}")
    private String refreshTokenName;

    protected HttpHeaders createCookieHeaders(String accessToken, String refreshToken, long accessTokenDuration, long refreshTokenDuration) {
        HttpHeaders headers = new HttpHeaders();

        Stream.of(
                String.format("%s=%s; Max-Age=%d; Path=/; HttpOnly; Secure", accessTokenName, accessToken, accessTokenDuration),
                String.format("%s=%s; Max-Age=%d; Path=/; HttpOnly; Secure", refreshTokenName, refreshToken, refreshTokenDuration)
        ).forEach(s -> headers.add("Set-Cookie", s));

        return headers;
    }
}
