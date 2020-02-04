package com.miaosha.day1.service;


import com.miaosha.day1.dao.UserDao;
import com.miaosha.day1.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    public User getById(int id){
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx(){
        User u1=new User();
        u1.setId(3);
        u1.setName("3333");
        userDao.insert(u1);

        User u2=new User();
        u1.setId(1);
        u1.setName("111");
        userDao.insert(u2);

        return true;
    }
}
