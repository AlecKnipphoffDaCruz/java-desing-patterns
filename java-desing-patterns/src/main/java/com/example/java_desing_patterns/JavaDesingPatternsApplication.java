package com.example.java_desing_patterns;

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

		Notification n = FactoryNotification.createNotification("email");
		n.send("Hello from email");


		AppConfiguration config = AppConfiguration.getInstance();
		System.out.println(config.getAmbient());


	}

}
