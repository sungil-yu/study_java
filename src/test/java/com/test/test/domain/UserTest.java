package com.test.test.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    //user가 level을 업그레이드 하는 테스트
    @Test
    void upgradeLevel() {
        Level[] levels = Level.values();

        for (Level level : levels) {

            if (level.nextLevel() == null) continue;

            user.setLevel(level);
            user.upgradeLevel();

            assertEquals(level.nextLevel(), user.getLevel());
        }

    }


    @Test
    void cannotUpgradeLevel() {
        Level[] levels = Level.values();

        for (Level level : levels) {

            if (level.nextLevel() != null) continue;
            user.setLevel(level);
            assertThrows(IllegalStateException.class,
                    () -> user.upgradeLevel());

        }

    }

}