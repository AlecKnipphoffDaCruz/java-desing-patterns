# Strategy & Observer Patterns

## Strategy Pattern

Defines a family of algorithms, encapsulates each one, and makes them interchangeable.
The goal is to allow the behavior of a class to be selected at runtime without using `if/else` or `switch` blocks scattered across the codebase.

### When to use it

- You have multiple variations of the same behavior
- You want to swap algorithms at runtime based on context
- You want to avoid conditionals that grow every time a new variation is added

### Java implementation

```java
// Common contract — all strategies follow this
public interface DescontoStrategy {
    double aplicar(double valor);
}

// Concrete strategies — each with its own logic
public class DescontoVIP implements DescontoStrategy {
    public double aplicar(double valor) {
        return valor * 0.80; // 20% discount
    }
}

public class DescontoEstudante implements DescontoStrategy {
    public double aplicar(double valor) {
        return valor * 0.90; // 10% discount
    }
}

public class SemDesconto implements DescontoStrategy {
    public double aplicar(double valor) {
        return valor; // no discount
    }
}

// The context — uses the strategy without knowing which one it is
public class Pedido {
    private DescontoStrategy desconto;
    private double valor;

    public Pedido(double valor, DescontoStrategy desconto) {
        this.valor = valor;
        this.desconto = desconto;
    }

    public double calcularTotal() {
        return desconto.aplicar(valor);
    }
}
```

**Usage:**
```java
Pedido pedido = new Pedido(100.0, new DescontoVIP());
System.out.println(pedido.calcularTotal()); // 80.0

Pedido outro = new Pedido(100.0, new DescontoEstudante());
System.out.println(outro.calcularTotal()); // 90.0
```

Adding a new discount type requires only a new class implementing `DescontoStrategy`. Nothing else changes.

---

### Strategy vs Factory

These two patterns are often confused because they look similar — both use interfaces and multiple implementations. The difference is in their purpose:

| Pattern | Decides | Result |
|---|---|---|
| Factory | Which object to **create** | A new instance |
| Strategy | Which behavior to **execute** | An action |

In practice they work together: **the Factory creates the right Strategy**.

In a payment system, for example, `ProcessadorFactory` returns a `ProcessadorPagamento` — which is a Strategy. The Factory chose it, the Strategy executed it.

---

## Observer Pattern

Defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.

The key benefit is **decoupling**: the subject does not need to know who is observing it or how many observers exist.

### When to use it

- One event needs to trigger multiple independent reactions
- You want to add or remove behaviors without touching the core logic
- You want each reaction to be isolated and independently maintainable

### Java implementation

```java
// The observer contract
public interface PedidoObserver {
    void onPedidoConfirmado(Pedido pedido);
}

// Concrete observers — each reacts in its own way
public class EstoqueObserver implements PedidoObserver {
    public void onPedidoConfirmado(Pedido pedido) {
        System.out.println("Stock: reducing item for order " + pedido.getId());
    }
}

public class EmailObserver implements PedidoObserver {
    public void onPedidoConfirmado(Pedido pedido) {
        System.out.println("Email: confirmation sent to " + pedido.getCliente());
    }
}

public class NotaFiscalObserver implements PedidoObserver {
    public void onPedidoConfirmado(Pedido pedido) {
        System.out.println("Invoice generated for order " + pedido.getId());
    }
}

// The subject — holds the observer list and notifies them
public class Pedido {
    private List<PedidoObserver> observers = new ArrayList<>();

    public void adicionarObserver(PedidoObserver observer) {
        observers.add(observer);
    }

    public void confirmar() {
        this.status = "CONFIRMED";
        observers.forEach(o -> o.onPedidoConfirmado(this));
    }
}
```

**Usage:**
```java
Pedido pedido = new Pedido();
pedido.adicionarObserver(new EstoqueObserver());
pedido.adicionarObserver(new EmailObserver());
pedido.adicionarObserver(new NotaFiscalObserver());

pedido.confirmar();
// Stock: reducing item...
// Email: confirmation sent...
// Invoice generated...
```

---

### Observer in Spring Boot — Application Events

Spring has a native implementation of the Observer pattern through `ApplicationEventPublisher`. The subject publishes an event; the observers listen to it. The subject never knows who is listening.

```java
// The event — carries all the data any observer might need
public class PedidoConfirmadoEvent {
    private final Pedido pedido;
    private final Usuario usuario;

    public PedidoConfirmadoEvent(Pedido pedido, Usuario usuario) {
        this.pedido = pedido;
        this.usuario = usuario;
    }

    public Pedido getPedido() { return pedido; }
    public Usuario getUsuario() { return usuario; }
}

// The publisher — confirms the order and fires the event
@Service
public class PedidoService {
    private final ApplicationEventPublisher publisher;

    public void confirmar(Pedido pedido, Usuario usuario) {
        pedido.setStatus("CONFIRMED");
        publisher.publishEvent(new PedidoConfirmadoEvent(pedido, usuario));
    }
}

// Observers — each listens independently
@Component
public class EstoqueListener {
    @EventListener
    public void onPedidoConfirmado(PedidoConfirmadoEvent event) {
        System.out.println("Reducing stock for order " + event.getPedido().getId());
    }
}

@Component
public class EmailListener {
    @EventListener
    public void onPedidoConfirmado(PedidoConfirmadoEvent event) {
        System.out.println("Sending confirmation email to " + event.getUsuario().getEmail());
    }
}
```

`PedidoService` does not know that `EstoqueListener` or `EmailListener` exist. Spring manages all the wiring.

---

### About the event payload

A common question: what if one observer needs more data than the others?

The answer is: **the event carries everything any observer might need**. Each observer picks what it requires and ignores the rest.

```java
public class TicketConfirmadoEvent {
    private final Ticket ticket;
    private final Usuario usuario;
    private final List<Item> itens;
    private final String templatePdf; // only the PDF observer uses this

    // constructor + getters
}
```

`SlackObserver` uses only `ticket` and `usuario`.
`S3PdfObserver` uses all four fields.
The subject does not care — it just publishes the full event.

> **Exception:** if an observer has complex logic that is reused elsewhere (like regenerating a PDF on demand, resending notifications, etc.), extracting it into a dedicated `@Service` is the right call. The observer then delegates to that service. This keeps the observer thin and the reusable logic centralized.

---

## Key Takeaways

| Pattern | Problem it solves | Spring Boot equivalent |
|---|---|---|
| Strategy | Swap algorithms at runtime without conditionals | `@Service` beans injected by type or name |
| Observer | Notify multiple objects when state changes | `ApplicationEventPublisher` + `@EventListener` |

> Both patterns follow the **Single Responsibility Principle**: each class does one thing only.
> Both follow the **Open/Closed Principle**: open for extension (add new strategies or observers), closed for modification (existing code does not change).
> You will see these SOLID principles again in Phase 2.