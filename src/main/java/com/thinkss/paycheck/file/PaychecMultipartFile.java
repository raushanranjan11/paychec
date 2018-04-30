package com.thinkss.paycheck.file;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class PaychecMultipartFile {

	
	// not used
	
	 private List<MultipartFile> documentFiles;

	public List<MultipartFile> getFiles() {
		return documentFiles;
	}

	public void setFiles(List<MultipartFile> documentFiles) {
		this.documentFiles = documentFiles;
	}
	 
	 
	 
	 
}
