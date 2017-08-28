package org.jcker.framework;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages={"org.jcker"})
public class ApplicationContext extends WebMvcConfigurerAdapter
{
}
