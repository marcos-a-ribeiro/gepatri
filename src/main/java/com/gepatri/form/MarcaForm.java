package com.gepatri.form;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gepatri.dto.MarcaDTO;
import com.gepatri.form.util.CustomPageImpl;
import com.gepatri.form.util.StatusRegistro;

/*
 * CONTROLLER PARA A VIEW DO CADASTRO DE MARCAS */

@Component
@SessionScope
public class MarcaForm {

	// BACKUP DO ITEM EM EDIÇÃO
	private MarcaDTO marcaDTObk;
	
	// LISTA RETORNADA PELA PESQUISA
	private List<MarcaDTO> marcasDTO;
	
	// PARAMETRO DE PESQUISA
	private String pesquisa;
	
	// PAGINA RETORNADA PELA PESQUISA
	private CustomPageImpl<MarcaDTO> page;

	// STATUS ATUAL
	private StatusRegistro status = StatusRegistro.EMPTY;

	// MAPPER PARA CONVERTER OS ITENS RETORNADOS PELA PESQUISA
	private final ObjectMapper mapper = new ObjectMapper();

	// LISTA DE ITENS DELETADOS
	private LinkedHashMap<Integer, Boolean> deletedItems = new LinkedHashMap<Integer, Boolean>();
	
	// INDICE DO ITEM EM EDIÇÃO
	private Integer editingItem;
	
	@PostConstruct
	public void init() {
		marcasDTO = new ArrayList<>();
		pesquisa = "";
	}

	// COLOCA EM MODO DE INSERÇÃO
	public void addNew() {
		setPage(null);
		insertMarcaDTO(new MarcaDTO());
		setStatus(StatusRegistro.NEW);
	}
	
	// SE O STATUS ATUAL PERMITE INSERIR UM NOVO REGISTRO
    public Boolean allowInsert() {
		return (status==StatusRegistro.EMPTY || status==StatusRegistro.VIEWING);
	}

	// CANCELA EDIÇÃO OU NOVO REGISTRO
	public void cancel(Integer index) {
		if (!isNew() ) {
			getMarcasDTO().set(index, marcaDTObk);
		} else {
			marcasDTO.clear();
		}
		refreshStatus();
	}
	
	// COLOCA UM DETERMINADO ITEM EM EDIÇÃO
	public void edit(Integer index) {
		if (isEditing()) {
			addMessage("Favor concluir a edição já iniciada!");	
			return;
		}
		setEditingItem(index);
		setStatus(StatusRegistro.EDITING);
	}

	// SE ALGUM ITEM ESTÁ EM EDIÇÃO
	public Boolean editing() {
		return status==StatusRegistro.EDITING;
	}

	// RETORNA UMA MARCA DA LISTA
	public MarcaDTO getMarcaDTO(int index) {
		return mapper.convertValue(marcasDTO.get(index), MarcaDTO.class);
		
	}

	public List<MarcaDTO> getMarcasDTO() {
		return marcasDTO;
	}

	public CustomPageImpl<MarcaDTO> getPage() {
		return page;
	}

	// INFORMAÇÕES DE PAGINAÇÃO

	public String getPageInfo() {
		if (page==null) {
			return "";
		}
		return "Página " + (page.getNumber()+1) + " de " + page.getTotalPages();
	}

	// TOTAL DE PAGINAS RETORNADAS PELA PESQUISA
	
	public Integer totalPages() {
		if (page == null) {
			return 0;
		} else {
			return page.getTotalPages();
		}
	}
	
	public String getPesquisa() {
		return pesquisa;
	}

	public StatusRegistro getStatus() {
		return status;
	}

	// LIMPA A LISTA E INSERE O ITEM PASSADO COMO PARAMETRO
	public void insertMarcaDTO(MarcaDTO marcaDTO) {
		marcasDTO.clear();
		marcasDTO.add( marcaDTO );
		refreshStatus();
	}
	
	// SE DETERMINADO ITEM FOI DELETADO
	public Boolean isDeleted(Integer index) {
		return (deletedItems.get(index)!=null);
	}
	
	// SE ALGUM ITEM ESTÁ EM EDIÇÃO
	public Boolean isEditing() {
		return status == StatusRegistro.EDITING;
	}

	// SE DETERMINADO ITEM ESTÁ EM EDIÇÃO
	public Boolean isEditing(Integer index) {
		return (isEditing() && editingItem == index );
	}

	// SE A LISTA ESTÁ VAZIA
	public Boolean isListaEmpty() {
		if (page == null || page.getContent() == null) {
			return true;
		}
		return page.getContent().isEmpty();
	}
	
	// SE UM NOVO ITEM ESTÁ SENDO INSERIDO
	public Boolean isNew() {
		return status==StatusRegistro.NEW;
	}

	// ATUALIZA O STATUS
	public void refreshStatus() {
		setStatus(isListaEmpty() ? StatusRegistro.EMPTY : StatusRegistro.VIEWING );
	}
	
	// COLOCA INDICE DO ITEM EXCLUIDO NA LISTA
	public void setDeleted(Integer index) {
		deletedItems.put(index, true);
	}
	
	// COLOCA EM EDIÇÃO, IDENTIFICA O RESPECTIVO INDICE E FAZ UM BACKUP DO MESMO, CASO A EDIÇÃO SEJA CANCELADA.
	public void setEditingItem(Integer editingItem) {
		this.editingItem = editingItem;
		MarcaDTO p = getMarcaDTO(editingItem);
		marcaDTObk = new MarcaDTO(p.getId(), p.getNome());
	}

	public void setMarcasDTO(List<MarcaDTO> marcasDTO) {
   		this.marcasDTO = marcasDTO;
	}

	// PAGINA RETORNADA PELA PESQUISA
	public void setPage(CustomPageImpl<MarcaDTO> page) {
		this.page = page;
		if(page == null) {
			marcasDTO.clear();

		} else {
			marcasDTO = page.getContent().stream().collect(Collectors.toList());
			
		}
		deletedItems.clear();
	}
	
	// SE A LISTA ESTÁ VAZIA
	public void setEmpty() {
		page = null;
		marcasDTO.clear();
		refreshStatus();
	}

	// PARAMETRO DE PESQUISA
	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}

	// STATUS ATUAL
	public void setStatus(StatusRegistro status) {
		this.status = status;
	}

	public void addMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
	}

}
