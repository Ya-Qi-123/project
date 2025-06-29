package cn.cd.query;

import lombok.Data;

@Data
public class UserQuery {
    private Integer currentPage = 1;
    private Integer pageSize = 5;

    private String email;
    private String phone;

}
