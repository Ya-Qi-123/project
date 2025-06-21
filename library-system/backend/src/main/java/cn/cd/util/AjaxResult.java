package cn.cd.util;

import lombok.Data;

@Data
public class AjaxResult {
    private Boolean success = true;
    private String message = "操作成功";
    private Object data;

    // 1.构造器
    public AjaxResult(){}
    public AjaxResult(Boolean success, String message){
        this.success = success;
        this.message = message;
    }
    public AjaxResult(Boolean success, String message, Object data){
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // 2.链式语法
    public static AjaxResult me(){
        return new AjaxResult();
    }
    public AjaxResult setSuccess(Boolean success) {
        this.success = success;
        return this;
    }
    public AjaxResult setMessage(String message) {
        this.message = message;
        return this;
    }
    public AjaxResult setData(Object data) {
        this.data = data;
        return this;
    }

    // 3.简化调用
    public static AjaxResult ok(){
        return new AjaxResult();
    }
    public static AjaxResult ok(Object data){
        return new AjaxResult().setData(data);
    }
    public static AjaxResult fail(){
        return new AjaxResult().setSuccess(false);
    }
    public static AjaxResult fail(Object data){
        return new AjaxResult().setSuccess(false).setData(data);
    }
}
