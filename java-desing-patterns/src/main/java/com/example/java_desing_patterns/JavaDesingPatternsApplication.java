package com.example.java_desing_patterns;

import com.example.java_desing_patterns.strategy.ContextDiscont;
import com.example.java_desing_patterns.strategy.NoDiscont;
import com.example.java_desing_patterns.strategy.StudentDiscont;
import com.example.java_desing_patterns.strategy.VIPDiscont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.java_desing_patterns.factory.Notifications.FactoryNotification;
import com.example.java_desing_patterns.factory.Notifications.Notification;
import com.example.java_desing_patterns.singleton.AppConfiguration;

@SpringBootApplication
public class JavaDesingPatternsApplication {

	public static void main(String[] args) {

		SpringApplication.run(JavaDesingPatternsApplication.class, args);

		NoDiscont noDiscont = new NoDiscont();
		StudentDiscont studentDiscont = new StudentDiscont();
		VIPDiscont vipDiscont = new VIPDiscont();

		ContextDiscont contextDiscont = new ContextDiscont(100 ,studentDiscont);
		System.out.println(contextDiscont.calculateDiscont());

	}

}
