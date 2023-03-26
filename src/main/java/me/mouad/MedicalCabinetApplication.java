package me.mouad;

import me.mouad.config.AppConfig;
import me.mouad.config.SwingAppContext;
import me.mouad.repositories.PatientRepository;
import me.mouad.services.PatientService;
import me.mouad.views.MainView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.awt.*;

@SpringBootApplication
@EnableAsync
@Configuration
public class MedicalCabinetApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = new SpringApplicationBuilder(AppConfig.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .run(args);

        EventQueue.invokeLater(() -> {
            final MainView ex = context.getBean(MainView.class);
            ex.setVisible(true);
        });
    }

}
