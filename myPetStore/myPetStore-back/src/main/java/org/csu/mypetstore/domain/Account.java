package org.csu.mypetstore.domain;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Account implements Serializable {

    private static final long serialVersionUID = 8751282105532159742L;

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String status;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;
    private String favouriteCategoryId;
    private String languagePreference;
    private boolean booleanListOption;
    private boolean booleanBannerOption;
    private String listOption;
    private String bannerOption;
    private String bannerName;
    private int type;

    public void duplicate(Account account){
        username = account.getUsername();
        password = account.getPassword();
        email = account.getEmail();
        firstName = account.getFirstName();
        lastName = account.getLastName();
        status = account.getStatus();
        address1 = account.getAddress1();
        address2 = account.getAddress2();
        city = account.getCity();
        state = account.getState();
        zip = account.getZip();
        country = account.getCountry();
        phone = account.getPhone();
        favouriteCategoryId = account.getFavouriteCategoryId();
        languagePreference = account.getLanguagePreference();
        booleanListOption = account.isBooleanListOption();
        booleanBannerOption = account.isBooleanBannerOption();
        listOption = account.getListOption();
        bannerOption = account.getBannerOption();
        bannerName = account.getBannerName();
        type = account.getType();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFavouriteCategoryId() {
        return favouriteCategoryId;
    }

    public void setFavouriteCategoryId(String favouriteCategoryId) {
        this.favouriteCategoryId = favouriteCategoryId;
    }

    public String getLanguagePreference() {
        return languagePreference;
    }

    public void setLanguagePreference(String languagePreference) {
        this.languagePreference = languagePreference;
    }

    public boolean isBooleanListOption() {
        return booleanListOption;
    }

    public void setBooleanListOption(boolean listOption) {
        this.booleanListOption = listOption;
    }

    public boolean isBooleanBannerOption() {
        return booleanBannerOption;
    }

    public void setBooleanBannerOption(boolean bannerOption) {
        this.booleanBannerOption = bannerOption;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getListOption() {
        return listOption;
    }

    public void setListOption(String listOption) {
        this.listOption = listOption;
    }

    public String getBannerOption() {
        return bannerOption;
    }

    public void setBannerOption(String bannerOption) {
        this.bannerOption = bannerOption;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String toString()
    {
        String info = "";
        info +=      "username : "+ getUsername();
        info += '\n'+"password : " + getPassword();
        info += '\n'+"email :"+ getEmail();
        info += '\n'+"firstName : "+ getFirstName();
        info += '\n'+"lastName : "+ getLastName();
        info += '\n'+"status : "+ getStatus();
        info += '\n'+"address1 : "+ getAddress1();
        info += '\n'+"address2 : "+ getAddress2();
        info += '\n'+"city : "+ getCity();
        info += '\n'+"state : "+ getState();
        info += '\n'+"zip : "+ getZip();
        info += '\n'+"country : "+ getCountry();
        info += '\n'+"phone : "+ getPhone();
        info += '\n'+"favoriteCategoryId : "+ getFavouriteCategoryId();
        info += '\n'+"languagePreference : "+ getLanguagePreference();
        info += '\n'+"listOption : "+ getListOption();
        info += '\n'+"bannerOption : "+ getBannerOption();
        info += '\n'+"bannerName : "+ getBannerName();
        info += '\n'+"type : "+ getType();

        return info;
    }
}
