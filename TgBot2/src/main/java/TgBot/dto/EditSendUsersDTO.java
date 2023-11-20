package TgBot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EditSendUsersDTO {
    private List<Integer> messageId;
    private List<Long> engTgId;
    private int size;
    public EditSendUsersDTO(List<Integer> messageId, List<Long> engTgId) {
        this.messageId = messageId;
        this.engTgId = engTgId;
        this.size = engTgId.size();
    }


}
