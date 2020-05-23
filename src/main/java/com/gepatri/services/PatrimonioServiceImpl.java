package com.gepatri.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.gepatri.dto.PatrimonioDTO;
import com.gepatri.form.util.CustomPageImpl;

@Service
public class PatrimonioServiceImpl implements PatrimonioService {

	private RestTemplate restTemplate; // = new RestTemplate();
	
	@Autowired
    public PatrimonioServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        //restTemplate = restTemplateBuilder.errorHandler(new CustomResponseException()).build();
		restTemplate = restTemplateBuilder.build();
    }
	
	private final String PATH = "http://localhost:8080/patrimonios";

	/*** FIND BY ID ***/
	@Override
	public PatrimonioDTO findById(String id) {
		
	    final String uri = PATH + "/{id}";
	     
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("id", id);
	     
	    return restTemplate.getForObject(uri, PatrimonioDTO.class, params );
	}

	/*** FIND BY NOME ***/
	@Override
	public List<PatrimonioDTO> findByNome(String nome) {
	    final String uri = PATH + "/nome/{nome}";
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("nome", nome);
	    return new LinkedList<PatrimonioDTO>(Arrays.asList( restTemplate.getForObject(uri, PatrimonioDTO[].class, params ) ));
	}

	/*** FIND ALL ***/
	@Override
	public List<PatrimonioDTO> findAll() {
	    return new LinkedList<PatrimonioDTO>(Arrays.asList( restTemplate.getForEntity(PATH, PatrimonioDTO[].class).getBody() ));
	}
	
	/*** FIND PAGE ***/
/*	
	@Override
	public List<Page<PatrimonioDTO>> findPage(Integer page, Integer linesPerPage, String orderBy, String direction ) {
	    final String uri = PATH + "/page/";
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("page", page.toString());
	    params.put("linesPerPage", linesPerPage.toString());
	    params.put("orderBy", orderBy.toString());
	    params.put("direction", direction.toString());
	    return new LinkedList<PatrimonioDTO>(Arrays.asList( restTemplate.getForObject(uri, PatrimonioDTO[].class, params ) ));
	}
*/
	/*** FIND PAGE LESS PARAMETERS ***/
	@Override
	public CustomPageImpl<PatrimonioDTO> findPage(Integer pageNumber, Integer linesPerPage ) {
	    final String uri = PATH + "/page";
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("page", pageNumber.toString());
	    params.put("linesPerPage", linesPerPage.toString());
	    return  ( (CustomPageImpl<PatrimonioDTO>) restTemplate.getForObject(uri, CustomPageImpl.class, params )) ;
	}

	/*** FIND PAGE BY NOME ***/
	@Override
	public CustomPageImpl<PatrimonioDTO> findPageByNome(Integer pageNumber, Integer linesPerPage, String nome) {
	    final String uri = PATH + "/page" + "?page=" + pageNumber.toString() + "&linesPerPage=" + linesPerPage + "&nome=" + nome; 
	    return  ( (CustomPageImpl<PatrimonioDTO>) restTemplate.getForObject(uri, CustomPageImpl.class ) ) ;
	}
	
	/*** INSERT ***/
	@Override
	public PatrimonioDTO insert(PatrimonioDTO patrimonioDTO) {
		patrimonioDTO = restTemplate.postForObject( PATH, patrimonioDTO, PatrimonioDTO.class);
		return patrimonioDTO;
	}
	
	/*** UPDATE ***/
	@Override
	public void update(PatrimonioDTO patrimonioDTO) {
	    final String uri = PATH + "/{id}";
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("id", patrimonioDTO.getId().toString() );
	    
	    restTemplate.put(uri, patrimonioDTO, params);
	}
	
	/*** UPLOAD IMAGE ***/
	@Override
	public void upload(byte[] image, Integer id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", image);		

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
	    final String uri = PATH + "/upload/{id}";
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("id", id.toString() );
		restTemplate.put(uri, requestEntity, String.class, params);
	}
	
	/*** DELETE ***/
	@Override
	public void delete(Integer id) {
	    final String uri = PATH + "/{id}";
	     
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("id", id.toString() );
	     
	    restTemplate.delete(uri, params);
	}
}
