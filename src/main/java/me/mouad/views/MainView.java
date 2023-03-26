package me.mouad.views;

import me.mouad.services.PatientService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class MainView extends JFrame {

    private final PatientService patientService;

    public MainView(PatientService patientService) throws HeadlessException {
        this.patientService = patientService;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 480);
        setLocationRelativeTo(null);

        final JPanel panel = new JPanel(new GridLayout(1, 1));
        final JButton button = new JButton("Create");

        button.addActionListener(action -> {
            new PatientFormView(patientService).build();
        });

        panel.add(button);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);

    }
}
