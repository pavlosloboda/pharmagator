package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CsvParserServiceImpl implements CsvParserService {

/*
	Example of csv file
	name, price, link, pharmacy
	Аспірин, 19.50, teest, DS
	Мефенамінова кислота, 21.00, teest, DS
*/
	private static final String NAME = "name";
	private static final String PRICE = "price";

	private static final String DELIMITER = ",";


	@Override
	public List<MedicineDto> parse(InputStream inputStream) {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			String header = reader.readLine();
			Map<String, Integer> headersMap = this.parseHeader(header);

			String line = reader.readLine();

			List<MedicineDto> medicines = new ArrayList<>();

			while (Objects.nonNull(line)) {
				MedicineDto medicineDto = this.parse(line.split(DELIMITER), headersMap);
				medicines.add(medicineDto);
			}
			return medicines;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return List.of();
	}

	private Map<String, Integer> parseHeader(String header) {
		Map<String, Integer> headersMap = new HashMap<>();
		String[] headers = header.split(DELIMITER);
		for (int i = 0; i < headers.length; i++) {
			headersMap.put(headers[i].trim(), i);
		}
		return headersMap;
	}

	private MedicineDto parse(String[] lines, Map<String, Integer> headers) {
		return MedicineDto.builder()
				.title(lines[headers.get(NAME)].trim())
				.price(new BigDecimal(lines[headers.get(PRICE)].trim()))
				.build();

	}

}
