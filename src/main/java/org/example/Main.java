package org.example;

import org.example.dao.UserDao;
import org.example.model.User;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args){

        User user = new User();
        user.setId(4);
        user.setName("anju");
        user.setEmail("anju@gmail.com");
        user.setMobile("9555312724");
//
       UserDao dao = new UserDao();
//        System.out.println(dao.save(user));

//        System.out.println(dao.findAll());
//        System.out.println(dao.findById(3));
        System.out.println(dao.delete(3));
        System.out.println(dao.update(user));

    }
}
