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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.config.AppConfig;
import co.edu.uptc.model.entities.Accounting;
import co.edu.uptc.presenter.interfaces.IAccountingPresenter;
import co.edu.uptc.view.interfaces.IAccountingView;
import co.edu.uptc.view.interfaces.IColleague;
import co.edu.uptc.view.interfaces.IMediator;

public class AccountingPanel extends JPanel implements IAccountingView, IColleague {

    private IAccountingPresenter presenter;
    private IMediator mediator;

    // ── formulario ────────────────────────────────────────────────────────
    private JTextField description;
    private JComboBox<String> movementType;
    private JTextField value;

    // ── labels ────────────────────────────────────────────────────────────
    private JLabel title;
    private JLabel descriptionTxt;
    private JLabel movementTypeTxt;
    private JLabel valueTxt;
    private JLabel totalBalanceLabel;
    private JLabel statusLabel;
    private JLabel pageLabel;

    // ── botones acciones ──────────────────────────────────────────────────
    private JButton add;
    private JButton list;
    private JButton export;
    private JButton back;

    // ── botones paginado ──────────────────────────────────────────────────
    private JButton prevBtn;
    private JButton nextBtn;

    // ── tabla ─────────────────────────────────────────────────────────────
    private JScrollPane scroll;
    private JTable table;
    private DefaultTableModel tableModel;

    public AccountingPanel() {
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
        applyTableAlignment();
    }

    private void initComponents() {
        title = new JLabel("Gestión de Contabilidad", SwingConstants.CENTER);
        descriptionTxt = new JLabel("Descripción:");
        movementTypeTxt = new JLabel("Tipo de movimiento:");
        valueTxt = new JLabel("Valor:");
        totalBalanceLabel = new JLabel("Saldo total: $0.00");
        statusLabel = new JLabel(" ");
        pageLabel = new JLabel("Página 1 de 1", SwingConstants.CENTER);

        description = new JTextField(20);
        movementType = new JComboBox<>(new DefaultComboBoxModel<>(new String[] { "INGRESO", "EGRESO" }));
        value = new JTextField(10);

        add = new JButton("Agregar");
        list = new JButton("Listar");
        export = new JButton("Exportar");
        back = new JButton("← Volver");

        prevBtn = new JButton("◀ Anterior");
        nextBtn = new JButton("Siguiente ▶");

        tableModel = new DefaultTableModel(
                new String[] { "Descripción", "Tipo", "Valor", "Fecha/Hora" }, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = new JTable(tableModel);
        scroll = new JScrollPane(table);
    }

    // Aplica la alineación leída del config a todas las columnas de la tabla
    private void applyTableAlignment() {
        int align = AppConfig.getInstance().getTableAlign();
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(align);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
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

        form.add(descriptionTxt);
        form.add(description);
        form.add(movementTypeTxt);
        form.add(movementType);
        form.add(valueTxt);
        form.add(value);
        form.add(new JLabel());
        form.add(new JLabel());

        return form;
    }

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
        buttons.add(list);
        buttons.add(export);
        buttons.add(back);

        JPanel info = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        info.add(totalBalanceLabel);
        info.add(statusLabel);

        south.add(buttons, BorderLayout.WEST);
        south.add(info, BorderLayout.CENTER);
        return south;
    }

    private void bindEvents() {
        add.addActionListener(e -> onAdd());
        list.addActionListener(e -> onList());
        export.addActionListener(e -> onExport());
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
            presenter.addAccounting(description.getText().trim(), movementType.getSelectedItem().toString(),
                    value.getText().trim());
    }

    private void onList() {
        if (presenter != null)
            presenter.listAccounting();
    }

    private void onExport() {
        if (presenter != null)
            presenter.exportFile();
    }

    // ── IAccountingView ────────────────────────────────────────────────────

    @Override
    public void showAccountingList(List<Accounting> accountings, int currentPage, int totalPages) {
        tableModel.setRowCount(0);
        for (Accounting a : accountings) {
            tableModel.addRow(new Object[] {
                    a.getDescription(),
                    a.getType().getDescripcion(),
                    String.format("%,.2f", a.getAmount()),
                    a.getDateTime().toString().replace("T", " ").substring(0, 19)
            });
        }
        pageLabel.setText("Página " + currentPage + " de " + totalPages);
        prevBtn.setEnabled(currentPage > 1);
        nextBtn.setEnabled(currentPage < totalPages);
    }

    @Override
    public void showTotalBalance(double total) {
        totalBalanceLabel.setText(String.format("Saldo total: $%,.2f", total));
        totalBalanceLabel.setForeground(
                total >= 0 ? new java.awt.Color(0, 128, 0) : java.awt.Color.RED);
    }

    @Override
    public void clearForm() {
        description.setText("");
        value.setText("");
        movementType.setSelectedIndex(0);
    }

    // ── ViewInterface ──────────────────────────────────────────────────────

    @Override
    public void setPresenter(IAccountingPresenter presenter) {
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