package com.sidabw.ann.cache.basic2.run;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author feiyi
 * @create 2021/3/22
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelEntity implements Serializable {

    private String name;

    private String describe;

}
