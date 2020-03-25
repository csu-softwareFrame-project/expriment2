package org.csu.mypetstore;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private AccountService accountService;

    @Test
    void contextLoads() {
        System.out.println("a");
    }

    @Test
    void test(){
        Account account = new Account();
        account.setUsername("j2ee");
        account = accountService.login(account);

        System.out.println(account.getUsername() + " " + account.getEmail());
    }

}
