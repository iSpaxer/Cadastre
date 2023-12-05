package TgBot.jwt.deserializer;

import TgBot.jwt.model.Token;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.UUID;
import java.util.function.Function;

public class TelegramTokenDeserializer implements Function<String, Token> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramTokenDeserializer.class);

    private final JWEDecrypter jweDecrypter;

    public TelegramTokenDeserializer(JWEDecrypter jweDecrypter) {
        this.jweDecrypter = jweDecrypter;
    }

    private Token token = null;
    @Override
    public Token apply(String string) {
        try {
            var encryptedJWT = EncryptedJWT.parse(string);
            encryptedJWT.decrypt(this.jweDecrypter);
            var claimsSet = encryptedJWT.getJWTClaimsSet();
            return new Token(Long.parseLong(claimsSet.getJWTID()),
                    claimsSet.getSubject(),
                    "",
                    claimsSet.getIssueTime().toInstant(),
                    claimsSet.getExpirationTime().toInstant());
        } catch (ParseException | JOSEException exception) {
            LOGGER.error(exception.getMessage(), exception);
        }

        return null;
    }
}
