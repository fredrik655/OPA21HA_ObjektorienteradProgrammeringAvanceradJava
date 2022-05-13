package Chat.ChatApp.LoginUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LoginUserRepo extends JpaRepository<LoginUser, Long> {
    Optional<LoginUser> findByEmail(String email);
}
