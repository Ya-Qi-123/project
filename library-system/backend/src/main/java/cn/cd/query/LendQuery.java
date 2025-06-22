package cn.cd.query;

import lombok.Data;

@Data
public class LendQuery {
    private Integer currentPage = 1;
    private Integer pageSize = 3;

    private String book_id;
    private String user_id;
    private String bookname;
    private String category;
    private Integer status;
}
