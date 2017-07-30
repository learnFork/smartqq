package com.helloalanturing.framework.spring;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
/**
 * Created by Alan Turing on 2017/6/29.
 * app initializer
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ApplicationContext.class};
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{Dispatcher.class};
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
