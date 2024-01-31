package ru.bolodurin.crud.controller;

import org.springframework.http.ResponseEntity;
import ru.bolodurin.crud.model.dto.BusinessObjectDTO;

import java.util.List;

public interface BusinessObjectController {
    ResponseEntity<List<BusinessObjectDTO>> create(BusinessObjectDTO objectDTO);

    ResponseEntity<List<BusinessObjectDTO>> read();

    ResponseEntity<List<BusinessObjectDTO>> update(BusinessObjectDTO objectDTO);

    ResponseEntity<List<BusinessObjectDTO>> delete(BusinessObjectDTO objectDTO);

}
