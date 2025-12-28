package org.example.portfolio.dtos;


import lombok.*;
import org.example.portfolio.entities.Company;
import org.example.portfolio.enums.ImageSize;
import org.example.portfolio.enums.WorkingType;

import java.util.ArrayList;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
private ArrayList<ImageDto> images;
private String summary;
private ArrayList<String> usedTechnologies;
private String usedTechsString;
private String title;
private Date startDate;
private Date endDate;
private Company company;
private String location;
private WorkingType workingType;
private BlogDto blog;
}
