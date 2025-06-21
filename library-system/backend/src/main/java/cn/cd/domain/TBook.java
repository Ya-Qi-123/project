package cn.cd.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value ="t_book")
public class TBook {
    @TableId(value = "id")
    private Long id;

    private String name;
    private String author;
    private String price;
    private String publisher;
    private String isbn;
    private String status;
}
