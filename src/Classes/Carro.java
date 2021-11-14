package Classes;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import Utils.Utils;

public class Carro {

	private Modelo modelo;
	private String placa;
	private LocalDateTime entrada;
	private LocalDateTime saida;
	private float valor = 10;
	private int historicoId;

	public Carro(String placa, Modelo modelo) {
		super();
		this.modelo = modelo;
		this.placa = placa;
	}

	public Carro(String placa, Modelo modelo, LocalDateTime entrada) {
		super();
		this.modelo = modelo;
		this.placa = placa;
		this.entrada = entrada;
	}

	public void setSaida(LocalDateTime saida) {
		this.saida = saida;
		float estadia = (float) this.saida.toEpochSecond(ZoneOffset.UTC) - this.entrada.toEpochSecond(ZoneOffset.UTC);
		int hora = 3600;
		if (estadia > hora) {
			int parcelaHora = (int) ((estadia - hora) / (15 * 60));
			double parcial = parcelaHora * 2.5;
			float f = (float) parcial;
			this.valor = this.valor + f;
		}
		ArrayList<Historico> historicos = new Utils().getHistorico();
		Historico hist = historicos.get(this.historicoId);
		System.out.println(this);
		hist.setCarro(this);
		historicos.set(this.historicoId, hist);
		new Utils().setDatabase(historicos, "Historico");
	}

	public String getPlaca() {
		return placa;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public float getValor() {
		return valor;
	}

	public String getEstadia() {
		float diff = this.saida.toEpochSecond(ZoneOffset.UTC) - this.entrada.toEpochSecond(ZoneOffset.UTC);

		return "time.toString()";
	}

	public int getHistoricoId() {
		return historicoId;
	}

	public void setHistoricoId(int historicoId) {
		this.historicoId = historicoId;
	}

	public void display() {
		System.out.print(this.modelo.getNome() + " - " + this.placa);
	}
}
