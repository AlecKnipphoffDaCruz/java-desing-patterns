package main.java.com.example.java_desing_patterns.builder;

public class Order {

    private final String cliente;
    private final String endereco;
    private final double valor;
    private final String tipoPagamento;
    private final int parcelas;
    private final boolean frete;

    private Order(Builder builder) {
        this.cliente = builder.cliente;
        this.endereco = builder.endereco;
        this.valor = builder.valor;
        this.tipoPagamento = builder.tipoPagamento;
        this.parcelas = builder.parcelas;
        this.frete = builder.frete;
    }

    public String getCliente() { return cliente; }
    public String getEndereco() { return endereco; }
    public double getValor() { return valor; }
    public String getTipoPagamento() { return tipoPagamento; }
    public int getParcelas() { return parcelas; }
    public boolean isFrete() { return frete; }

    @Override
    public String toString() {
        return "Order{" +
                "cliente='" + cliente + '\'' +
                ", endereco='" + endereco + '\'' +
                ", valor=" + valor +
                ", tipoPagamento='" + tipoPagamento + '\'' +
                ", parcelas=" + parcelas +
                ", frete=" + frete +
                '}';
    }

    public static class Builder {

        private String cliente;
        private String endereco;
        private double valor;
        private String tipoPagamento = "PIX";
        private int parcelas = 1;
        private boolean frete = false;

        public Builder cliente(String cliente) {
            this.cliente = cliente;
            return this;
        }

        public Builder endereco(String endereco) {
            this.endereco = endereco;
            return this;
        }

        public Builder valor(double valor) {
            this.valor = valor;
            return this;
        }

        public Builder tipoPagamento(String tipoPagamento) {
            this.tipoPagamento = tipoPagamento;
            return this;
        }

        public Builder parcelas(int parcelas) {
            this.parcelas = parcelas;
            return this;
        }

        public Builder comFrete() {
            this.frete = true;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}