package edunhnil.project.forum.api.dao.userRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import edunhnil.project.forum.api.dao.AbstractRepository;
import edunhnil.project.forum.api.ultilities.StringUtils;

@Repository
public class UserRepositoryImpl extends AbstractRepository implements UserRepository {

    @Override
    public Optional<List<User>> getUsers(Map<String, String> allParams, String keySort, int page, int pageSize,
            String sortField) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM account.user u ");
        sql.append(convertParamsFilterSelectQuery(allParams, attributeNamesForSelect(User.class).split(",")));
        if (keySort.trim().compareTo("") != 0 && sortField.trim().compareTo("") != 0) {
            sql.append(" ORDER BY ").append(StringUtils.camelCaseToSnakeCase(sortField)).append(" ").append(keySort);
        }
        sql.append(" OFFSET ").append((page - 1) * pageSize).append(" ROWS FETCH NEXT ").append(pageSize)
                .append(" ROWS ONLY");
        return replaceQuery(sql.toString(), User.class);
    }

    @Override
    public Optional<User> getUserById(int id) {
        String sql = "SELECT * FROM account.user u WHERE u.id = ? AND u.deleted = 0";
        return replaceQueryForObjectWithId(sql.toString(), User.class, id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        String sql = "SELECT * FROM account.user u WHERE u.username = ? AND u.deleted = 0";
        return replaceQueryForObjectWithId(sql.toString(), User.class, username);
    }

    @Override
    public boolean checkExistedUsername(String username) {
        String sql = "SELECT * FROM account.user u WHERE u.username = ? AND u.deleted = 0";
        try {
            User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
            return user != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean checkExistedUserCode(String userCode) {
        String sql = "SELECT * FROM account.user u WHERE u.user_code = ? AND u.deleted = 0";
        try {
            User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), userCode);
            return user != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public void addNewUser(User user) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO account.user (username, password)");
        sql.append(" VALUES (?,?)");
        jdbcTemplate.update(sql.toString(), user.getUsername(), user.getPassword());
    }

    @Override
    public void updateUserById(int id, User user) {
        StringBuilder sql = new StringBuilder();
        sql.append(
                "UPDATE account.user SET username = ?, role = ?, modified = now(), enabled = ?");
        sql.append(" WHERE id = ?");
        jdbcTemplate.update(sql.toString(), user.getUsername(), user.getRole(),
                user.getEnabled(), id);
    }

    @Override
    public void deleteUserById(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append(
                "UPDATE account.user SET deleted = 1, modified = now()");
        sql.append(" WHERE id = ?");
        jdbcTemplate.update(sql.toString(), id);
    }

    @Override
    public int getTotalPage(Map<String, String> allParams) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM account.user u ");
        sql.append(convertParamsFilterSelectQuery(allParams, attributeNamesForSelect(User.class).split(",")));
        return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
    }

    @Override
    public void resetId() {
        int max = jdbcTemplate.queryForObject("SELECT MAX(id) FROM account.user", Integer.class) + 1;
        String sql = "ALTER SEQUENCE account.user_id_seq RESTART WITH "
                + max;
        jdbcTemplate.execute(sql);
    }

    @Override
    public void changePasswordById(String password, int id) {
        StringBuilder sql = new StringBuilder();
        sql.append(
                "UPDATE account.user SET password=?, modified = now()");
        sql.append(" WHERE id = ?");
        jdbcTemplate.update(sql.toString(), password, id);
    }

    @Override
    public void changeUserNameById(String username, int id) {
        StringBuilder sql = new StringBuilder();
        sql.append(
                "UPDATE account.user SET username = ?, modified = now()");
        sql.append(" WHERE id = ?");
        jdbcTemplate.update(sql.toString(), username, id);
    }

    @Override
    public void changeEnabled(int enabled, int id) {
        StringBuilder sql = new StringBuilder();
        sql.append(
                "UPDATE account.user SET enabled = ?, modified = now()");
        sql.append(" WHERE id = ?");
        jdbcTemplate.update(sql.toString(), enabled, id);
    }

}
