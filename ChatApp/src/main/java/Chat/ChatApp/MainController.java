package Chat.ChatApp;


import Chat.ChatApp.LoginUser.LoginUser;
import Chat.ChatApp.LoginUser.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private LoginUserService loginUserService;

    public String getReceiverUsername() {
        return ReceiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        System.out.print(receiverUsername);
        ReceiverUsername = receiverUsername;
    }

    private String ReceiverUsername = "";

    @PostMapping("/user/save")
    public String saveUser(LoginUser user, RedirectAttributes redirectAttributes){
        loginUserService.save(user);
        return "redirect:/";
    }

    @PostMapping("/user/delete")
    public String deleteUser(LoginUser user, RedirectAttributes redirectAttributes){
        loginUserService.delete(user);
        return "redirect:/logout";
    }

    @GetMapping("/user/{id}")
    public String ShowUserPage(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            LoginUser loginUser = loginUserService.findUserById(id);
            String currentPrincipalName = authentication.getName();
            if(loginUser != null){
                model.addAttribute("currentUser", loginUser);
                Long currentUserId = ((LoginUser)authentication.getPrincipal()).getId();
                model.addAttribute("currentUserId", currentUserId);
            }
            else {
                return "index";
            }

            model.addAttribute("currentUserName", currentPrincipalName);

            List<LoginUser> listUsers = loginUserService.listAll();
            model.addAttribute("listUsers", listUsers);
        }
        else {
            model.addAttribute("currentUserName", "");
        }

        return "user";
    }

    @GetMapping("")
    public  String showPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Long currentUserId = ((LoginUser)authentication.getPrincipal()).getId();
            String currentPrincipalName = authentication.getName();
            model.addAttribute("currentUserName", currentPrincipalName);
            model.addAttribute("currentUserId", currentUserId);
            List<LoginUser> listUsers = loginUserService.listAll();
            model.addAttribute("listUsers", listUsers);
        }
        else {
            model.addAttribute("currentUser", "");
        }

        return "index";
    }
}
