package me.mouad.views;

import me.mouad.models.Patient;
import me.mouad.services.PatientService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class PatientFormView extends JPanel {
    private final JTextField firstName,
            lastName,
            cin,
            address;
    private final JTextArea caseDescription;
    private final JButton addButton;
    private final PatientService patientService;

    public PatientFormView(PatientService service) throws HeadlessException {
        this.patientService = service;
        this.firstName = new JTextField();
        this.lastName = new JTextField();
        this.cin = new JTextField();
        this.address = new JTextField();
        this.caseDescription = new JTextArea();
        this.addButton = new JButton("Save");

        setupButtons();
        add(firstName, lastName, cin, address, caseDescription, address);
    }

    private void add(java.awt.Component... components) {
        for (java.awt.Component c : components)
            add(c);
    }

    private void setupButtons() {
        addButton.addActionListener(listener -> {
            final Patient patient = new Patient(-1,
                    firstName.getText(),
                    lastName.getText(),
                    cin.getText(),
                    address.getText(),
                    caseDescription.getText());
            if (!Patient.isValid(patient))
                JOptionPane.showMessageDialog(null,
                        "Invalid form!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            else {
                patientService.save(patient).whenComplete((result, error) -> {
                    if (error == null) {
                        JOptionPane.showMessageDialog(null,
                                "Patient successfully added!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Could not save user! " + error.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });

            }

        });
    }

    public void build() {
        JOptionPane.showMessageDialog(null,
                this,
                "Patient Form",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
