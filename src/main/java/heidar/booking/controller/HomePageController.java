package heidar.booking.controller;

import heidar.booking.model.User;
import heidar.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class HomePageController {

    private final UserService userService;

    @Autowired
    public HomePageController(UserService userService) {
        this.userService = userService;
    }

    //inloggnings sidan
    @GetMapping("/")
    public String showHomePage(){
        return "signin";
    }

    //inloggnigs sidan 2
    @GetMapping("/signin")
    public String showLoginPage() {
        return "signin";
    }

    //registrerings sidan
    @GetMapping("/signup")
    public String Registeration(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    //genomföring av konto registrering
    @PostMapping("/process")
    public String ShowSuccessPage(@Valid @ModelAttribute("user") User user, BindingResult result) {
        user.setRole("ROLE_USER");
        user.setPassword(userService.enCryptedPassword(user));
        if (result.hasErrors()) {
            System.out.println(result.toString());
            return "signup";
        } else {
            userService.save(user);
        }
        return "signin";
    }

    //identifiering av admin eller user till rätt websida
    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin/main";
        }
        return "redirect:/user/main";
    }
}
