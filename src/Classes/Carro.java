package Classes;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Carro {

	private Modelo modelo;
	private String placa;
	private LocalDateTime entrada;
	private LocalDateTime saida;
	private float valor = 10;

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
		float estadia = this.saida.toEpochSecond(ZoneOffset.UTC) - this.entrada.toEpochSecond(ZoneOffset.UTC);
		int hora = 3600;
		if (estadia > hora) {
			
			int parcelaHora =Integer.parseInt(String.valueOf((estadia - hora) / 15));
			double parcial = parcelaHora*2.5;
			this.valor = this.valor + Float.valueOf(Double.toString(parcial));
		}
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

	public void display() {
		System.out.print(this.modelo.getNome() + " - " + this.placa);
	}
}
