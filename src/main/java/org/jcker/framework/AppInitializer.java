package org.jcker.framework;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
  protected Class<?>[] getRootConfigClasses()
  {
    return new Class[] { ApplicationContext.class, DatabaseConfiguration.class, HibernateConfiguration.class, JpaConfiguration.class, TransactionConfiguration.class };
  }

  protected Class<?>[] getServletConfigClasses()
  {
    return new Class[] { Dispatcher.class, JckerWebSocketConfiguration.class };
  }

  protected String[] getServletMappings() {
    return new String[] { "/" };
  }
}
