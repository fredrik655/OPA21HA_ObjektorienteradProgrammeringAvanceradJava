package Chat.ChatApp.Msg;

import org.springframework.data.repository.CrudRepository;

public interface MessagesRepo extends CrudRepository<Messages, Integer> {
}
