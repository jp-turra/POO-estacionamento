package Classes;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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

	public Marca getMarca() {
		ArrayList<Marca> marcas = new Utils().getMarcas();
		for (Marca m : marcas) {
			for (Modelo m2 : m.getModelos()) {
				if (m2.getNome().equals(modelo.getNome()))
					return m;
			}
		}
		return null;
	}

	public LocalDateTime getEntrada() {
		return entrada;
	}

	public String getEntradaFormatted(String pattern) {
		if (pattern.equals(""))
			pattern = "dd/MM/yyyy HH:mm";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return entrada.format(formatter);
	}

	public String getSaidaFormatted(String pattern) {
		if (pattern.equals(""))
			pattern = "dd/MM/yyyy HH:mm";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return saida.format(formatter);
	}

	public float getValor() {
		return valor;
	}

	public String getEstadia() {
		float diff = saida.toEpochSecond(ZoneOffset.UTC) - entrada.toEpochSecond(ZoneOffset.UTC);

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
