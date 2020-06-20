package org.csu.mypetstore.controller.management;

import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.MailService;
import org.csu.mypetstore.util.ReturnPack;
import org.csu.mypetstore.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
public class ManageAccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    MailService mailService;

    //获取所有帐号信息
    @GetMapping("/management/accounts")
    public ReturnPack allAccounts(){
        JSONObject data = new JSONObject();
        List<Account> accountList = accountService.getAccountList();
        data.put("accountList",accountList);
        return ReturnPack.success(data);
    }

    //todo 删除帐号
    @DeleteMapping("/management/accounts")
    public ReturnPack removeAccounts(@RequestBody List<String> accountList){
        System.out.println(accountList);
        for (int i = 0; i < accountList.size(); i++) {
            accountService.removeAccount(accountList.get(i));
        }
        JSONObject data = new JSONObject();
        return ReturnPack.success(data);
    }

    //添加帐号
    @PostMapping("/management/accounts")
    public ReturnPack addAccount(@RequestBody Account account){
        JSONObject data = new JSONObject();
        String username = account.getUsername();
        String password = account.getPassword();
//        String email = account.getEmail();
        if(accountService.getAccount(username)!=null) return ReturnPack.fail("该用户名已存在");
        else{
            try{
                String enc_password = passwordEncoder.encode(password);
                account.setPassword(enc_password);
                accountService.insertAccount(account);//插入数据库
            }catch (Exception e){
                e.printStackTrace();
                return ReturnPack.fail("服务器错误");
            }
            data.put("account",accountService.getAccount(username));
            return ReturnPack.success(data);
        }
    }

    //修改账户
    @PutMapping("/management/accounts")
    public ReturnPack editAccount(@RequestBody Account account){
        JSONObject data = new JSONObject();
//        System.out.println(account);
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

    //重置密码
    @PutMapping("/management/accounts/passwords")
    public ReturnPack resetPassword(@RequestBody Account account){
        System.out.println(account.getUsername()+"重置密码");
        //todo 随机生成密码，发送给用户邮箱
        String newpass = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的重置密码为："+newpass;
        try {
            mailService.sendSimpleMail(account.getEmail(), "petstore:重置密码", message);
        }catch (Exception e){
            e.printStackTrace();
            return ReturnPack.fail("发送失败，推测是邮箱问题");
        }
//        String enc_password = passwordEncoder.encode(password);//加密码
//        Account account1 = accountService.getAccount(account.getUsername()).setPassword(enc_password)
//        accountService.updateAccount(account1);
        JSONObject data = new JSONObject();
        return ReturnPack.success(data);
    }
}
