package com.demo.project.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demo.project.filesystem.FileStorageService;
import com.demo.project.filesystem.UploadFileResponse;
import com.demo.project.models.Questionnaire;
import com.demo.project.security.services.QuestionnaireService;

@RestController
public class FileStorageController {
	
	@Autowired
    private FileStorageService fileStorageService;
	
	@Autowired
    private QuestionnaireService questionnaireService;
	
	 @PostMapping("/uploadFile/{QueID}")
	 @PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
	 @ResponseStatus(HttpStatus.CREATED)
	    public UploadFileResponse uploadSingleFile(@RequestParam("file") MultipartFile file,
	    		@PathVariable int QueID) {
		 Optional<Questionnaire> questionnaire = questionnaireService.findByID(QueID);
		 
		 Questionnaire content = questionnaire.get();
		 
	        String fileName = fileStorageService.storeFile(file);

	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/downlodfile/")
	                .path(fileName)
	                .toUriString();
	        
	        content.setFileUrl(fileDownloadUri);
	        questionnaireService.save(content);

	        return new UploadFileResponse(fileName, fileDownloadUri,
	                file.getContentType(), file.getSize());
	    }
	 
	 
	 
	
	 @PostMapping("/uploadFile")
	 @PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
	 @ResponseStatus(HttpStatus.CREATED)
	    public UploadFileResponse uploadSingleFile(@RequestParam("file") MultipartFile file) {
	        String fileName = fileStorageService.storeFile(file);

	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/downlodfile/")
	                .path(fileName)
	                .toUriString();

	        return new UploadFileResponse(fileName, fileDownloadUri,
	                file.getContentType(), file.getSize());
	    }
	 
	 @PostMapping("/uploadMultiple")
	 @PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
	 @ResponseStatus(HttpStatus.CREATED)
	    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
	        return Arrays.asList(files)
	                .stream()
	                .map(file -> uploadSingleFile(file))
	                .collect(Collectors.toList());
	    }

	    @GetMapping("/downloadFile/{fileName:.+}")
		@PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
		@ResponseStatus(HttpStatus.CREATED)
	    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
	        // Load file as Resource
	        Resource resource = fileStorageService.loadFileAsResource(fileName);

	        // Try to determine file's content type
	        String contentType = null;
	        try {
	            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	        } catch (IOException ex) {
	            System.out.print("Could not determine file type.");
	        }

	        // Fallback to the default content type if type could not be determined
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
	    }

}


