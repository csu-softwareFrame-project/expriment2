package org.csu.mypetstore.controller.management;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
