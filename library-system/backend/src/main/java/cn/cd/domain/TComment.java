package cn.cd.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_comment")
public class TComment {
    private Long id;
    private String isbn;

    @TableField("user_id")
    private Long userId;

    @TableField("content")
    private String content;

    @TableField("rating")
    private Integer rating = 5;

}