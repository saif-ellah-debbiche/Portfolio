package org.example.portfolio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.portfolio.enums.CompletionStatus;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String issuer;
    private Date issueDate;
    private Date expirationDate;
    private CompletionStatus status;
    private String credentialUrl;
    private String description;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    private Image image;





}
