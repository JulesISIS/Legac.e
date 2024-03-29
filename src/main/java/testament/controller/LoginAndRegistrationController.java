package testament.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import testament.entity.Utilisateur;
import testament.service.SecurityService;
import testament.service.UserService;
import testament.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

@Controller
public class LoginAndRegistrationController {
    private final UserService userService;

    private final SecurityService securityService;

    private final UserValidator userValidator;

    @Autowired
    private JavaMailSender javaMailSender;

    public LoginAndRegistrationController(UserService userService, SecurityService securityService, UserValidator userValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new Utilisateur());
        return "inscription";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("userForm") Utilisateur userForm, BindingResult bindingResult) throws MessagingException {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "inscription";
        }
        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        String email = userForm.getEmail();

        envoiMail(email);
        if (userForm.isTestament()) {
            envoiTestament(email);
        }

        return "redirect:/welcome";

    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Nom d'utilisateur ou mot de passe incorrect.");

        if (logout != null)
            model.addAttribute("message", "Vous avez été déconnecté.");

        return "connexion";
    }
    
    @GetMapping("/esapceUtilisateur")
    public String conecte(Model model) {
        return "espaceUtilisateur";
    }
    
    @GetMapping({"/","/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }

    public void envoiMail(String email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email, email);
        
        msg.setSubject("Bienvenue sur Legac.e");
        msg.setText("Cher utilisateur,\n\n" +
                "Merci de faire confiance à Legac.e pour gérer vos données numériques quand vous ne serez plus là. \n\n " +
                "Vous pouvez maintenant vous connecter en tant qu’utilisateur sur https://legace.herokuapp.com/ afin d’accéder à vos informations personnelles, vos volontés et la configuration de votre page souvenir.\n\n" +
                "Ce n’était pas vous ? Merci d’envoyer un mail à Legac.etest@gmail.com.\n\n" +
                "L’équipe Legac.e \n");
        
        javaMailSender.send(msg);
    }

    public void envoiTestament(String email) throws MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(email);

        helper.setSubject("Modèle de testament");

        helper.setText("Cher utilisateur,\n\n" +
                "En vous inscrivant sur Legac-e.com, vous avez souhaité recevoir un modèle de testament écrit. Vous pouvez le retrouver en pièce jointe de ce mail. \n\n" +
                "N’oubliez pas que vous devez inscrire sur votre testament, de manière claire, que Legac.e est le tiers de confiance qui se chargera de la gestion de vos données numériques après votre décès.\n\n" +
                "Nous vous rappelons qu'un testament doit obligatoirement être manuscrit, et que ce modèle n'est donc pas recevable comme testament officiel. Il s'agit juste d'un exemple. \n\n" +
                "Ce n’était pas vous ? Merci d’envoyer un mail à Legac.etest@gmail.com.\n\n" +
                "L’équipe Legac.e\n");


        helper.addAttachment("Modèle de testament", new ClassPathResource("static/Testament.pdf"));

        javaMailSender.send(msg);

    }

}