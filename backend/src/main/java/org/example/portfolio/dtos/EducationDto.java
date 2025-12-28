package org.example.portfolio.dtos;


import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.portfolio.entities.Company;
import org.example.portfolio.enums.CompletionStatus;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EducationDto {
    private Long id;
    private String degree;
    private CompletionStatus status;
    private Date startDate;
    private Date endDate;
    private String description;
    private Company univercity;
}
