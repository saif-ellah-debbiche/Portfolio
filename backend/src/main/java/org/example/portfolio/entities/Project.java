package org.example.portfolio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.portfolio.enums.WorkingType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String summary;
    private String title;
    private Date startDate;
    private Date endDate;
    private String location;
    private String usedTechnologies;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;


    @OneToOne(cascade = CascadeType.PERSIST)
    private Company company;

    private WorkingType workingType;


    @OneToOne
    private Blog blog;

}
