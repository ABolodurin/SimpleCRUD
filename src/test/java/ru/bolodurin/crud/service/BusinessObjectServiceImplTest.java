package ru.bolodurin.crud.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.bolodurin.crud.model.dto.BusinessObjectDTO;
import ru.bolodurin.crud.model.entity.BusinessObject;
import ru.bolodurin.crud.model.mapper.BusinessObjectDTOMapper;
import ru.bolodurin.crud.repository.BusinessObjectRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusinessObjectServiceImplTest {
    BusinessObjectService service;
    @Mock
    BusinessObjectRepository objectRepository;
    BusinessObjectDTOMapper mapper;

    @BeforeEach
    void init() {
        this.mapper = new BusinessObjectDTOMapper();
        this.service = new BusinessObjectServiceImpl(objectRepository, mapper);
    }

    @Test
    void shouldAddObject() {
        String expectedName = new Object().toString();
        BusinessObjectDTO obj = new BusinessObjectDTO(1L, expectedName, LocalDateTime.now());
        ArgumentCaptor<BusinessObject> objectArgumentCaptor = ArgumentCaptor.forClass(BusinessObject.class);

        service.add(obj);

        verify(objectRepository).save(objectArgumentCaptor.capture());

        BusinessObject object = objectArgumentCaptor.getValue();

        assertEquals(expectedName, object.getName());

    }

    @Test
    void shouldShowObjects() {
        service.show();
        verify(objectRepository).findAll();
    }

    @Test
    void shouldUpdateObjects() {
        String name = new Object().toString();
        long existingId = (long) (Math.random() * 1000);
        int nonBusiness = (int) existingId;
        long nonExistingId = existingId + 1L;
        BusinessObject expected = new BusinessObject(existingId, name, LocalDateTime.now(), nonBusiness);
        BusinessObjectDTO expectedDTO = mapper.apply(expected);

        when(objectRepository.findById(existingId)).thenReturn(
                Optional.of(expected));
        when(objectRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.update(new BusinessObjectDTO(nonExistingId, "name", LocalDateTime.now())))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(String.valueOf(nonExistingId));

        ArgumentCaptor<BusinessObject> objectArgumentCaptor = ArgumentCaptor.forClass(BusinessObject.class);

        service.update(expectedDTO);

        verify(objectRepository).save(objectArgumentCaptor.capture());

        BusinessObject actual = objectArgumentCaptor.getValue();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldDeleteObjects() {
        long expected = (long) (Math.random() * 1000);
        service.delete(new BusinessObjectDTO(expected, "name", LocalDateTime.now()));
        verify(objectRepository).deleteById(expected);
    }

}
