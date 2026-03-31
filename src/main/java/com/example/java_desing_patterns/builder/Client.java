package main.java.com.example.java_desing_patterns.builder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Client {
    private String nome;
    private String email;
    private String telefone;
    private String instagram;

    @Builder.Default
    private boolean ativo = true;
}
