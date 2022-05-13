package Chat.ChatApp.Msg;

import Chat.ChatApp.LoginUser.LoginUser;
import Chat.ChatApp.LoginUser.LoginUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessagesService {

    @Autowired
    private final MessagesRepo messagesRepo;


    public List<Messages> listAll(){
        return(List<Messages>)messagesRepo.findAll();
    }
}
