package testament.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import testament.dao.UserRepository;
import testament.dao.VolonteRepository;
import testament.entity.Utilisateur;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import testament.entity.Reseau;
import testament.entity.Volonte;
import testament.service.VolonteService;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping(path = "/user")
@Secured({"ROLE_ADMIN", "ROLE_USER"}) // Réservé aux utilisateurs qui ont le rôle 'ROLE_USER' ou 'ROLE_ADMIN'
public class UserController {

    @Autowired
    VolonteRepository volonteDAO;

    @Autowired
    UserRepository utilisateurDAO;

    @GetMapping(path = "pageSouvenir")
    public String souvenir(@AuthenticationPrincipal Utilisateur user, Model model) {
        return "pageSouvenir";
    }

    @GetMapping("/preferencesReseaux")
    public String autresVolontes(Model model) {
        model.addAttribute("autresVolontes", new Volonte());
        return "preferencesReseaux";
    }

    @PostMapping("/preferencesReseaux")
    public String enregistrerAutresVolontes(@AuthenticationPrincipal Utilisateur user, @Valid @ModelAttribute("autresVolontes") Volonte autresVolontes,
                                            RedirectAttributes redirectInfo) {
        String resultat;
        try {
            Utilisateur u = utilisateurDAO.getOne(user.getId());

            autresVolontes.setUtilisateur(u);
            volonteDAO.save(autresVolontes);
            resultat = "Vos préférences ont bien été enregistrées. " + "\n" + "Vous pouvez les retrouver dans l'onglet Informations Utilisateur.";

        } catch (Exception ex) {
            resultat = "Un problème est survenu : " + ex.getMessage();
        }
        redirectInfo.addFlashAttribute("resultat", resultat);
        return "redirect:/welcome";

    }

    @GetMapping("/informationsUtilisateur")
    public String informationsUtilisateur(@AuthenticationPrincipal Utilisateur user, Model model) {
        model.addAttribute("volontes", volonteDAO.findAll());
        return "informationsUtilisateur";
    }

}

