package com.sidabw.mybatisplus.mysql.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sidabw.mybatisplus.mysql.generator.entity.ModelChangeLog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feiyi
 * @since 2020-12-16
 */
public interface IModelChangeLogService extends IService<ModelChangeLog> {

    Object selfDesignSql1();

    Object selfDesignSql2();
}
