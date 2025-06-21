package cn.cd.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value ="t_lendrecord")
public class TLendrecord {
    private Long id;

    @TableField(value = "book_id")
    private String bookid;

    @TableField(value = "user_id")
    private String userid;

    @TableField(value = "lend_time")
    private String lendtime;

    @TableField(value = "return_time")
    private String returntime;

    @TableField(value = "status")
    private String status;

    @TableField(value = "category")
    private String category;
}
