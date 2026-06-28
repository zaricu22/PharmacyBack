package com.pharmacy.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.security.jwt")
public class JwtConfigProperties {

    private String secreteKey;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;

    public String getSecreteKey() {
        return secreteKey;
    }

    public void setSecreteKey(String secreteKey) {
        this.secreteKey = secreteKey;
    }

    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public void setAccessTokenExpiration(long accessTokenExpiration) {
        this.accessTokenExpiration = accessTokenExpiration;
    }

    public long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    public void setRefreshTokenExpiration(long refreshTokenExpiration) {
        this.refreshTokenExpiration = refreshTokenExpiration;
    }
}
