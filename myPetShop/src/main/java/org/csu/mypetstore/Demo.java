package org.csu.mypetstore;


import org.csu.mypetstore.persistence.AccountMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Demo {
    @Autowired
    AccountMapper accountMapper;

    @Test
    public void test(){
        System.out.println(accountMapper);
        System.out.println("hello");
        /**
         * 方法一：注解Test
         * 方法二：断言assert
         */
    }
}
