package com.sidabw.common;

import lombok.Getter;

/**
 * @author shaogz
 * @since 2024/9/24 10:14
 */
@Getter
public enum StdFailEnum {

    ROLE_MISSING_ADMINISTRATION("操作失败：行政人员角色缺失"),

    ROLE_MISSING_SIGNER("操作失败：签发人角色缺失"),

    DUPLICATE_ASSIGN_OPERATION("操作失败：项目已完成分配"),

    ;

    private final String value;


    StdFailEnum(String value) {
        this.value = value;
    }
}
