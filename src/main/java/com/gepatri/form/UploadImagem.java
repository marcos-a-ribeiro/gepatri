package com.gepatri.form;

import java.io.Serializable;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gepatri.dto.PatrimonioDTO;
// import org.springframework.http.codec.multipart.Part;
import com.gepatri.services.PatrimonioServiceImpl;

@Component
public class UploadImagem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	PatrimonioServiceImpl patrimonioService;

	@Autowired
	PatrimonioForm patrimonioForm;

	private Part partFile;

	// GRAVA IMAGEM NO BD
	public void uploadFile(int index) {
		if (partFile!=null) {
		  PatrimonioDTO p = patrimonioForm.getPatrimonioDTO(index);
		  patrimonioService.upload(p.getImagem(), p.getId());
		  patrimonioForm.refreshStatus();
		} else {
			patrimonioForm.addMessage("Imagem não foi carregada!");
		}
	}

	public Part getPartFile() {
		return partFile;
	}

	public void setPartFile(Part partFile) {
		this.partFile = partFile;
	}	
}