package org.jcker.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableMapping {

    /**
     *
     * @return 实体对应的数据库表的表名
     */
    public String tableName();

    /**
     *
     * @return 数据库表主键类型：Single-单个字段的主键,Combine-联合主键,None-无主键
     */
    public String primaryKeyType();

    /**
     *
     * @return 主键名称，多个主键用逗号隔开,只能用实体属性名，不能用注解名
     */
    public String primaryKey();
}
