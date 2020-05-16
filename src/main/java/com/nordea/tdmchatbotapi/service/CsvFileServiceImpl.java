package com.nordea.tdmchatbotapi.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nordea.tdmchatbotapi.model.Category;
import com.nordea.tdmchatbotapi.util.ChatUtil;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

@Service("csvFileService")
public class CsvFileServiceImpl implements CsvFileService {	

	public List<Category> readDataFromCsv() {
		List<Category> categories = new ArrayList<>();
		try {
			FileReader filereader = new FileReader(ChatUtil.getFilePath());
			CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
			List<String[]> allData = csvReader.readAll();
			int id = 0;
			for (String[] row : allData) {
				Category category = getCategory(row, id);
				categories.add(category);
				id++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categories;
	}	


	public void addRow(Category category) {
		CSVReader csvReader;
		try {
			if (category != null ) {
				csvReader = new CSVReader(new FileReader(ChatUtil.getFilePath()));
				List<String[]> allElements = csvReader.readAll();
				allElements.add(getCategory(category));
				FileWriter sw = new FileWriter(ChatUtil.getFilePath());
				CSVWriter writer = new CSVWriter(sw);
				writer.writeAll(allElements);
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editRow(Category category) {
		CSVReader csvReader;
		try {
			if (category != null) {
				csvReader = new CSVReader(new FileReader(ChatUtil.getFilePath()));
				List<String[]> allElements = csvReader.readAll();
				allElements.get(category.getId())[4] = category.getAnswer();
				FileWriter sw = new FileWriter(ChatUtil.getFilePath());
				CSVWriter writer = new CSVWriter(sw);
				writer.writeAll(allElements);
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteRow(Category category) {
		CSVReader csvReader;
		try {
			if (category != null) {
				csvReader = new CSVReader(new FileReader(ChatUtil.getFilePath()));
				List<String[]> allElements = csvReader.readAll();
				allElements.remove(category.getId());
				FileWriter sw = new FileWriter(ChatUtil.getFilePath());
				CSVWriter writer = new CSVWriter(sw);
				writer.writeAll(allElements);
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Category getCategory(String[] row, int id) {
		Category category = new Category();
		if (row != null && row.length > 0) {
			category.setId(id);
			category.setQuestion(row[1]);
			category.setAnswer(row[4]);			
		}
		return category;
	}

	private String[] getCategory(Category category) {
		String[] strArray = new String[] { "0", category.getQuestion(), "*", "*", category.getAnswer(),
				"a-custom-entry.aiml" };
		return strArray;
	}

}
