package com.coong_backend.config; // 본인의 패키지 구조에 맞게 수정

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 API 경로에 대해 CORS 설정을 적용
                .allowedOrigins("http://localhost:3000") // 💡 Next.js(프론트엔드) 주소 명시
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true) // 쿠키나 인증 헤더를 포함한 요청도 허용할 경우 true
                .maxAge(3600); // 프리플라이트(Preflight) 요청 캐싱 시간(초)
    }
}