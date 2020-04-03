package org.csu.mypetstore.service;

import org.csu.mypetstore.Constants;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.AccountMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    //根据用户名获取账户信息
    public Account getAccount(String username) {
        return accountMapper.getAccountByUsername(username);
    }

    //因为耦合被弃用
    public Account getAccount(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        return accountMapper.getAccountByUsernameAndPassword(account);
    }

    //todo insertProfile有问题
    public void insertAccount(Account account) {  //使用时注意填写信息要完整，不然可能出现前面一个表插入以后后面的表插入失败，这样子再次插入时会提示用户已存在
        accountMapper.insertAccount(account);
        accountMapper.insertProfile(account);
        accountMapper.insertSignon(account);
    }

    public void updateAccount(Account account) {
        accountMapper.updateAccount(account);
        accountMapper.updateProfile(account);

        if (account.getPassword() != null && account.getPassword().length() > 0) {
            accountMapper.updateSignon(account);
        }
    }

    public String getPassword(String username){
        return accountMapper.getPasswordByUsername(username);
    }

    public boolean userAccessService(String username, String password, Map<String,Object> map, Model model){
        String pswd = accountMapper.getPasswordByUsername(username);
        if(pswd == null){
            if(Constants.DEBUG_MODE&&Constants.DEBUG_CONTROLLER)System.out.println("用户名不存在");
            map.put("msg","用户名不存在");
            pswd = "";
        }
        if(pswd.equals(password)) {
            if(Constants.DEBUG_MODE&&Constants.DEBUG_CONTROLLER)System.out.println("登录成功");
            Account account = getAccount(username);
            model.addAttribute("loginUser",account);//登录成功把用户信息放进session
            System.out.println(username+"  "+account);
            return true;
        }else {
            if(Constants.DEBUG_MODE&&Constants.DEBUG_CONTROLLER)System.out.println("用户名或密码错误");
            map.put("msg","用户名或密码错误");
        }
        return false;
    }

    public boolean signUpservice(Account account,String repeatedPassword, Map<String,Object> map){
        boolean success = true;
        if(account.getUsername().equals("")){
            System.out.println("用户名不能为空");
            map.put("username_msg","用户名不能为空");
            success =false;
        }
        if(account.getPassword().equals("")){
            System.out.println("密码不能为空");
            map.put("password_msg","密码不能为空");
            success = false;
        }
        if(repeatedPassword.equals("")){
            System.out.println("重复密码不能为空");
            map.put("repeated_password_msg","重复密码不能为空");
            success = false;
        }
        if(getPassword(account.getUsername()) != null){
            System.out.println(getPassword(account.getUsername()));
            if(getPassword(account.getUsername()) != ""){
                System.out.println("用户名已存在");
                map.put("username_msg","用户名已存在");
                success = false;
            }
        }
        if(!account.getPassword().equals(repeatedPassword)){
            System.out.println("重复密码有误");
            map.put("repeated_password_msg","重复密码有误");
            success = false;
        }

        if(success) {
            //前端的checkbox勾选了，返回on，没勾选，返回null;将其转化为boolean
            if(account.getListOption() != null){
                if (account.getListOption().equals("on")) account.setBooleanListOption(true);
                else account.setBooleanListOption(false);
            }else{
                account.setBooleanListOption(false);
            }
            if(account.getBannerOption() != null){
                if(account.getBannerOption().equals("on")) account.setBooleanBannerOption(true);
                else account.setBooleanBannerOption(false);
            }else {
                account.setBooleanBannerOption(false);
            }
            if(account.getLanguagePreference() == null){
                account.setLanguagePreference("English");
            }else if(account.getLanguagePreference().equals("")){
                account.setLanguagePreference("English");
            }
            insertAccount(account);//插入数据库
        }
        return success;
    }
}