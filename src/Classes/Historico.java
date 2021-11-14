package Classes;

import java.time.LocalDateTime;
import java.util.ArrayList;

import Utils.Utils;

public class Historico {
    private Carro carro;
    private LocalDateTime dataCreated;
    private int index = 0;

    public Historico(Carro carro) {
        super();
        this.dataCreated = LocalDateTime.now();
        ArrayList<Historico> hist = new Utils().getHistorico();
        if (!hist.isEmpty()) {
            this.index = hist.size();
        }
        carro.setHistoricoId(index);
        this.carro = carro;
    }

    public LocalDateTime getDataCreated() {
        return dataCreated;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Carro getCarro() {
        return carro;
    }

    public long getIndex() {
        return index;
    }
}
