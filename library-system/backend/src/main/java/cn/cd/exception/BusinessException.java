package cn.cd.exception;

public class BusinessException extends RuntimeException {
    // 保留原始异常堆栈
    public BusinessException(String message) {
        super(message); // 关键：设置父类message
    }

}
