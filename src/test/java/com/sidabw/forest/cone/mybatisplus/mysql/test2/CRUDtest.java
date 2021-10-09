/**
 * Copyright (C), 2018-2020, zenki.ai
 * FileName: CRUDtest
 * Author:   feiyi
 * Date:     2020/12/16 3:20 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.forest.cone.mybatisplus.mysql.test2;

//import com.sidabw.forest.cone.mybatisplus.generator.entity.ModelChangeLog;
//import com.sidabw.forest.cone.mybatisplus.generator.service.IModelChangeLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sidabw.forest.cone.mybatisplus.generator.entity.ModelChangeLog;
import com.sidabw.forest.cone.mybatisplus.generator.service.IModelChangeLogService;
import com.sidabw.forest.cone.mybatisplus.util.JacksonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 〈一句话功能简述〉:
 * 〈CRUD test.
 *  有关QueryWrapper、UpdateWrapper的使用：https://blog.csdn.net/qq_43378945/article/details/95353989
 *  默认null不更新，那要跟新一个null怎么办？（未测试） https://www.cnblogs.com/jcb1991/p/13689922.html
 *
 *  自增主键， 这个就不测试了。正经分布式应用哪有用主键自增的。
 * 〉
 *
 * @author feiyi
 * @create 2020/12/16
 * @since 1.0.0
 */
@SpringBootTest
public class CRUDtest {

    @Autowired
    private IModelChangeLogService modelChangeLogService;

    @Test
    public void saveTest(){
        ModelChangeLog modelChangeLog = new ModelChangeLog();
//        modelChangeLog.setId("3");
        modelChangeLog.setIsDelete(0);
        modelChangeLog.setCreatedUserId("18");
        modelChangeLog.setGmt8Created("2020-12-11 09:49:10:064");
        modelChangeLog.setGmt8Modified("2020-12-11 09:49:10:064");
        modelChangeLog.setModelVersion("v-1.2.1");
        modelChangeLog.setModelDescribe("asdjlkjlk");
        modelChangeLogService.save(modelChangeLog);
        //还有俩批量插入的就不测了
        // 插入（批量）
        //boolean saveBatch(Collection<T> entityList);
        // 插入（批量）
        //boolean saveBatch(Collection<T> entityList, int batchSize);
    }

    @Test
    public void saveOrUpdateTest(){
        ModelChangeLog modelChangeLog = new ModelChangeLog();
        modelChangeLog.setId("2");
        modelChangeLog.setIsDelete(0);
        modelChangeLog.setCreatedUserId("18");
        modelChangeLog.setGmt8Created("2020-12-11 09:49:10:064");
        modelChangeLog.setGmt8Modified("2020-12-11 09:49:10:064");
        modelChangeLog.setModelVersion("v-1.2.1");
        modelChangeLog.setModelDescribe("ddda");
        //id存在即更新（不用entity上非得有@TableId的注解，官网有误导。。）
        //经过测试，这里是updateSelective
        modelChangeLogService.saveOrUpdate(modelChangeLog);

        //不测了
        // 根据updateWrapper尝试更新，否继续执行saveOrUpdate(T)方法
        //boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper);
        // 批量修改插入
        //boolean saveOrUpdateBatch(Collection<T> entityList);
        // 批量修改插入
        //boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);
    }

    @Test
    public void removeTest(){
        QueryWrapper<ModelChangeLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("model_describe", "ddda");
        boolean removeResult = modelChangeLogService.remove(queryWrapper);
        System.out.println(removeResult);

        //不测了
        // 根据 ID 删除
        //boolean removeById(Serializable id);
        // 根据 columnMap 条件，删除记录
        //boolean removeByMap(Map<String, Object> columnMap);
        // 删除（根据ID 批量删除）
        //boolean removeByIds(Collection<? extends Serializable> idList);
    }

    @Test
    public void updateTest(){
        UpdateWrapper<ModelChangeLog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("model_describe", "ddda").and(i -> i.eq("model_version", "v-1.2.1"));
        //同样是null的就不更新
        ModelChangeLog modelChangeLog = new ModelChangeLog();
        modelChangeLog.setModelDescribe("aaa");
        boolean update = modelChangeLogService.update(modelChangeLog, updateWrapper);
        System.out.println(update);

        //不测了
        // 根据 ID 选择修改
        //boolean updateById(T entity);
        // 根据ID 批量更新
        //boolean updateBatchById(Collection<T> entityList);
        // 根据ID 批量更新
        //boolean updateBatchById(Collection<T> entityList, int batchSize);
    }

    @Test
    public void getTest(){
        QueryWrapper<ModelChangeLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("model_describe", "aaa").and(i -> i.eq("model_version", "v-1.2.1"));
        // 根据 Wrapper，查询一条记录。结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")
        ModelChangeLog one = modelChangeLogService.getOne(queryWrapper);
        System.out.println(JacksonUtils.convertToJsonStr(one));

        //不测了
        // 根据 ID 查询
        //T getById(Serializable id);
        // 根据 Wrapper，查询一条记录
        //T getOne(Wrapper<T> queryWrapper, boolean throwEx);
        // 根据 Wrapper，查询一条记录
        //Map<String, Object> getMap(Wrapper<T> queryWrapper);
        // 根据 Wrapper，查询一条记录
        //<V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);
    }

    @Test
    public void listTest(){
        QueryWrapper<ModelChangeLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("model_describe", "aaa").and(i -> i.eq("model_version", "v-1.2.1"));
        List<ModelChangeLog> result = modelChangeLogService.list(queryWrapper);
        System.out.println(JacksonUtils.convertToJsonStr(result));

        //不测了
        // 查询所有
        //List<T> list();
        // 查询（根据ID 批量查询）
        //Collection<T> listByIds(Collection<? extends Serializable> idList);
        // 查询（根据 columnMap 条件）
        //Collection<T> listByMap(Map<String, Object> columnMap);
        // 查询所有列表
        //List<Map<String, Object>> listMaps();
        // 查询列表
        //List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper);
        // 查询全部记录
        //List<Object> listObjs();
        // 查询全部记录
        //<V> List<V> listObjs(Function<? super Object, V> mapper);
        // 根据 Wrapper 条件，查询全部记录
        //List<Object> listObjs(Wrapper<T> queryWrapper);
        // 根据 Wrapper 条件，查询全部记录
        //<V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);
    }

    @Test
    public void pageTest(){
        QueryWrapper<ModelChangeLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("model_describe", "aaa").and(i -> i.eq("model_version", "v-1.2.1"));

        IPage<ModelChangeLog> page = new Page<>();
        page.setCurrent(1);
        page.setSize(10);

        IPage<ModelChangeLog> pageRes = modelChangeLogService.page(page, queryWrapper);
        System.out.println(JacksonUtils.convertToJsonStr(pageRes));

        //不测了
        // 无条件分页查询
        //IPage<T> page(IPage<T> page);
        // 无条件分页查询
        //IPage<Map<String, Object>> pageMaps(IPage<T> page);
        // 条件分页查询
        //IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper);
    }

    @Test
    public void countTest(){
        System.out.println(modelChangeLogService.count());

        //不测了
        // 根据 Wrapper 条件，查询总记录数
        //int count(Wrapper<T> queryWrapper);
    }

    @Test
    public void chainTest(){
        //链式查询：注意，不是join关联查询，只是一种新的查询代码形式
        List<ModelChangeLog> res = modelChangeLogService.lambdaQuery().eq(ModelChangeLog::getModelDescribe, "aaa").list();
        System.out.println(JacksonUtils.convertToJsonStr(res));

        //不测了
        // 链式查询 普通
        //QueryChainWrapper<T> query();
        // 示例：
        //query().eq("column", value).one();

        // 链式更改 普通
        //UpdateChainWrapper<T> update();
        // 链式更改 lambda 式。注意：不支持 Kotlin
        //LambdaUpdateChainWrapper<T> lambdaUpdate();
        // 示例：
        //update().eq("column", value).remove();
        //lambdaUpdate().eq(Entity::getId, value).update(entity);
    }

    //官网还有Mapper层的CRUD就不测试了，感觉上边这些就够用了

    @Test
    public void idAutoGenTest(){
        //就配置个TableId 同时id类型为String，那么就会自动生成id
        //默认使用雪花算法+UUID生成id。当然也可以自定义id生成器。这个看官网吧。
        ModelChangeLog modelChangeLog = new ModelChangeLog();
        modelChangeLog.setModelVersion("v-1.2.2");
        modelChangeLog.setModelDescribe("kkkdddasdfasdfkk");

        modelChangeLogService.save(modelChangeLog);
        //自动生成id会存在当前对象里。
        System.out.println(JacksonUtils.convertToJsonStr(modelChangeLog));
    }
}
