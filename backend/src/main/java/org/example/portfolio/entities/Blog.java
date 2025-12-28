package org.example.portfolio.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.example.portfolio.enums.SavingPlace;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String contentUrl;


    @Lob // Character Large Object (CLOB)
    @Column(columnDefinition = "TEXT")
    private String content;

    private String title;
    private String summary;
    private Date date;
    private String defaultImageLink;


    @Enumerated(EnumType.STRING)
    private SavingPlace savingPlace;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Image> images;





}
