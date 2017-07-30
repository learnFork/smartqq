package com.helloalanturing.framework.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Alan Turing on 2017/6/29.
 */

@Configuration
@ComponentScan(basePackages = {"com"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class),
                            @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)})
public class ApplicationContext extends WebMvcConfigurerAdapter {


/*    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/messageboard?verifyServerCertificate=false&useSSL=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }*/

/*    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource());
        return jdbcTemplate;
    }*/

/*    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(this.dataSource());
        return sqlSessionFactoryBean;
    }*/

/*    @Bean
    public DataSourceTransactionManager transactionManager(){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(this.dataSource());
        return transactionManager;
    }*/

/*    @Bean
    public DwrSpringServlet dwrSpringServlet(){
        DwrSpringServlet dwrSpringServlet = new DwrSpringServlet();
        dwrSpringServlet.setIncludeDefaultConfig(true);
        return dwrSpringServlet;
    }*/


}