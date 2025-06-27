package cn.cd.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_favorite")
public class TFavor {
    private Long id; // 添加id字段，数据库自增

    @TableField("isbn")
    private String isbn;

    @TableField("user_id")
    private Long userId;

    @TableField("book_name")
    private String bookName;

    @TableField("author")
    private String author;
}