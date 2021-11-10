package com.eleks.academy.pharmagator.dataproviders;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.services.CsvParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Qualifier("fileSystemDataProvider")
@RequiredArgsConstructor
public class FileSystemDataProvider implements DataProvider {

	private final CsvParserService csvParserService;

	@Value("${pharmagator.data-providers.filesystem.path}")
	private String pathToFiles;

	@Override
	public Stream<MedicineDto> loadData() {
		try (Stream<Path> paths = Files.walk(Paths.get(pathToFiles))) {
			List<Path> collect = paths.filter(Files::isRegularFile).collect(Collectors.toList());
			return collect.stream().filter(Files::isRegularFile)
					.flatMap(path -> {
						try (FileInputStream inputStream = new FileInputStream(path.toFile())) {
							List<MedicineDto> medicineDtos = this.csvParserService.parse(inputStream);
							return medicineDtos.stream();
						} catch (IOException e) {
							e.printStackTrace();
							return Stream.empty();
						}
					});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Stream.empty();
	}
}
