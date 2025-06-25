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
        // --- 1) Basic validation ---
        if (part == null || part.getSize() == 0
            || part.getSubmittedFileName() == null
            || part.getSubmittedFileName().isEmpty()) {
            return null;
        }
        if (subDirectory == null || subDirectory.trim().isEmpty()) {
            throw new IllegalArgumentException("Sub-directory cannot be null or empty.");
        }

        // --- 2) Read the base uploads folder name from web.xml ---
        String baseDirName = request.getServletContext().getInitParameter("UPLOAD_BASE_DIR");
        if (baseDirName == null || baseDirName.trim().isEmpty()) {
            throw new IllegalStateException("UPLOAD_BASE_DIR not set in web.xml");
        }

        // --- 3) Resolve project root (option 2) ---
        //    When run from IDE, "." is the project root. In deployed WAR it’ll be the container working dir.
        File projectRoot = new File(".").getCanonicalFile();

        // --- 4) Build the full upload directory path ---
        File uploadDir = new File(projectRoot,
                                  baseDirName + File.separator + subDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // --- 5) Generate unique filename & write file ---
        String uniqueFileName = generateUniqueFileName(part);
        File savedFile = new File(uploadDir, uniqueFileName);
        part.write(savedFile.getAbsolutePath());

        // --- 6) Return a web‐relative path (use this in <img src="…"> or store in DB) ---
        //     e.g. "uploads/images/pet_images/163847324324_cat.png"
        return baseDirName
             + "/"
             + subDirectory.replace(File.separator, "/")
             + "/"
             + uniqueFileName;
    }

    private static String generateUniqueFileName(Part part) {
        String orig = part.getSubmittedFileName().replaceAll("[^a-zA-Z0-9.\\-]", "_");
        String ext = "";
        int idx = orig.lastIndexOf('.');
        if (idx > 0) {
            ext = orig.substring(idx);
            orig = orig.substring(0, idx);
        }
        return System.currentTimeMillis() + "_" + orig + ext;
    }
}
