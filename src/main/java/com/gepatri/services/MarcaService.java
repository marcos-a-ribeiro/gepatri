package com.gepatri.services;

import java.util.List;

import com.gepatri.dto.MarcaDTO;
import com.gepatri.form.util.CustomPageImpl; 


public interface MarcaService {
	/*** FIND BY ID ***/
	public MarcaDTO findById(String id) ;

	/*** FIND BY NOME ***/
	public List<MarcaDTO> findByNome(String id) ;
	
	/*** FIND ALL***/
	public List<MarcaDTO> findAll() ;
	
	/*** FIND PAGE***/
//	public List<MarcaDTO> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) ;

	/*** FIND PAGE LESS PARAMETERS***/
	public CustomPageImpl<MarcaDTO> findPage(Integer page, Integer linesPerPage);
	
	/*** FIND PAGE BY NOME***/
	public CustomPageImpl<MarcaDTO> findPageByNome(Integer pageNumber, Integer linesPerPage, String nome);

	/*** INSERT ***/
	public MarcaDTO insert(MarcaDTO marcaDTO) ;
	
	/*** UPDATE ***/
	public void update(MarcaDTO marcaDTO) ;
	
	/*** DELETE ***/
	public void delete(Integer id) ;
	
}
