package com.example.COMPARAR.BINARIOS;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class config implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Ajusta si quieres restringir a rutas específicas
                .allowedOrigins("https://comparador-binarios-ang.vercel.app") // Agrega tu dominio de Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                .allowedHeaders("*") // Puedes restringir si es necesario
                .allowCredentials(true);
    }}
