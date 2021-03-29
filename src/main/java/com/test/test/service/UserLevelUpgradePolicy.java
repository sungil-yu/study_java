package com.test.test.service;

import com.test.test.domain.User;

public interface UserLevelUpgradePolicy {
     void upgradeLevels();
     boolean canUpgradeLevel(User user);
}
