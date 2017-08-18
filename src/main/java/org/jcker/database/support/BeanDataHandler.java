package org.jcker.database.support;

import org.jcker.database.exception.DaoException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author cqchenf@web.com
 * @date 2010-8-10
 * @version v1.0
 */
public class BeanDataHandler {

	/**
	 * 对象转换方法
	 * 
	 * @param value
	 * @param columnTypeClazz
	 * @return
	 */
	public static Object trans(Object value, Class<?> columnTypeClazz,
			String dateFormatPattern) throws DaoException {
		if (!StringUtils.isEmpty((String)value)) {
			if (value instanceof String) {
				String v = (String) value;
				if (StringUtils.isNotBlank(v)) {
/*					if (columnTypeClazz.equals(java.sql.Date.class)) {
						return DateTimeUtil.getStrToDate(v, dateFormatPattern);
					}
					if (columnTypeClazz.equals(java.util.Date.class)) {
						return DateTimeUtil.getStrToDateTime(v, dateFormatPattern);
					}
					if (columnTypeClazz.equals(Time.class)) {
						return DateTimeUtil.getStrToTime(v, dateFormatPattern);
					}
					if (columnTypeClazz.equals(Timestamp.class)) {
						return DateTimeUtil.getStrToTimestamp(v,
								dateFormatPattern);
					}*/
					if (columnTypeClazz.equals(Long.class)) {
						return Long.valueOf(v);
					}
					if (columnTypeClazz.equals(Short.class)) {
						return Short.valueOf(v);
					}
					if (columnTypeClazz.equals(Integer.class)) {
						return Integer.valueOf(v);
					}
					if (columnTypeClazz.equals(Float.class)) {
						return Float.valueOf(v);
					}
					if (columnTypeClazz.equals(Double.class)) {
						return Double.valueOf(v);
					}
				}
			}
		}
		return value;
	}

	public static void main(String[] args) {
		// System.out.println(trans("2012-09-07 12:09:09", Date.class));
		
//		System.out.println(ColumnTypeEnum.BLOB);
	}
}
