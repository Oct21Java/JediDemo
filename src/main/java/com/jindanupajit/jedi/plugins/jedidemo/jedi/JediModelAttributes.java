package com.jindanupajit.jedi.plugins.jedidemo.jedi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class JediModelAttributes {
    private int code;
    private String error;
    private String success;
    private String info;
    private Object entity;
    private String defaultTemplate = "jedi/entrypoint";

    public void set(int code, Object entity) {
        this.code = code;
        this.entity = entity;
    }

    public void set(int code, String error, String success, String info, Object entity) {
        this.code = code;
        this.error = error;
        this.success = success;
        this.info = info;
        this.entity = entity;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public String view(Model model) {
        return view(model, this.defaultTemplate);
    }

    public String view(Model model, String template) {


        HashMap<String, Object> modelAttributes = new HashMap<>();

        modelAttributes.put("jediCode", getCode());
        modelAttributes.put("jediEntity", getEntity());
        modelAttributes.put("jediError", getError());
        modelAttributes.put("jediSuccess", getSuccess());
        modelAttributes.put("jediInfo", getInfo());
        model.addAllAttributes(modelAttributes);
        System.out.printf("Calling template %s\n", template);
        return template;
    }

    public String redirect(String url) {
        HashMap<String, Object> modelAttributes = new HashMap<>();

        modelAttributes.put("jediCode", getCode());
        modelAttributes.put("jediEntity", getEntity());
        modelAttributes.put("jediError", getError());
        modelAttributes.put("jediSuccess", getSuccess());
        modelAttributes.put("jediInfo", getInfo());
        StringBuilder queryString = new StringBuilder();
        modelAttributes.forEach(
                (k,v) -> {
                    if (v == null) return;

                    if (queryString.length() > 0)
                        queryString.append("&");

                    queryString.append(k);
                    queryString.append("=");
                    try {
                        queryString.append(URLEncoder.encode((String) v,"UTF-8"));
                    } catch (UnsupportedEncodingException ignored) {

                    }
                }
        );
        if (queryString.length() == 0) {
            return String.format("redirect:%s");
        }
        else if (url.contains("?")) {
            return String.format("redirect:%s&%s",url, queryString.toString());
        } else {
            return String.format("redirect:%s?%s",url, queryString.toString());
        }

    }
}
