package Classes;

import java.util.ArrayList;

public class Marca {

	private String nome;
	private ArrayList<Modelo> modelos;

	public Marca (String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
