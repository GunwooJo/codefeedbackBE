package cloudweb.codefeedbackBE.repository;

import cloudweb.codefeedbackBE.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

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
}
