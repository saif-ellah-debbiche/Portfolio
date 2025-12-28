package org.example.portfolio.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.portfolio.enums.SavingPlace;

import java.sql.Blob;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String darkUrl;
    private String lightUrl;
    private String defaultUrl;
    private String name;

    @Lob
    private byte[] content;


    @Enumerated(EnumType.STRING)
    private SavingPlace savingPlace;
}
