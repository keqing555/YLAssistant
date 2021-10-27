package com.psi.springboot.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
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
@TableName("t_user")
public class User extends Model {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private LocalDate birthday;

    private String gender;

    private String username;

    private String password;

    private String remark;

    private String station;

    private String telephone;


}
