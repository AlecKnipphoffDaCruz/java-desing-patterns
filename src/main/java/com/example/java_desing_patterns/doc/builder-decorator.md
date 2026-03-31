# Builder & Decorator Patterns

## Builder Pattern

Solves the problem of constructing complex objects that have many optional parameters.
Without Builder, you end up with either giant constructors with unclear parameter order, or scattered setters that leave objects in inconsistent states.

### The problem it solves

```java
// What is each parameter? Impossible to tell without reading the class
Pedido pedido = new Pedido("João", "Rua A", 100.0, true, false, "PIX", null, 3);
```

With Builder, every field is explicit and optional fields have default values.

### When to use it

- Objects with many optional fields
- You want immutable objects (final fields set only at construction)
- You want a readable, fluent API for object creation

### Java implementation

```java
public class Pedido {
    private final String cliente;
    private final String endereco;
    private final double valor;
    private final String tipoPagamento;
    private final int parcelas;
    private final boolean frete;

    private Pedido(Builder builder) {
        this.cliente = builder.cliente;
        this.endereco = builder.endereco;
        this.valor = builder.valor;
        this.tipoPagamento = builder.tipoPagamento;
        this.parcelas = builder.parcelas;
        this.frete = builder.frete;
    }

    public static class Builder {
        private String cliente;
        private String endereco;
        private double valor;
        private String tipoPagamento = "PIX"; // default value
        private int parcelas = 1;
        private boolean frete = false;

        public Builder cliente(String cliente) { this.cliente = cliente; return this; }
        public Builder endereco(String endereco) { this.endereco = endereco; return this; }
        public Builder valor(double valor) { this.valor = valor; return this; }
        public Builder tipoPagamento(String tipo) { this.tipoPagamento = tipo; return this; }
        public Builder parcelas(int parcelas) { this.parcelas = parcelas; return this; }
        public Builder comFrete() { this.frete = true; return this; }

        public Pedido build() {
            return new Pedido(this);
        }
    }
}
```

**Usage:**
```java
Pedido pedido = new Pedido.Builder()
    .cliente("João")
    .endereco("Rua A, 123")
    .valor(250.0)
    .tipoPagamento("CARTAO")
    .parcelas(3)
    .comFrete()
    .build();
```

### In Spring Boot — Lombok

In real projects, nobody writes Builder manually. Lombok generates it automatically:

```java
@Builder
@Data
public class ClienteDTO {
    private String nome;
    private String email;
    private String telefone;      // optional
    private String instagram;     // optional
    @Builder.Default
    private boolean ativo = true;
}
```

**Usage:**
```java
// Only the fields you need — no constructor overloads required
ClienteDTO c1 = ClienteDTO.builder()
    .nome("João")
    .email("joao@email.com")
    .build();

ClienteDTO c2 = ClienteDTO.builder()
    .nome("Maria")
    .email("maria@email.com")
    .telefone("51999999999")
    .instagram("@maria")
    .build();
```

> **Real-world use:** Builder is the standard pattern for DTOs and request/response objects in Spring Boot APIs. With Lombok, `@Builder` on a DTO is the norm in professional projects.

---

## Decorator Pattern

Adds behavior to an object dynamically, without modifying its original class and without creating a subclass explosion.

The key idea is **composition over inheritance** — wrap objects in layers, like Russian dolls. Each layer does its part and passes to the next.

### The problem with inheritance

If you have 3 notification channels and want every combination, inheritance requires:

```
EmailNotificador
SMSNotificador
SlackNotificador
EmailSMSNotificador
EmailSlackNotificador
SMSSlackNotificador
EmailSMSSlackNotificador
```

7 classes for 3 channels. With 5 channels, dozens. This is called **subclass explosion**.

Inheritance is also **static** — behaviors are fixed at compile time. You cannot add or remove a channel at runtime.

### When to use it

- You need to add behavior to objects at runtime
- You want to combine behaviors flexibly without a class for each combination
- You want to follow Open/Closed Principle — extend without modifying

### Java implementation

```java
// Base contract
public interface Notificador {
    void enviar(String mensagem);
}

// Base implementation
public class NotificadorEmail implements Notificador {
    public void enviar(String mensagem) {
        System.out.println("Email: " + mensagem);
    }
}

// Abstract decorator — wraps any Notificador
public abstract class NotificadorDecorator implements Notificador {
    protected final Notificador wrapped;

    public NotificadorDecorator(Notificador wrapped) {
        this.wrapped = wrapped;
    }

    public void enviar(String mensagem) {
        wrapped.enviar(mensagem);
    }
}

// Concrete decorators — each adds its own behavior
public class NotificadorSMS extends NotificadorDecorator {
    public NotificadorSMS(Notificador wrapped) { super(wrapped); }

    public void enviar(String mensagem) {
        wrapped.enviar(mensagem);
        System.out.println("SMS: " + mensagem);
    }
}

public class NotificadorSlack extends NotificadorDecorator {
    public NotificadorSlack(Notificador wrapped) { super(wrapped); }

    public void enviar(String mensagem) {
        wrapped.enviar(mensagem);
        System.out.println("Slack: " + mensagem);
    }
}
```

**Usage — composing layers at runtime:**
```java
// Email only
Notificador n1 = new NotificadorEmail();

// Email + SMS
Notificador n2 = new NotificadorSMS(new NotificadorEmail());

// Email + SMS + Slack
Notificador n3 = new NotificadorSlack(
                     new NotificadorSMS(
                         new NotificadorEmail()));

n3.enviar("Order confirmed!");
// Email: Order confirmed!
// SMS: Order confirmed!
// Slack: Order confirmed!
```

### Real-world analogy — coffee shop

```java
Cafe pedido = new ComChantilly(new ComLeite(new CafeSimples()));
System.out.println(pedido.getDescricao()); // Café + Leite + Chantilly
System.out.println(pedido.getPreco());     // 10.0
```

Each decorator adds its description and price to whatever is wrapped inside it.

### In Spring Boot — Filter Chain

The Decorator pattern is all over Spring Boot. The `FilterChain` in Spring Security is the clearest example — each filter wraps the next, adding behavior in layers:

```java
@Component
public class LogFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("Incoming: " + ((HttpServletRequest) req).getRequestURI());
        chain.doFilter(req, res); // passes to the next decorator
        System.out.println("Outgoing response sent");
    }
}
```

You never see the layers — you just use the final wrapped object.

---

## Key Takeaways

| Pattern | Problem it solves | Spring Boot equivalent |
|---|---|---|
| Builder | Construct complex objects cleanly with optional fields | `@Builder` from Lombok on DTOs |
| Decorator | Add behavior in layers without subclass explosion | `FilterChain`, `HttpServletRequestWrapper` |

> **Inheritance vs Decorator:**
> Inheritance is static — behaviors are fixed at compile time and combining them requires a new class for each combination.
> Decorator is dynamic — behaviors are composed at runtime by wrapping objects in layers. Adding a new behavior means one new class, not many.

> Both patterns appear together with **Strategy** and **Observer** in real systems.
> You will revisit them when studying Clean Architecture in Phase 2.