package ru.bolodurin.crud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.bolodurin.crud.model.dto.BusinessObjectDTO;
import ru.bolodurin.crud.service.BusinessObjectService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BusinessObjectControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BusinessObjectControllerImplTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    BusinessObjectService service;

    @Test
    void shouldReturn201WhenObjectIsCreated() throws Exception {
        BusinessObjectDTO expected = new BusinessObjectDTO(1L, "name", LocalDateTime.now());
        given(service.add(any())).willReturn(
                List.of(expected));

        mvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(mapper.writeValueAsBytes(expected)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].id", Matchers.is(expected.id().intValue())))
                .andExpect(jsonPath("$[0].name", Matchers.is(expected.name())))
                .andExpect(jsonPath("$[0].timestamp",
                        Matchers.startsWith(expected.timestamp()
                                .toString()
                                .substring(0, 25))));

        ArgumentCaptor<BusinessObjectDTO> objectDTOArgumentCaptor = ArgumentCaptor.forClass(BusinessObjectDTO.class);

        verify(service).add(objectDTOArgumentCaptor.capture());

        BusinessObjectDTO actual = objectDTOArgumentCaptor.getValue();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldReturn200WhenShowObjects() throws Exception {
        BusinessObjectDTO expected = new BusinessObjectDTO(1L, "name", LocalDateTime.now());
        given(service.show()).willReturn(
                List.of(expected));

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(expected.id().intValue())))
                .andExpect(jsonPath("$[0].name", Matchers.is(expected.name())))
                .andExpect(jsonPath("$[0].timestamp",
                        Matchers.startsWith(expected.timestamp()
                                .toString()
                                .substring(0, 25))));

        verify(service).show();
    }

    @Test
    void shouldReturn200WhenObjectIsUpdated() throws Exception {
        BusinessObjectDTO expected = new BusinessObjectDTO(1L, "name", LocalDateTime.now());
        given(service.update(any())).willReturn(
                List.of(expected));

        mvc.perform(put("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(mapper.writeValueAsBytes(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(expected.id().intValue())))
                .andExpect(jsonPath("$[0].name", Matchers.is(expected.name())))
                .andExpect(jsonPath("$[0].timestamp",
                        Matchers.startsWith(expected.timestamp()
                                .toString()
                                .substring(0, 25))));

        ArgumentCaptor<BusinessObjectDTO> objectDTOArgumentCaptor = ArgumentCaptor.forClass(BusinessObjectDTO.class);

        verify(service).update(objectDTOArgumentCaptor.capture());

        BusinessObjectDTO actual = objectDTOArgumentCaptor.getValue();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldReturn200WhenObjectIsDeleted() throws Exception {
        BusinessObjectDTO expected = new BusinessObjectDTO(1L, "name", LocalDateTime.now());
        given(service.delete(any())).willReturn(
                List.of(expected));

        mvc.perform(delete("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(mapper.writeValueAsBytes(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(expected.id().intValue())))
                .andExpect(jsonPath("$[0].name", Matchers.is(expected.name())))
                .andExpect(jsonPath("$[0].timestamp",
                        Matchers.startsWith(expected.timestamp()
                                .toString()
                                .substring(0, 25))));

        ArgumentCaptor<BusinessObjectDTO> objectDTOArgumentCaptor = ArgumentCaptor.forClass(BusinessObjectDTO.class);

        verify(service).delete(objectDTOArgumentCaptor.capture());

        BusinessObjectDTO actual = objectDTOArgumentCaptor.getValue();

        assertThat(actual).isEqualTo(expected);
    }

}
