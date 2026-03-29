package com.example.java_desing_patterns.singleton;

public class AppConfiguration {
    private static AppConfiguration instance;
    private String ambient;

    private AppConfiguration(){
        this.ambient = System.getenv("APP_ENV");
    }

    public static AppConfiguration getInstance(){
        if (instance == null){
            instance = new AppConfiguration();
        }
        return instance;
    }

    public String getAmbient(){
        return ambient;
    }
}
