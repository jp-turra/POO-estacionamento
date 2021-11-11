package Classes;

import java.time.LocalDateTime;

public class Historico {
    private Carro carro;

    public Historico (Carro carro) {
        super();
        carro.setSaida(LocalDateTime.now());
        this.carro = carro;
    }

    public Carro getCarro() {
        return carro;
    }
}
