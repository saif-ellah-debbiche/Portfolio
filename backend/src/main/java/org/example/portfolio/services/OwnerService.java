package org.example.portfolio.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.portfolio.dtos.CertificationDto;
import org.example.portfolio.dtos.EducationDto;
import org.example.portfolio.dtos.ProjectDto;
import org.example.portfolio.dtos.mapper.GlobalMapper;
import org.example.portfolio.entities.Certification;
import org.example.portfolio.entities.Education;
import org.example.portfolio.entities.Image;
import org.example.portfolio.entities.Project;
import org.example.portfolio.enums.ImageSize;
import org.example.portfolio.exceptions.ResourceNotFoundException;
import org.example.portfolio.repos.CertificationRepo;
import org.example.portfolio.repos.EducationRepo;
import org.example.portfolio.repos.ProjectRepo;
import org.example.portfolio.utils.ImageManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OwnerService {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private EducationRepo educationRepo;
    @Autowired
    private CertificationRepo certificationRepo;

    @Autowired
    private ImageManagement imageManagement;

    public ProjectDto createProject(MultipartFile[] imageFiles,String projectDetailsData) throws IOException {
        ProjectDto projectDto = getProjectDtoFromProjectString(projectDetailsData);

        //complete from resizing the image i still didn't do it
        ArrayList<Image> images =  imageManagement.saveMultipartFilesToCloudinary(imageFiles, ImageSize.PROJECT_BANNER);
        Project newProject=GlobalMapper.projectDtoToProject(projectDto);
        newProject.setImages(images);
        projectRepo.save(newProject);

        projectDto.setImages(GlobalMapper.getImageDtosFromImages(new ArrayList<>(newProject.getImages())));
        return projectDto;
    }

    public ProjectDto updateProject(Long projectId,ProjectDto projectDto ) throws ResourceNotFoundException {
            Project project=projectRepo.findById(projectId).orElse(null);
            if(project==null) throw new ResourceNotFoundException("Project","Id",projectId.toString());
            project.setTitle(project.getTitle());
            project.setUsedTechnologies(project.getUsedTechnologies());
            project.setCompany(project.getCompany());
            project.setLocation(project.getLocation());
            project.setEndDate(project.getEndDate());
            project.setStartDate(project.getStartDate());
            project.setSummary(project.getSummary());
            projectRepo.save(project);
            return projectDto;
    }

    public void deleteProject(Long projectId) throws ResourceNotFoundException {
        Project project = getResourceById(projectId,projectRepo::findById,"Project");
            if(!project.getImages().isEmpty()){
                try {
                    imageManagement.deleteImagesFromCloudinary(project.getImages());
                    projectRepo.delete(project);
                } catch (IOException e) {
                    throw new RuntimeException("Something went wrong when deleting project image from Cloudinary");
                }
            }
    }
 public List<Project> getProjects() {
           return projectRepo.findAll();
    }

    public void deleteImagesFromProject(Long projectId,List<String> imagePublicIds) throws IOException {
        Project project = getResourceById(projectId,projectRepo::findById,"Project");
        imageManagement.deleteImagesByNamesFromCloudinary(imagePublicIds);
        List<Image> imagesToDelete = project.getImages().stream().filter(image -> imagePublicIds.contains(image.getName())).collect(Collectors.toList());
        for(Image image:imagesToDelete) {
            project.getImages().remove(image);
        }
        projectRepo.save(project);
        }
        public void addImagesToProject(Long projectId,MultipartFile[] imageFiles) throws IOException {
        Project project = getResourceById(projectId,projectRepo::findById,"Project");
        List<Image> images= imageManagement.saveMultipartFilesToCloudinary(imageFiles,ImageSize.PROJECT_BANNER);
         project.getImages().addAll(images);
        projectRepo.save(project);
        }




    private ProjectDto getProjectDtoFromProjectString(String projectDetailsData) throws JsonProcessingException {
        ProjectDto projectDto= mapper.readValue(projectDetailsData,ProjectDto.class);
        projectDto.setUsedTechnologies(new ArrayList<>(List.of(projectDto.getUsedTechsString().split(","))));
        return projectDto;
    }
    public Education createEducation(EducationDto educationDto) {
        Education education =GlobalMapper.getEducationFromEducationDto(educationDto);
        return educationRepo.save(education);
    }

    public Education updateEducation(EducationDto educationDto,Long educationId) {
        Education education = getResourceById(educationId,educationRepo::findById,"Education");
        return educationRepo.save(GlobalMapper.updateEducationFromEducationDto(educationDto,education));
    }

    public Certification createCertification(CertificationDto certificationDto){
        Certification certification=GlobalMapper.getCertificationFromCertificationDto(certificationDto);
        return certificationRepo.save(certification);
    }

    public Certification updateCertification(CertificationDto certificationDto,Long certId){
        Certification certification = getResourceById(certId,certificationRepo::findById,"Certification");
        return certificationRepo.save(GlobalMapper.updateCertificationFromCertificationDto(certificationDto,certification));
    }





    private <T> T getResourceById(Long resourceId, Function<Long,Optional<T>> finder,String resourceName){
        T resource =finder.apply(resourceId).orElse(null);
        if(resource==null) throw new ResourceNotFoundException(resourceName,"Id",resourceId.toString());
        return resource;
    }
}
