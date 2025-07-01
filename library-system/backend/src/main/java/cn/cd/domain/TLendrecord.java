package cn.cd.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName(value ="t_lendrecord")
public class TLendrecord {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long bookId;

    private Long userId;


    private String rentTime;

    private String returnTime;

    private String category;
    private String bookname;
    private Integer status; // 0已还,1未还

}
