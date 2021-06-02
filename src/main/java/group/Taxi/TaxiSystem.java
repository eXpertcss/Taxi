package group.Taxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaxiSystem {

    public static void main(String[] args) {
        SpringApplication.run(TaxiSystem.class, args);
    }


//    @Bean
//    public WebMvcConfigurer corsConfig(){
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:3000")
//                        .allowedMethods("PUT", "DELETE")
//                        .allowedHeaders("Access-Control-Allow-Origin", "header2", "header3")
//                        .exposedHeaders("Access-Control-Allow-Origin", "header2")
//                        .allowCredentials(false).maxAge(3600);
//            }
//        };
//    }
}

