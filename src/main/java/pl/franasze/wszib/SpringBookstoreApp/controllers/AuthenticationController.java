package pl.franasze.wszib.SpringBookstoreApp.controllers;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.franasze.wszib.SpringBookstoreApp.model.User;
import pl.franasze.wszib.SpringBookstoreApp.services.IAuthenticationService;
import pl.franasze.wszib.SpringBookstoreApp.session.SessionObject;


@Controller
public class AuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("userModel", new User());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute User user) {
        this.authenticationService.login(user.getLogin(), user.getPassword());
        if(this.sessionObject.isLogged()) {
            return "redirect:/main";
        }
        return "redirect:/login";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout() {
        this.authenticationService.logout();
        return "redirect:/main";
    }
}
