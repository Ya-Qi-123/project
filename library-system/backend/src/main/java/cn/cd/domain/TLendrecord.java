package cn.cd.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value ="t_lendrecord")
public class TLendrecord {
    private Long id;

    @TableField(value = "book_id")
    private String book_id;

    @TableField(value = "user_id")
    private String user_id;

    @TableField(value = "rent_time")
    private String rent_time;

    @TableField(value = "return_time")
    private String return_time;

    @TableField(value = "category")
    private String category;
    private String bookname;
    private Integer status; // 0已还,1未还


    private int categoryLendNum; //每个类的图书被借阅次数
    private int booknameLendNum; // 每种图书被借阅次数
    private int userRentNum; // 每个用户借书次数
    private  int statusSum;
}
