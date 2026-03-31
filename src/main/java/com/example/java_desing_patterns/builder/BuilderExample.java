package main.java.com.example.java_desing_patterns.builder;


public class BuilderExample {

    public static void main(String[] args) {

        // Manual Builder — full order
        Order pedido1 = new Order.Builder()
                .cliente("João")
                .endereco("Rua A, 123")
                .valor(250.0)
                .tipoPagamento("CARTAO")
                .parcelas(3)
                .comFrete()
                .build();

        System.out.println(pedido1);

        // Manual Builder — minimal order (defaults applied)
        Order pedido2 = new Order.Builder()
                .cliente("Maria")
                .endereco("Rua B, 456")
                .valor(80.0)
                .build();

        System.out.println(pedido2);

        // Lombok Builder — cliente with all fields
        Client cliente1 = Client.builder()
                .nome("Pedro")
                .email("pedro@email.com")
                .telefone("51999999999")
                .instagram("@pedro")
                .build();

        System.out.println(cliente1);

        // Lombok Builder — cliente with only required fields
        Client cliente2 = Client.builder()
                .nome("Ana")
                .email("ana@email.com")
                .build();

        System.out.println(cliente2);
    }
}