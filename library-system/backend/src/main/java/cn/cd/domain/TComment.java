package cn.cd.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_comment")
public class TComment {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String isbn;
    private Long userId;
    private String content;
    private Integer rating = 5;

    @TableField(exist = false) // 非数据库字段
    private String bookName;
}