package org.jcker.database;

import org.apache.commons.lang3.StringUtils;

public class Constant {

    public enum Type {
        STRING("String", "VARCHAR"),
        LONG("long", "LONG"),
        INT("int", "INT");

        Type(String fieldType, String columnType) {
            this.fieldType = fieldType;
            this.columnType = columnType;
        }

        private String fieldType;
        private String columnType;

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }

        public String getColumnType() {
            return columnType;
        }

        public void setColumnType(String columnType) {
            this.columnType = columnType;
        }

        public static String getColumnType(String fieldType){
            for (Type type : Type.values()){
                if (StringUtils.equals(type.getFieldType(), fieldType)) {
                    return type.getColumnType();
                }
            }
            return null;
        }
    }
}
