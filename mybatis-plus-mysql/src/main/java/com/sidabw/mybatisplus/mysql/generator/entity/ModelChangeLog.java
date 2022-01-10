package com.sidabw.mybatisplus.mysql.generator.entity;

import com.sidabw.mybatisplus.mysql.basic.BasicEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author feiyi
 * @since 2020-12-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ModelChangeLog extends BasicEntity {

    private static final long serialVersionUID = 1L;

    private String modelVersion;

    private String modelDescribe;


}
