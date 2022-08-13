package com.springboot.blogging.app.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class FileUpload {
	
	@Deprecated
	private final String UPLOAD_DIR  = "/Users/adnanghori/springboot-workspace/BOOK-REST-API/src/main/resources/static/images";
	private final String CLASS_PATH_RESOURCE = new ClassPathResource("").getFile().getAbsolutePath(); 
	
	public FileUpload() throws IOException{
		
	}
	
	@Deprecated 
	/*
	 * @deprecated use {@link #upload(MultipartFile file)} instead.
	 * */
	public boolean uploadFile(MultipartFile file) {
		boolean value = false;
		try {
			
			InputStream inputStream = file.getInputStream();
			byte data[] = new byte[inputStream.available()];
			inputStream.read(data);
			
			FileOutputStream fileOutputStream = new FileOutputStream(UPLOAD_DIR+File.separator+file.getOriginalFilename());
			fileOutputStream.write(data);
			fileOutputStream.flush();
			fileOutputStream.close();
			value = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return value;
	}
	public boolean upload(MultipartFile file) {
		 boolean value = false;
		 try {
			 Files.copy(file.getInputStream(),Paths.get(CLASS_PATH_RESOURCE+File.separator+file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
			 value = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 return value;
	}
}
