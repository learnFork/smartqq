package org.jcker.database.support;

public enum PrimaryKeyType {
        /** 单个主键 */
        Single("Single", "单个主键"),
        /** 联合主键 */
        Combine("Combine", "联合主键"),
        /** 无主键 */
        None("None", "无主键");

        private String code;
        private String name;

        PrimaryKeyType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * 校验主键类型是否非法
         * @param code
         * @return
         */
        public static boolean isCheckTypeExist(String code) {
            for (PrimaryKeyType pkType : values()) {
                if (pkType.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }
}
