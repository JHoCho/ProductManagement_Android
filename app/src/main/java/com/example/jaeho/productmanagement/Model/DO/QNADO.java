package com.example.jaeho.productmanagement.Model.DO;

/**
 * Created by jaeho on 2017. 7. 31..
 */

public class QNADO {
    //어레이 리스트에 동적으로 할당하기위한 첫번째 과제인 DO클래스를 만들어 주는 것 입니다
    private String name;
    private String subject;
    private String contents;
    private String date;
    private String email;
    private String key;
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
