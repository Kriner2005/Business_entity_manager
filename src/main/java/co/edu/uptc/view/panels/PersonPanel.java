package co.edu.uptc.view.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.model.entities.Person;
import co.edu.uptc.presenter.interfaces.IPersonPresenter;
import co.edu.uptc.view.interfaces.IColleague;
import co.edu.uptc.view.interfaces.IMediator;
import co.edu.uptc.view.interfaces.IPersonView;

public class PersonPanel extends JPanel implements IPersonView, IColleague {

    private IPersonPresenter presenter;
    private IMediator mediator;

    // ── formulario ────────────────────────────────────────────────────────
    private JTextField name;
    private JTextField lastName;
    private JTextField birthDate;
    private JComboBox<String> genderBox;

    // ── labels ────────────────────────────────────────────────────────────
    private JLabel title;
    private JLabel nameTxt;
    private JLabel lastNameTxt;
    private JLabel dateTxt;
    private JLabel genderTxt;
    private JLabel statusLabel;
    private JLabel pageLabel; // "Página 1 de 3"

    // ── botones acciones ──────────────────────────────────────────────────
    private JButton add;
    private JButton remove;
    private JButton list;
    private JButton persis;
    private JButton back;

    // ── botones paginado ──────────────────────────────────────────────────
    private JButton prevBtn;
    private JButton nextBtn;

    // ── tabla ─────────────────────────────────────────────────────────────
    private JScrollPane scroll;
    private JTable table;
    private DefaultTableModel tableModel;

    public PersonPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        initComponents();

        add(buildNorth(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(buildSouth(), BorderLayout.SOUTH);

        bindEvents();
    }

    private void initComponents() {
        title = new JLabel("Gestión de Personas", SwingConstants.CENTER);
        nameTxt = new JLabel("Nombres:");
        lastNameTxt = new JLabel("Apellidos:");
        dateTxt = new JLabel("Fecha nacimiento:");
        genderTxt = new JLabel("Género:");
        statusLabel = new JLabel(" ");
        pageLabel = new JLabel("Página 1 de 1", SwingConstants.CENTER);

        name = new JTextField(15);
        lastName = new JTextField(15);
        birthDate = new JTextField("yyyy-MM-dd", 10);
        genderBox = new JComboBox<>(new DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        add = new JButton("Agregar");
        remove = new JButton("Retirar");
        list = new JButton("Listar");
        persis = new JButton("Exportar CSV");
        back = new JButton("← Volver");

        prevBtn = new JButton("◀ Anterior");
        nextBtn = new JButton("Siguiente ▶");

        tableModel = new DefaultTableModel(
                new String[] { "ID", "Nombres", "Apellidos", "Género", "Edad" }, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = new JTable(tableModel);
        scroll = new JScrollPane(table);
    }

    private JLabel buildNorth() {
        return title;
    }

    private JPanel buildCenter() {
        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.add(buildForm(), BorderLayout.NORTH);
        center.add(scroll, BorderLayout.CENTER);
        center.add(buildPaging(), BorderLayout.SOUTH);
        return center;
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(new GridLayout(2, 4, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Datos"));

        form.add(nameTxt);
        form.add(name);
        form.add(lastNameTxt);
        form.add(lastName);
        form.add(genderTxt);
        form.add(genderBox);
        form.add(dateTxt);
        form.add(birthDate);

        return form;
    }

    // Barra de paginado: [◀ Anterior] Página 1 de 3 [Siguiente ▶]
    private JPanel buildPaging() {
        JPanel paging = new JPanel(new BorderLayout(8, 0));
        paging.add(prevBtn, BorderLayout.WEST);
        paging.add(pageLabel, BorderLayout.CENTER);
        paging.add(nextBtn, BorderLayout.EAST);
        return paging;
    }

    private JPanel buildSouth() {
        JPanel south = new JPanel(new BorderLayout());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttons.add(add);
        buttons.add(remove);
        buttons.add(list);
        buttons.add(persis);
        buttons.add(back);

        south.add(buttons, BorderLayout.WEST);
        south.add(statusLabel, BorderLayout.CENTER);
        return south;
    }

    private void bindEvents() {
        add.addActionListener(e -> onAdd());
        remove.addActionListener(e -> onRemove());
        list.addActionListener(e -> onList());
        persis.addActionListener(e -> onExport());
        back.addActionListener(e -> mediator.notify(this, "back"));

        prevBtn.addActionListener(e -> {
            if (presenter != null)
                presenter.prevPage();
        });
        nextBtn.addActionListener(e -> {
            if (presenter != null)
                presenter.nextPage();
        });
    }

    private void onAdd() {
        if (presenter != null)
            presenter.addPerson(name.getText(), lastName.getText(), genderBox.getSelectedItem().toString(),
                    birthDate.getText());
    }

    private void onRemove() {
        if (presenter != null)
            presenter.removePerson();
    }

    private void onList() {
        if (presenter != null)
            presenter.listPersons();
    }

    private void onExport() {
        if (presenter != null)
            presenter.exportCSV();
    }

    // ── IPersonView ────────────────────────────────────────────────────────

    @Override
    public void showPersonList(List<Person> persons, int currentPage, int totalPages) {
        tableModel.setRowCount(0);
        for (Person p : persons) {
            tableModel.addRow(new Object[] {
                    p.getId(),
                    p.getName(),
                    p.getLastName(),
                    p.getGender() == 'M' ? "Masculino" : "Femenino",
                    java.time.LocalDate.now().getYear() - p.getBirthDate().getYear()
            });
        }
        pageLabel.setText("Página " + currentPage + " de " + totalPages);

        // Habilita/deshabilita los botones según si hay páginas disponibles
        prevBtn.setEnabled(currentPage > 1);
        nextBtn.setEnabled(currentPage < totalPages);
    }

    @Override
    public void showRemovedPerson(Person person) {
        JOptionPane.showMessageDialog(this,
                "Persona retirada: " + person.getName() + " " + person.getLastName(),
                "Retirado", JOptionPane.INFORMATION_MESSAGE);
    }

    // ── ViewInterface ──────────────────────────────────────────────────────

    @Override
    public void setPresenter(IPersonPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start() {
    }

    @Override
    public void showMessage(String msg) {
        statusLabel.setForeground(new java.awt.Color(0, 128, 0));
        statusLabel.setText(msg);
    }

    @Override
    public void showError(String msg) {
        statusLabel.setForeground(java.awt.Color.RED);
        statusLabel.setText(msg);
    }

    @Override
    public void showAlert(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    // ── IColleague ─────────────────────────────────────────────────────────

    @Override
    public void setMediator(IMediator mediator) {
        this.mediator = mediator;
    }
}