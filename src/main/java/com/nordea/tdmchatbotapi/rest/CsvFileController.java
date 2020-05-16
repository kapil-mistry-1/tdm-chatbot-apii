package com.nordea.tdmchatbotapi.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nordea.tdmchatbotapi.model.Category;
import com.nordea.tdmchatbotapi.service.CsvFileService;

@CrossOrigin
@RestController
public class CsvFileController {
	
	@Autowired
	private CsvFileService csvFileService;
	
	@CrossOrigin
	@RequestMapping("/getAllCategories")
	public List<Category> getAllCategories() {		
		return csvFileService.readDataFromCsv();
	}
	
	@PostMapping("/addRow")
	public void addRow(@Valid @RequestBody Category category) {
		csvFileService.addRow(category);		
	}
	
	@PostMapping("/editRow")
	public void editRow(@Valid @RequestBody Category category) {
		csvFileService.editRow(category);		
	}
	
	@PostMapping("/deleteRow")
	public void deleteRow(@Valid @RequestBody Category category) {
		csvFileService.deleteRow(category);		
	}

}
