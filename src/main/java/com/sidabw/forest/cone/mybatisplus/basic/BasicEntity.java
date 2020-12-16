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
package com.sidabw.forest.cone.mybatisplus.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId(value = "id")
    private String id;

    private String gmt8Created;

    private String gmt8Modified;

    private Integer isDelete;

    private String createdUserId;

}
