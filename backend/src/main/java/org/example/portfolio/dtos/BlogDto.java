package org.example.portfolio.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.portfolio.entities.Image;
import org.example.portfolio.enums.SavingPlace;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogDto {
    private String contentUrl;
    private String title;
    private String content;
    private SavingPlace savingPlace;
    private SavingPlace imageSavingPlace;
    private String summary;
    private Date date;
    private String defaultImageLink;
}
