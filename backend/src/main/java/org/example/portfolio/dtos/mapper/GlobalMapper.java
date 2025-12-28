package org.example.portfolio.dtos.mapper;

import org.example.portfolio.dtos.CertificationDto;
import org.example.portfolio.dtos.EducationDto;
import org.example.portfolio.dtos.ImageDto;
import org.example.portfolio.dtos.ProjectDto;
import org.example.portfolio.entities.Certification;
import org.example.portfolio.entities.Education;
import org.example.portfolio.entities.Image;
import org.example.portfolio.entities.Project;

import java.util.ArrayList;
import java.util.List;

public class GlobalMapper {
    public static ImageDto getImageDtoFromImage(Image image){
        return ImageDto.builder()
                .name(image.getName())
                .savingPlace(image.getSavingPlace())
                .defaultUrl(image.getDefaultUrl())
                .build();

    }

    public static ArrayList<ImageDto> getImageDtosFromImages(ArrayList<Image> images){
        ArrayList<ImageDto> imageDtos = new ArrayList<>();
        for(Image image: images){
            imageDtos.add(getImageDtoFromImage(image));
        }
        return imageDtos;

    }

    public static Project projectDtoToProject(ProjectDto projectDto){
        return Project.builder()
                .location(projectDto.getLocation())
                .title(projectDto.getTitle())
                .startDate(projectDto.getStartDate())
                .endDate(projectDto.getEndDate())
                .company(projectDto.getCompany())
                .summary(projectDto.getSummary())
                .workingType(projectDto.getWorkingType())
                .build();
    }

    public static Education getEducationFromEducationDto(EducationDto educationDto) {
        return Education.builder()
                .degree(educationDto.getDegree())
                .description(educationDto.getDescription())
                .status(educationDto.getStatus())
                .univercity(educationDto.getUnivercity())
                .startDate(educationDto.getStartDate())
                .endDate(educationDto.getEndDate())
                .build();
    }

    public static Education updateEducationFromEducationDto(EducationDto educationDto,Education education) {
        education.setStatus(educationDto.getStatus());
        education.setDescription(educationDto.getDescription());
        education.setDegree(educationDto.getDegree());
        education.setStatus(educationDto.getStatus());
        education.setStartDate(educationDto.getStartDate());
        education.setEndDate(educationDto.getEndDate());
        education.getUnivercity().setSummary(educationDto.getUnivercity().getSummary());
        education.getUnivercity().setName(educationDto.getUnivercity().getName());
        education.getUnivercity().setHomePageLink(educationDto.getUnivercity().getHomePageLink());
        education.getUnivercity().setLogoLink(educationDto.getUnivercity().getLogoLink());
    return education;
    }

    public static Certification getCertificationFromCertificationDto(CertificationDto certificationDto) {
        return Certification.builder()
                .credentialUrl(certificationDto.getCredentialUrl())
                .expirationDate(certificationDto.getExpirationDate())
                .image(Image.builder()
                        .defaultUrl(certificationDto.getImage().getDefaultUrl())
                        .name(certificationDto.getImage().getName())
                        .build())
                .issuer(certificationDto.getIssuer())
                .issueDate(certificationDto.getIssueDate())
                .title(certificationDto.getTitle())
                .status(certificationDto.getStatus())
                .description(certificationDto.getDescription())
                .build();
    }

    public static Certification updateCertificationFromCertificationDto(CertificationDto certificationDto, Certification certification) {
    certification.setDescription(certificationDto.getDescription());
    certification.getImage().setName(certificationDto.getImage().getName());
    certification.getImage().setDefaultUrl(certificationDto.getImage().getDefaultUrl());
    certification.setIssuer(certificationDto.getIssuer());
    certification.setStatus(certificationDto.getStatus());
    certification.setTitle(certificationDto.getTitle());
    certification.setCredentialUrl(certificationDto.getCredentialUrl());
    certification.setExpirationDate(certificationDto.getExpirationDate());
    certification.setIssueDate(certificationDto.getIssueDate());
    return certification;
    }
}
