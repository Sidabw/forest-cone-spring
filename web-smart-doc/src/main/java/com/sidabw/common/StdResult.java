package com.sidabw.common;

import lombok.Getter;

/**
 * 中联标准返回
 * @author shaogz
 * @since 2024/9/19 10:52
 */
@Getter
public class StdResult<T> {

    private final boolean result;

    private final String msg;

    private final T value;

    protected StdResult(boolean result, String msg, T value) {
        this.result = result;
        this.msg = msg;
        this.value = value;
    }

    public static <T> StdResult<T> success(T value) {
        return new StdResult<>(true, "", value);
    }

    public static <T> StdResult<T> success() {
        return new StdResult<>(true, "", null);
    }

    public static <T> StdResult<T> fail(StdFailEnum stdFailEnum) {
        return new StdResult<>(false, stdFailEnum.getValue(), null);
    }


}
