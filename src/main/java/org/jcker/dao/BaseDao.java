/*    */ package org.jcker.dao;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.lang.reflect.ParameterizedType;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.SessionFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.dao.DataAccessException;
/*    */ import org.springframework.orm.hibernate5.HibernateTemplate;
/*    */ import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
/*    */ 
/*    */ public abstract class BaseDao<E, I extends Serializable> extends HibernateDaoSupport
/*    */ {
/*    */   private Class<E> entityClass;
/*    */ 
/*    */   public BaseDao()
/*    */   {
/* 18 */     Type genericSuperClass = getClass().getGenericSuperclass();
/*    */ 
/* 20 */     ParameterizedType parametrizedType = null;
/* 21 */     while (parametrizedType == null) {
/* 22 */       if ((genericSuperClass instanceof ParameterizedType))
/* 23 */         parametrizedType = (ParameterizedType)genericSuperClass;
/*    */       else {
/* 25 */         genericSuperClass = ((Class)genericSuperClass).getGenericSuperclass();
/*    */       }
/*    */     }
/*    */ 
/* 29 */     this.entityClass = ((Class)parametrizedType.getActualTypeArguments()[0]);
/*    */   }
/*    */ 
/*    */   public List<E> findAll()
/*    */     throws DataAccessException
/*    */   {
/* 39 */     return getHibernateTemplate().find("from " + this.entityClass.getSimpleName(), new Object[0]);
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setJckerSessionFactoryAndHibernateTemplate(SessionFactory sessionFactory, HibernateTemplate hibernateTemplate)
/*    */   {
/* 50 */     super.setSessionFactory(sessionFactory);
/* 51 */     super.setHibernateTemplate(hibernateTemplate);
/*    */   }
/*    */ 
/*    */   public E find(I id)
/*    */   {
/* 61 */     return currentSession().get(this.entityClass, id);
/*    */   }
/*    */ 
/*    */   public void create(E e)
/*    */   {
/* 70 */     currentSession().save(e);
/*    */   }
/*    */ 
/*    */   public void saveOrUpdate(E e)
/*    */   {
/* 79 */     currentSession().saveOrUpdate(e);
/*    */   }
/*    */ 
/*    */   public void delete(E e)
/*    */   {
/* 88 */     currentSession().delete(e);
/*    */   }
/*    */ 
/*    */   public void flush()
/*    */   {
/* 95 */     currentSession().flush();
/*    */   }
/*    */ }

/* Location:           C:\Users\Pactera\Desktop\ROOT\WEB-INF\classes\
 * Qualified Name:     org.jcker.dao.BaseDao
 * JD-Core Version:    0.6.2
 */