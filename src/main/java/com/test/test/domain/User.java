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

}
