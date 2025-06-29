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
}
