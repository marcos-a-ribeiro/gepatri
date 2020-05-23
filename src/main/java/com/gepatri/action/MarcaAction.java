package com.gepatri.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.annotation.SessionScope;

import com.gepatri.dto.MarcaDTO;
import com.gepatri.form.MarcaForm;
import com.gepatri.form.util.CustomPageImpl;
import com.gepatri.services.MarcaServiceImpl;

@Component
@SessionScope
public class MarcaAction {
	
	@Autowired
	MarcaServiceImpl marcaService;
	
	@Autowired
	MarcaForm marcaForm;
	
	private static final Integer LINHAS_POR_PAGINA = 11;
	
	// PESQUISAR MARCAS
	
	public void find() {

		String nome = marcaForm.getPesquisa();

        // SE NENHUM PARÂMETRO FOR INFORMADO, IRÁ BUSCAR TODOS OS REGISTROS
        
        if (nome == null || nome.isBlank()) {
			if (findPage(0, LINHAS_POR_PAGINA) == 0) {
	            marcaForm.addMessage("A pesquisa não retornou nenhum resultado!");			
			};
			return;
		}

        // CASO SEJA POSSÍVEL CONVERTER PARA INTEIRO, IRÁ PESQUISAR PELO ID
        
        try {
			Integer.parseInt( nome );
			MarcaDTO marcaDTO = new MarcaDTO();
			marcaDTO = marcaService.findById( nome );
			if(marcaDTO == null) {
				marcaForm.addMessage("Nenhum registro encontrado com o ID informado!");
			} else {
				marcaForm.insertMarcaDTO(marcaDTO);
				
			}
			
		} catch (NumberFormatException e ) {
			if (findPageByNome(0, LINHAS_POR_PAGINA, nome) == 0) {
				marcaForm.addMessage("A pesquisa não retornou nenhuma marca com o NOME informado!");			
			};

		} catch (HttpServerErrorException e) {
			e.printStackTrace();
			marcaForm.setEmpty();
			marcaForm.addMessage("Item não encontrado!");			
			
		} catch (Exception e) {
			e.printStackTrace();
			marcaForm.setEmpty();
			marcaForm.addMessage("Ocorreu um erro inesperado!");			
		}

		marcaForm.refreshStatus();
	}

	// PESQUISA POR TODOS OS ITENS NO BD (OBTEM UMA DETERMINADA PÁGINA DE UMA LISTA PAGINADA)
	
	public Long findPage(Integer pageNumber, Integer linesPerPage) {
		CustomPageImpl<MarcaDTO> page = marcaService.findPage(pageNumber, linesPerPage);
		marcaForm.setPage(page);
		return page.getTotalElements();
	}

	// PESQUISA DE ACORDO COM O NOME INFORMADO NO CAMPO DE PESQUISA (OBTEM UMA LISTA PAGINADA)
	
	public Long findPageByNome(Integer pageNumber, Integer linesPerPage, String nome) {
		CustomPageImpl<MarcaDTO> page = marcaService.findPageByNome(pageNumber, linesPerPage, nome);
		marcaForm.setPage(page);
		return page.getTotalElements();
	}
	
	// INSERE OU ATUALIZA NO BD, DE ACORDO COM O STATUS (NOVO OU EM EDIÇÃO)
	
	public void insertOrUpdate(Integer index) {
		if (marcaForm.isNew() ) {
			insert();
		} else {
			update(index);
		}
	}
	
	// INSERE NO BD O NOVO ITEM
	
	public void insert() {
		
		MarcaDTO marcaDTONew = marcaForm.getMarcaDTO(0);
		marcaDTONew = marcaService.insert(marcaDTONew);
		marcaForm.getMarcasDTO().set(0, marcaDTONew);
		marcaForm.refreshStatus();
	}
	
	// ATUALIZA NO BD O ITEM EDITADO

	public void update(Integer index) {
		MarcaDTO marcaDTO = marcaForm.getMarcaDTO(index);
		marcaService.update( marcaDTO );
		marcaForm.refreshStatus();
	}
	
	// DELETA NO BD E MARCA O ITEM COMO EXCLUÍDO (IMPEDE QUE UMA NOVA TENTATIVA DE REMOÇÃO, OU EDIÇÃO, SEJA FEITA)
	public void delete(int index) {
		if (marcaForm.isEditing()) {
			marcaForm.addMessage("Favor concluir a edição já iniciada!");	
			return;
		}
	    try {
			marcaService.delete( marcaForm.getMarcaDTO(index).getId() );
			marcaForm.setDeleted(index);
	        
	    } catch (Exception e) {
	    	marcaForm.addMessage("Ocorreu um erro ao tentar excluir: " + e.getMessage());
	    	
	    }
		
	}
	
}
