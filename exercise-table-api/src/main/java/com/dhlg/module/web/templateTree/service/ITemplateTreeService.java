package com.dhlg.module.web.templateTree.service;

import com.dhlg.module.web.templateTree.entity.TemplateTree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dhlg.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xu
 * @since
 */
public interface ITemplateTreeService extends IService<TemplateTree> {

    Result saveOrUpdateCommon(TemplateTree templateTree);

    Result getTree();

    Result getTree2();
}
