package ru.bolodurin.crud.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ru.bolodurin.crud.model.dto.BusinessObjectDTO;
import ru.bolodurin.crud.model.entity.BusinessObject;
import ru.bolodurin.crud.model.mapper.BusinessObjectDTOMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(scripts = {"classpath:v1.sql"})
@DataJpaTest
@AutoConfigureTestEntityManager
class BusinessObjectServiceImplTest {
    @Autowired
    EntityManager entityManager;
    BusinessObjectDTOMapper mapper;
    BusinessObjectService service;

    @BeforeEach
    void init() {
        this.mapper = new BusinessObjectDTOMapper();
        this.service = new BusinessObjectServiceImpl(entityManager, mapper);
    }

    @AfterEach
    void clearData() {
        entityManager.createNativeQuery("TRUNCATE TABLE business_objects;");
    }

    @Test
    void shouldAddObject() {
        String expectedName = new Object().toString();
        BusinessObjectDTO obj = new BusinessObjectDTO(1L, expectedName, LocalDateTime.now());

        service.add(obj);

        String sql = "from BusinessObject o where o.name = :name";

        BusinessObject object = (BusinessObject) entityManager
                .createQuery(sql)
                .setParameter("name", obj.name())
                .getSingleResult();

        BusinessObjectDTO actual = mapper.apply(object);

        assert actual != null;
        assertEquals(expectedName, actual.name());
    }

    @Test
    void shouldShowObjects() {
        String expectedName1 = new Object().toString();
        String expectedName2 = new Object().toString();
        List<BusinessObjectDTO> list = List.of(
                new BusinessObjectDTO(1L, expectedName1, LocalDateTime.now()),
                new BusinessObjectDTO(1L, expectedName2, LocalDateTime.now()));

        list.forEach(obj -> service.add(obj));

        List<BusinessObjectDTO> actual = service.show();

        assertEquals(expectedName1, actual.get(0).name());
        assertEquals(expectedName2, actual.get(1).name());
    }

    @Test
    void shouldUpdateObjects() {
        String name = new Object().toString();
        service.add(new BusinessObjectDTO(1L, name, LocalDateTime.now()));
        BusinessObjectDTO obj = service.show().get(0);

        String expectedName = new Object().toString();
        service.update(new BusinessObjectDTO(obj.id(), expectedName, LocalDateTime.now()));

        BusinessObjectDTO actual = service.show().get(0);

        assert actual != null;
        assertEquals(expectedName, actual.name());
    }

    @Test
    void shouldDeleteObjects() {
        String name = new Object().toString();
        service.add(new BusinessObjectDTO(1L, name, LocalDateTime.now()));
        BusinessObjectDTO obj = service.show().get(0);

        service.delete(obj);

        List<BusinessObjectDTO> actual = service.show();
        assertEquals(0, actual.size());
    }

}
