package com.gepatri.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatrimonioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message = "Preenchimento ogrigatório")
	private String nome;

	private String descricao;
	private Integer numeroTombo;
	private Integer marcaId;
	private String nomeMarca;
	
	@JsonIgnore
	private byte[] imagem;

	public PatrimonioDTO() {
	}

	public PatrimonioDTO(Integer id, String nome, String descricao, Integer marcaId, Integer numeroTombo) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.marcaId = marcaId;
		this.numeroTombo = numeroTombo;
		
	}

/*
	public PatrimonioDTO(PatrimonioDTO patrimonio) {
		super();
		this.id 			= patrimonio.getId();
		this.nome 			= patrimonio.getNome();
		this.descricao 		= patrimonio.getDescricao();
		this.marcaId 		= patrimonio.getMarcaId();
		this.numeroTombo 	= patrimonio.getNumeroTombo();
	}
	*/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getNumeroTombo() {
		return numeroTombo;
	}

	public void setNumeroTombo(Integer numeroTombo) {
		this.numeroTombo = numeroTombo;
	}

	public Integer getMarcaId() {
		return marcaId;
	}

	public void setMarcaId(Integer marcaId) {
		this.marcaId = marcaId;
	}

	public String getNomeMarca() {
		return nomeMarca;
	}

	public void setNomeMarca(String nomeMarca) {
		this.nomeMarca = nomeMarca;
	}
	
	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	@Override
	public String toString() {
		return "PatrimonioDTO [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", numeroTombo="
				+ numeroTombo + ", marcaId=" + marcaId + ", nomeMarca=" + nomeMarca + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatrimonioDTO other = (PatrimonioDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
