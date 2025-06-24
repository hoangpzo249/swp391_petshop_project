/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Lenovo
 */
public class ImageUploader {

    public static String saveImage(Part part, String subDirectory, HttpServletRequest request) throws IOException {
        if (part == null || part.getSize() == 0 || part.getSubmittedFileName() == null || part.getSubmittedFileName().isEmpty()) {
            return null;
        }
        if (subDirectory == null || subDirectory.trim().isEmpty()) {
            throw new IllegalArgumentException("Sub-directory for saving the file cannot be null or empty.");
        }

        String uniqueFileName = generateUniqueFileName(part);
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + subDirectory;

        File uploadDir = new File(uploadFilePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        part.write(uploadFilePath + File.separator + uniqueFileName);

        String relativePath = subDirectory.replace(File.separator, "/") + "/" + uniqueFileName;
        return relativePath;
    }

    private static String generateUniqueFileName(Part part) {
        String originalFileName = part.getSubmittedFileName();
        String sanitizedFileName = originalFileName.replaceAll("[^a-zA-Z0-9.-]", "_");

        String fileExtension = "";
        int i = sanitizedFileName.lastIndexOf('.');
        if (i > 0) {
            fileExtension = sanitizedFileName.substring(i);
            sanitizedFileName = sanitizedFileName.substring(0, i);
        }

        return System.currentTimeMillis() + "_" + sanitizedFileName + fileExtension;
    }
}
