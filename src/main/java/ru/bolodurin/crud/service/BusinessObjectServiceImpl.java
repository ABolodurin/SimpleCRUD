package ru.bolodurin.crud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bolodurin.crud.model.dto.BusinessObjectDTO;
import ru.bolodurin.crud.model.mapper.BusinessObjectDTORowMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessObjectServiceImpl implements BusinessObjectService {
    private final JdbcTemplate jdbcTemplate;
    private final BusinessObjectDTORowMapper mapper;

    @Override
    @CachePut(value = "objects", key = "#objectDTO.name()")
    public List<BusinessObjectDTO> add(BusinessObjectDTO objectDTO) {
        String sql = """
                INSERT INTO business_objects
                (name, timestamp, non_business) VALUES (?,?,?);
                """;

        int nonBusiness = (int) (Math.random() * 255);
        jdbcTemplate.update(sql, objectDTO.name(), LocalDateTime.now(), nonBusiness);

        return show();
    }

    @Override
    @Cacheable("objects")
    public List<BusinessObjectDTO> show() {
        System.out.println("Log: LOADING FROM DB..."); //log imitation for cached loading check
        String sql = "SELECT * FROM business_objects;";

        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    @Transactional
    @CachePut(value = "objects", key = "#objectDTO.name()")
    public List<BusinessObjectDTO> update(BusinessObjectDTO objectDTO) {
        String sql = """
                UPDATE business_objects SET
                name = ?,
                timestamp = ?
                WHERE id = ?;
                """;

        jdbcTemplate.update(sql, objectDTO.name(), LocalDateTime.now(), objectDTO.id());

        return show();
    }

    @Override
    @CacheEvict(value = "objects", key = "#objectDTO.name()")
    public List<BusinessObjectDTO> delete(BusinessObjectDTO objectDTO) {
        String sql = """
                DELETE FROM business_objects
                WHERE id = ?;
                """;

        jdbcTemplate.update(sql, objectDTO.id());

        return show();
    }

}
