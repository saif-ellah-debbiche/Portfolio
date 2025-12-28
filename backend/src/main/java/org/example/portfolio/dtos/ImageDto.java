package org.example.portfolio.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.portfolio.enums.SavingPlace;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
    private Long id;
    private String darkUrl;
    private String lightUrl;
    private String defaultUrl;
    private String name;
    private byte[] content;
    private SavingPlace savingPlace;
}
