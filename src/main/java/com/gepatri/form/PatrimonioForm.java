package com.gepatri.form;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gepatri.dto.MarcaDTO;
import com.gepatri.dto.PatrimonioDTO;
import com.gepatri.form.util.CustomPageImpl;
import com.gepatri.form.util.StatusRegistro;
import com.gepatri.services.MarcaService;

/*
 * CONTROLLER PARA A VIEW DO CADASTRO DE PATRIMÔNIOS */

@Component
@SessionScope
public class PatrimonioForm {
	
	@Autowired
	MarcaService marcaService;
	
	// BACKUP, NO CASO DA EDIÇÃO DO ITEM SER CANCELDA
	private PatrimonioDTO patrimonioDTObk;
	
	// LISTA PARA O RESULTADO DA PESQUISA
	private List<PatrimonioDTO> patrimoniosDTO;
	
	// LISTA DE MARCAS CADASTRADAS
	private List<MarcaDTO> marcasDTO;
	
	// PARAMETRO DE PESQUISA
	private String pesquisa;
	
	// PAGINA RETORNADA PELA PESQUISA
	private CustomPageImpl<PatrimonioDTO> page;

	// INDICE DO ITEM EM EDIÇÃO
	private Integer editingItem;

	// STATUS ATUAL
	private StatusRegistro status = StatusRegistro.EMPTY;
	
	// MAPPER PARA CONVERTER OS ITENS RETORNADOS PELA PESQUISA
	private final ObjectMapper mapper = new ObjectMapper();
	
	// LISTA DE ITENS DELETADOS
	private LinkedHashMap<Integer, Boolean> deletedItems = new LinkedHashMap<Integer, Boolean>();

	@PostConstruct
	public void init() {
		patrimoniosDTO = new ArrayList<>();
		pesquisa = "";
		marcasDTO = marcaService.findAll();
	}
	
	// LISTA DE PATRIMONIOS
	public List<PatrimonioDTO> getPatrimoniosDTO() {
		return patrimoniosDTO;
	}

	public void setPatrimoniosDTO(List<PatrimonioDTO> patrimoniosDTO) {
   		this.patrimoniosDTO = patrimoniosDTO;
	}

	// PARAMETRO DE PESQUISA
	public String getPesquisa() {
		return pesquisa;
	}
	
	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}	

	// SE UM NOVO ITEM ESTÁ SENDO INSERIDO
	public Boolean isNew() {
		return status == StatusRegistro.NEW;
	}
	
	// SE ALGUM ITEM ESTÁ EM EDIÇÃO
	public Boolean isEditing() {
		return status == StatusRegistro.EDITING;
	}

	// SE DETERMINADO ITEM ESTÁ EM EDIÇÃO
	public Boolean isEditing(Integer index) {
		return (isEditing() && editingItem == index );
	}

	// SE DETERMINADO ITEM FOI DELETADO
	public Boolean isDeleted(Integer index) {
		return (deletedItems.get(index)!=null);
	}
	
	// COLOCA O ITEM EXCLUIDO NA LISTA
	public void setDeleted(Integer index) {
		deletedItems.put(index, true);
	}
	
	public Boolean isListaEmpty() {
		return (patrimoniosDTO == null || patrimoniosDTO.isEmpty());
	}

	public Boolean allowInsert() {
		return (status==StatusRegistro.EMPTY || status==StatusRegistro.VIEWING);
	}

	public StatusRegistro getStatus() {
		return status;
	}

	public void setStatus(StatusRegistro status) {
		this.status = status;
	}
	
	public void refreshStatus() {
		setStatus(isListaEmpty() ? StatusRegistro.EMPTY : StatusRegistro.VIEWING );
	}

	// COLOCA EM EDIÇÃO, IDENTIFICA O RESPECTIVO INDICE E FAZ UM BACKUP DO MESMO, CASO A EDIÇÃO SEJA CANCELADA.
	public void setEditingItem(Integer editingItem) {
		this.editingItem = editingItem;
		PatrimonioDTO p = getPatrimonioDTO(editingItem);
		patrimonioDTObk = new PatrimonioDTO(p.getId(), p.getNome(), p.getDescricao(), p.getMarcaId(), p.getNumeroTombo());
	}
	
	public void setPatrimonioDTO(Integer index, PatrimonioDTO patrimonioDTO) {
		patrimoniosDTO.set(index, patrimonioDTO);
	}
	
	// RETORNA A LISTA DE MARCAS
	public List<MarcaDTO> getMarcasDTO() {
		return marcasDTO;
	}

	// LIMPA A LISTA E INSERE O ITEM PASSADO COMO PARAMETRO
	public void insertPatrimonioDTO(PatrimonioDTO patrimonioDTO) {
		patrimoniosDTO.clear();
		patrimoniosDTO.add( patrimonioDTO );
		refreshStatus();
	}

	// PAGINA RETORNADA PELA PESQUISA
	public void setPage(CustomPageImpl<PatrimonioDTO> page) {
		this.page = page;
		if(page == null) {
			patrimoniosDTO.clear();

		} else {
			patrimoniosDTO = page.getContent().stream().collect(Collectors.toList());
			
		}
		deletedItems.clear();
	}

	// LIMPA A LISTA E ATUALIZA O STATUS
	public void setEmpty() {
		setPage(null);
		refreshStatus();
	}

	// PAGINA RETORNADA PELA PESQUISA
	public CustomPageImpl<PatrimonioDTO> getPage() {
		return page;
	}

	// INFORMAÇÕES DE PAGINAÇÃO
	public String getPageInfo() {
		if (page==null) {
			return "";
		}
		return "Página " + (page.getNumber()+1) + " de " + page.getTotalPages();
	}
	
	// TOTAL DE PÁGINAS RETORNADAS PELA PESQUISA
	public Integer totalPages() {
		if (page == null) {
			return 0;
		} else {
			return page.getTotalPages();
		}
	}
	
	// RETORNA UM PATRIMONIO DA LISTA 
	public PatrimonioDTO getPatrimonioDTO(int index) {
		return mapper.convertValue(patrimoniosDTO.get(index), PatrimonioDTO.class);
	}
	
	// INSERE UM NOVO PATRIMONIO. SOMENTE O NOVO ITEM PERMANECERÁ NA LISTA
	public void addNew() {
		setPage(null);
		insertPatrimonioDTO(new PatrimonioDTO());
		setStatus(StatusRegistro.NEW);
	}

	// MODO DE EDIÇÃO PARA UM DETERMINADO ITEM
	public void edit(Integer index) {
		if (isEditing()) {
			addMessage("Favor concluir a edição já iniciada!");	
			return;
		}
		setEditingItem(index);
		setStatus(StatusRegistro.EDITING);
	}

	// CANCELA EDIÇÃO OU NOVO REGISTRO
	public void cancel(Integer index) {
		if (!isNew() ) {
			getPatrimoniosDTO().set(index, patrimonioDTObk);
		} else {
			patrimoniosDTO.clear();
		}
		refreshStatus();
	}

	public void addMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
	}

}
