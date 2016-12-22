package com.kazyle.hugohelper.server.config.domain.data;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by Kazyle on 2016/8/23.
 */
public class ResponseEntity implements Serializable {

    private int code;

    @JSONField(name = "data")
    private Object obj;

    private String msg;

    private long time;

    public ResponseEntity() {
    }

    public ResponseEntity(int code) {
        this.code = code;

    }

    public ResponseEntity(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseEntity(int code, Object obj) {
        this.code = code;
        this.obj = obj;
    }

    public ResponseEntity(int code, Object obj, String msg) {
        this.code = code;
        this.obj = obj;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public void setTime(long time) {
        this.time = time;
    }
}
