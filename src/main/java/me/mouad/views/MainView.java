package me.mouad.views;

import me.mouad.models.Patient;
import me.mouad.services.PatientService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class MainView extends JFrame {

    public MainView(PatientService patientService) throws HeadlessException {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setResizable(false);
        setLocationRelativeTo(null);

        final JPanel panel = new JPanel(), helper = new JPanel();
        final BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(boxLayout);

        final JButton add = new JButton("New Patient");

        add.addActionListener(action -> {
            new PatientFormView(patientService).build("Add a new Patient", form -> {
                final Patient patient = form.getInput();

                if (!Patient.isValid(patient)) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid form!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                patientService.get(patient.getCin()).whenComplete((result, error) -> {
                    if (result != null) {
                        JOptionPane.showMessageDialog(null,
                                "Patient already exists!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (error != null) {
                        JOptionPane.showMessageDialog(null,
                                "Could not fetch patient!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    patientService.save(patient).whenComplete(($, secondError) -> {
                        if (secondError == null) {
                            JOptionPane.showMessageDialog(null,
                                    "Patient successfully added!",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Could not save user! " + secondError.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    });
                });


            });
        });

        final JButton list = new JButton("List Patients");

        list.addActionListener(event -> new PatientsListView(patientService).build());

        helper.add(add);
        helper.add(list);

        panel.add(helper);

        // pack();
        getContentPane().add(panel);

    }
}
