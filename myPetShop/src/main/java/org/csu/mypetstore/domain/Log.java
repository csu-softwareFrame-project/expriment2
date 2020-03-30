package org.csu.mypetstore.domain;

public class Log {
    private String time;
    private String log;
    private String log2;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getLog2() {
        return log2;
    }

    public void setLog2(String log2) {
        this.log2 = log2;
    }

    public void construct(String user, String action, String obj){
        if (action.equals("/petstore/viewCategory") || action.equals("/petstore/viewProduct") || action.equals("/petstore/viewItem")){
            log2 = user + " " + "had looked through " + obj +".";
        }
        else if(action.equals("/petstore/addCartToItem")){
            log2 = user + " " + "had taken " + obj + " to cart.";
        }
        else if (action.equals("/petstore/removeItemFromCart")){
            log2 = user + " " + "had removed " + obj + " to cart.";
        }
        else if (action.equals("/petstore/confirm")){
            log2 = user + " " + "had confirmed an order.";
        }
        else{
            log2 = "No action.";
        }
    }
}
