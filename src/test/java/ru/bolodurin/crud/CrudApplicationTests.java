package ru.bolodurin.crud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.bolodurin.crud.model.dto.BusinessObjectDTO;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:v1.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class CrudApplicationTests {
    @Autowired
    WebTestClient webClient;

    @Test
    void shouldWork() {
        BusinessObjectDTO objectDTO = new BusinessObjectDTO(1L, new Object().toString(), LocalDateTime.now());

        webClient.post().uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectDTO)
                .exchange()
                .expectStatus().isCreated();

        var result = webClient.get().uri("/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(BusinessObjectDTO.class).returnResult().getResponseBody();

        assert result != null;
        objectDTO = result.get(0);

        webClient.put().uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectDTO)
                .exchange()
                .expectStatus().isOk();

		webClient.method(HttpMethod.DELETE).uri("/")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(objectDTO)
				.exchange()
				.expectStatus().isOk();
    }

}
