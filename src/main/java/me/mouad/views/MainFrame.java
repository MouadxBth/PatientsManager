package me.mouad.views;

import me.mouad.services.PatientService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final PatientService service;
    public MainFrame(PatientService service) throws HeadlessException {
        this.service = service;

        setTitle("Patients manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
