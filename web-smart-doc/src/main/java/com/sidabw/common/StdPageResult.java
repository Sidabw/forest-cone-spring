package com.sidabw.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Getter;

import java.util.List;

/**
 * @author shaogz
 * @since 2024/9/19 10:58
 */
@Getter
public class StdPageResult<T> extends StdResult<T> {

    private final long current;

    private final long pages;

    private final long total;

    private final List<T> data;

    private StdPageResult(long current, long pages, long total, List<T> data) {
        super(true, "", null);
        this.current = current;
        this.pages = pages;
        this.total = total;
        this.data = data;
    }

    public static <T> StdPageResult<T> success(IPage<T> mybatisPlusPageRes) {
        return new StdPageResult<>(mybatisPlusPageRes.getCurrent(), mybatisPlusPageRes.getPages(),
                mybatisPlusPageRes.getTotal(), mybatisPlusPageRes.getRecords());
    }

    public static <T> StdPageResult<T> success(long current, long pages, long total, List<T> data) {
        return new StdPageResult<>(current, pages, total, data);
    }

    public static <T> StdPageResult<T> empty() {
        return new StdPageResult<>(1, 0, 0, null);
    }
}
