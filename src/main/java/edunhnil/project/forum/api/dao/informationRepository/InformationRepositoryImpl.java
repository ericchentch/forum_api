package edunhnil.project.forum.api.dao.informationRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import edunhnil.project.forum.api.dao.AbstractRepository;
import edunhnil.project.forum.api.ultilities.StringUtils;

@Repository
public class InformationRepositoryImpl extends AbstractRepository implements InformationRepository {

    @Override
    public Optional<List<Information>> getAllInformation(Map<String, String> allParams, String keySort, int page,
            int pageSize,
            String sortField) {
        StringBuilder sql = new StringBuilder();
        sql.append(
                "SELECT * FROM account.information i LEFT JOIN account.user u ON i.user_id = u.id ");
        String conditionInQuery = convertParamsFilterSelectQuery(allParams,
                attributeNamesForSelect(Information.class).split(","));
        sql.append(conditionInQuery);
        if (conditionInQuery.length() == 0) {
            sql.append(" WHERE u.deleted = 0");
        } else {
            sql.append(" AND u.deleted = 0");
        }
        if (keySort.trim().compareTo("") != 0 && sortField.trim().compareTo("") != 0) {
            sql.append(" ORDER BY i.").append(StringUtils.camelCaseToSnakeCase(sortField)).append(" ").append(keySort);
        }
        sql.append(" OFFSET ").append((page - 1) * pageSize).append(" ROWS FETCH NEXT ").append(pageSize)
                .append(" ROWS ONLY");
        return replaceQuery(sql.toString(), Information.class);
    }

    @Override
    public Optional<Information> getInformationByUserCode(int id, int deleted) {
        StringBuilder sql = new StringBuilder();
        sql.append(
                "SELECT * FROM account.information i LEFT JOIN account.user u ON i.user_id = u.id WHERE i.user_id = ? AND u.deleted = "
                        + deleted);
        return replaceQueryForObjectWithId(sql.toString(), Information.class, id);
    }

    @Override
    public void addNewInformation(Information information) {
        StringBuilder sql = new StringBuilder();
        sql.append(
                "INSERT INTO account.information ( first_name, last_name, email, phone, gender, dob, address, user_id)");
        sql.append(" VALUES (?,?,?,?,?,?,?,?)");
        jdbcTemplate.update(sql.toString(), information.getFirstName(),
                information.getLastName(), information.getEmail(), information.getPhone(), information.getGender(),
                information.getDob(), information.getAddress(), information.getUserId());
    }

    @Override
    public void updateInformationByUserCode(Information information, int id) {
        StringBuilder sql = new StringBuilder();
        sql.append(
                "UPDATE account.information SET first_name = ?, last_name = ?, modified = now(), email=?, phone=?, gender = ?, dob = ?, address = ?");
        sql.append(" WHERE user_id=?");
        jdbcTemplate.update(sql.toString(), information.getFirstName(), information.getLastName(),
                information.getEmail(), information.getPhone(), information.getGender(), information.getDob(),
                information.getAddress(), id);
    }

    @Override
    public int getTotalPage(Map<String, String> allParams) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM account.information i LEFT JOIN account.user u ON i.user_id = u.id ");
        String conditionInQuery = convertParamsFilterSelectQuery(allParams,
                attributeNamesForSelect(Information.class).split(","));
        sql.append(conditionInQuery);
        if (conditionInQuery.length() == 0) {
            sql.append(" WHERE u.deleted = 0");
        } else {
            sql.append(" AND u.deleted = 0");

        }
        return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
    }

    @Override
    public void resetId() {
        int max = jdbcTemplate.queryForObject("SELECT MAX(id) FROM account.information", Integer.class) + 1;
        String sql = "ALTER SEQUENCE account.information_id_seq RESTART WITH "
                + max;
        jdbcTemplate.execute(sql);

    }

}
