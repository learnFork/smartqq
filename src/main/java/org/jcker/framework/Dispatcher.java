package org.jcker.framework;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"org.jcker"})
public class Dispatcher extends WebMvcConfigurerAdapter
{
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
  {
    configurer.enable();
  }

  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setViewClass(JstlView.class);
    viewResolver.setPrefix("/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
  }

  public void addResourceHandlers(ResourceHandlerRegistry registry)
  {
    registry.addResourceHandler(new String[] { "/js/**" }).addResourceLocations(new String[] { "/js/" });
    registry.addResourceHandler(new String[] { "/bootstrap-3.3.7/**" }).addResourceLocations(new String[] { "/bootstrap-3.3.7/" });
    registry.addResourceHandler(new String[] { "/easyui/**" }).addResourceLocations(new String[] { "/easyui/" });
    registry.addResourceHandler(new String[] { "/img/**" }).addResourceLocations(new String[] { "/img/" });
    registry.addResourceHandler(new String[] { "/css/**" }).addResourceLocations(new String[] { "/css/" });
  }

  public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
  {
    converters.add(converter());
  }

  @Bean
  MappingJackson2HttpMessageConverter converter() {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

    List types = new ArrayList();
    types.add(MediaType.APPLICATION_JSON);
    types.add(MediaType.TEXT_XML);
    types.add(MediaType.ALL);
    converter.setSupportedMediaTypes(types);
    return converter;
  }
}
