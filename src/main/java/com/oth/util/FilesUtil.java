package com.oth.util;
import java.io.*;
import java.nio.file.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Class util to improve
 */
@Slf4j
public class FilesUtil {

	private FilesUtil() {
	}

	/**
	 * save file
	 * @param dirFiles
	 * @param fileName
	 * @param multipartFile
	 * @throws IOException
	 */
	public static void saveFile(String dirFiles, String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(dirFiles);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("failed store file : " + fileName, ioe);
		}
	}

	/**
	 *
	 * @param directory
	 * @param fileName
	 * @return
	 */
	public static byte[] getFile(String directory, String fileName) {
		try {
			log.info("directory:  {} fileName : {}", directory, fileName);
			return IOUtils.toByteArray(Files.newInputStream(new File(directory + fileName).toPath()));
		} catch (IOException e) {
			try {
				return IOUtils.toByteArray(new ClassPathResource("/static/images/anonyme.jpg").getInputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
}