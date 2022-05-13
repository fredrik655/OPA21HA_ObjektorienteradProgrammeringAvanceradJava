package Chat.ChatApp.Registration;

import Chat.ChatApp.LoginUser.LoginUser;
import Chat.ChatApp.LoginUser.LoginUserService;
import Chat.ChatApp.LoginUser.UserRole;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private LoginUserService loginUserService;
    private EmailValidator emailValidator;


    public String register(RegistrationRequest request){

        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        return loginUserService.signUpUser(
                new LoginUser(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getAge(),
                        UserRole.USER
                )
        );
    }
}
