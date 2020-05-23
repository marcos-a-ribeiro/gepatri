package com.gepatri.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.gepatri.dto.MarcaDTO;
import com.gepatri.form.util.CustomPageImpl;

@Service
public class MarcaServiceImpl implements MarcaService {

	private RestTemplate restTemplate; // = new RestTemplate();
	
	@Autowired
    public MarcaServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        //restTemplate = restTemplateBuilder.errorHandler(new CustomResponseException()).build();
		restTemplate = restTemplateBuilder.build();
    }
	
	private static final String PATH = "http://localhost:8080/marcas";
	
	/*** FIND BY ID ***/
	@Override
	public MarcaDTO findById(String id) {
		
	    final String uri = PATH + "/{id}";
	     
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("id", id);
	     
	    return restTemplate.getForObject(uri, MarcaDTO.class, params );
	}

	/*** FIND BY NOME ***/
	@Override
	public List<MarcaDTO> findByNome(String nome) {
	    final String uri = PATH + "/nome/{nome}";
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("nome", nome);
	    return new LinkedList<MarcaDTO>(Arrays.asList( restTemplate.getForObject(uri, MarcaDTO[].class, params ) ));
	}

	/*** FIND ALL ***/
	@Override
	public List<MarcaDTO> findAll() {
		
	    return new LinkedList<MarcaDTO>(Arrays.asList( restTemplate.getForEntity(PATH, MarcaDTO[].class).getBody() ));

	}
	
	/*** FIND PAGE ***/
/*	
	@Override
	public List<Page<MarcaDTO>> findPage(Integer page, Integer linesPerPage, String direction, String orderBy ) {
	    final String uri = PATH + "/page/";
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("page", page.toString());
	    params.put("linesPerPage", linesPerPage.toString());
	    params.put("orderBy", orderBy.toString());
	    params.put("direction", direction.toString());
	    return new LinkedList<MarcaDTO>(Arrays.asList( restTemplate.getForObject(uri, MarcaDTO[].class, params ) ));
	}
*/
	/*** FIND PAGE LESS PARAMETERS ***/
	@Override
	public CustomPageImpl<MarcaDTO> findPage(Integer pageNumber, Integer linesPerPage ) {
	    final String uri = PATH + "/page";
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("page", pageNumber.toString());
	    params.put("linesPerPage", linesPerPage.toString());
	    return  ( (CustomPageImpl<MarcaDTO>) restTemplate.getForObject(uri, CustomPageImpl.class, params )) ;
	}
	
	/*** FIND PAGE BY NOME ***/
	@Override
	public CustomPageImpl<MarcaDTO> findPageByNome(Integer pageNumber, Integer linesPerPage, String nome) {
	    final String uri = PATH + "/page" + "?page=" + pageNumber.toString() + "&linesPerPage=" + linesPerPage + "&nome=" + nome; 
	    return  ( (CustomPageImpl<MarcaDTO>) restTemplate.getForObject(uri, CustomPageImpl.class ) ) ;
	}
	
	/*** INSERT ***/
	@Override
	public MarcaDTO insert(MarcaDTO marcaDTO) {
		marcaDTO = restTemplate.postForObject( PATH, marcaDTO, MarcaDTO.class);
		return marcaDTO;
	}
	
	/*** UPDATE ***/
	@Override
	public void update(MarcaDTO marcaDTO) {
		
	    final String uri = PATH + "/{id}";
	     
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("id", marcaDTO.getId().toString() );
	     
	    restTemplate.put(uri, marcaDTO, params);
	}
	
	/*** DELETE ***/
	@Override
	public void delete(Integer id) throws HttpServerErrorException {
	    final String uri = PATH + "/{id}";
	     
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("id", id.toString() );
    	restTemplate.delete(uri, params);
	    
	}
}
