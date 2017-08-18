package org.jcker.database.support;

import org.jcker.database.annotation.TableMapping;
import org.jcker.database.exception.DaoErrorCode;
import org.jcker.database.exception.DaoException;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * SQL构建器
 *
 * @author Aaln Turing http://helloalanturing.com
 * @version v1.0
 */
public class SqlHelper {

    /**
     * 生成insert语句
     *
     * @param entityDescribe
     * @param dtoMap
     * @return
     */
    public String createInsertSql(EntityDescribe entityDescribe, Map<String, Object> dtoMap) throws DaoException {
        TableMapping tm = (TableMapping) entityDescribe.getClassAnnotation();
        String tableName = tm.tableName();
        String pkType = tm.primaryKeyType();
        String primaryKeyName = tm.primaryKey();
        checkTableMapping(entityDescribe.getEntityName(), tableName, pkType,
                primaryKeyName);

        StringBuffer sql = new StringBuffer();
        StringBuffer valuesSql = new StringBuffer();

        // insert into tableName(primaryKeyName
        sql.append("INSERT INTO ").append(tableName).append("(");

        // insert into tableName(primaryKeyName,column1,column2 ... ,columnN
        for (int i = 0; i < entityDescribe.getColumnList().size(); i++) {
            ColumnDescribe columnDesc = entityDescribe.getColumnList().get(i);
            String columnName = columnDesc.getColumnName();

            // Object value = dtoMap.get(columnDesc.getFieldName());
            if (StringUtils.isBlank(columnName)) {
                continue;
            }
            if (i != 0) {
                sql.append(",");
                valuesSql.append(",");
            }
            sql.append(columnName);
            valuesSql.append("?");
        }
        sql.append(") VALUES (").append(valuesSql).append(")");

        return sql.toString();
    }

    /**
     * 生成INSERT语句的values
     *
     * @param entityDescribe
     * @param dtoMap
     * @return
     */
    public Object[] getInsertValues(EntityDescribe entityDescribe,
                                    Map<String, Object> dtoMap) throws DaoException {
        TableMapping tm = (TableMapping) entityDescribe.getClassAnnotation();
        String primaryKeyName = tm.primaryKey();
        String primaryKeyType = tm.primaryKeyType();
        List<Object> list = new LinkedList<Object>();
        if (!PrimaryKeyType.None.getCode().equals(primaryKeyType)) {
            // 校验配置的primaryKeyName是否为实体定义的属性名
            String primaryKeyNames[] = primaryKeyName.split(",");
            checkPrimaryKeyValid(entityDescribe, dtoMap, primaryKeyName,
                    primaryKeyNames);
            // 校验主键是否有值
            if (PrimaryKeyType.Single.getCode().equals(primaryKeyType)) {
                // 单个主键的情况时,如果主健值为空,则默认自动生成主键
                if (StringUtils.isEmpty((String) dtoMap.get(primaryKeyName))) {
                    dtoMap.put(primaryKeyName, PKGenerator.getInstanse().getGUID());
                }
            } else if (PrimaryKeyType.Combine.getCode().equals(primaryKeyType)) {
                // 如果为联合主键时必须由调用者手工赋值
                for (int i = 0; i < primaryKeyNames.length; i++) {
                    if (dtoMap.get(primaryKeyNames[i]) == null) {
                        throw new DaoException(
                                DaoErrorCode.ENT_PKVALUE_NULL.getCode(),
                                new String[]{entityDescribe.getEntityName(),
                                        primaryKeyName});
                    }
                }
            }
        }
        for (int i = 0; i < entityDescribe.getColumnList().size(); i++) {
            ColumnDescribe column = (ColumnDescribe) entityDescribe.getColumnList().get(i);
            String columnType = column.getColumnType();
            Object value = dtoMap.get(column.getFieldName());
//            value = BeanDataHandler.trans(value, ColumnType.getType(columnType), column.getDateFormatPattern());

            // 校验字段VALUE的长度和非空约束
            checkColumnLengthAndNull(entityDescribe.getEntityName(), column, value);

            list.add(value);
        }
        return list.toArray();
    }
/*

    */
/**
 * 生成UPDATE语句
 *
 * @param entityDescribe
 * @param dtoMap
 * @param isUpdateValueNullField 是否更新空值字段,true:更新,false:不更新
 * @return
 *//*

    public String createUpdateSql(EntityDescribe entityDescribe,
                                  Map<String, Object> dtoMap, boolean isUpdateValueNullField)
            throws DaoException {
        TableMapping tm = (TableMapping) entityDescribe.getClassAnnotation();
        String tableName = tm.tableName();
        // 校验实体表注解信息
        checkTableMapping(entityDescribe.getEntityName(), tableName,
                tm.primaryKeyType(), tm.primaryKey());
        String[] primaryKeyNames = getPrimaryKey(tm);
        if (primaryKeyNames == null || primaryKeyNames.length == 0) {
            throw new DaoException(DaoErrorCode.SQL_UPDATE_NOTPK.getCode(),
                    new String[]{entityDescribe.getEntityName()});
        }
        int i = 0;

        StringBuffer sql = new StringBuffer();
        StringBuffer whereSql = new StringBuffer(" WHERE ");
        // update tableName set
        sql.append("UPDATE ").append(tableName).append(" SET ");
        // update tableName set column1=?,column2=? ... ,columnN=?
        for (i = 0; i < entityDescribe.getColumnList().size(); i++) {
            ColumnDescribe column = entityDescribe.getColumnList().get(i);
            // 校验属性列注解信息
            checkColumnMapping(entityDescribe.getEntityName(),
                    column.getFieldName(), column.getColumnName(),
                    column.getColumnType());
            Object value = dtoMap.get(column.getFieldName());
            if (StringUtil.isNullOrEmpty(value)) {
                if (isUpdateValueNullField) {
                    // 需要更新空值字段
                    sql.append(column.getColumnName()).append("=?");
                    sql.append(", ");
                }
            } else {
                sql.append(column.getColumnName()).append("=?");
                sql.append(", ");
            }
        }
        sql.delete(sql.lastIndexOf(","), sql.lastIndexOf(",") + 1);
        for (i = 0; i < primaryKeyNames.length; i++) {
            if (i != 0) {
                whereSql.append(" AND ");
            }
            whereSql.append(primaryKeyNames[i]);
            Object value = dtoMap.get(primaryKeyNames[i]);
            if (StringUtil.isNullOrEmpty(value) && primaryKeyNames.length != 1) {
                whereSql.append(" IS NULL ");
            } else {
                whereSql.append("=?");
            }
        }
        sql.append(whereSql.toString());
        return sql.toString();
    }

    */
/**
 * 生成update values
 *
 * @param entityDescribe
 * @param dtoMap
 * @param isUpdateValueNullField 是否更新空值字段 true:更新,false:不更新
 * @return
 * @throws DaoException
 *//*

    public Object[] getUpdateValues(EntityDescribe entityDescribe,
                                    Map<String, Object> dtoMap, boolean isUpdateValueNullField)
            throws DaoException {
        TableMapping tm = (TableMapping) entityDescribe.getClassAnnotation();
        String[] primaryKeyNames = getPrimaryKey(tm);
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < entityDescribe.getColumnList().size(); i++) {
            ColumnDescribe column = entityDescribe.getColumnList().get(i);
            String columnType = column.getColumnType();
            Object value = dtoMap.get(column.getFieldName());
            if (StringUtil.isNullOrEmpty(value)) {
                if (isUpdateValueNullField) {
                    // 需要更新空值字段
                    list.add(value);
                }
            } else {
                value = BeanDataHandler.trans(value,
                        ColumnTypeEnum.getType(columnType),
                        column.getDateFormatPattern());
                list.add(value);
            }
        }

        for (int i = 0; i < primaryKeyNames.length; i++) {
            ColumnDescribe column = entityDescribe.getColumnMap().get(
                    primaryKeyNames[i]);
            Object value = dtoMap.get(column.getFieldName());
            String columnType = column.getColumnType();
            if (value == null) {
                continue;
            }
            value = BeanDataHandler.trans(value,
                    ColumnTypeEnum.getType(columnType),
                    column.getDateFormatPattern());
            list.add(value);
        }
        return list.toArray();
    }

    */
/**
 * 生成DELETE语句
 *
 * @param entityDescribe
 * @param dtoMap
 * @return
 * @throws DaoException
 *//*

    public String createDeleteSql(EntityDescribe entityDescribe,
                                  Map<String, Object> dtoMap) throws DaoException {
        TableMapping tm = (TableMapping) entityDescribe.getClassAnnotation();
        String tableName = tm.tableName();
        // 校验表关系注解信息
        checkTableMapping(entityDescribe.getEntityName(), tableName,
                tm.primaryKeyType(), tm.primaryKey());
        String[] primaryKeyNames = getPrimaryKey(tm);
        if (primaryKeyNames == null || primaryKeyNames.length == 0) {
            throw new DaoException(DaoErrorCode.SQL_DELETE_NOTPK.getCode(),
                    new String[]{entityDescribe.getEntityName()});
        }

        StringBuffer sql = new StringBuffer();
        // delete from tableName where primaryKeyName=?
        sql.append("DELETE FROM ").append(tableName).append(" WHERE ");
        for (int i = 0; i < primaryKeyNames.length; i++) {
            if (i != 0) {
                sql.append(" AND ");
            }
            sql.append(primaryKeyNames[i]);
            Object value = dtoMap.get(primaryKeyNames[i]);
            if (StringUtil.isNullOrEmpty(value) && primaryKeyNames.length != 1) {
                sql.append(" is null ");
            } else {
                sql.append("=?");
            }
        }
        return sql.toString();
    }

    public Object[] getPKWhereList(EntityDescribe entityDescribe,
                                   Map<String, Object> dtoMap) throws DaoException {
        TableMapping tm = (TableMapping) entityDescribe.getClassAnnotation();
        String[] primaryKeyNames = getPrimaryKey(tm);
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < primaryKeyNames.length; i++) {
            Object value = dtoMap.get(primaryKeyNames[i]);
            ColumnDescribe column = entityDescribe.getColumnMap().get(
                    primaryKeyNames[i]);
            String columnType = column.getColumnType();
            if (StringUtil.isNullOrEmpty(value)) {
                continue;
            }
            value = BeanDataHandler.trans(value,
                    ColumnTypeEnum.getType(columnType),
                    column.getDateFormatPattern());
            list.add(value);
        }
        return list.toArray();
    }

    public String createQuerySql(EntityDescribe entityDescribe)
            throws DaoException {
        StringBuffer selectSql = new StringBuffer(
                createSelectSql(entityDescribe, false));
        selectSql.append(" WHERE ");
        TableMapping tm = (TableMapping) entityDescribe.getClassAnnotation();
        String[] primaryKeys = getPrimaryKey(tm);

        for (int i = 0; i < primaryKeys.length; i++) {
            if (i != 0) {
                selectSql.append(" AND ");
            }
            ColumnDescribe column = entityDescribe.getColumnMap().get(
                    primaryKeys[i]);
            selectSql.append(column.getColumnName() + " = ? ");
        }
        return selectSql.toString();
    }

    public String createSelectSql(EntityDescribe entityDescribe, boolean isOrderBy)
            throws DaoException {
        TableMapping tm = (TableMapping) entityDescribe.getClassAnnotation();
        String tableName = tm.tableName();

        StringBuffer sql = new StringBuffer();

        // select column1,column2 ... columnN from tableName
        sql.append("SELECT ");

        Iterator<String> columns = entityDescribe.getColumnMap().keySet()
                .iterator();
        while (columns.hasNext()) {
            String key = columns.next();
            ColumnDescribe column = (ColumnDescribe) entityDescribe
                    .getColumnMap().get(key);
            String columnName = column.getColumnName();

            sql.append(columnName).append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" FROM ").append(tableName);
        if (isOrderBy && StringUtils.isNotBlank(tm.primaryKey())) {
            sql.append(" ORDER BY ").append(tm.primaryKey()).append(" ASC ");
        }
        return sql.toString();
    }

    public String createWhereSql(Map<String, Object> condition)
            throws DaoException {
        StringBuffer sql = new StringBuffer();
        // where
        sql.append(" WHERE ");

        // where column1=? and column2=? ... and columnX=?
        Iterator<String> keys = condition.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            if (key.equals("order by")) {
                continue;
            }

            if (key.endsWith("!=")) {
                sql.append(key.substring(0, key.length() - 2)).append("!=?");
            } else if (key.endsWith("<>")) {
                sql.append(key.substring(0, key.length() - 2)).append("<>?");
            } else if (key.endsWith("%")) {
                sql.append(key.substring(0, key.length() - 1))
                        .append(" like ?");
            } else if (key.endsWith(">")) {
                sql.append(key.substring(0, key.length() - 1)).append(">?");
            } else if (key.endsWith(">=")) {
                sql.append(key.substring(0, key.length() - 2)).append(">=?");
            } else if (key.endsWith("<")) {
                sql.append(key.substring(0, key.length() - 1)).append("<?");
            } else if (key.endsWith("<=")) {
                sql.append(key.substring(0, key.length() - 2)).append("<=?");
            } else if (key.endsWith("_in")) {
                sql.append(key.substring(0, key.length() - 3)).append(" in (")
                        .append(condition.get(key)).append(")");
            } else if (key.endsWith("_null")) {
                sql.append(key.substring(0, key.length() - 5)).append(" is ?");
            } else {
                sql.append(key).append("=?");
            }
            sql.append(" AND ");
        }
        sql.delete(sql.length() - 5, sql.length());

        String orderBy = (String) condition.remove("order by");
        if (condition.isEmpty()) {
            sql = new StringBuffer();
        }
        if (orderBy != null) {
            sql.append(" order by ").append(orderBy);
        }

        return sql.toString();
    }

    */
/**
 * 获取主键
 *
 * @param tm
 * @return
 *//*

    public String[] getPrimaryKey(TableMapping tm) {
        String[] primaryKeyNames = null;
        if (PrimaryKeyType.Single.getCode().equals(tm.primaryKeyType())) {
            primaryKeyNames = new String[1];
            primaryKeyNames[0] = tm.primaryKey();
        } else if (PrimaryKeyType.Combine.getCode().equals(
                tm.primaryKeyType())) {
            primaryKeyNames = tm.primaryKey().split(",");
        }
        return primaryKeyNames;
    }

    */

    /**
     * 校验TableMapping
     *
     * @param entityName
     * @param tableName
     * @param pkType
     * @param primaryKeyName
     */

    private void checkTableMapping(String entityName, String tableName,
                                   String pkType, String primaryKeyName) {
        // 校验表名是否为空
        if (StringUtils.isBlank(tableName)) {
            throw new DaoException(DaoErrorCode.ENT_TABNAME_NULL.getCode(),
                    new String[]{entityName});
        }
        // 校验主键类型是否为空
        if (StringUtils.isBlank(pkType)) {
            throw new DaoException(DaoErrorCode.ENT_PKTYPE_NULL.getCode(),
                    new String[]{entityName});
        }
        // 校验主键类型是否合法
        if (!(PrimaryKeyType.isCheckTypeExist(pkType))) {
            throw new DaoException(DaoErrorCode.ENT_INVALID_PKTYPE.getCode(),
                    new String[]{entityName, pkType});
        }
        // 校验主键名是否为空
        if (!PrimaryKeyType.None.getCode().equals(pkType)
                && StringUtils.isBlank(primaryKeyName)) {
            throw new DaoException(DaoErrorCode.ENT_PK_NULL.getCode(),
                    new String[]{entityName});
        }
        // 校验主键名为联合主键时的主键个数是否大于等于2
        if (PrimaryKeyType.Combine.getCode().equals(pkType)) {
            String pkNames[] = primaryKeyName.split(",");
            if (pkNames.length < 2) {
                throw new DaoException(
                        DaoErrorCode.ENT_NOT_COMBINE_PK.getCode(),
                        new String[]{entityName, primaryKeyName});
            }
        }
    }

    /**
     * 校验配置的主键名是否有效
     *
     * @param entityDescribe
     * @param dtoMap
     * @param primaryKeyName
     * @param primaryKeyNames
     */

    private void checkPrimaryKeyValid(EntityDescribe entityDescribe,
                                      Map<String, Object> dtoMap, String primaryKeyName,
                                      String[] primaryKeyNames) {
        for (int i = 0; i < primaryKeyNames.length; i++) {
            if (!dtoMap.containsKey(primaryKeyNames[i])) {
                throw new DaoException(DaoErrorCode.ENT_INVALID_PK.getCode(),
                        new String[]{entityDescribe.getEntityName(),
                                primaryKeyName});
            }
        }
    }


/**
 * 校验ColumnMapping的注解属性
 *
 *//*

    public static void checkColumnMapping(String entityName, String fieldName,
                                          String columnName, String columnType) {
        if (StringUtils.isBlank(columnName)) {
            throw new DaoException(DaoErrorCode.ENT_COLUMNNAME_NULL.getCode(),
                    new String[]{entityName, fieldName});
        }
        if (!(StringUtils.isNotBlank(columnType) && ColumnTypeEnum
                .isCheckTypeExist(columnType))) {
            throw new DaoException(
                    DaoErrorCode.ENT_INVALID_COLUMTYPE.getCode(), new String[]{
                    entityName, fieldName, columnType});
        }
    }

    */

    /**
     * 校验字段VALUE的长度和非空约束
     *
     * @param entityName
     * @param column
     * @param value
     */

    private void checkColumnLengthAndNull(String entityName,
                                          ColumnDescribe column, Object value) {
        // NA时不校验
        if (!"".equals(column.getColumnLength())) {
            /*if (ColumnTypeEnum.DOUBLE.getTypeCode().equals(column.getColumnType()) || ColumnTypeEnum.BigDecimal.getTypeCode().equals(
                    column.getColumnType())) {
                String dLens[] = column.getColumnLength().split(",");
                if (dLens != null && dLens.length != 2) {
                    throw new DaoException(
                            DaoErrorCode.ENT_INVALID_COLUMN_LENGTH.getCode(),
                            new String[]{entityName, column.getColumnName()});
                }
                int dLen1 = Integer.valueOf(dLens[0]);
                int dLen2 = Integer.valueOf(dLens[1]);
                Double dValue = (Double) value;
                String sValue = String.valueOf(dValue);
                String[] sValues = sValue.split("\\.");
                if (sValues[0].length() > dLen1) {
                    throw new DaoException(
                            DaoErrorCode.ENT_COLUMN_VALUE_THANLENGTH.getCode());
                }
                if (sValues.length == 2) {//有小数位时才校验
                    if (sValues[1].length() > dLen2) {
                        throw new DaoException(
                                DaoErrorCode.ENT_COLUMN_VALUE_THANLENGTH.getCode());
                    }
                }
            }*/
/*            if (CharUtil.getChineseStrLen((String) value) > Integer
                    .parseInt(column.getColumnLength())) {
                throw new DaoException(
                        DaoErrorCode.ENT_COLUMN_VALUE_THANLENGTH.getCode(),
                        new String[]{entityName, column.getColumnName()});
            }*/
        }

/*        // 非空校验
        if (column.getColumnIsNotNull()) {
            if (StringUtil.isNullOrEmpty(value)) {
                throw new DaoException(
                        DaoErrorCode.ENT_COLUMN_VALUE_NULL.getCode(),
                        new String[]{entityName, column.getColumnName()});
            }
        }*/
    }


    /**
     * 将参数数组转换成用逗号分隔的字符串，以便日志输出
     *
     * @param args
     * @return
     */
    public static String getArrayToString(Object[] args) {
        if (args == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean done = false;
        for (int i = 0; i < args.length; i++) {
            if (done) {
                sb.append(",");
            }
            sb.append(args[i]);
            done = true;
        }
        return sb.toString();
    }

}
