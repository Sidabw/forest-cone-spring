package com.sidabw.forest.cone.mybatisplus.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sidabw.forest.cone.mybatisplus.generator.entity.ModelChangeLog;
import com.sidabw.forest.cone.mybatisplus.generator.mapper.ModelChangeLogMapper;
import com.sidabw.forest.cone.mybatisplus.generator.service.IModelChangeLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feiyi
 * @since 2020-12-16
 */
@Service
public class ModelChangeLogServiceImpl extends ServiceImpl<ModelChangeLogMapper, ModelChangeLog> implements IModelChangeLogService {

    @Autowired
    private ModelChangeLogMapper modelChangeLogMapper;

    @Override
    public Object selfDesignSql1() {
        QueryWrapper<ModelChangeLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", "2");

        return modelChangeLogMapper.selfDesignSql1(queryWrapper);
    }

    @Override
    public Object selfDesignSql2() {
        QueryWrapper<ModelChangeLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", "2");

        return modelChangeLogMapper.selfDesignSql2(queryWrapper);
    }
}
