package com.sid.wellness.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "health_tip")
@Entity
@Data
public class HealthTip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    @OneToOne(mappedBy = "healthTip", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private HealthTipDetail detail;
}
