package cn.cd.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;


@Data
@TableName(value = "t_book")
public class TBook {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "isbn")
    private String isbn;

    @TableField(value = "name")
    private String name;

    @TableField(value = "price")
    private BigDecimal price;
    private String author;

    @TableField(value = "publisher")
    private String publisher;

    @TableField(value = "category")
    private String category;

    @TableField(value = "language")
    private String language;

    @TableField(value = "introduction")
    private String introduction;


    @TableField(value = "total_quantity")
    private int total_quantity;

    @TableField(value = "available_quantity")
    private int available_quantity;
}
