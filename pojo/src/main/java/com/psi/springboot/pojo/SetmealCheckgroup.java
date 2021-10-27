package com.psi.springboot.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author psi
 * @since 2021-10-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_setmeal_checkgroup")
public class SetmealCheckgroup extends Model {

    private static final long serialVersionUID = 1L;

      private Integer setmealId;

    private Integer checkgroupId;


}
