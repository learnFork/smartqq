package org.jcker.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*  
 * 元注解@Target,@Retention,@Documented,@Inherited  
 *   
 *     @Target 表示该注解用于什么地方，可能的 ElemenetType 参数包括：  
 *         ElemenetType.CONSTRUCTOR 构造器声明  
 *         ElemenetType.FIELD 域声明（包括 enum 实例）  
 *         ElemenetType.LOCAL_VARIABLE 局部变量声明  
 *         ElemenetType.METHOD 方法声明  
 *         ElemenetType.PACKAGE 包声明  
 *         ElemenetType.PARAMETER 参数声明  
 *         ElemenetType.TYPE 类，接口（包括注解类型）或enum声明  
 *           
 *     @Retention 表示在什么级别保存该注解信息。可选的 RetentionPolicy 参数包括：  
 *         RetentionPolicy.SOURCE 注解将被编译器丢弃  
 *         RetentionPolicy.CLASS 注解在class文件中可用，但会被VM丢弃  
 *         RetentionPolicy.RUNTIME VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息。  
 *           
 *     @Documented 将此注解包含在 javadoc 中  
 *       
 *     @Inherited 允许子类继承父类中的注解  
 *     
 */

/**
 * @author cqchenf@web.com
 * @date 2010-8-10 
 * @version v1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnMapping {

	/**
	 * 列名
	 * @return 对应数据库表的字段名称
	 */
	String columnName();

	/**
	 * 列类型
	 * @return Integer,Long,Short,Float,Double,String,Date,Timestamp,Time
	 * <br>oracle中date类型可对应Date(不含时分秒)和Timestamp(含时分秒)
	 * <br>mysql中的datetime类型可以直接用String类型代替
	 */
	String columnType();
	
	/**
	 * 列长度
	 * @return
	 */
	String columnLength() default "";
	
	/**
	 * 非空约束
	 * @return
	 */
	boolean columnIsNotNull() default false;
	
	/**
	 * 日期时间格式
	 * @return
	 */
	String dateFormatPattern() default "";
}
