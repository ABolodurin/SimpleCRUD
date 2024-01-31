package ru.bolodurin.crud.model.mapper;

import org.springframework.stereotype.Component;
import ru.bolodurin.crud.model.dto.BusinessObjectDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BusinessObjectDTORowMapper implements RowMapper<BusinessObjectDTO> {
    @Override
    public BusinessObjectDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new BusinessObjectDTO(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getTimestamp("timestamp").toLocalDateTime()
        );
    }

}
