package com.example.java_desing_patterns;

import com.example.java_desing_patterns.observability.Order;
import com.example.java_desing_patterns.observability.observers.EmailObserver;
import com.example.java_desing_patterns.observability.observers.FNObserver;
import com.example.java_desing_patterns.observability.observers.StockObserver;
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

			Order order = new Order();
			order.addObserver(new EmailObserver());
			order.addObserver(new FNObserver());
			order.addObserver(new StockObserver());

			order.confirm();
	}

}
