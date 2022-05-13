package Chat.ChatApp.Msg;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class Messages {
    @Id
    @Column(name = "msg_id")
    private Integer Id;
    @Column
    private Long senderId;
    @Column
    private Long ReceiverId;
    @Column
    private String message;

}
