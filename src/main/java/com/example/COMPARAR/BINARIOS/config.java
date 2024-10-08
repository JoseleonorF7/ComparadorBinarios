package com.example.COMPARAR.BINARIOS;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class config implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Asegúrate de que el mapeo es correcto
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Asegúrate de que todos los métodos están permitidos
                .allowedHeaders("*")  // Permite todos los encabezados
                .exposedHeaders("Access-Control-Allow-Origin")  // Exponer el encabezado necesario
                .allowCredentials(true);  // Permitir cookies si es necesario
    }

}
