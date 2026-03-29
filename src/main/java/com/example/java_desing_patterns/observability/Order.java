package com.example.java_desing_patterns.observability;

import java.util.ArrayList;
import java.util.List;

public class Order {

        private final List<OrderObserver> listObservers = new ArrayList<>();
        private String status;

        public void addObserver(OrderObserver observer){
            listObservers.add(observer);
        }

        public void confirm(){
            this.status = "CONFIRMADO";
            listObservers.forEach(o -> o.onConfirmedOrder(this));
        }
}
