package com.psi.springboot.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("t_member")
public class Member extends Model {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("fileNumber")
    private String filenumber;

    private String name;

    private String sex;

    @TableField("idCard")
    private String idcard;

    @TableField("phoneNumber")
    private String phonenumber;

    @TableField("regTime")
    private LocalDate regtime;

    private String password;

    private String email;

    private LocalDate birthday;

    private String remark;


}
