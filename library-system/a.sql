-- 创建用户表
CREATE TABLE IF NOT EXISTS t_user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL
);

-- 创建评论表
CREATE TABLE IF NOT EXISTS t_comment (
    comment_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL
);

-- 添加外键约束
ALTER TABLE t_comment
    ADD CONSTRAINT t_comment_ibfk_1
    FOREIGN KEY (user_id) REFERENCES t_user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;