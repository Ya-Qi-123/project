package cn.cd.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_admin")
public class TAdmin {
    private Long id;

    @TableField(value = "adminname")
    private String adminname;

    @TableField(value = "password")
    private String password;

    @TableField(value = "email")
    private String email;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAdminname() { return adminname; }
    public void setAdminname(String adminname) { this.adminname = adminname; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}