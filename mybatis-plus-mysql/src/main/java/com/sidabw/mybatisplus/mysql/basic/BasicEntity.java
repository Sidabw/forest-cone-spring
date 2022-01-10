/**
 * Copyright (C), 2018-2020, zenki.ai
 * FileName: BasicEntity
 * Author:   feiyi
 * Date:     2020/12/16 10:51 AM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.mybatisplus.mysql.basic;

import lombok.Data;

/**
 * 〈一句话功能简述〉:
 * 〈〉
 *
 * @author feiyi
 * @create 2020/12/16
 * @since 1.0.0
 */
@Data
public class BasicEntity {

    //不标记@TableId执行插入操作时，id没有值，则会生成默认值（雪花算法）
//    @TableId(value = "id")
    private String id;

    private String gmt8Created;

    private String gmt8Modified;

    private Integer isDelete;

    private String createdUserId;

}
