package com.dhlg.module.web.tTestDemo.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dhlg.module.web.tTestDemo.entity.TTestDemo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface TTestDemoMapper extends BaseMapper<TTestDemo> {

    IPage<TTestDemo> queryByCondition(Page page, @Param("params") TTestDemo parameter);

    IPage<TTestDemo> listFieldQuery(Page page, @Param("parameter") Map<String, Object> params);
}
        