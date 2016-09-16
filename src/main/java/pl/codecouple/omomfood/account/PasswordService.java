package pl.codecouple.omomfood.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Krzysztof Chruściel.
 */
@Service
public class PasswordService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SecureRandom generator;
    private BCryptPasswordEncoder bcryptEncoder;

    public PasswordService() throws NoSuchAlgorithmException {
        generator = new SecureRandom();
        bcryptEncoder = new BCryptPasswordEncoder();
    }

    public String getNextPasswordSeed(){
        return String.valueOf(generator.nextInt());
    }

    public String encrypt(String password){
        return bcryptEncoder.encode(password);
    }



    public SecureRandom getGenerator() {
        return generator;
    }

    public void setGenerator(SecureRandom generator) {
        this.generator = generator;
    }
}
