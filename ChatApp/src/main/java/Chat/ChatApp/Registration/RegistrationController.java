package Chat.ChatApp.Registration;

import Chat.ChatApp.LoginUser.LoginUser;
import Chat.ChatApp.LoginUser.LoginUserService;
import Chat.ChatApp.Msg.Messages;
import Chat.ChatApp.Msg.MessagesRepo;
import Chat.ChatApp.Msg.MessagesService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.management.LockInfo;
import java.util.List;

@Controller
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/registration")
    public String showPage(Model model){
        model.addAttribute("user", new LoginUser());
        return "registration";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model){
        return "login";
    }

    @PostMapping("/registration/save")
    public String saveUser(LoginUser user){
        registrationService.register(new RegistrationRequest(user.getUsername(), user.getPassword(), user.getEmail(),  user.getAge(), "") );
        return "redirect:/";
    }
}
