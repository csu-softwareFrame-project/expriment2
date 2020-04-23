package org.csu.mypetstore.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.AccountMapper;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ManageController {
    @Autowired
    AccountMapper accountMapper;

    @GetMapping("/management/accounts/results")
    public ReturnPack Search(@RequestParam String key){

        return null;
    }

    @GetMapping("/management/accounts")
    public ReturnPack allAccounts(){
        JSONObject data = new JSONObject();
        List<Account> accountList = accountMapper.getAccountList();
        data.put("accountList",accountList);
        return ReturnPack.success(data);
    }

    @DeleteMapping("/management/accounts")
    public ReturnPack removeAccounts(){
        return null;
    }

}
