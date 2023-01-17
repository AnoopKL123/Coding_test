package com.fastaccounting.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;

public class Common {

	public String storeFiles(Part filePart) throws ServletException, IOException {
//		response.setContentType("text/html;charset=UTF-8");
		String today = LocalDate.now().toString().replace("-", "");

		Files.createDirectories(Paths.get("files/uploadedFile/" + today));
		File starting = new File("files/uploadedFile/" + today + "/");
        final String path = starting.getAbsolutePath();
//        final Part filePart = request.getPart("myfile[]");
        String fileName = path + File.separator + filePart.getSubmittedFileName();

        OutputStream otpStream = null;
        InputStream iptStream = null;
        try {
            otpStream = new FileOutputStream(new File(fileName), false);
            iptStream = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];
 
            while ((read = iptStream.read(bytes)) != -1) {
                otpStream.write(bytes, 0, read);
            }
            Thread.sleep(10);
        } catch (FileNotFoundException | InterruptedException fne) {
        	fne.printStackTrace();
        }
        return fileName;
	}
}
