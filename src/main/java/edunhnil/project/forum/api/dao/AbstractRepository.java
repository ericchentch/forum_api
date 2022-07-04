package edunhnil.project.forum.api.dao;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import edunhnil.project.forum.api.log.AppLogger;
import edunhnil.project.forum.api.log.LoggerFactory;
import edunhnil.project.forum.api.log.LoggerType;
import edunhnil.project.forum.api.ultilities.StringUtils;

public abstract class AbstractRepository {
    @Autowired
    @Qualifier("dataSource")
    protected DataSource dataSource;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    protected AppLogger APP_LOGGER = LoggerFactory.getLogger(LoggerType.APPLICATION);

    protected <T> Optional<List<T>> replaceQuery(String sql, Class<T> clazz) {
        try {
            List<T> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(clazz));
            return Optional.of(list);
        } catch (BadSqlGrammarException e) {
            APP_LOGGER.error("ERROR SQL QUERY: " + sql);
            return Optional.empty();
        }
    }

    protected <T> Optional<T> replaceQueryForObjectWithId(String sql, Class<T> clazz, Object objectInput) {
        try {
            T object = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(clazz), objectInput);
            return Optional.of(object);
        } catch (BadSqlGrammarException e) {
            APP_LOGGER.error("ERROR SQL QUERY: " + sql);
            return Optional.empty();
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    protected String convertParamsFilterSelectQuery(Map<String, String> allParams, String[] objectFields) {
        StringBuilder result = new StringBuilder();
        int count = 1;
        for (Map.Entry<String, String> items : allParams.entrySet()) {
            String[] values = items.getValue().split(",");
            for (int i = 0; i < values.length; i++) {
                boolean found = false;
                for (int j = 0; j < objectFields.length; j++) {
                    if (objectFields[j].compareTo(StringUtils.camelCaseToSnakeCase(items.getKey())) == 0) {
                        found = true;
                    }
                }
                if (found) {
                    if (count == 1) {
                        result.append(" WHERE ");
                    } else {
                        result.append(" AND ");
                    }
                    try {
                        int valuesDigit = Integer.parseInt(values[i]);
                        result.append(StringUtils.camelCaseToSnakeCase(items.getKey()))
                                .append(" = ")
                                .append(valuesDigit);
                    } catch (NumberFormatException e) {
                        result.append(StringUtils.camelCaseToSnakeCase(items.getKey()))
                                .append(" LIKE '%").append(values[i]).append("%'");
                    }
                    count++;
                }
            }
        }
        return result.toString();
    }

    protected List<String> listAttributeName(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields).map(Field::getName).collect(Collectors.toList());
    }

    protected String attributeNamesForSelect(Class<?> clazz) {
        List<String> listAttribute = listAttributeName(clazz);
        if (listAttribute == null || listAttribute.isEmpty()) {
            return "";
        }
        // Covert CamelCase to SnakeCase
        String attributeNames = listAttribute.stream().map(StringUtils::camelCaseToSnakeCase)
                .collect(Collectors.joining(","));
        return attributeNames;
    }

}
