package cn.cd.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_favorite")
public class TFavorite {
    private Long id;
    private Long bookId;
    private Long userId;
    private String bookName;
    private String author;
}