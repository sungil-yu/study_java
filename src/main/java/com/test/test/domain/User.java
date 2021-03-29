package com.test.test.domain;


import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {

        private String id;
        private String name;
        private String password;
        Level level;
        int login;
        int recommend;
        private String mail;

        //Level의 순서를 userService가 알아야하는 이유는 없다.
        //또한 User에게 업그레이드 하라고 요청하는 편도 괜찮다.
        public void upgradeLevel() {
                Level nextLevel = this.level.nextLevel();

                if(nextLevel == null) {
                        throw new IllegalStateException(this.level + "은 업그레이드가 불가능 합니다.");
                }
                else{
                        this.level =nextLevel;
                }
        }

}
