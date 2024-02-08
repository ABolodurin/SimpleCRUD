package ru.bolodurin.crud.model.mapper;

import org.springframework.stereotype.Component;
import ru.bolodurin.crud.model.dto.BusinessObjectDTO;
import ru.bolodurin.crud.model.entity.BusinessObject;

import java.util.function.Function;

@Component
public class BusinessObjectDTOMapper implements Function<BusinessObject, BusinessObjectDTO> {
    @Override
    public BusinessObjectDTO apply(BusinessObject o) {
        return new BusinessObjectDTO(
                o.getId(),
                o.getName(),
                o.getTimestamp());
    }

}
