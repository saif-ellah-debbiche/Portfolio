package org.example.portfolio.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.example.portfolio.dtos.ImageDto;
import org.example.portfolio.dtos.ImageToTransfer;
import org.example.portfolio.entities.Image;
import org.example.portfolio.enums.ImageSize;
import org.example.portfolio.enums.ImageType;
import org.example.portfolio.enums.SavingPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


@Component
public class ImageManagement {
    @Autowired
    private Cloudinary cloudinary;
    private static final String UPLOAD_DIR="images/";

    public String saveImageLocally(MultipartFile image) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        String imageName= UUID.randomUUID()+"_"+image.getOriginalFilename();

        Path imagePath=uploadPath.resolve(imageName);
        Files.copy(image.getInputStream(),imagePath, StandardCopyOption.REPLACE_EXISTING);
        return imagePath.toString();
    }
    public String updateLocalImage(MultipartFile image,String oldImageName) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(oldImageName);
        Files.deleteIfExists(filePath);
        String newImageName= UUID.randomUUID()+"_"+image.getOriginalFilename();

        Path imagePath=uploadPath.resolve(newImageName);
        Files.copy(image.getInputStream(),imagePath, StandardCopyOption.REPLACE_EXISTING);
        return newImageName;
    }


    public Image saveImageInCloudinary(ImageToTransfer image) throws IOException {
        Map uploadParams = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true,
                "transformation", new Transformation<>()
                        .width(image.getWantedSize().getWidth()) // Specify the desired width
                        .crop("scale") // Or other cropping modes like "fill", "limit", "pad", etc.
        );
        Map uploadResult=null;
        switch (image.getImageType()){
            case ImageType.MULTIPART_FILE:
                uploadResult= saveFromMultipartToCloudinary(image.getContent(),uploadParams);
                break; // optional, but recommended to prevent fall-through
            default:
                // code to be executed if no case matches the expression
                break; //
        }
        return   Image.builder()
                .defaultUrl(uploadResult!=null?uploadResult.get("url").toString():"")
                .savingPlace(SavingPlace.CLOUDINARY)
                .name(uploadResult!=null?uploadResult.get("public_id").toString():"")
                .build();
    }

    private Map<String,String> saveFromMultipartToCloudinary(MultipartFile image,Map uploadParams) throws IOException {
        uploadParams.put("public_id",image.getOriginalFilename());
        Map<String,String> map =  cloudinary.uploader().upload(image.getBytes(),uploadParams);
        System.out.println("Results from cloudinary : "+map);
        return map;
    }

    public ArrayList<Image> saveMultipartFilesToCloudinary(MultipartFile[] imageFiles, ImageSize wantedSize) throws IOException {
        ArrayList<Image> images=new ArrayList<>();
        for(MultipartFile imageFile:imageFiles){
        images.add(saveImageInCloudinary(ImageToTransfer.builder()
                        .imageType(ImageType.MULTIPART_FILE)
                        .content(imageFile)
                .wantedSize(wantedSize)
                .build() ));
        }
        return images;
    }

    public void deleteImagesFromCloudinary(List<Image> images) throws IOException {
                for(Image image:images){
                    cloudinary.uploader().destroy(image.getName(), new HashMap());
                }
    }
    public void deleteImagesByNamesFromCloudinary(List<String> imageNames) throws IOException {
                for(String image:imageNames){
                    cloudinary.uploader().destroy(image, new HashMap());
                }
    }
}
