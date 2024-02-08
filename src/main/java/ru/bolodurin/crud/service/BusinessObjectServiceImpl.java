package ru.bolodurin.crud.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bolodurin.crud.model.dto.BusinessObjectDTO;
import ru.bolodurin.crud.model.entity.BusinessObject;
import ru.bolodurin.crud.model.mapper.BusinessObjectDTOMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessObjectServiceImpl implements BusinessObjectService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final BusinessObjectDTOMapper mapper;

    @Override
    @Transactional
    @CachePut(value = "objects", key = "#objectDTO.name()")
    public List<BusinessObjectDTO> add(BusinessObjectDTO objectDTO) {
        int nonBusiness = (int) (Math.random() * 255);

        BusinessObject object = BusinessObject
                .builder()
                .name(objectDTO.name())
                .timestamp(LocalDateTime.now())
                .nonBusinessField(nonBusiness)
                .build();

        entityManager.persist(object);

        return show();
    }

    @Override
    @Cacheable("objects")
    public List<BusinessObjectDTO> show() {
        System.out.println("Log: LOADING FROM DB..."); //log imitation for cached loading check

        List<BusinessObjectDTO> list = new ArrayList<>();

        String sql = "FROM BusinessObject";
        entityManager.createQuery(sql)
                .getResultList()
                .stream()
                .map(mapper)
                .forEach(o -> list.add((BusinessObjectDTO) o));

        return list;
    }

    @Override
    @Transactional
    @CachePut(value = "objects", key = "#objectDTO.name()")
    public List<BusinessObjectDTO> update(BusinessObjectDTO objectDTO) {
        BusinessObject object = entityManager.find(BusinessObject.class, objectDTO.id());

        object.setName(objectDTO.name());
        object.setTimestamp(LocalDateTime.now());

        return show();
    }

    @Override
    @Transactional
    @CacheEvict(value = "objects", key = "#objectDTO.name()")
    public List<BusinessObjectDTO> delete(BusinessObjectDTO objectDTO) {
        String sql = """
                DELETE FROM BusinessObject o
                WHERE o.id = :id
                """;

        entityManager.createQuery(sql)
                .setParameter("id", objectDTO.id())
                .executeUpdate();

        return show();
    }

}
