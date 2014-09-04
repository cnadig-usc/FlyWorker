package handlers;

import com.avaje.ebean.Ebean;
import message.Message;
import models.Account;

import java.util.List;

/**
 * Created by johntower on 2/11/14.
 */




public class AccountHandler {


    //public static Model.Finder<String, Account> find = new Model.Finder<String,Account>(String.class, Account.class);

    public static Message authenticate (String username, String password) {
//        System.out.println("username:" +username+ " password: "+password);
        List<Account> list = Ebean.find(Account.class)
                .where()
                .eq("username", username)
                .findList();

        if (list.isEmpty()) {
            return Message.ACCOUNT_NOT_FOUND;
        } else {
            Account a = list.get(0);
            if (a.getPassword().equals(password)) {
                return Message.AUTHENTICATED;
            } else {
                return Message.WRONG_PASSWORD;
            }
        }


    }
}
