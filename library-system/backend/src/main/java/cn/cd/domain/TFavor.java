package cn.cd.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_favorite")
public class TFavor {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String isbn;

    @TableField("user_id")
    private Long userId;

    @TableField("book_name")
    private String bookName;

    @TableField("author")
    private String author;
}