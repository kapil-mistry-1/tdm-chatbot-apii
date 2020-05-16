package com.nordea.tdmchatbotapi.service;

import java.util.List;

import com.nordea.tdmchatbotapi.model.Category;

public interface CsvFileService {
	
	public List<Category> readDataFromCsv();	
	
	public void addRow(Category category);
	
	public void editRow(Category category);
	
	public void deleteRow(Category category); 

}
