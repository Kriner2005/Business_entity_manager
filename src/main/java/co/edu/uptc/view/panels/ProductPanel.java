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
import co.edu.uptc.config.MessageManager;
import co.edu.uptc.model.entities.Product;
import co.edu.uptc.presenter.interfaces.IProductPresenter;
import co.edu.uptc.view.interfaces.IColleague;
import co.edu.uptc.view.interfaces.IMediator;
import co.edu.uptc.view.interfaces.IProductView;

public class ProductPanel extends JPanel implements IProductView, IColleague {

    private IProductPresenter presenter;
    private IMediator mediator;
    
    private JTextField description;
    private JComboBox<String> unit;
    private JTextField price;

    private JLabel title;
    private JLabel descriptionTxt;
    private JLabel unitTxt;
    private JLabel priceTxt;
    private JLabel statusLabel;
    private JLabel pageLabel;

    private JButton add;
    private JButton remove;
    private JButton list;
    private JButton export;
    private JButton back;

    private JButton prevBtn;
    private JButton nextBtn;

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
        applyTableAlignment();
    }

    private void initComponents() {
        title = new JLabel(MessageManager.msg("product.panel.title"), SwingConstants.CENTER);
        descriptionTxt = new JLabel(MessageManager.msg("product.field.description"));
        unitTxt = new JLabel(MessageManager.msg("product.field.unit"));
        priceTxt = new JLabel(MessageManager.msg("product.field.price"));
        statusLabel = new JLabel(" ");
        pageLabel = new JLabel(MessageManager.msg("paging.label", 1, 1), SwingConstants.CENTER);

        description = new JTextField(20);
        unit = new JComboBox<>(new DefaultComboBoxModel<>(new String[] {
                "KILO", "LIBRA", "BULTO", "TONELADA", "LITRO", "UNIDAD", "OTRO"
        }));
        price = new JTextField(10);

        add = new JButton(MessageManager.msg("action.add"));
        remove = new JButton(MessageManager.msg("action.remove"));
        list = new JButton(MessageManager.msg("action.list"));
        export = new JButton(MessageManager.msg("action.export"));
        back = new JButton(MessageManager.msg("action.back"));

        prevBtn = new JButton(MessageManager.msg("action.prev"));
        nextBtn = new JButton(MessageManager.msg("action.next"));

        tableModel = new DefaultTableModel(new String[] {
                MessageManager.msg("product.table.id"),
                MessageManager.msg("product.table.description"),
                MessageManager.msg("product.table.unit"),
                MessageManager.msg("product.table.price")
        }, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = new JTable(tableModel);
        scroll = new JScrollPane(table);
    }

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
        form.setBorder(BorderFactory.createTitledBorder(
                MessageManager.msg("product.form.title")));
        form.add(descriptionTxt);
        form.add(description);
        form.add(unitTxt);
        form.add(unit);
        form.add(priceTxt);
        form.add(price);
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
        buttons.add(remove);
        buttons.add(list);
        buttons.add(export);
        buttons.add(back);

        south.add(buttons, BorderLayout.WEST);
        south.add(statusLabel, BorderLayout.CENTER);
        return south;
    }

    private void bindEvents() {
        add.addActionListener(e -> onAdd());
        remove.addActionListener(e -> onRemove());
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
            presenter.addProduct(description.getText().trim(), unit.getSelectedItem().toString(),
                    price.getText());
    }

    private void onRemove() {
        if (presenter != null)
            presenter.removeProduct();
    }

    private void onList() {
        if (presenter != null)
            presenter.listProducts();
    }

    private void onExport() {
        if (presenter != null)
            presenter.exportCSV();
    }

    @Override
    public void showProductList(List<Product> products, int currentPage, int totalPages) {
        tableModel.setRowCount(0);
        for (Product p : products) {
            tableModel.addRow(new Object[] {
                    p.getId(),
                    p.getDescription(),
                    p.getUnit(),
                    String.format("%,.2f", p.getPrice())
            });
        }
        pageLabel.setText(MessageManager.msg("paging.label", currentPage, totalPages));
        prevBtn.setEnabled(currentPage > 1);
        nextBtn.setEnabled(currentPage < totalPages);
    }

    @Override
    public void showRemovedProduct(Product product) {
        JOptionPane.showMessageDialog(this,
                MessageManager.msg("product.removed.msg",
                        product.getDescription(), product.getUnit(),
                        String.format("%,.2f", product.getPrice())),
                MessageManager.msg("product.removed.title"),
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void clearForm() {
        description.setText("");
        price.setText("");
        unit.setSelectedIndex(0);
    }

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
        JOptionPane.showMessageDialog(this, msg,
                MessageManager.msg("dialog.alert.title"), JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void setMediator(IMediator mediator) {
        this.mediator = mediator;
    }
}