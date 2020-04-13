package org.csu.mypetstore.domain;

import java.io.Serializable;

public class SignUpInfor implements Serializable {
    private static final long serialVersionUID = 8751282205532169742L;

    private String username;
    private String password;
    private String repeatedPassword;

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

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String toString()
    {
        String info = new String();
        info = getUsername();
        info = info + '\n' + getPassword();
        info = info + '\n' + getRepeatedPassword();

        return info;
    }
}
