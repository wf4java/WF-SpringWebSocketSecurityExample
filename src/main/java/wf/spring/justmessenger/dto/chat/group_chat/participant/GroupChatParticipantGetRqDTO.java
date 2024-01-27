package wf.spring.justmessenger.dto.chat.group_chat.participant;

import lombok.*;
import org.bson.types.ObjectId;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupChatParticipantGetRqDTO {

    @NonNull
    private ObjectId chatId;

    @NonNull
    private ObjectId personId;

}
