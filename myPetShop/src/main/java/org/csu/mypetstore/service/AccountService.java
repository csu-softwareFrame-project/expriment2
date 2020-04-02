package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    public Account getAccount(String username) {  //获取用户信息（修改信息时使用）
        return accountMapper.getAccountByUsername(username);
    }

    public Account getAccount(String username, String password) {  //获取用户（登录时使用）
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        return accountMapper.getAccountByUsernameAndPassword(account);
    }

    public void insertAccount(Account account) {  //新增用户
        accountMapper.insertAccount(account);
        accountMapper.insertProfile(account);
        accountMapper.insertSignon(account);
    }

    public void updateAccount(Account account) {  //更新用户信息
        accountMapper.updateAccount(account);
        accountMapper.updateProfile(account);

        if (account.getPassword() != null && account.getPassword().length() > 0) {
            accountMapper.updateSignon(account);
        }
    }
}
