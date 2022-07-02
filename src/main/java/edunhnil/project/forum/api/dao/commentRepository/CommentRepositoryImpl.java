package edunhnil.project.forum.api.dao.commentRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import edunhnil.project.forum.api.dao.AbstractRepository;

@Repository
public class CommentRepositoryImpl extends AbstractRepository implements CommentRepository {

    @Override
    public Optional<List<Comment>> getAllComment(Map<String, String> allParams, String keySort, int page, int pageSize,
            String sortField) {
        StringBuilder sql = new StringBuilder();
        sql.append(
                "SELECT * FROM forum.comment c ");
        sql.append(convertParamsFilterSelectQuery(allParams, attributeNamesForSelect(Comment.class).split(",")));
        if (keySort.trim().compareTo("") != 0 && sortField.trim().compareTo("") != 0) {
            sql.append(" ORDER BY ").append(sortField).append(" ").append(keySort);
        }
        sql.append(" OFFSET ").append((page - 1) * pageSize).append(" ROWS FETCH NEXT ").append(pageSize)
                .append(" ROWS ONLY");
        return replaceQuery(sql.toString(), Comment.class);
    }

    @Override
    public Optional<Comment> getCommentById(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM forum.comment WHERE deleted = 0 AND id = ?");
        return replaceQueryForObjectWithId(sql.toString(), Comment.class, id);
    }

    @Override
    public void addNewComment(Comment comment) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO forum.comment (owner_id, post_id, content)");
        sql.append(" VALUES (?, ? ,?)");
        jdbcTemplate.update(sql.toString(), comment.getOwnerId(), comment.getPostId(), comment.getContent());
    }

    @Override
    public void editCommentById(Comment comment, int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE forum.comment SET content=?, modified = now()");
        sql.append(" WHERE id = ?");
        jdbcTemplate.update(sql.toString(), comment.getContent(), id);
    }

    @Override
    public void deleteCommentById(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE forum.comment SET deleted = 1, modified = now()");
        sql.append(" WHERE id = ?");
        jdbcTemplate.update(sql.toString(), id);

    }

    @Override
    public void resetId() {
        int max = jdbcTemplate.queryForObject("SELECT MAX(id) FROM forum.comment", Integer.class) + 1;
        String sql = "ALTER SEQUENCE forum.comment_id_seq RESTART WITH "
                + max;
        jdbcTemplate.execute(sql);

    }

    @Override
    public int getTotalCommentPost(int postId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM forum.comment c");
        sql.append(" WHERE c.deleted = 0 AND c.post_id = " + postId);
        return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
    }

    @Override
    public int getTotalCommentAdmin(Map<String, String> allParams) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM forum.comment c ");
        sql.append(convertParamsFilterSelectQuery(allParams, attributeNamesForSelect(Comment.class).split(",")));
        return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
    }

}
