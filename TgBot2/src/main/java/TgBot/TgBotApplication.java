package TgBot;

import TgBot.jwt.TokenTelegramAuthenticationConfigurer;
import TgBot.jwt.deserializer.TelegramTokenDeserializer;
import TgBot.jwt.serializer.TelegramTokenSerializer;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.text.ParseException;

@EnableCaching
@SpringBootApplication
public class TgBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TgBotApplication.class, args);
	}

	@Bean
	public TelegramTokenSerializer telegramTokenSerializer(
			@Value("${jwt.telegram_token.key}") String telegramTokenKey
	) throws Exception {
		return new TelegramTokenSerializer(new DirectEncrypter(
				OctetSequenceKey.parse(telegramTokenKey)
		));
	}

	@Bean
	public TelegramTokenDeserializer telegramTokenDeserializer(
		@Value("${jwt.telegram_token.key}") String telegramTokenKey
	) throws Exception {
			return new TelegramTokenDeserializer(
					new DirectDecrypter(OctetSequenceKey.parse(telegramTokenKey)
			));
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http,
												   TokenTelegramAuthenticationConfigurer telegramAuthenticationConfigurer) throws Exception {
		http.apply(telegramAuthenticationConfigurer);

		return http
//                .httpBasic(Customizer.withDefaults())
				.csrf().disable()
				.cors()
				.and()
				.sessionManagement(sessionManagement ->
						sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorizeHttpRequests ->
						authorizeHttpRequests
								.requestMatchers("/").permitAll()
								.anyRequest().permitAll())
				.build();
	}

}
