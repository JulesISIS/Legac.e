/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testament.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.lang.Nullable;

/**
 *
 * @author nelsonrogers
 */
@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
@Entity
public class Volonte {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;
    
    @NonNull
    private Reseau reseau;
    
    @Nullable
    private String message;
    
    @Nullable
    private String idTweet;
    
    @Nullable
    private String usernameDestinataire;
    
    @ManyToOne
    private Utilisateur utilisateur;

    private boolean paypal;

    private boolean pinterest;

    private boolean fbCommemoration;

    private boolean fbFormulaire;

    private boolean instaCommemoration;

    private boolean instaFormulaire;
    
    private boolean google;

    
}