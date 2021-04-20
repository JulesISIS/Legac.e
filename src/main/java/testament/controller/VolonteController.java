/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testament.controller;

import javax.mail.MessagingException;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import testament.dao.UserRepository;
import testament.dao.VolonteRepository;
import testament.entity.Reseau;
import testament.entity.Volonte;
import testament.entity.Utilisateur;
import testament.service.VolonteService;
import testament.validator.UserValidator;


/**
 *
 * @author nelsonrogers
 */

@Controller
@Slf4j
public class VolonteController {

    @Autowired
    VolonteRepository volonteDAO;

    @Autowired
    UserRepository utilisateurDAO;

    @GetMapping("/donnesTwitter")
    public String volonte(Model model) {
        model.addAttribute("volonte", new Volonte());
        return "donnesTwitter";
    }

    @PostMapping("/donnesTwitter")
    public String enregistrerVolontes(@AuthenticationPrincipal Utilisateur user, @Valid @ModelAttribute("volonte") Volonte volonte,
                                      RedirectAttributes redirectInfo) {
        log.info(user.getNom());

        String resultat;
        try {
            if (volonte.getIdTweet() != null || volonte.getMessage() != null || volonte.getUsernameDestinataire() != null) {

            }

            Utilisateur u = utilisateurDAO.getOne(user.getId());
            volonte.setUtilisateur(u);
            volonte.setReseau(Reseau.TWITTER);

            volonteDAO.save(volonte);
            resultat = "Vos préférences Twitter ont bien été enregistrés";

        } catch (Exception ex) {
            resultat = "Un problème est survenu : " + ex.getMessage();
            // log.error("Unable to tweet {}", message, ex);
        }
        redirectInfo.addFlashAttribute("resultat", resultat);
        return "redirect:/welcome";

    }
}