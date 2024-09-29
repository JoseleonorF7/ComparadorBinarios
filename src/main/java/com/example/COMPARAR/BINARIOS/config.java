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
        registry.addMapping("/api/**")  // Asegúrate de que el mapeo es correcto
                .allowedOrigins("https://comparador-binarios-ang.vercel.app",
                        "https://comparador-binarios-c8osfvtrg-joseleonorf7s-projects.vercel.app","http://localhost:4200","https://comparadorbinarios-ang.onrender.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Asegúrate de que todos los métodos están permitidos
                .allowedHeaders("*")  // Permite todos los encabezados
                .exposedHeaders("Access-Control-Allow-Origin")  // Exponer el encabezado necesario
                .allowCredentials(true);  // Permitir cookies si es necesario
    }

}
