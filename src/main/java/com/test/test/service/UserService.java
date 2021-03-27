package com.test.test.service;

import com.test.test.domain.Level;
import com.test.test.domain.User;
import com.test.test.domain.UserDao;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Setter
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    //매일 9시 수행
    public void upgradeLevels(){
        List<User> users = userDao.getAll();

        for(User user: users){
            if(canUpgradeLevel(user)){
                user.upgradeLevel();
                userDao.update(user);
            }
        }

    }



    //주어진 user가 업그레이드가 가능한지 조건만 비교
    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel){
            case BASIC: return (user.getLogin() >= 50);
            case SILVER: return (user.getRecommend() >= 30);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level : "+currentLevel );
        }
    }

    // defalut level = BASIC config
    public void add(User user){
        if(user.getLevel() ==null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }
}
