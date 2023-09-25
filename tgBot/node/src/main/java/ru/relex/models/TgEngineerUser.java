package ru.relex.models;

import lombok.*;
import ru.relex.models.enums.UserState;

import javax.persistence.*;

@Entity
@Table(name = "tg_engineer")
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TgEngineerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long telegramUserId;

    private String username;
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private UserState state;
}
