package edunhnil.project.forum.api.dao.userRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository {
        Optional<List<User>> getUsers(Map<String, String> allParams, String keySort, int page, int pageSize,
                        String sortField);

        Optional<User> getUserById(int id);

        Optional<User> getUserByUsername(String username);

        boolean checkExistedUsername(String username);

        boolean checkExistedUserCode(String userCode);

        void changePasswordById(String password, int id);

        void changeUserNameById(String username, int id);

        void changeEnabled(int enabled, int id);

        void addNewUser(User user);

        void updateUserById(int id, User user);

        void deleteUserById(int id);

        int getTotalPage(Map<String, String> allParams);

        void resetId();
}
