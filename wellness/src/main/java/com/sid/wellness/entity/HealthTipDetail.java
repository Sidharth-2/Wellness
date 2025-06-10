package com.sid.wellness.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "health_tip_detail")
@Entity
@Data
public class HealthTipDetail {

    @Id
    private Long id;  // Same ID as HealthTip

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private HealthTip healthTip;

    private String detailedDescription;
    private String imageUrl;
    private String source; 

}

