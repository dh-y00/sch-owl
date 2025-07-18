package com.sch.owl.domain.constants.resource;

import lombok.Getter;

public interface EleConstant {

    @Getter
    enum EleTypeEnum {
        CATALOG("M", "目录"),
        MENU("C", "菜单"),
        BUTTON("F", "按钮");

        private String code;

        private String desc;

        EleTypeEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

}
