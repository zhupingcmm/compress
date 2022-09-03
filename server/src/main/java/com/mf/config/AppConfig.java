package com.mf.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    @Getter
    @Setter
    private Jwt jwt = new Jwt();

    @Data
    public static class Jwt {
        private String jwtId = "compress";
        private String authorities = "authorities";
        private Long tokenExpireTime = 30*60*1000L;
    }
}
