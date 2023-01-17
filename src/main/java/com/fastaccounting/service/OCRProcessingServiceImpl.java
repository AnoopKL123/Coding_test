package com.fastaccounting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastaccounting.entity.OCRProcessing;
import com.fastaccounting.repository.OCRProcessingRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OCRProcessingServiceImpl {

	@Autowired
	OCRProcessingRepository repo;

	public OCRProcessing createReceipt(OCRProcessing ocrImg) {
		return repo.save(ocrImg);
	}

	public List<OCRProcessing> saveAllReceipts(List<OCRProcessing> ocrImg) {
		return repo.saveAll(ocrImg);
	}

	
	public List<OCRProcessing> getHistoryDetails(String fromDate, String toDate) {
		return repo.getHistoryDetailsByDate(fromDate, toDate);
	}
}
