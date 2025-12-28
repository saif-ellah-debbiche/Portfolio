package org.example.portfolio.dtos;
import lombok.Data;
import org.example.portfolio.entities.Image;
import org.example.portfolio.enums.CompletionStatus;

import java.util.Date;


@Data
public class CertificationDto {
    private String title;
    private String issuer;
    private Date issueDate;
    private Date expirationDate;
    private CompletionStatus status;
    private String credentialUrl;
    private String description;
    private ImageDto image    ;
}
