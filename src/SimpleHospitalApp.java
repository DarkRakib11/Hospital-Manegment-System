import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class SimpleHospitalApp extends JFrame {


    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Nurse> nurses = new ArrayList<>();


    private DefaultTableModel patientModel;
    private DefaultTableModel doctorModel;
    private DefaultTableModel nurseModel;


    private CardLayout cardLayout;
    private JPanel contentPanel;


    private final String PATIENT_FILE = "patients.txt";
    private final String DOCTOR_FILE = "doctors.txt";
    private final String NURSE_FILE = "nurses.txt";

    public SimpleHospitalApp() {

        loadPatients();
        loadDoctors();
        loadNurses();

        setTitle("Hospital Management System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
    }

    private void createGUI() {

        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 55, 80));
        header.setPreferredSize(new Dimension(0, 70));

        JLabel title = new JLabel("  HOSPITAL MANAGEMENT SYSTEM");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel adminLabel = new JLabel("Admin Panel  ");
        adminLabel.setForeground(Color.WHITE);
        adminLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        header.add(title, BorderLayout.WEST);
        header.add(adminLabel, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);


        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(35, 45, 55));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new GridLayout(6, 1, 5, 5));

        JButton dashboardButton = createMenuButton("Dashboard");
        JButton patientButton = createMenuButton("Patients");
        JButton doctorButton = createMenuButton("Doctors");
        JButton nurseButton = createMenuButton("Nurses");
        JButton billingButton = createMenuButton("Billing & Report");
        JButton exitButton = createMenuButton("Exit");

        sidebar.add(dashboardButton);
        sidebar.add(patientButton);
        sidebar.add(doctorButton);
        sidebar.add(nurseButton);
        sidebar.add(billingButton);
        sidebar.add(exitButton);

        add(sidebar, BorderLayout.WEST);


        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(createDashboardPanel(), "Dashboard");
        contentPanel.add(createPatientPanel(), "Patients");
        contentPanel.add(createDoctorPanel(), "Doctors");
        contentPanel.add(createNursePanel(), "Nurses");
        contentPanel.add(createBillingPanel(), "Billing");

        add(contentPanel, BorderLayout.CENTER);

        dashboardButton.addActionListener(e ->
                cardLayout.show(contentPanel, "Dashboard"));

        patientButton.addActionListener(e ->
                cardLayout.show(contentPanel, "Patients"));

        doctorButton.addActionListener(e ->
                cardLayout.show(contentPanel, "Doctors"));

        nurseButton.addActionListener(e ->
                cardLayout.show(contentPanel, "Nurses"));

        billingButton.addActionListener(e ->
                cardLayout.show(contentPanel, "Billing"));

        exitButton.addActionListener(e ->
                System.exit(0));

        cardLayout.show(contentPanel, "Dashboard");
    }


    private JButton createMenuButton(String text) {

        JButton button = new JButton(text);

        button.setForeground(Color.WHITE);
        button.setBackground(new Color(45, 60, 70));
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setFocusPainted(false);

        return button;
    }


    private JPanel createDashboardPanel() {

        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        panel.add(title, BorderLayout.NORTH);

        JPanel cards = new JPanel(
                new GridLayout(1, 3, 20, 20)
        );

        cards.add(
                createCard("Patients",
                        String.valueOf(patients.size()))
        );

        cards.add(
                createCard("Doctors",
                        String.valueOf(doctors.size()))
        );

        cards.add(
                createCard("Nurses",
                        String.valueOf(nurses.size()))
        );

        panel.add(cards, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCard(String title, String number) {

        JPanel panel = new JPanel(
                new GridLayout(2, 1)
        );

        panel.setBorder(
                BorderFactory.createLineBorder(Color.GRAY)
        );

        JLabel titleLabel =
                new JLabel(title, SwingConstants.CENTER);

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        JLabel numberLabel =
                new JLabel(number, SwingConstants.CENTER);

        numberLabel.setFont(
                new Font("Arial", Font.BOLD, 35)
        );

        panel.add(titleLabel);
        panel.add(numberLabel);

        return panel;
    }


    private JPanel createPatientPanel() {

        JPanel panel = new JPanel(
                new BorderLayout(10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        JLabel title =
                new JLabel("Patient Management");

        title.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        panel.add(title, BorderLayout.NORTH);

        patientModel = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Name",
                        "Age",
                        "Disease",
                        "Total Bill",
                        "Paid",
                        "Due"
                },
                0
        );

        JTable table =
                new JTable(patientModel);

        panel.add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        JPanel buttons = new JPanel();

        JButton addButton =
                new JButton("Add Patient");

        JButton updateButton =
                new JButton("Update Patient");

        JButton deleteButton =
                new JButton("Delete Patient");

        buttons.add(addButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);

        panel.add(buttons, BorderLayout.SOUTH);

        refreshPatientTable();

        addButton.addActionListener(e ->
                addPatient());

        updateButton.addActionListener(e ->
                updatePatient());

        deleteButton.addActionListener(e ->
                deletePatient());

        return panel;
    }

    private void addPatient() {

        try {

            String id =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Patient ID:"
                    );

            String name =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Patient Name:"
                    );

            String ageText =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Age:"
                    );

            String disease =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Disease:"
                    );

            String totalText =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Total Bill:"
                    );

            String paidText =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Paid Amount:"
                    );

            if (id == null ||
                    name == null ||
                    ageText == null ||
                    disease == null ||
                    totalText == null ||
                    paidText == null) {

                return;
            }

            int age =
                    Integer.parseInt(ageText);

            double total =
                    Double.parseDouble(totalText);

            double paid =
                    Double.parseDouble(paidText);

            if (age <= 0) {

                throw new InvalidDataException(
                        "Age must be greater than 0."
                );
            }

            if (total < 0 || paid < 0) {

                throw new InvalidDataException(
                        "Bill amount cannot be negative."
                );
            }

            if (paid > total) {

                throw new InvalidDataException(
                        "Paid amount cannot be greater than total bill."
                );
            }

            Patient patient =
                    new Patient(
                            id,
                            name,
                            age,
                            disease,
                            total,
                            paid
                    );

            patients.add(patient);

            savePatients();
            refreshPatientTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Patient added successfully."
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers."
            );

        } catch (InvalidDataException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }


    private void updatePatient() {

        String id =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Patient ID to update:"
                );

        if (id == null) {
            return;
        }

        for (int i = 0; i < patients.size(); i++) {

            Patient oldPatient =
                    patients.get(i);

            if (oldPatient.getId().equals(id)) {

                try {

                    String name =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter New Name:",
                                    oldPatient.getName()
                            );

                    String ageText =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter New Age:",
                                    oldPatient.getAge()
                            );

                    String disease =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter New Disease:",
                                    oldPatient.getDisease()
                            );

                    String totalText =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter New Total Bill:",
                                    oldPatient.getTotalBill()
                            );

                    String paidText =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter New Paid Amount:",
                                    oldPatient.getPaidAmount()
                            );

                    if (name == null ||
                            ageText == null ||
                            disease == null ||
                            totalText == null ||
                            paidText == null) {

                        return;
                    }

                    int age =
                            Integer.parseInt(ageText);

                    double total =
                            Double.parseDouble(totalText);

                    double paid =
                            Double.parseDouble(paidText);

                    if (age <= 0) {

                        throw new InvalidDataException(
                                "Age must be greater than 0."
                        );
                    }

                    if (total < 0 || paid < 0) {

                        throw new InvalidDataException(
                                "Bill cannot be negative."
                        );
                    }

                    if (paid > total) {

                        throw new InvalidDataException(
                                "Paid amount cannot be greater than total bill."
                        );
                    }

                    // Create a new Patient instead of using setters
                    Patient updatedPatient =
                            new Patient(
                                    id,
                                    name,
                                    age,
                                    disease,
                                    total,
                                    paid
                            );

                    patients.set(i, updatedPatient);

                    savePatients();
                    refreshPatientTable();

                    JOptionPane.showMessageDialog(
                            this,
                            "Patient updated successfully."
                    );

                } catch (NumberFormatException e) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter valid numbers."
                    );

                } catch (InvalidDataException e) {

                    JOptionPane.showMessageDialog(
                            this,
                            e.getMessage()
                    );
                }

                return;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Patient not found."
        );
    }

    private void deletePatient() {

        String id =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Patient ID to delete:"
                );

        if (id == null) {
            return;
        }

        for (int i = 0; i < patients.size(); i++) {

            if (patients.get(i).getId().equals(id)) {

                patients.remove(i);

                savePatients();
                refreshPatientTable();

                JOptionPane.showMessageDialog(
                        this,
                        "Patient deleted successfully."
                );

                return;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Patient not found."
        );
    }


    private void refreshPatientTable() {

        if (patientModel == null) {
            return;
        }

        patientModel.setRowCount(0);

        for (Patient p : patients) {

            patientModel.addRow(
                    new Object[]{
                            p.getId(),
                            p.getName(),
                            p.getAge(),
                            p.getDisease(),
                            p.getTotalBill(),
                            p.getPaidAmount(),
                            p.getDueAmount()
                    }
            );
        }
    }



    private JPanel createDoctorPanel() {

        JPanel panel = new JPanel(
                new BorderLayout(10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        JLabel title =
                new JLabel("Doctor Management");

        title.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        panel.add(title, BorderLayout.NORTH);

        doctorModel = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Name",
                        "Specialization"
                },
                0
        );

        JTable table =
                new JTable(doctorModel);

        panel.add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        JPanel buttons = new JPanel();

        JButton addButton =
                new JButton("Add Doctor");

        JButton updateButton =
                new JButton("Update Doctor");

        JButton deleteButton =
                new JButton("Delete Doctor");

        buttons.add(addButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);

        panel.add(buttons, BorderLayout.SOUTH);

        refreshDoctorTable();

        addButton.addActionListener(e ->
                addDoctor());

        updateButton.addActionListener(e ->
                updateDoctor());

        deleteButton.addActionListener(e ->
                deleteDoctor());

        return panel;
    }

    private void addDoctor() {

        try {

            String id =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Doctor ID:"
                    );

            String name =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Doctor Name:"
                    );

            String specialization =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Specialization:"
                    );

            if (id == null ||
                    name == null ||
                    specialization == null) {

                return;
            }

            if (id.trim().isEmpty() ||
                    name.trim().isEmpty() ||
                    specialization.trim().isEmpty()) {

                throw new InvalidDataException(
                        "Fields cannot be empty."
                );
            }

            Doctor doctor =
                    new Doctor(
                            id,
                            name,
                            specialization
                    );

            doctors.add(doctor);

            saveDoctors();
            refreshDoctorTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Doctor added successfully."
            );

        } catch (InvalidDataException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }


    private void updateDoctor() {

        String id =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Doctor ID to update:"
                );

        if (id == null) {
            return;
        }

        for (int i = 0; i < doctors.size(); i++) {

            Doctor oldDoctor =
                    doctors.get(i);

            if (oldDoctor.getId().equals(id)) {

                try {

                    String name =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter New Name:",
                                    oldDoctor.getName()
                            );

                    String specialization =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter New Specialization:",
                                    oldDoctor.getSpecialization()
                            );

                    if (name == null ||
                            specialization == null) {

                        return;
                    }

                    if (name.trim().isEmpty() ||
                            specialization.trim().isEmpty()) {

                        throw new InvalidDataException(
                                "Fields cannot be empty."
                        );
                    }

                    // Create a new Doctor
                    Doctor updatedDoctor =
                            new Doctor(
                                    id,
                                    name,
                                    specialization
                            );

                    doctors.set(i, updatedDoctor);

                    saveDoctors();
                    refreshDoctorTable();

                    JOptionPane.showMessageDialog(
                            this,
                            "Doctor updated successfully."
                    );

                } catch (InvalidDataException e) {

                    JOptionPane.showMessageDialog(
                            this,
                            e.getMessage()
                    );
                }

                return;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Doctor not found."
        );
    }

    private void deleteDoctor() {

        String id =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Doctor ID to delete:"
                );

        if (id == null) {
            return;
        }

        for (int i = 0; i < doctors.size(); i++) {

            if (doctors.get(i).getId().equals(id)) {

                doctors.remove(i);

                saveDoctors();
                refreshDoctorTable();

                JOptionPane.showMessageDialog(
                        this,
                        "Doctor deleted successfully."
                );

                return;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Doctor not found."
        );
    }


    private void refreshDoctorTable() {

        if (doctorModel == null) {
            return;
        }

        doctorModel.setRowCount(0);

        for (Doctor d : doctors) {

            doctorModel.addRow(
                    new Object[]{
                            d.getId(),
                            d.getName(),
                            d.getSpecialization()
                    }
            );
        }
    }

    // =========================================================
    // NURSE MANAGEMENT
    // =========================================================

    private JPanel createNursePanel() {

        JPanel panel = new JPanel(
                new BorderLayout(10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        JLabel title =
                new JLabel("Nurse Management");

        title.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        panel.add(title, BorderLayout.NORTH);

        nurseModel = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Name",
                        "Shift"
                },
                0
        );

        JTable table =
                new JTable(nurseModel);

        panel.add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        JPanel buttons = new JPanel();

        JButton addButton =
                new JButton("Add Nurse");

        JButton updateButton =
                new JButton("Update Nurse");

        JButton deleteButton =
                new JButton("Delete Nurse");

        buttons.add(addButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);

        panel.add(buttons, BorderLayout.SOUTH);

        refreshNurseTable();

        addButton.addActionListener(e ->
                addNurse());

        updateButton.addActionListener(e ->
                updateNurse());

        deleteButton.addActionListener(e ->
                deleteNurse());

        return panel;
    }


    private void addNurse() {

        try {

            String id =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Nurse ID:"
                    );

            String name =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Nurse Name:"
                    );

            String shift =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Shift:"
                    );

            if (id == null ||
                    name == null ||
                    shift == null) {

                return;
            }

            if (id.trim().isEmpty() ||
                    name.trim().isEmpty() ||
                    shift.trim().isEmpty()) {

                throw new InvalidDataException(
                        "Fields cannot be empty."
                );
            }

            Nurse nurse =
                    new Nurse(
                            id,
                            name,
                            shift
                    );

            nurses.add(nurse);

            saveNurses();
            refreshNurseTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Nurse added successfully."
            );

        } catch (InvalidDataException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }


    private void updateNurse() {

        String id =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Nurse ID to update:"
                );

        if (id == null) {
            return;
        }

        for (int i = 0; i < nurses.size(); i++) {

            Nurse oldNurse =
                    nurses.get(i);

            if (oldNurse.getId().equals(id)) {

                try {

                    String name =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter New Name:",
                                    oldNurse.getName()
                            );

                    String shift =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter New Shift:",
                                    oldNurse.getShift()
                            );

                    if (name == null ||
                            shift == null) {

                        return;
                    }

                    if (name.trim().isEmpty() ||
                            shift.trim().isEmpty()) {

                        throw new InvalidDataException(
                                "Fields cannot be empty."
                        );
                    }

                    // Create a new Nurse
                    Nurse updatedNurse =
                            new Nurse(
                                    id,
                                    name,
                                    shift
                            );

                    nurses.set(i, updatedNurse);

                    saveNurses();
                    refreshNurseTable();

                    JOptionPane.showMessageDialog(
                            this,
                            "Nurse updated successfully."
                    );

                } catch (InvalidDataException e) {

                    JOptionPane.showMessageDialog(
                            this,
                            e.getMessage()
                    );
                }

                return;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Nurse not found."
        );
    }

    private void deleteNurse() {

        String id =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Nurse ID to delete:"
                );

        if (id == null) {
            return;
        }

        for (int i = 0; i < nurses.size(); i++) {

            if (nurses.get(i).getId().equals(id)) {

                nurses.remove(i);

                saveNurses();
                refreshNurseTable();

                JOptionPane.showMessageDialog(
                        this,
                        "Nurse deleted successfully."
                );

                return;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Nurse not found."
        );
    }


    private void refreshNurseTable() {

        if (nurseModel == null) {
            return;
        }

        nurseModel.setRowCount(0);

        for (Nurse n : nurses) {

            nurseModel.addRow(
                    new Object[]{
                            n.getId(),
                            n.getName(),
                            n.getShift()
                    }
            );
        }
    }


    private JPanel createBillingPanel() {

        JPanel panel = new JPanel(
                new BorderLayout(15, 15)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 30, 30, 30
                )
        );

        JLabel title =
                new JLabel("Billing & Report");

        title.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        panel.add(title, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel();

        JLabel idLabel =
                new JLabel("Patient ID:");

        JTextField idField =
                new JTextField(15);

        JButton searchButton =
                new JButton("Search Bill");

        JButton reportButton =
                new JButton("Generate Report");

        searchPanel.add(idLabel);
        searchPanel.add(idField);
        searchPanel.add(searchButton);
        searchPanel.add(reportButton);

        panel.add(searchPanel, BorderLayout.CENTER);

        JTextArea resultArea =
                new JTextArea();

        resultArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        16
                )
        );

        resultArea.setEditable(false);

        panel.add(
                new JScrollPane(resultArea),
                BorderLayout.SOUTH
        );

        searchButton.addActionListener(e -> {

            String id = idField.getText();

            boolean found = false;

            for (Patient p : patients) {

                if (p.getId().equals(id)) {

                    resultArea.setText(
                            "Patient ID   : " + p.getId() +
                                    "\nPatient Name : " + p.getName() +
                                    "\nDisease      : " + p.getDisease() +
                                    "\nTotal Bill   : " + p.getTotalBill() +
                                    "\nPaid Amount  : " + p.getPaidAmount() +
                                    "\nDue Amount   : " + p.getDueAmount()
                    );

                    found = true;
                    break;
                }
            }

            if (!found) {

                resultArea.setText(
                        "Patient not found."
                );
            }
        });

        reportButton.addActionListener(e ->
                generateReport());

        return panel;
    }

    private void generateReport() {

        try {

            PrintWriter writer =
                    new PrintWriter(
                            new FileWriter(
                                    "hospital_report.txt"
                            )
                    );

            writer.println(
                    "======================================"
            );

            writer.println(
                    "       HOSPITAL MANAGEMENT REPORT"
            );

            writer.println(
                    "======================================"
            );

            writer.println();

            writer.println("PATIENTS");
            writer.println("--------------------------------------");

            for (Patient p : patients) {


                Person person = p;

                writer.println(
                        person.getRoleInfo()
                );
            }

            writer.println();

            // ---------- Doctors ----------
            writer.println("DOCTORS");
            writer.println("--------------------------------------");

            for (Doctor d : doctors) {

                // Polymorphism
                Person person = d;

                writer.println(
                        person.getRoleInfo()
                );
            }

            writer.println();

            // ---------- Nurses ----------
            writer.println("NURSES");
            writer.println("--------------------------------------");

            for (Nurse n : nurses) {

                // Polymorphism
                Person person = n;

                writer.println(
                        person.getRoleInfo()
                );
            }

            writer.println();

            writer.println(
                    "======================================"
            );

            writer.close();

            JOptionPane.showMessageDialog(
                    this,
                    "Report generated successfully!\n" +
                            "File: hospital_report.txt"
            );

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error creating report."
            );
        }
    }



    private void savePatients() {

        try {

            PrintWriter writer =
                    new PrintWriter(
                            new FileWriter(PATIENT_FILE)
                    );

            for (Patient p : patients) {

                writer.println(
                        p.toFileString()
                );
            }

            writer.close();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error saving patients."
            );
        }
    }

    private void loadPatients() {

        File file =
                new File(PATIENT_FILE);

        if (!file.exists()) {
            return;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(file)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data =
                        line.split(",");

                if (data.length >= 6) {

                    String id = data[0];
                    String name = data[1];
                    int age =
                            Integer.parseInt(data[2]);

                    String disease = data[3];

                    double total =
                            Double.parseDouble(data[4]);

                    double paid =
                            Double.parseDouble(data[5]);

                    patients.add(
                            new Patient(
                                    id,
                                    name,
                                    age,
                                    disease,
                                    total,
                                    paid
                            )
                    );
                }
            }

            reader.close();

        } catch (Exception e) {

            System.out.println(
                    "Error loading patients."
            );
        }
    }


    private void saveDoctors() {

        try {

            PrintWriter writer =
                    new PrintWriter(
                            new FileWriter(DOCTOR_FILE)
                    );

            for (Doctor d : doctors) {

                writer.println(
                        d.toFileString()
                );
            }

            writer.close();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error saving doctors."
            );
        }
    }

    private void loadDoctors() {

        File file =
                new File(DOCTOR_FILE);

        if (!file.exists()) {
            return;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(file)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data =
                        line.split(",");

                if (data.length >= 3) {

                    doctors.add(
                            new Doctor(
                                    data[0],
                                    data[1],
                                    data[2]
                            )
                    );
                }
            }

            reader.close();

        } catch (Exception e) {

            System.out.println(
                    "Error loading doctors."
            );
        }
    }


    private void saveNurses() {

        try {

            PrintWriter writer =
                    new PrintWriter(
                            new FileWriter(NURSE_FILE)
                    );

            for (Nurse n : nurses) {

                writer.println(
                        n.toFileString()
                );
            }

            writer.close();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error saving nurses."
            );
        }
    }

    private void loadNurses() {

        File file =
                new File(NURSE_FILE);

        if (!file.exists()) {
            return;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(file)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data =
                        line.split(",");

                if (data.length >= 3) {

                    nurses.add(
                            new Nurse(
                                    data[0],
                                    data[1],
                                    data[2]
                            )
                    );
                }
            }

            reader.close();

        } catch (Exception e) {

            System.out.println(
                    "Error loading nurses."
            );
        }
    }


    private static void showLogin() {

        JTextField usernameField =
                new JTextField();

        JPasswordField passwordField =
                new JPasswordField();

        JPanel panel =
                new JPanel(
                        new GridLayout(2, 2, 5, 5)
                );

        panel.add(
                new JLabel("Username:")
        );

        panel.add(usernameField);

        panel.add(
                new JLabel("Password:")
        );

        panel.add(passwordField);

        int result =
                JOptionPane.showConfirmDialog(
                        null,
                        panel,
                        "Admin Login",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result == JOptionPane.OK_OPTION) {

            String username =
                    usernameField.getText();

            String password =
                    new String(
                            passwordField.getPassword()
                    );

            if (username.equals("admin") &&
                    password.equals("1234")) {

                SwingUtilities.invokeLater(() -> {

                    SimpleHospitalApp app =
                            new SimpleHospitalApp();

                    app.setVisible(true);
                });

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "Invalid username or password."
                );

                showLogin();
            }

        } else {

            System.exit(0);
        }
    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                SimpleHospitalApp::showLogin
        );
    }
}