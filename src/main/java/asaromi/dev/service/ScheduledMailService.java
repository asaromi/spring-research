package asaromi.dev.service;

import asaromi.dev.entity.User;
import asaromi.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ScheduledMailService {
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE_TIME;

    @Autowired
    UserRepository userRepository;

    @Scheduled(cron = "0 */2 * * * ?")
    public void cronSomething() {
        LocalDateTime now = LocalDateTime.now();

        List<User> users = userRepository.findUsersByNameOrUsername("a", "a");
        if(users.size() > 0) System.out.println("first user:" + users.get(0).getName());
        if(users.size() > 1) System.out.println("last user:" + users.get(users.size() - 1).getName());
        System.out.println("check scheduler: " + now.format(dateFormat));
    }
}
