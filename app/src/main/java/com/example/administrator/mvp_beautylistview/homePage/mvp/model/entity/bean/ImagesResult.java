package com.example.administrator.mvp_beautylistview.homePage.mvp.model.entity.bean;

import java.util.List;

/**
 * Created by Mr.Egg on 2016/10/6.
 */
public class ImagesResult {
    private String status;
    private int code;
    private List<ImageInfo> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ImageInfo> getResult() {
        return result;
    }

    public void setResult(List<ImageInfo> result) {
        this.result = result;
    }
}
