package com.fastaccounting.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fastaccounting.common.Common;
import com.fastaccounting.entity.OCRProcessing;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import javax.xml.bind.DatatypeConverter;

@SuppressWarnings("deprecation")
@Controller
public class OCRProcessingController {

	private final String apiUrl = "https://api-gt01-sandbox.fastaccounting.jp/fa/v1.5/";
	private final String apiKey = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYXN0YWNjb3VudGluZy5hcGkudG9rZW4iLCJhdWQiOiJmYXN0YWNjb3VudGluZy5hcGkudG9rZW4iLCJqdGkiOiIwOTFhMzhhODc3OTZiY2FhYmRhZDI3MDA4YzMxMzE5NWRkOWU1ODZjZTU3ODZkMTdmYzgwN2RiOTYyMDI4YWRlIiwiaWF0IjoxNjY4MDY5MTAxLCJpZGVudGlmaWVyIjoyMjI2fQ.DQL42w01IwveWouxQINN-Dz-OGsyi2raGRUFyVu-lvI";
	@Autowired
	OCRProcessingHistoryController ocrHistory;

	@RequestMapping("/homePage")
	public String homePage() {
		System.out.println("HomePage");
		return "ocrprocessing";
	}

	@RequestMapping("/")
	public String home() {
		return "ocrprocessing";
	}

	@RequestMapping("/getDetails")
	public ModelAndView getDetails(HttpServletRequest req, HttpServletResponse res) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss"); 
		List<String> images = new ArrayList<>();
		Common obj = new Common();
		String today = LocalDate.now().toString().replace("-", "");
		String path = "files/extractedImages/" + today;

		try {
			Files.createDirectories(Paths.get(path));
			Collection<Part> parts = req.getParts();
			for (Part filePart : parts) {
				String extension = FilenameUtils.getExtension(filePart.getSubmittedFileName());
				String filePath = obj.storeFiles(filePart);
	
				if (extension.equals("pdf")) {
					Unirest.setTimeouts(0, 0);
					HttpResponse<String> response = Unirest.post(apiUrl + "convert_to_jpg")
						.header("Authorization", apiKey)
						.field("file", new File(filePath))
						.asString();
	
					JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();
					String base64String = jsonObject.getAsJsonObject("data").get("image").toString();
					String[] strings = base64String.split(",");
					for (int i = 1; i < strings.length; i+=2) {
						Date date = Calendar.getInstance().getTime();
						byte[] data = DatatypeConverter.parseBase64Binary(strings[i]);
						String fileName = path + "/" + dateFormat.format(date) + ".jpg";
						File file = new File(fileName);
						try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file, false))) {
							outputStream.write(data);
						}
						images.add(fileName);
					}
				} else {
					Date date = Calendar.getInstance().getTime();
					String fileName = path + "/" + dateFormat.format(date) + ".jpg";
					File file = new File(fileName);
					Files.copy(new File(filePath).toPath(),
							file.toPath(),
					        StandardCopyOption.REPLACE_EXISTING);
					images.add(fileName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return extractReceiptDetails(images);
	}

	public ModelAndView extractReceiptDetails(List<String> images) {
		ModelAndView mv = new ModelAndView("ocrprocessing");
		List<OCRProcessing> ocrList = new ArrayList<>();
		JsonObject jsonObject = new JsonObject();

		for (String img : images) {
			try {
				Unirest.setTimeouts(0, 0);
				HttpResponse<String> response = Unirest.post(apiUrl + "receipt")
				  .header("Authorization", apiKey)
				  .field("file", new File(img))
				  .asString();

				jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();

				String date = jsonObject.get("date").toString().replace("\"", "");
				String issuer = jsonObject.get("issuer").toString().replace("\"", "");
				String tel = jsonObject.get("tel").toString().replace("\"", "");
				String amt = jsonObject.get("amount").toString().replace("\"", "");
				OCRProcessing ocrImg = new OCRProcessing(date, issuer, amt, tel);

				ocrList.add(ocrImg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ocrHistory.saveReceiptDetails(ocrList);
		mv.addObject("receipt", ocrList);
		return mv;
	}

}
