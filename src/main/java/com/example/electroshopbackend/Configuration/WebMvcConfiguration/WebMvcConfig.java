package com.example.electroshopbackend.Configuration.WebMvcConfiguration;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer   {


    @Override
    public final void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = "D:\\java_programs\\Java-App\\electronicshop\\storage\\photos";
        registry
                .addResourceHandler("/assets/photos/**")
                .addResourceLocations("file:"+ path + "/")
                .setCachePeriod(7 * 24 * 3600);
    }



    @Override
    public final void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3628800);
    }
}
