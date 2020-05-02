package org.csu.mypetstore.controller.management;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ManageAccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("management/accountManagement")
    public String viewAccountList(Model model){
        System.out.println("success");
        List<Account> accountList = accountService.getAccountList();
        model.addAttribute("accountList",accountList);
        return "management/accountManagement";
    }
}
