package Classes;

import java.time.LocalDateTime;

public class Carro {

	private Modelo modelo;
	private String placa;
	private LocalDateTime entrada;
	private LocalDateTime saida;
	private float valor;

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
}
