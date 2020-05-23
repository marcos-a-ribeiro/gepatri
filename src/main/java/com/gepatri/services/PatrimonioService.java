package com.gepatri.services;

import java.util.List;

import com.gepatri.dto.PatrimonioDTO;
import com.gepatri.form.util.CustomPageImpl; 


public interface PatrimonioService {
	/*** FIND BY ID ***/
	public PatrimonioDTO findById(String id) ;

	/*** FIND BY NOME ***/
	public List<PatrimonioDTO> findByNome(String id) ;
	
	/*** FIND ALL***/
	public List<PatrimonioDTO> findAll() ;
	
	/*** FIND PAGE***/
//	public List<PatrimonioDTO> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) ;

	/*** FIND PAGE LESS PARAMETERS***/
	public CustomPageImpl<PatrimonioDTO> findPage(Integer page, Integer linesPerPage);

	/*** FIND PAGE BY NOME***/
	public CustomPageImpl<PatrimonioDTO> findPageByNome(Integer pageNumber, Integer linesPerPage, String nome);
	
	/*** INSERT ***/
	public PatrimonioDTO insert(PatrimonioDTO patrimonioDTO) ;
	
	/*** UPDATE ***/
	public void update(PatrimonioDTO patrimonioDTO) ;
	
	/*** UPLOAD IMAGE ***/
	public void upload(byte[] image, Integer id);
	
	/*** DELETE ***/
	public void delete(Integer id) ;
	
}
