package me.mouad.views;

import me.mouad.models.Patient;
import me.mouad.services.PatientService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

@Component
public class PatientFormView extends JPanel {
    private final LabeledComponent<JTextField> firstName,
            lastName,
            cin,
            address;
    private final LabeledComponent<JTextField> caseDescription;


    public PatientFormView() throws HeadlessException {
        this.firstName = LabeledComponent.create("First Name:",
                new JTextField());
        this.lastName = LabeledComponent.create("Last Name:",
                new JTextField());
        this.cin = LabeledComponent.create("CIN:",
                new JTextField());
        this.address = LabeledComponent.create("Address:",
                new JTextField());
        this.caseDescription = LabeledComponent.create("Diagnosis:",
                new JTextField());

        setSize(500, 500);
        setupLayout();
        add(firstName, lastName, cin, address, caseDescription);
    }

    private void setupLayout() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
    }

    private void add(LabeledComponent<?>... components) {
        for (LabeledComponent<?> c : components) {
            c.addTo(this);
            add(Box.createVerticalStrut(20));
        }
    }

    public void build(String title, Consumer<PatientFormView> onSuccess) {
        final int option = JOptionPane.showConfirmDialog(null,
                this,
                title,
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            onSuccess.accept(this);
        }
    }

    public Patient getInput() {
        return new Patient(-1,
                firstName.getComponent().getText(),
                lastName.getComponent().getText(),
                cin.getComponent().getText(),
                address.getComponent().getText(),
                caseDescription.getComponent().getText());
    }

    public void bind(Patient patient) {
        if (patient == null)
            return ;

        if (patient.getFirstName() != null)
            firstName.getComponent().setText(patient.getFirstName());
        if (patient.getLastName() != null)
            lastName.getComponent().setText(patient.getLastName());
        if (patient.getCin() != null)
            cin.getComponent().setText(patient.getCin());
        if (patient.getAddress() != null)
            address.getComponent().setText(patient.getAddress());
        if (patient.getCaseDescription() != null)
            caseDescription.getComponent().setText(patient.getCaseDescription());
    }

}
