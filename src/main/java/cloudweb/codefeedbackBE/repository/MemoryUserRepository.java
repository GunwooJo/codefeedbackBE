package cloudweb.codefeedbackBE.repository;

import cloudweb.codefeedbackBE.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class MemoryUserRepository implements UserRepository{

    private Long sequence = 0L;
    private final Map<Long, User> db = new HashMap<>();

    @Override
    public Long save(User user) {
        user.setId(sequence++);
        db.put(user.getId(), user);
        return user.getId();
    }

    @Override
    public void deleteUserByEmail(String email) {

        User foundUser = db.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("해당 email을 가진 user가 없습니다."));

        db.remove(foundUser.getId());
    }

    @Override
    public User userSignIn(HashMap<String, String> loginUser){
        User lUser = null;
        for (Map.Entry<Long, User> entry : db.entrySet()) {
            User v = entry.getValue();
            if (v.getEmail().equals(loginUser.get("email")) && v.getPassword().equals(loginUser.get("password"))) {
                lUser = v;
            }
        }
        return lUser;
    }
}
