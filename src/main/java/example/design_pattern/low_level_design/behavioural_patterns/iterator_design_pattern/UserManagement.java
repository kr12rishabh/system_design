package example.design_pattern.low_level_design.behavioural_patterns.iterator_design_pattern;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class UserManagement {
    private List<User> userList = new ArrayList<>();

    public void addUser(User user) {
        userList.add(user);
    }

    public User getUser(int index) {
        return userList.get(index);

    }

    public MyIterator getIterator(){
        return new MyIteratorImpl(userList);
    }
}
