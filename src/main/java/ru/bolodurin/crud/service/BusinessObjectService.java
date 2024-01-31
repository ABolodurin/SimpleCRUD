package ru.bolodurin.crud.service;

import ru.bolodurin.crud.model.dto.BusinessObjectDTO;

import java.util.List;

public interface BusinessObjectService {
    List<BusinessObjectDTO> add(BusinessObjectDTO objectDTO);

    List<BusinessObjectDTO> show();

    List<BusinessObjectDTO> update(BusinessObjectDTO objectDTO);

    List<BusinessObjectDTO> delete(BusinessObjectDTO objectDTO);

}
