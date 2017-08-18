package org.jcker.database.support;

/**
 * @author cqchenf@web.com
 * @date 2010-8-10 
 * @version v1.0
 */
public class ColumnDescribe {
	
	/**
	 * JAVA实体属性名
	 */
	private String fieldName;
	/**
	 * 数据库表字段名
	 */
	private String columnName;
	/**
	 * 数据库表字段类型
	 */
	private String columnType;
	
	private String columnLength = "";
	
	private boolean columnIsNotNull = false;
	
	private String dateFormatPattern = "";

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnLength() {
		return columnLength;
	}

	public void setColumnLength(String columnLength) {
		this.columnLength = columnLength;
	}

	public boolean getColumnIsNotNull() {
		return columnIsNotNull;
	}

	public void setColumnIsNotNull(boolean columnIsNotNull) {
		this.columnIsNotNull = columnIsNotNull;
	}

	public String getDateFormatPattern() {
		return dateFormatPattern;
	}

	public void setDateFormatPattern(String dateFormatPattern) {
		this.dateFormatPattern = dateFormatPattern;
	}

}
