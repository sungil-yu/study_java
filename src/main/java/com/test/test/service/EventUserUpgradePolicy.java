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
public class EventUserUpgradePolicy implements UserLevelUpgradePolicy{

    @Autowired
    UserDao userDao;
    
    //이벤트로 업그레이드 회수를 낮춤
    public static final int MIN_LOGCOUNT_FOR_SILVER = 40;
    public static final int MIN_RECOMMEND_FOR_GOLD = 20;
    
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
    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel){
            case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
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
