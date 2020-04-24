package org.csu.mypetstore.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.AccountMapper;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ManageController {
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    AccountService accountService;

    //最上方的搜索
    @GetMapping("/management/accounts/results")
    public ReturnPack Search(@RequestParam String key){

        return null;
    }

    //获取所有帐号信息
    @GetMapping("/management/accounts")
    public ReturnPack allAccounts(){
        JSONObject data = new JSONObject();
        List<Account> accountList = accountMapper.getAccountList();
        data.put("accountList",accountList);
        return ReturnPack.success(data);
    }

    //删除帐号
    @DeleteMapping("/management/accounts")
    public ReturnPack removeAccounts(){
        return null;
    }

    //添加帐号
    @PostMapping("/management/accounts")
    public ReturnPack addAccount(@RequestBody Account account){
        return null;
    }

    @PutMapping("/management/accounts")
    public ReturnPack editAccount(@RequestBody Account account){
        JSONObject data = new JSONObject();
        System.out.println(account);
        if (account.getListOption().equals("true")) account.setBooleanListOption(true);
        else account.setBooleanListOption(false);
        if (account.getBannerOption().equals("true")) account.setBooleanBannerOption(true);
        else account.setBooleanBannerOption(false);
        System.out.println("l:" + account.isBooleanListOption() + "  b:" + account.isBooleanBannerOption());

        if (account.getLanguagePreference() == null) {
            account.setLanguagePreference("English");
        } else if (account.getLanguagePreference().equals("")) {
            account.setLanguagePreference("English");
        }

        System.out.println("修改后:" + account);
        //将修改信息同步到数据库
        accountService.updateAccount(account);

        data.put("account", account);
        data.put("msg","修改成功");
        return ReturnPack.success(data);
    }

}
