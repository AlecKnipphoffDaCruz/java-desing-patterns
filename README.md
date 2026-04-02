# Java Design Patterns

A practical study of the most important design patterns in Java,
implemented with real-world examples focused on backend development.

Each pattern includes a working implementation and a brief explanation
of the problem it solves and when to use it.

## Why Design Patterns?

Design patterns are proven solutions to recurring problems in software design.
They are not ready-made code — they are guidelines that help you write code
that is easier to maintain, extend, and understand.

Knowing patterns is also essential for technical interviews at international companies.

## Patterns Covered

### Creational — how objects are created
- [x] Factory — centralizes object creation, removes conditionals from business logic
- [x] Singleton — ensures a class has only one instance throughout the application
- [x] Builder — constructs complex objects step by step
- [ ] Prototype — creates new objects by cloning existing ones

### Behavioral — how objects communicate
- [x] Strategy — defines a family of algorithms and makes them interchangeable
- [x] Observer — notifies multiple objects when another object changes state
- [ ] Command — encapsulates a request as an object

### Structural — how objects are composed
- [x] Decorator — adds behavior to objects dynamically without modifying their class
- [ ] Adapter — allows incompatible interfaces to work together

## Tech Stack

- Java 26
- Spring Boot 4
- Maven

## How to Run
```bash
git clone https://github.com/AlecKnipphoffDaCruz/java-design-patterns.git
cd java-design-patterns
./mvnw spring-boot:run
```

## About

Developed by [Alec Knipphoff da Cruz](https://linkedin.com/in/alec-knipphoff-da-cruz-155358314)
as part of a structured backend development roadmap aimed at international remote positions.
