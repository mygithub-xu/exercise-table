package com.dhlg.module.web.templateTree.controller;

import com.dhlg.module.web.templateTree.entity.TemplateTree;
import com.dhlg.module.web.templateTree.service.ITemplateTreeService;
import com.dhlg.utils.Result;
import com.dhlg.utils.common.StringUtils;
import com.dhlg.module.common.ParamIsNullException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xu
 * @since
 */
@RestController
@RequestMapping("/api/test/templateTree")
@CrossOrigin
public class TemplateTreeController {

        @Autowired
        ITemplateTreeService doService;

        @ApiOperation("保存或者更新")
        @PostMapping("/saveOrUpdate")
        public Result saveOrUpdate(@RequestBody TemplateTree templateTree) {
                if (StringUtils.isBlank(templateTree)) {
                        throw new ParamIsNullException();
                }
                return doService.saveOrUpdateCommon(templateTree);
        }

        @ApiOperation("获取tree状结构1")
        @PostMapping("/query")
        public Result query() {
                return doService.getTree();
        }

        @ApiOperation("获取tree状结构2")
        @PostMapping("/query2")
        public Result query2() {
                return doService.getTree2();
        }

}


