package me.mouad;

import me.mouad.views.MainFrame;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import java.awt.*;

@SpringBootApplication
@EnableAsync
public class MedicalCabinetApplication {

	public static void main(String[] args) {
		final ConfigurableApplicationContext ctx = new SpringApplicationBuilder(MainFrame.class)
				.headless(false).run(args);

		EventQueue.invokeLater(() -> {
			final MainFrame ex = ctx.getBean(MainFrame.class);
			ex.setVisible(true);
		});
	}

}
