package edunhnil.project.forum.api.dao.categoryRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Optional<List<Category>> getCategories();

    Optional<Category> getCategoryById(int id);
}
