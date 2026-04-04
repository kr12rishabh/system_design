package example.service.impl;

import example.service.WelcomeService;
import org.springframework.stereotype.Service;

@Service
public class WelcomeServiceImpl implements WelcomeService {

    @Override
    public String getWelcomeMessage() {
        return "Deposit Service is running successfully.";
    }
}
