package org.example.portfolio.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.portfolio.enums.CompletionStatus;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String degree;
    @Enumerated(EnumType.STRING)
    private CompletionStatus status;
    private Date startDate;
    private Date endDate;
    private String description;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Company univercity;
}
