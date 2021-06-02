package group.Taxi.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@Deprecated
public class Config extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
//                .allowedOrigins("http://localhost:3000")
                .allowedMethods("PUT", "DELETE", "GET", "POST")
                .allowedHeaders("Access-Control-Allow-Origin", " $http_origin", "Origin", "Content-Type", "Accept")
                .exposedHeaders("Access-Control-Allow-Origin", " $http_origin", "Origin", "Content-Type", "Accept")
                .allowCredentials(false).maxAge(3600);
    }
}