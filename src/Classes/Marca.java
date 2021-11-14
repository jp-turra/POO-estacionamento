package Classes;

import java.util.ArrayList;

public class Marca {

	private String nome;
	private ArrayList<Modelo> modelos = new ArrayList<Modelo>();

	public Marca (String nome) {
		super();
		this.nome = nome;
	}
	public Marca (String nome, ArrayList<Modelo> modelos) {
		super();
		this.nome = nome;
		this.modelos = modelos;
	}

	public String getNome() {
		return nome;
	}
	public ArrayList<Modelo> getModelos() {
		return modelos;
	}
	public void addModelo(Modelo modelo) {
		if (this.temModelo(modelo)) {
			System.out.println("Modelo já registrado");
			return;
		}
		if (this.modelos == null) this.modelos = new ArrayList<Modelo>();
		this.modelos.add(modelo);
	}
	public Boolean temModelo(Modelo modelo) {
		if (this.modelos != null) {
			for (Modelo m : this.modelos) {
				if (m.getNome().equals(modelo.getNome())) return true;
			}
		}
		return false;
	}
}
