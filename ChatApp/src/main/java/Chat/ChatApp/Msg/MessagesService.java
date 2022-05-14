package Chat.ChatApp.Msg;

import Chat.ChatApp.LoginUser.LoginUser;
import Chat.ChatApp.LoginUser.LoginUserRepo;
import Chat.ChatApp.LoginUser.UserRole;
import Chat.ChatApp.Registration.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessagesService {

    @Autowired
    private final MessagesRepo messagesRepo;

    public Messages findMsgById(Integer id) {
        Optional<Messages> optionalMessage = messagesRepo.findById(id);
        if(optionalMessage.isPresent()) {
            return optionalMessage.get();
        }
        return null;
    }


    public List<Messages> listAll(){
        return(List<Messages>)messagesRepo.findAll();
    }

    public void SaveMessage(Messages message){
        messagesRepo.save(message);
    }

    public void edit(Messages message) {
        Optional<Messages> optionalUser = messagesRepo.findById(message.getId());
        if(optionalUser.isPresent()) {
            Messages msg = optionalUser.get();
            msg.setMessage(message.getMessage());
            messagesRepo.save(msg);
        }

    }

    public void delete(Integer messageId) {
        Optional<Messages> optionalMessage = messagesRepo.findById(messageId);
        if(optionalMessage.isPresent()) {
            Messages msg = optionalMessage.get();
            messagesRepo.delete(msg);
        }
    }
}
