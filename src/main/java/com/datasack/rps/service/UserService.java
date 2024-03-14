package com.datasack.rps.service;

import com.datasack.rps.entity.User;
import com.datasack.rps.repository.ReferralSequenceRepository;
import com.datasack.rps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ReferralSequenceRepository referralSequenceRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        Optional<User> optUser = userRepository.findById(id);
        return optUser.orElse(null);
    }

    @Transactional
    public User create(User user){
        Long currentSequence = referralSequenceRepository.getSequenceByFirstAndLastName(
                user.getFirstName().trim().substring(0,2).toUpperCase(),
                user.getLastName().trim().substring(0,2).toUpperCase()
        );
        user.setReferralCode(generateSequence(user, currentSequence));
        user.setPoints(100);
        user = userRepository.save(user);
        User referredUser = userRepository.findByReferredByCode(user.getReferredByCode());
        if(referredUser != null) {
            referredUser.setPoints(referredUser.getPoints() + 20);
            userRepository.save(referredUser);
        }
        return user;
    }

    private String generateSequence (User user, Long currentSequence){
        if (currentSequence != null && currentSequence > 0) {
            return user.getFirstName().trim().substring(0,2).toUpperCase()
                    + user.getLastName().trim().substring(0,2).toUpperCase()
                    + (currentSequence+1);
        } else {
            return user.getFirstName().trim().substring(0,2).toUpperCase()
                    + user.getLastName().trim().substring(0,2).toUpperCase()
                    + 1;
        }
    }

    public void deleteUser(long id){
        userRepository.deleteById(id);
    }
}
