package com.gepatri.form.util;

public enum StatusRegistro {

	NEW(0, "Novo"),
	EDITING(1, "Em edição"),
	VIEWING(2, "Visualizando"),
	EMPTY(3, "Vazio");
	
	private int cod;
	private String descricao;

	private StatusRegistro(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
