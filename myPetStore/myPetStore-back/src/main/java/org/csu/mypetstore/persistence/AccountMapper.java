package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountMapper {

    Account getAccountByUsername(String username);

    Account getAccountByUsernameAndPassword(Account account);

    String getPasswordByUsername(String username);

    void insertAccount(Account account);

    void insertProfile(Account account);

    void insertSignon(Account account);

    void updateAccount(Account account);

    void updateProfile(Account account);

    void updateSignon(Account account);

    void removeAccount(String username);

    void removeProfile(String username);

    void removeSignon(String username);

    List<Account> getAccountList();

    void updatePassword(@Param("username") String username, @Param("password") String password);
}
