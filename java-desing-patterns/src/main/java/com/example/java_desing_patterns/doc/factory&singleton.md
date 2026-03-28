# Factory & Singleton Patterns

## Singleton Pattern

Ensures a class has **only one instance** throughout the entire application lifecycle.

Useful for things that should never be duplicated: configurations, connections, loggers — shared resources that don't change.

### Java implementation

```java
public class ConfiguracaoApp {
    private static ConfiguracaoApp instancia;
    private String ambiente;

    // Private constructor — no one creates it directly with "new"
    private ConfiguracaoApp() {
        this.ambiente = System.getenv("APP_ENV");
    }

    public static ConfiguracaoApp getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracaoApp();
        }
        return instancia;
    }

    public String getAmbiente() {
        return ambiente;
    }
}
```

No matter how many times you call `getInstancia()`, you always get the same object.

### In Spring Boot

Every `@Service`, `@Repository`, and `@Component` is already a Singleton by default. Spring creates one instance and reuses it everywhere.

```java
@Service // Spring creates ONE instance and injects it wherever needed
public class PagamentoService {
    public void processar(Pagamento p) { ... }
}
```

> **Important:** because of this, `@Service` classes should never hold state (instance variables that change between calls). Two simultaneous requests share the same instance — mutable state causes race conditions.

---

## Factory Pattern

Centralizes object creation in a single place, removing scattered `if/else` blocks across the codebase.

The main benefit is **scalability**: adding a new type only requires creating a new class that implements the interface and registering it. Nothing else changes.

### Basic Java implementation

```java
// Common contract — all implementations follow this
public interface Notificacao {
    void enviar(String mensagem);
}

public class NotificacaoEmail implements Notificacao {
    public void enviar(String mensagem) {
        System.out.println("Email: " + mensagem);
    }
}

public class NotificacaoSMS implements Notificacao {
    public void enviar(String mensagem) {
        System.out.println("SMS: " + mensagem);
    }
}

// The Factory — the only place that knows how to create
public class NotificacaoFactory {
    public static Notificacao criar(String tipo) {
        return switch (tipo) {
            case "email" -> new NotificacaoEmail();
            case "sms"   -> new NotificacaoSMS();
            default -> throw new IllegalArgumentException("Invalid type: " + tipo);
        };
    }
}
```

---

## Factory Pattern in Spring Boot — Real World Example

A payment system with multiple methods (Pix, Card, Boleto) is a classic Factory use case.

Each payment type has its **own specific logic** (calling different APIs, different validations), but they all share **common logic** (saving to the database, sending a confirmation email).

### The interface

```java
public interface ProcessadorPagamento {
    void processar(Pagamento pagamento);
}
```

### Specific implementations

```java
@Service("pix")
public class ProcessadorPix implements ProcessadorPagamento {
    public void processar(Pagamento pagamento) {
        // Pix-specific logic: call Pix API, verify connection, etc.
        System.out.println("Processing Pix...");
    }
}

@Service("cartao")
public class ProcessadorCartao implements ProcessadorPagamento {
    public void processar(Pagamento pagamento) {
        // Card-specific logic: call card operator, handle authorization, etc.
        System.out.println("Processing Card...");
    }
}

@Service("boleto")
public class ProcessadorBoleto implements ProcessadorPagamento {
    public void processar(Pagamento pagamento) {
        // Boleto-specific logic: generate barcode, set due date, etc.
        System.out.println("Processing Boleto...");
    }
}
```

### The Factory

```java
@Service
public class ProcessadorFactory {
    private final Map<String, ProcessadorPagamento> processadores;

    // Spring automatically injects ALL ProcessadorPagamento implementations
    public ProcessadorFactory(Map<String, ProcessadorPagamento> processadores) {
        this.processadores = processadores;
    }

    public ProcessadorPagamento criar(String tipo) {
        ProcessadorPagamento p = processadores.get(tipo);
        if (p == null) throw new IllegalArgumentException("Invalid type: " + tipo);
        return p;
    }
}
```

### The Service — orchestrates the full flow

```java
@Service
public class PagamentoService {
    private final ProcessadorFactory factory;
    private final EmailService emailService;
    private final PagamentoRepository repository;

    public void processar(Pagamento pagamento) {
        // 1. Type-specific logic — Factory decides who handles it
        ProcessadorPagamento processador = factory.criar(pagamento.getTipo());
        processador.processar(pagamento);

        // 2. Common logic — always runs, regardless of payment type
        repository.save(pagamento);
        emailService.enviarConfirmacao(pagamento);
    }
}
```

### The flow

```
Request arrives
→ Controller receives the Pagamento object
→ PagamentoService calls the Factory with pagamento.getTipo()
→ Factory returns the correct processor
→ Processor runs its specific logic
→ PagamentoService saves to DB and sends confirmation email
```

### Adding a new payment method

To add Crypto tomorrow, you only need to:

1. Create `ProcessadorCrypto` implementing `ProcessadorPagamento`
2. Annotate it with `@Service("crypto")`
3. Done — nothing else changes

This is the key benefit of the Factory pattern: **open for extension, closed for modification**.

---

## Key Takeaways

| Pattern | Problem it solves | In Spring Boot |
|---|---|---|
| Singleton | Prevent multiple instances of a shared resource | Every `@Service`, `@Component`, `@Repository` |
| Factory | Centralize object creation and isolate type-specific logic | `Map<String, Interface>` injected by Spring |

> The Factory pattern is not about creating database entities. It is about choosing the right **behavior** (strategy) at runtime based on a condition. Entities still go to the database normally via their Repositories.