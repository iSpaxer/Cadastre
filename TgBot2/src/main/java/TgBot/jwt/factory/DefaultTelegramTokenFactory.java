package TgBot.jwt.factory;

import TgBot.jwt.model.Token;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

public class DefaultTelegramTokenFactory implements Function<Update, Token> {

    private Duration tokenTtl = Duration.ofMinutes(1);
    private Duration tokenTtlLinux = Duration.ofHours(3);

    @Override
    public Token apply(Update update) {
        var now = Instant.now();
        return new Token(
                update.getMessage().getChatId(),
                update.getMessage().getFrom().getFirstName(),
                update.getMessage().getFrom().getLastName(),
                now,
                now.plus(this.tokenTtl));
    }

    public void setTokenTtl(Duration tokenTtl) {
        this.tokenTtl = tokenTtl;
    }
}
