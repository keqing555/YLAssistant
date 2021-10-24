package com.psi.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("t_checkitem")
public class Checkitem extends Model {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String code;

    private String name;

    private String sex;

    private String age;

    private Float price;

    /**
     * 查检项类型,分为检查和检验两种
     */
    private String type;

    private String attention;

    private String remark;


}
