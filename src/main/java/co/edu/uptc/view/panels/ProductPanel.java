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

import co.edu.uptc.model.entities.Product;
import co.edu.uptc.presenter.interfaces.IProductPresenter;
import co.edu.uptc.view.interfaces.IColleague;
import co.edu.uptc.view.interfaces.IMediator;
import co.edu.uptc.view.interfaces.IProductView;

public class ProductPanel extends JPanel implements IProductView, IColleague {

    // ── presenter y mediador ───────────────────────────────────────────────
    private IProductPresenter presenter;
    private IMediator mediator;

    // ── formulario ────────────────────────────────────────────────────────
    private JTextField description;
    private JComboBox<String> unit;
    private JTextField price;

    // ── labels ────────────────────────────────────────────────────────────
    private JLabel title;
    private JLabel descriptionTxt;
    private JLabel unitTxt;
    private JLabel priceTxt;
    private JLabel statusLabel;

    // ── botones ───────────────────────────────────────────────────────────
    private JButton add;
    private JButton remove;
    private JButton list;
    private JButton export;
    private JButton back;

    // ── tabla ─────────────────────────────────────────────────────────────
    private JScrollPane scroll;
    private JTable table;
    private DefaultTableModel tableModel;

    public ProductPanel() {
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
        // labels
        title       = new JLabel("Gestión de Productos", SwingConstants.CENTER);
        descriptionTxt = new JLabel("Descripción:");
        unitTxt     = new JLabel("Unidad:");
        priceTxt    = new JLabel("Precio:");
        statusLabel = new JLabel(" ");

        // campos
        description = new JTextField(20);
        unit = new JComboBox<>(new DefaultComboBoxModel<>(new String[] {
            "KILO", "LIBRA", "BULTO", "TONELADA", "LITRO", "UNIDAD", "OTRO"
        }));
        price = new JTextField(10);

        // botones
        add    = new JButton("Agregar");
        remove = new JButton("Retirar");
        list   = new JButton("Listar");
        export = new JButton("Exportar CSV");
        back   = new JButton("← Volver");

        // tabla
        tableModel = new DefaultTableModel(
                new String[] { "ID", "Descripción", "Unidad", "Precio" }, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table  = new JTable(tableModel);
        scroll = new JScrollPane(table);
    }

    // ── construcción de secciones ──────────────────────────────────────────

    private JLabel buildNorth() {
        return title;
    }

    private JPanel buildCenter() {
        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.add(buildForm(), BorderLayout.NORTH);
        center.add(scroll, BorderLayout.CENTER);
        return center;
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(new GridLayout(2, 4, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Datos"));

        form.add(descriptionTxt);
        form.add(description);
        form.add(unitTxt);
        form.add(unit);
        form.add(priceTxt);
        form.add(price);
        form.add(new JLabel()); // celda vacía para mantener el grid alineado
        form.add(new JLabel());

        return form;
    }

    private JPanel buildSouth() {
        JPanel south = new JPanel(new BorderLayout());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttons.add(add);
        buttons.add(remove);
        buttons.add(list);
        buttons.add(export);
        buttons.add(back);

        south.add(buttons, BorderLayout.WEST);
        south.add(statusLabel, BorderLayout.CENTER);
        return south;
    }

    // ── eventos ────────────────────────────────────────────────────────────

    private void bindEvents() {
        add.addActionListener(e    -> onAdd());
        remove.addActionListener(e -> onRemove());
        list.addActionListener(e   -> onList());
        export.addActionListener(e -> onExport());
        back.addActionListener(e   -> mediator.notify(this, "back"));
    }

    private void onAdd() {
        if (presenter != null) {
            presenter.addProduct(
                    description.getText().toUpperCase().trim(),
                    unit.getSelectedItem().toString(),
                    price.getText());
        }
    }

    private void onRemove() {
        if (presenter != null) {
            presenter.removeProduct();
        }
    }

    private void onList() {
        if (presenter != null) {
            presenter.listProducts();
        }
    }

    private void onExport() {
        if (presenter != null) {
            presenter.exportCSV();
        }
    }

    // ── IProductView ───────────────────────────────────────────────────────

    @Override
    public void showProductList(List<Product> products) {
        tableModel.setRowCount(0);
        for (Product p : products) {
            tableModel.addRow(new Object[] {
                    p.getId(),
                    p.getDescription(),
                    p.getUnit(),
                    String.format("%,.2f", p.getPrice())
            });
        }
    }

    @Override
    public void showRemovedProduct(Product product) {
        JOptionPane.showMessageDialog(this,
                "Producto retirado: " + product.getDescription()
                + " | " + product.getUnit()
                + " | $" + String.format("%,.2f", product.getPrice()),
                "Retirado",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // ── ViewInterface ──────────────────────────────────────────────────────

    @Override
    public void setPresenter(IProductPresenter presenter) {
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