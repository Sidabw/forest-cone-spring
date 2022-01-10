package com.sidabw.mybatisplus.mysql.generator.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.sidabw.mybatisplus.mysql.generator.entity.ModelChangeLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author feiyi
 * @since 2020-12-16
 */
public interface ModelChangeLogMapper extends BaseMapper<ModelChangeLog> {

    @Select("select * from model_change_log ${ew.customSqlSegment}")
    ModelChangeLog selfDesignSql1(@Param(Constants.WRAPPER) Wrapper<ModelChangeLog> queryWrapper);

    List<ModelChangeLog> selfDesignSql2(@Param(Constants.WRAPPER) Wrapper<ModelChangeLog> ew);

}
