package org.csu.mypetstore.util;

public class ReturnPack {
    public boolean status;
    public String msg;
    public Object result;

    private ReturnPack(){}

    public ReturnPack(boolean status, String msg, Object result) {
        this.status = status;
        this.msg = msg;
        this.result = result;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static ReturnPack success(Object data){ return new ReturnPack(true,"",data); }

    public static ReturnPack fail(String msg){ return new ReturnPack(false,msg,null); }

    public static ReturnPack fail(Object msg){ return new ReturnPack(false,"",msg); }
}
