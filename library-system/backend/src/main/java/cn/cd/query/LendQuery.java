package cn.cd.query;

import lombok.Data;

@Data
public class LendQuery {
    private Integer currentPage = 1;
    private Integer pageSize = 5;

    private Long book_id;
    private Long user_id;
    private String bookname;
    private String category;
    private Integer status;

    public Long getUser_id() {
        return user_id;
    }
    public String getBookname() {
        return bookname;
    }
    public String getCategory() {
        return category;
    }
    public Integer getStatus() {
        return status;
    }
}
