package me.mouad;

import me.mouad.views.MainView;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import java.awt.*;

@SpringBootApplication
@EnableAsync
public class MedicalCabinetApplication {

	public static void main(String[] args) {
		final ConfigurableApplicationContext ctx = new SpringApplicationBuilder(MainView.class)
				.headless(false).run(args);

		EventQueue.invokeLater(() -> {
			final MainView ex = ctx.getBean(MainView.class);
			ex.setVisible(true);
		});
	}

}
