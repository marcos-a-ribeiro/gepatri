package com.gepatri.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.annotation.SessionScope;

import com.gepatri.dto.PatrimonioDTO;
import com.gepatri.form.PatrimonioForm;
import com.gepatri.form.util.CustomPageImpl;
import com.gepatri.form.util.StatusRegistro;
import com.gepatri.services.PatrimonioServiceImpl;

@Component
@SessionScope
public class PatrimonioAction {
	
	@Autowired
	PatrimonioServiceImpl patrimonioService;
	
	@Autowired
	PatrimonioForm patrimonioForm;
	
	private final Integer LINHAS_POR_PAGINA = 11;

	public void edit(Integer index) {
		patrimonioForm.setEditingItem(index);
		patrimonioForm.setStatus(StatusRegistro.EDITING);
	}
	
	public void find() {
		
		String nome = patrimonioForm.getPesquisa();

        // SE NENHUM PARÂMETRO FOR INFORMADO, IRÁ BUSCAR TODOS OS REGISTROS
        
        if (nome == null || nome.isBlank()) {
			if (findPage(0, LINHAS_POR_PAGINA) == 0) {
	            patrimonioForm.addMessage("A pesquisa não retornou nenhum resultado!");			
			};
			return;
		}

        // CASO SEJA POSSÍVEL CONVERTER PARA INTEIRO, IRÁ PESQUISAR PELO ID

        try {
			Integer.parseInt( nome );
			patrimonioForm.insertPatrimonioDTO( patrimonioService.findById( nome ));
			
		} catch (NumberFormatException e ) {
			if (findPageByNome(0, LINHAS_POR_PAGINA, nome) == 0) {
				patrimonioForm.addMessage("A pesquisa não retornou nenhum resultado!");			
			};

		} catch (HttpServerErrorException e) {
			e.printStackTrace();
			patrimonioForm.setEmpty();
			patrimonioForm.addMessage("Item não encontrado!");			
			
		} catch (Exception e) {
			e.printStackTrace();
			patrimonioForm.setEmpty();
			patrimonioForm.addMessage("Ocorreu um erro inesperado!");			
		}
		
		patrimonioForm.refreshStatus();
	}
	
	// PESQUISAR PATRIMONIOS

	public Long findAll() {
		CustomPageImpl<PatrimonioDTO> page = patrimonioService.findPage(0, LINHAS_POR_PAGINA);
		patrimonioForm.setPage(page);
		return page.getTotalElements();
	}

	// PESQUISA POR TODOS OS ITENS NO BD (OBTEM UMA DETERMINADA PÁGINA DE UMA LISTA PAGINADA)
	
	public Long findPage(Integer pageNumber, Integer linesPerPage) {
		CustomPageImpl<PatrimonioDTO> page = patrimonioService.findPage(pageNumber, linesPerPage);
		patrimonioForm.setPage(page);
		return page.getTotalElements();
	}
	
	// PESQUISA DE ACORDO COM O NOME INFORMADO NO CAMPO DE PESQUISA (OBTEM UMA LISTA PAGINADA)
	
	public Long findPageByNome(Integer pageNumber, Integer linesPerPage, String nome) {
		CustomPageImpl<PatrimonioDTO> page = patrimonioService.findPageByNome(pageNumber, linesPerPage, nome);
		patrimonioForm.setPage(page);
		return page.getTotalElements();
	}
	
	// INSERE OU ATUALIZA NO BD, DE ACORDO COM O STATUS (NOVO OU EM EDIÇÃO)
	
	public void insertOrUpdate(Integer index) {
		if (patrimonioForm.isNew() ) {
			insert();
		} else {
			update(index);
		}
	}
	
	// INSERE NO BD O NOVO ITEM
	
	public void insert() {
		try {
			patrimonioForm.setPatrimonioDTO(0, patrimonioService.insert(patrimonioForm.getPatrimonioDTO(0)));
			patrimonioForm.refreshStatus();
			
	    } catch (Exception e) {
	    	patrimonioForm.addMessage(e.getMessage());
	    	
	    }
	}
	
	// ATUALIZA NO BD O ITEM EDITADO

	public void update(Integer index) {
		try {
			patrimonioService.update( patrimonioForm.getPatrimonioDTO(index) );
			patrimonioForm.refreshStatus();
			
	    } catch (Exception e) {
	    	patrimonioForm.addMessage(e.getMessage());
	    	
	    }
	}
	
	// DELETA NO BD E MARCA O ITEM COMO EXCLUÍDO (IMPEDE QUE UMA NOVA TENTATIVA DE REMOÇÃO, OU EDIÇÃO, SEJA FEITA)

	public void delete(int index) {
		if (patrimonioForm.isEditing()) {
			patrimonioForm.addMessage("Favor concluir a edição já iniciada!");	
			return;
		}
	    try {
			patrimonioService.delete( patrimonioForm.getPatrimonioDTO(index).getId() );
			patrimonioForm.setDeleted(index);
	        
	    } catch (Exception e) {
	    	patrimonioForm.addMessage(e.getMessage());
	    	
	    }
		
	}

}
