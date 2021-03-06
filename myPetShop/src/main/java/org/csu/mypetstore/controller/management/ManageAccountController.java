package org.csu.mypetstore.controller.management;

import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/management")
public class ManageAccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/accountManagement")
    public String viewAccountList(Model model){
        System.out.println("success");
        List<Account> accountList = accountService.getAccountList();
        model.addAttribute("accountList",accountList);
        return "management/accountManagement";
    }

    @GetMapping("/removeAccount")
    public String removeAccount(String usernameList){
        System.out.println("success");
        System.out.println(usernameList);
        String[] usernameArray = usernameList.split(",");
        List<String> usernameList2 = new ArrayList<>();
        for(int i=0;i<usernameArray.length;i++){
            usernameList2.add(usernameArray[i]);
        }
        accountService.removeAccountList(usernameList2);
        return "redirect:/management/accountManagement";
    }

    @GetMapping("/getAccount")
    @ResponseBody
    public String getAccount(String username){
        Account account = accountService.getAccount(username);
        String info = account.getUsername();
        info = info + '#' + account.getFirstName();
        info = info + '#' + account.getLastName();
        info = info + '#' + account.getEmail();
        info = info + '#' + account.getPhone();
        info = info + '#' + account.getAddress1();
        info = info + '#' + account.getAddress2();
        info = info + '#' + account.getCity();
        info = info + '#' + account.getState();
        info = info + '#' + account.getZip();
        info = info + '#' + account.getCountry();
        System.out.println(info);
        return info;
    }

    @GetMapping("/updateAccount")
    public void updateAccount(String accountInfo){
        System.out.println("success");
        System.out.println(accountInfo);
        String[] accountInfoList = accountInfo.split(",");

        Account account = new Account();
        account.setUsername(accountInfoList[0]);
        account.setFirstName(accountInfoList[1]);
        account.setLastName(accountInfoList[2]);
        account.setEmail(accountInfoList[3]);
        account.setPhone(accountInfoList[4]);
        account.setAddress1(accountInfoList[5]);
        account.setAddress2(accountInfoList[6]);
        account.setCity(accountInfoList[7]);
        account.setState(accountInfoList[8]);
        account.setZip(accountInfoList[9]);
        account.setCountry(accountInfoList[10]);
        account.setLanguagePreference(accountInfoList[11]);
        account.setFavouriteCategoryId(accountInfoList[12]);
        account.setBooleanListOption(accountInfoList[13].equals("true"));
        account.setBooleanBannerOption(accountInfoList[14].equals("true"));
        if(accountInfoList.length == 15){
            account.setPassword(null);
        }
        else{
            account.setPassword(accountInfoList[15]);
        }
        if(accountService.getAccount(account.getUsername()) == null){
            account.setType(1);
            accountService.insertAccount(account);
        }
        else{
            accountService.updateAccount(account);
        }
    }
}
