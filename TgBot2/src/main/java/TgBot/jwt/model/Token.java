package TgBot.jwt.model;

import java.time.Instant;

public record Token(Long tgId, String tgFirstnameEngineer, String tgLastnameEngineer, Instant createdAt, Instant expiresAt) {
}
