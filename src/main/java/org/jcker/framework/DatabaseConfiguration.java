package org.jcker.framework;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseConfiguration
{
  @Bean
  public DataSource dataSource()
  {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.h2.Driver");
    dataSource.setUrl("jdbc:h2:/Users/alanturing/database/jcker");
    dataSource.setUsername("sa");
    dataSource.setPassword("");
    return dataSource;
  }
}
