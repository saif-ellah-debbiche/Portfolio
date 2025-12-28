package org.example.portfolio.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.portfolio.enums.ImageSize;
import org.example.portfolio.enums.ImageType;
import org.example.portfolio.enums.SavingPlace;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageToTransfer {
    private MultipartFile content;
    private String fromUrl ;
    private ImageType imageType;
    private ImageSize wantedSize;
}
