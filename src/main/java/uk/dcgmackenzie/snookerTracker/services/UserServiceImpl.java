package uk.dcgmackenzie.snookerTracker.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemailv2.AmazonSimpleEmailServiceV2;
import com.amazonaws.services.simpleemailv2.AmazonSimpleEmailServiceV2ClientBuilder;
import com.amazonaws.services.simpleemailv2.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uk.dcgmackenzie.snookerTracker.DTO.UserConfirmDTO;
import uk.dcgmackenzie.snookerTracker.DTO.UserRegisterDTO;
import uk.dcgmackenzie.snookerTracker.entities.Drill;
import uk.dcgmackenzie.snookerTracker.entities.User;
import uk.dcgmackenzie.snookerTracker.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String registerUser(UserRegisterDTO userRegisterDTO) {
        User user = getUser(userRegisterDTO.getUsername());
        if (user != null && user.getIsVerified()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }
        user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setIsVerified(false);
        user.setVerificationCode(RandomStringUtils.randomAlphanumeric(10));
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        testEmail(userRegisterDTO.getUsername(), user.getVerificationCode());
        userRepository.save(user);
        return "User created";
    }

    @Override
    public String confirmUser(UserConfirmDTO userConfirmDTO) {
        User user = getUser(userConfirmDTO.getUsername());
        if (user != null && user.getIsVerified()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already verified");
        }
        if (!userConfirmDTO.getVerificationCode().equals(user.getVerificationCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect verification code");
        }
        user.setIsVerified(true);
        userRepository.save(user);
        return "User verified";
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Collection<Drill> getDrills(String username) {
        return userRepository.findByUsername(username).getDrills();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.getIsVerified()) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    private String testEmail(String email, String verificationCode) {
        String emailFrom = "david.mackenzie@dcgmackenzie.uk";
        try {
            AmazonSimpleEmailServiceV2 client =
                    AmazonSimpleEmailServiceV2ClientBuilder.standard()
                            .withRegion(Regions.EU_WEST_1).build();

            EmailContent emailContent = new EmailContent();
            Message message = new Message();
            Content subjectContent = new Content();
            subjectContent.setData("Verify your account");
            subjectContent.setCharset("UTF-8");
            Content bodyContent = new Content();
            bodyContent.setData("Your verification code is: " + verificationCode);
            bodyContent.setCharset("UTF-8");
            Body body = new Body();
            body.setText(bodyContent);
            message.setSubject(subjectContent);
            message.setBody(body);
            emailContent.setSimple(message);
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(email))
                    .withContent(emailContent)
                    .withFromEmailAddress(emailFrom);
            client.sendEmail(request);

            System.out.println("Email sent!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "OK";
    }
}
