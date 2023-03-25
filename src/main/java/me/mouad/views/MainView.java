package me.mouad.views;

import me.mouad.models.Patient;
import me.mouad.services.PatientService;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JTextField firstName;
    private JTextField lastName;
    private JTextField cin;
    private JTextField address;
    private JTextArea caseDescription;
    private JButton addButton;
    private JPanel mainPanel;

    private final PatientService patientService;

    public MainView(PatientService patientService) throws HeadlessException {
        this.patientService = patientService;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();

        addButton.addActionListener(listener -> {
            final Patient patient = new Patient(-1, firstName.getText(), lastName.getText(), cin.getText(), address.getText(), caseDescription.getText());
            patientService.save(patient);
        });
    }
}
