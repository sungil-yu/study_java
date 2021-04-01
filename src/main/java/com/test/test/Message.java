package com.test.test;

import java.awt.event.ItemEvent;

public class Message {
    String text;

    private Message(String text){
        this.text = text;

    }

    public String getText() {
        return text;
    }


    //생성자 대신 사용할 수 있는 스태틱 팩토리 메소드.
    //private 생성자를 가진 클래스를 직접 빈으로 등록하지 못한다. 사실 리플렉션을 이용해 오브젝트를 만들어 주긴 한다.
    public static Message newMessage(String text){
        return new Message(text);
    }
}
