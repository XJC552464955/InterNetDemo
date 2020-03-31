package com.example.internetdemo;

public class JsonParseClass {
    private int status;
    private Book data;
    private String msg;

    public JsonParseClass() {
    }

    public JsonParseClass(int status, Book data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Book getData() {
        return data;
    }

    public void setData(Book data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "JsonParseClass{" +
                "status=" + status +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
