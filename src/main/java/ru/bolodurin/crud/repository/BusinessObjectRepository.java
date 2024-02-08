package ru.bolodurin.crud.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.bolodurin.crud.model.entity.BusinessObject;

@Repository
public interface BusinessObjectRepository extends ListCrudRepository<BusinessObject, Long> {
}
