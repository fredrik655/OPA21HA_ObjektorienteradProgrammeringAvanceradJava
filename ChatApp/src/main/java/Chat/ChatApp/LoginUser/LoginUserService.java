package Chat.ChatApp.LoginUser;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginUserService implements UserDetailsService {

    private final LoginUserRepo loginUserRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return loginUserRepo.findByEmail(email).
                orElseThrow(
                        () -> new UsernameNotFoundException("No such email")
                );
    }

    public LoginUser findUserById(Long id) {
        Optional<LoginUser> optionalUser = loginUserRepo.findById(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return null;
    }

    public void save(LoginUser user) {
        Optional<LoginUser> optionalUser = loginUserRepo.findById(user.getId());
        if(optionalUser.isPresent()) {
            LoginUser loginUser = optionalUser.get();
            loginUser.setUsername(user.getUsername());
            loginUser.setEmail(user.getEmail());
            loginUser.setAge(user.getAge());
            loginUserRepo.save(loginUser);
        }

    }

    public void delete(LoginUser user) {
        Optional<LoginUser> optionalUser = loginUserRepo.findById(user.getId());
        if(optionalUser.isPresent()) {
            LoginUser loginUser = optionalUser.get();
            loginUserRepo.delete(loginUser);
        }
    }

    public String signUpUser(LoginUser loginUser){
        boolean userExists = loginUserRepo.findByEmail(loginUser.getEmail()).isPresent();
        if(userExists){
            throw new IllegalStateException();
        }
        String encodedPassword = bCryptPasswordEncoder.encode(loginUser.getPassword());
        loginUser.setPassword(encodedPassword);

        loginUserRepo.save(loginUser);

        return "this works";
    }

    public List<LoginUser> listAll(){
        return(List<LoginUser>)loginUserRepo.findAll();
    }

}
