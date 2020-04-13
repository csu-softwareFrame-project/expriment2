package org.csu.mypetstore.LocaleResolver;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lang = request.getParameter("lang");
        Locale locale = Locale.getDefault();
        if(lang!=null&&lang.length()==5){
            String[] split = lang.split("_");
            locale = new Locale(split[0],split[1]);
            return locale;
        }
        //默认为美国英语
        locale = new Locale("en","US");
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    }
}