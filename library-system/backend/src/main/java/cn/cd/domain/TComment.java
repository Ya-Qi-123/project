package cn.cd.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@TableName("t_comment")
public class TComment {
    private Long id;

    @TableField("isbn")
    @NotBlank(message = "ISBN不能为空")
    private String isbn;

    @TableField("user_id")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @TableField("content")
    @Size(min = 5, max = 500, message = "评论长度需在5-500字符")
    private String content;

    @TableField("rating")
    @Min(value = 1, message = "评分不能低于1分")
    @Max(value = 5, message = "评分不能超过5分")
    private Integer rating = 5;

    @TableField(exist = false) // 非数据库字段
    private String bookName; // 新增书名字段
}