package edunhnil.project.forum.api.dao.categoryRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import edunhnil.project.forum.api.dao.AbstractRepository;

@Repository
public class CategoryRepositoryImpl extends AbstractRepository implements CategoryRepository {

    @Override
    public Optional<List<Category>> getCategories() {
        String sql = "SELECT * FROM category";
        return replaceQuery(sql.toString(), Category.class);
    }

    @Override
    public Optional<Category> getCategoryById(int id) {
        String sql = "SELECT * FROM category WHERE id = ?";
        return replaceQueryForObjectWithId(sql.toString(), Category.class, id);
    }

}
