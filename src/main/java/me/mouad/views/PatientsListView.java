package me.mouad.views;

import me.mouad.models.Patient;
import me.mouad.services.PatientService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PatientsListView extends JPanel {

    private final PatientService service;

    private JTable table;

    private JScrollPane scrollPane;
    private JButton modifyButton, deleteButton;

    public PatientsListView(PatientService service) {
        this.service = service;

        setSize(1080, 720);

        final JPanel buttonsPanel = new JPanel(new FlowLayout());
        final BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

        setLayout(boxLayout);

        setupTable();
        setupButtons();

        buttonsPanel.add(modifyButton);
        buttonsPanel.add(deleteButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonsPanel);
    }

    public void build() {
        service.fetch().whenComplete((list, error) -> {
            if (error != null) {
                JOptionPane.showMessageDialog(null,
                        "Could not fetch patients! " + error.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            list.forEach(patient -> ((DefaultTableModel) table.getModel()).addRow(new Object[]{
                    patient.getCin(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getAddress(),
                    patient.getCaseDescription()
            }));
        });
        JOptionPane.showMessageDialog(null,
                this,
                "Patients List",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void setupTable() {
        final DefaultTableModel model = new DefaultTableModel(null,
                new String[]{"CIN", "First Name", "Last Name", "Address", "Diagnosis"});

        table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        scrollPane = new JScrollPane(table);

        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(390);
    }

    private void updateRow(int row, Patient patient) {
        final DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setValueAt(patient.getCin(), row, 0);
        model.setValueAt(patient.getFirstName(), row, 1);
        model.setValueAt(patient.getLastName(), row, 2);
        model.setValueAt(patient.getAddress(), row, 3);
        model.setValueAt(patient.getCaseDescription(), row, 4);
    }

    private void setupButtons() {
        modifyButton = new JButton("Modify");
        modifyButton.addActionListener(event -> {

            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null,
                        "Select a patient to modify!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            final String cin = (String) table.getValueAt(table.getSelectedRow(), 0);
            service.get(cin).whenComplete((result, error) -> {
                if (error != null) {
                    JOptionPane.showMessageDialog(null,
                            "Could not save patient! " + error.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                final PatientFormView formView = new PatientFormView();
                formView.bind(result);

                formView.build("Modify " + result.getFirstName() + " " + result.getLastName()
                        , form -> {
                            final Patient updated = form.getInput();
                            updated.setId(result.getId());
                            service.update(updated);

                            updateRow(selectedRow, updated);

                            JOptionPane.showMessageDialog(null,
                                    "Patient successfully updated!",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                        });

            });

        });

        deleteButton = new JButton("Delete");

        deleteButton.addActionListener(actionEvent -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(null,
                        "Select a patient to delete!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            service.get((String) table.getValueAt(selectedRow, 0)).whenComplete((result, error) -> {
                if (error != null) {
                    JOptionPane.showMessageDialog(null,
                            "Could not retrieve patient data",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                service.delete(result).whenComplete((success, e) -> {
                    if (e != null) {
                        JOptionPane.showMessageDialog(null,
                                "Could not delete patient!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(null,
                            "Patient successfully deleted!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                });
            });
        });
    }

}
