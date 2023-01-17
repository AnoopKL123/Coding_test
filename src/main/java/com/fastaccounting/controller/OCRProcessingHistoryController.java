package com.fastaccounting.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fastaccounting.entity.OCRProcessing;
import com.fastaccounting.service.OCRProcessingServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OCRProcessingHistoryController {

	@Autowired
	OCRProcessingServiceImpl service;

	public List<OCRProcessing> saveReceiptDetails(List<OCRProcessing> ocrImg) {
		return service.saveAllReceipts(ocrImg);
	}

	@RequestMapping("/history")
	public ModelAndView history(HttpServletRequest req) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	    Date now = new Date();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		Date result = cal.getTime();
		
		ModelAndView mv = new ModelAndView("history");
		try {
			String fromDate = req.getParameter("fromDate") == null ? dateFormat.format(result) : req.getParameter("fromDate");
			String toDate = req.getParameter("toDate") == null ? dateFormat.format(now) : req.getParameter("toDate");
			
			List<OCRProcessing> hist = service.getHistoryDetails(fromDate, toDate);
			mv.addObject("history", hist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
