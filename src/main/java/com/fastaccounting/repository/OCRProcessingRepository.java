package com.fastaccounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.fastaccounting.entity.OCRProcessing;

@Component
public interface OCRProcessingRepository extends JpaRepository<OCRProcessing, Integer> {

	@Query("select ocr from OCRProcessing ocr where ocr.date between ?1 and ?2 order by ocr.date")
	public List<OCRProcessing> getHistoryDetailsByDate(String fromDate, String toDate);
}
