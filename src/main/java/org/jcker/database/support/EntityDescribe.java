package org.jcker.database.support;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.Map;

/**
 * 
 * @author Alan Turing http://helloalanturing.com
 * @version v1.0
 */
public class EntityDescribe {

	/** 实体名 */
	private String entityName;
	/** TableMapping */
	private Annotation classAnnotation;
	/** key为实体字段名，value为ColumnDescribe */
	private Map<String, ColumnDescribe> columnMap;
	private LinkedList<ColumnDescribe> columnList;

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public LinkedList<ColumnDescribe> getColumnList() {
		return columnList;
	}

	public void setColumnList(LinkedList<ColumnDescribe> columnList) {
		this.columnList = columnList;
	}

	public Annotation getClassAnnotation() {
		return classAnnotation;
	}

	public void setClassAnnotation(Annotation classAnnotation) {
		this.classAnnotation = classAnnotation;
	}

	public Map<String, ColumnDescribe> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, ColumnDescribe> columnMap) {
		this.columnMap = columnMap;
	}
}
