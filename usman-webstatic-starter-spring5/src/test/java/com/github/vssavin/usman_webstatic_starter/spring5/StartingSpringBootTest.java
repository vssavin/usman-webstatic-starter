package com.github.vssavin.usman_webstatic_starter.spring5;

import com.github.vssavin.usman_webstatic_starter.spring5.config.ApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author vssavin on 28.12.2023.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true", classes = ApplicationConfig.class)
public class StartingSpringBootTest {

    @Test
    public void contextLoads() {

    }

}
