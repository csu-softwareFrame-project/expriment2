package org.csu.mypetstore.service;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.csu.mypetstore.Constants;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.AccountMapper;
import org.csu.mypetstore.util.JwtUtil;
import org.csu.mypetstore.util.ReturnPack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.stream.XMLStreamReader;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
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

    //todo 验证码部分
    public ReturnPack signInService(String username, String password, String checkCode, String getterToken){
        JSONObject data = new JSONObject();
        String pswd = accountMapper.getPasswordByUsername(username);
        if(pswd == null){
            if(Constants.DEBUG_MODE&&Constants.DEBUG_CONTROLLER)System.out.println("用户名不存在");
            return ReturnPack.fail("用户名不存在");
        }
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        //密码验证成功
        if(passwordEncoder.matches(password,pswd)) {
            //生成token
            String token = JwtUtil.generate(account.getUsername());
            data.put("token",token);
            String failToken = JwtUtil.generateFail(account.getUsername());
            data.put("failToken",failToken);
//            System.out.println(JwtUtil.decode(token, JwtUtil.SECRET).get("name").asString());

            //验证码验证部分
//            String right = (String)session.getAttribute("checkCode");//从服务器获取正确验证码
//            if(checkCode != null && right != null){
//                if(right.isEmpty()){
//                    map.put("msg","请获取邮箱验证码");
//                    return false;
//                }
//                if(checkCode.isEmpty()){
//                    map.put("msg","请输入收到的邮箱验证码");
//                    return false;
//                }
//                if(!right.equals(checkCode) && Constants.EMAIL_VERIFY){
//                    map.put("msg","邮箱验证码错误");
//                    return false;
//                }
//            }else if(checkCode == null){
//                map.put("msg","请输入验证码");
//                return false;
//            }else if(right == null){
//                map.put("msg","验证码发送失败，请检查您的网络情况");
//                return false;
//            }
            if(Constants.DEBUG_MODE&&Constants.DEBUG_CONTROLLER)System.out.println("登录成功");
            account = getAccount(username);
            if(account.getBannerOption().equals("1")) account.setBooleanBannerOption(true);
            else account.setBooleanBannerOption(false);
            if(account.getListOption().equals("1")) account.setBooleanListOption(true);
            else account.setBooleanListOption(false);
            System.out.println(account);
            data.put("account",account);
           return ReturnPack.success(data);
        }else {
            if(Constants.DEBUG_MODE&&Constants.DEBUG_CONTROLLER)System.out.println("用户名或密码错误");
            data.put("msg","用户名或密码错误");
            return ReturnPack.fail("用户名或密码错误");
        }
    }

    public ReturnPack signUpservice(Map<String,String> params){
        JSONObject data = new JSONObject();
        String username = params.get("username");
        String password = params.get("password");
        String repeatedPassword = params.get("repeatedPassword");
        String firstName = params.get("firstName");
        String lastName = params.get("lastName");
        String email = params.get("email");
        String phone = params.get("phone");
        String address1 = params.get("address1");
        String address2 = params.get("address2");
        String city = params.get("city");
        String state = params.get("state");
        String zip = params.get("zip");
        String country = params.get("country");
        String languagePreference = params.get("languagePreference");
        String favouriteCategoryId = params.get("favouriteCategoryId");
        String listOption = params.get("listOption");
        String bannerOption = params.get("bannerOption");
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPhone(phone);
        account.setAddress1(address1);
        account.setAddress2(address2);
        account.setCity(city);
        account.setState(state);
        account.setZip(zip);
        account.setCountry(country);
        account.setLanguagePreference(languagePreference);
        account.setFavouriteCategoryId(favouriteCategoryId);
        account.setListOption(listOption);
        account.setBannerOption(bannerOption);
        boolean success = true;
        if(account.getUsername().equals("")){
            System.out.println("用户名不能为空");
            data.put("usernameMsg","用户名不能为空");
            success =false;
        }
        if(account.getPassword().equals("")){
            System.out.println("密码不能为空");
            data.put("passwordMsg","密码不能为空");
            success = false;
        }
        if(getPassword(account.getUsername()) != null){
            System.out.println(getPassword(account.getUsername()));
            if(getPassword(account.getUsername()) != ""){
                System.out.println("用户名已存在");
                data.put("usernameMsg","用户名已存在");
                success = false;
            }
        }
        if(!password.equals(repeatedPassword)){
            data.put("repeatedPasswordMsg","前后两次密码不一致");
            success = false;
        }
        if(success) {
            //前端的checkbox勾选了，返回on，没勾选，返回null;将其转化为boolean
            if(account.getListOption() != null){
                if (account.getListOption().equals("true")) account.setBooleanListOption(true);
                else account.setBooleanListOption(false);
            }else{
                account.setBooleanListOption(false);
            }
            if(account.getBannerOption() != null){
                if(account.getBannerOption().equals("true")) account.setBooleanBannerOption(true);
                else account.setBooleanBannerOption(false);
            }else {
                account.setBooleanBannerOption(false);
            }

            //语言偏好(不设置默认为英语)
            if(account.getLanguagePreference() == null){
                account.setLanguagePreference("English");
            }else if(account.getLanguagePreference().equals("")){
                account.setLanguagePreference("English");
            }
            //最喜欢的种类


            //加密
            String enc_password = passwordEncoder.encode(password);
            account.setPassword(enc_password);
            if(account.getBannerOption().equals("")) account.setFavouriteCategoryId("NOBANNER");//默认NOBANNER
            insertAccount(account);//插入数据库
            return ReturnPack.success(data);
        }else {
            return ReturnPack.fail(data);
        }
    }

    public ReturnPack editAccountService(HttpServletRequest httpServletRequest,Account account){
        String username =  httpServletRequest.getHeader("UserName");
        boolean success = true;
        JSONObject data;
        if(httpServletRequest.getAttribute("data")!=null) data = (JSONObject) httpServletRequest.getAttribute("data");
        else  {
            data = new JSONObject();
//            String token = JwtUtil.generate(username);
//            data.put("token",token);
        }
//        //如果用户名为空，说明注册的逻辑有bug
//        if(account.getUsername().equals("")){
//            System.out.println("修改账户信息异常：userId为空");
//            map.put("username_msg","账户信息异常");
//            success =false;
//        }
//
//        //密码不能修改为空
//        if(account.getPassword().equals("")){
//            System.out.println("密码不能为空");
//            map.put("password_msg","密码不能为空");
//            success = false;
//        }
//        //确认密码不能为空
//        if(repeatedPassword.equals("")){
//            System.out.println("重复密码不能为空");
//            map.put("repeated_password_msg","重复密码不能为空");
//            success = false;
//        }
//        //如果密码和确认密码不一致
//        if(!account.getPassword().equals(repeatedPassword)){
//            System.out.println("重复密码有误");
//            map.put("repeated_password_msg","重复密码有误");
//            success = false;
//        }

        if(success) {
            System.out.println("l:"+account.getListOption()+"  b:"+account.getBannerOption());
            if (account.getListOption().equals("true")) account.setBooleanListOption(true);
            else account.setBooleanListOption(false);
            if(account.getBannerOption().equals("true")) account.setBooleanBannerOption(true);
            else account.setBooleanBannerOption(false);
            System.out.println("l:"+account.isBooleanListOption()+"  b:"+account.isBooleanBannerOption());

            if(account.getLanguagePreference() == null){
                account.setLanguagePreference("English");
            }else if(account.getLanguagePreference().equals("")){
                account.setLanguagePreference("English");
            }

            //加密
//            String password = passwordEncoder.encode(account.getPassword());
//            account.setPassword(password);
            System.out.println("修改后:"+account);
            account.setUsername(username);
            //将修改信息同步到数据库
            updateAccount(account);

            data.put("account",account);
        }
        return ReturnPack.success(data);
    }

    /**
     * @获取所有用户列表
     * @返回所有用户的Account列表
     */
    public List<Account> getAccountList(){
        return accountMapper.getAccountList();
    }
    /**
     *
     * @更新密码
     * @参数1：用户名
     * @参数2：密码
     */
    public void updatePassword(String username,String password){
        String passwordEnc = passwordEncoder.encode(password);
        accountMapper.updatePassword(username,passwordEnc);
    }

    public void removeAccount(String username){
        accountMapper.removeAccount(username);
        accountMapper.removeProfile(username);
        accountMapper.removeSignon(username);
    }
}
