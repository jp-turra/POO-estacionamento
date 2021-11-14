package Classes;

import java.time.LocalDateTime;

public class Historico {
    private Carro carro;
    private LocalDateTime dataCreated;

    public Historico (Carro carro) {
        super();
        this.dataCreated = LocalDateTime.now();
        carro.setSaida(this.dataCreated);
        this.carro = carro;
    }

    public LocalDateTime getDataCreated() {
        return dataCreated;
    }
    public Carro getCarro() {
        return carro;
    }
}
