package Chat.ChatApp;


import Chat.ChatApp.LoginUser.LoginUser;
import Chat.ChatApp.LoginUser.LoginUserService;
import Chat.ChatApp.Msg.Messages;
import Chat.ChatApp.Msg.MessagesService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private MessagesService messagesService;

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

    @PostMapping("/chatWith/save/{id}/{id2}")
    public String saveMessage(@PathVariable("id") Long id, @PathVariable("id2") long id2, Messages message, RedirectAttributes redirectAttributes){
        Messages tmpMessage = new Messages();
        tmpMessage.setSenderId(id);
        tmpMessage.setReceiverId(id2);
        tmpMessage.setMessage(message.getMessage());
        messagesService.SaveMessage(tmpMessage);
        return "redirect:/chatWith/" + id + "/" + id2;
    }

    @PostMapping("/message/save/{id}/{id2}")
    public String saveEditMessage(@PathVariable("id") Long id, @PathVariable("id2") long id2, Messages message, RedirectAttributes redirectAttributes){
        messagesService.edit(message);
        return "redirect:/chatWith/" + id + "/" + id2;
    }

    @GetMapping("/chatWith/delete/{id}/{id2}/{id3}")
    public String deleteUser(@PathVariable("id") Integer id,@PathVariable("id2") Integer id2,@PathVariable("id3") Integer id3, RedirectAttributes redirectAttributes){
        messagesService.delete(id);
        return "redirect:/chatWith/" + id2 + "/" + id3;
    }

    @GetMapping("/chatWith/edit/{id}/{id2}/{id3}")
    public String deleteUser(@PathVariable("id") Integer id,@PathVariable("id2") Integer id2,@PathVariable("id3") Integer id3, Model model, RedirectAttributes redirectAttributes){
        Messages msg = messagesService.findMsgById(id);
        if(msg != null){
            model.addAttribute("msgMessage", msg.getMessage());
            model.addAttribute("msg", msg);
            model.addAttribute("txId", id2);
            model.addAttribute("rxId", id3);
        }
        else {
            return "redirect:/chatWith/" + id2 + "/" + id3;
        }

        return "Edit";
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

    @GetMapping("/chatWith/{id}/{id2}")
    public String ShowChatPage(@PathVariable("id") Long id, @PathVariable("id2") long id2  ,Model model, RedirectAttributes redirectAttributes){
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

            LoginUser chatUser = loginUserService.findUserById(id2);
            if(chatUser != null){
                model.addAttribute("chatUserName", chatUser.getUsername());
                model.addAttribute("chatUserId", id2);
                model.addAttribute("newMessage", new Messages());
            }
            else {
                model.addAttribute("chatUserName", "Unknown");
            }
            model.addAttribute("currentUserName", currentPrincipalName);

            List<LoginUser> listUsers = loginUserService.listAll();
            model.addAttribute("listUsers", listUsers);

            List<Messages> messages = messagesService.listAll();
            List<Messages> filteredMessages = new ArrayList<Messages>();
            for(int i = 0; i < messages.size(); i++){
                if(messages.get(i).getReceiverId() == id || messages.get(i).getSenderId() == id){
                    if(messages.get(i).getReceiverId() == id2 || messages.get(i).getSenderId() == id2){
                        filteredMessages.add(messages.get(i));
                    }
                }
            }
            model.addAttribute("listMessages", filteredMessages);
        }
        else {
            model.addAttribute("currentUserName", "");
        }

        return "chatWith";
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
