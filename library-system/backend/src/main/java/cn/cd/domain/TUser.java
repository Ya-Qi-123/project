package cn.cd.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

@TableName(value ="t_user")
@Data
public class TUser implements Serializable {

    @TableId(value = "id")
    private Long id;

    private String username;
    private String password;

    @TableField(value = "email")
    private String email;

    private String phone;
    private String gender;
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}