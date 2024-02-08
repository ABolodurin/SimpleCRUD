package ru.bolodurin.crud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bolodurin.crud.model.dto.BusinessObjectDTO;
import ru.bolodurin.crud.model.entity.BusinessObject;
import ru.bolodurin.crud.model.mapper.BusinessObjectDTOMapper;
import ru.bolodurin.crud.repository.BusinessObjectRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessObjectServiceImpl implements BusinessObjectService {
    private final BusinessObjectRepository objectRepository;
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

        objectRepository.save(object);

        return show();
    }

    @Override
    @Cacheable("objects")
    public List<BusinessObjectDTO> show() {
        System.out.println("Log: LOADING FROM DB..."); //log imitation for cached loading check

        return objectRepository
                .findAll().stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CachePut(value = "objects", key = "#objectDTO.name()")
    public List<BusinessObjectDTO> update(BusinessObjectDTO objectDTO) {
        BusinessObject object = objectRepository
                .findById(objectDTO.id())
                .orElseThrow(() -> new RuntimeException("Object not found. ID: " + objectDTO.id()));

        object.setName(objectDTO.name());
        object.setTimestamp(LocalDateTime.now());
        objectRepository.save(object);

        return show();
    }

    @Override
    @Transactional
    @CacheEvict(value = "objects", key = "#objectDTO.name()")
    public List<BusinessObjectDTO> delete(BusinessObjectDTO objectDTO) {
        objectRepository.deleteById(objectDTO.id());

        return show();
    }

}
