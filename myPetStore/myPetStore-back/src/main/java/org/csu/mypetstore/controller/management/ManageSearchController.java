package org.csu.mypetstore.controller.management;

import org.csu.mypetstore.util.ReturnPack;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManageSearchController {

    //最上方的搜索
    @GetMapping("/management/accounts/results")
    public ReturnPack Search(@RequestParam String key){

        return null;
    }

    //查询种类

    //查询产品类型

    //查询产品

    //查新账户

}
