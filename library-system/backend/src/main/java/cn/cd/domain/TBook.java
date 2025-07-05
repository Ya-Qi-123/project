package cn.cd.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;


@Data
@TableName(value = "t_book")
public class TBook {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String isbn;
    private String name;
    private BigDecimal price;
    private String author;
    private String publisher;
    private String category;
    private String language;
    private String introduction;


    private int totalQuantity;
    private int availableQuantity;

    @TableField(exist = false) // 非数据库字段
    private int bookNum;
}
