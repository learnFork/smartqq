package org.jcker.database;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SqlHelper {

    public static String getInsertString(Entity entity) {
        StringBuffer insertString = new StringBuffer("INSERT INTO ");
        Class cls = entity.getClass();
        try {
            Map<String, String> dtoMap = BeanUtils.describe(entity);
            dtoMap.remove("class");
            System.out.println("dtoMap = " + dtoMap);
            Field[] fields = cls.getDeclaredFields();
            Map<String, Field> fieldMap = new HashMap<>();
            for (Field field :
                    fields) {
                fieldMap.put(field.getName(), field);
            }
            System.out.println("fieldMap = " + fieldMap);
            insertString.append(cls.getSimpleName().toLowerCase());
            StringBuffer columns = new StringBuffer("(");
            StringBuffer values = new StringBuffer(") VALUES(");
            for (Map.Entry entry : dtoMap.entrySet()) {
                columns.append(entry.getKey()).append(", ");

                values.append(fieldMap.get(entry.getKey()).getType().getSimpleName().equals("String") ? "'" + entry.getValue().toString() + "'" : entry.getValue().toString()).append(", ");
            }
            insertString.append(columns.substring(0, columns.length() - 2)).append(values.substring(0, values.length() - 2) + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertString.toString();
    }

    public static String getCreateTableString(Class cls) {

        String createTableString = "CREATE TABLE IF NOT EXISTS " + cls.getSimpleName().toLowerCase();
        StringBuffer columnString = new StringBuffer("(");
        Field[] fields = cls.getDeclaredFields();
        for (Field field :
                fields) {
            columnString.append(field.getName()).append(" ").append(Constant.Type.getColumnType(field.getType().getSimpleName())).append(", ");
        }
        createTableString += columnString.substring(0, columnString.length() - 2) + ")";

        System.out.println("createTableString = " + createTableString);
        return createTableString;
    }

    public static void main(String[] args) throws Exception{

        getCreateTableString(Person.class);


        Person person = new Person("alanturing", "helloworld");

        System.out.println(getInsertString(person));
        System.out.println("person.getClass().getDeclaredField(\"content\") = " + person.getClass().getDeclaredFields()[0]);

    }
}
