package org.jcker.database;

import org.jcker.database.exception.DaoException;
import org.jcker.smartqq.model.GroupMessage;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BaseDao is a DAO util Class, which is based on spring's JdbcTemplate.
 */
@Repository
public abstract class BaseDao {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * 通过实体对象想数据表插入数据，要做的是通过对象得到"表名"和"字段"。
     *
     * @param dto 插入对象
     * @param <E> Entity
     * @return 插入成功，返回原对象
     */
    protected <E extends Entity> E insert(E dto) {

        try {
            String sql = SqlHelper.getInsertString(dto);
            System.out.println("sql = " + sql);
            jdbcTemplate.update(sql);
            return dto;
        } catch (Exception e) {
            throw new DaoException("Executing insert error:" + e.getMessage(), e);
        }
    }


    /**
     * Create a table based on dto.
     *
     * @param cls
     * @return
     */
    protected void createTable(Class cls) throws Exception {
        jdbcTemplate.execute(SqlHelper.getCreateTableString(cls));
    }

    public static void main(String[] args) throws Exception {
        GroupMessage g = new GroupMessage(null);
        g.setContent("helloworld");
        g.setGroupId(123);
        g.setTime(456);
        g.setUserId(789);
        Map<String, String> map = BeanUtils.describe(g);
        map.remove("class");
        System.out.println("map = " + map);
        Field[] fields = g.getClass().getDeclaredFields();
        List<Field> fieldList = Arrays.asList(fields);
        Map<String, String> fieldHashMap = new HashMap<String, String>();
        for (Field field : fieldList
                ) {
            fieldHashMap.put(field.getName(), field.getType().getSimpleName());
        }
        System.out.println("fields = " + fields);
        for (Map.Entry entry : map.entrySet()
                ) {
            System.out.println(entry.getKey().toString() + "---" + fieldHashMap.get(entry.getKey()) + "---" + BeanUtils.getProperty(g, entry.getKey().toString()));
        }
        String tableName = g.getClass().getSimpleName().toLowerCase();
        String createTableString = "CREATE TABLE IF NOT EXISTS " + tableName;
        String columnString = "(";
        Map<String, String> columnTypeMap = new HashMap<String, String>();
        for (Map.Entry entry :
                fieldHashMap.entrySet()) {
            columnString += entry.getKey().toString() + " " + Constant.Type.getColumnType(entry.getValue().toString()) + ", ";
        }
        columnString = columnString.substring(0, columnString.length() - 2) + ")";
        System.out.println("createTableString = " + createTableString + columnString);
    }
}
