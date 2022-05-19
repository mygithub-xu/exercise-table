package com.dhlg.module.web.tProductRecord.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_product_record")
public class TProductRecord implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
        *id
        */
        @TableId("id")
        private String id;

        /**
         * 
         */
        @TableField("userId")
        private int userId;
        /**
         * 
         */
        @TableField("productNo")
        private String productNo;

        BigDecimal dd;
}
