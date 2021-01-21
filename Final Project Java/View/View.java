package View;

import Model.Category;
import Model.CostItem;
import Model.Currency;
import ViewModel.IViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


/*
 * View class to implement the IView interface.
 *
 * @params IViewModel vm
 *         ApplicationUI ui
 * @Methods setViewModel() - setting the viewmodel data member
 *          View() - To initiate all data members and start the program/
 */
public class View implements IView {

    private IViewModel vm;
    private ApplicationUI ui;

    @Override
    public void setViewModel(IViewModel vm) {
        this.vm = vm;
    }

    public View() {

        SwingUtilities.invokeLater(() -> {
            View.this.ui = new ApplicationUI();
            View.this.ui.start();
        });
    }

    /*
     * This inner class implements the specific functionality of the GUI
     * provide to the view class the required functions to implement the the IView contract
     *
     * @params  frame - to set the frame of the application
     *          current - to hold the current performance of the screen displayed
     *          mainPanel -  functionality of the main panel
     *          addCostPanel - functionality of the add cost panel
     *          addCategoryPanel - functionality of the add category panel
     *          dateChoosePanel - functionality of the date choose category panel
     *@Methods  ApplicationUI() - Constructor to initiate all class panels that will be rendered.
     *          displayMainMenu()  - application method  to show the home screen
     *          replaceScreen() - application method to render different screens in the frame
     *          cleanTextInputs() - application method to clean all the inputs from the different screens
     *
     */

    public class ApplicationUI {

        //General frame component
        private JFrame frame;
        private JPanel current;
        private MainPanel mainPanel;
        private AddCostPanel addCostPanel;
        private AddCategoryPanel addCategoryPanel;
        private DatesChoosePanel dateChoosePanel;
        private TablePanel tablePanel;

        public ApplicationUI() {

            // starting the panels inner class data members
            mainPanel = new MainPanel();
            addCostPanel = new AddCostPanel();
            addCategoryPanel = new AddCategoryPanel();
            dateChoosePanel = new DatesChoosePanel();
            tablePanel = new TablePanel();

            //general common components setup
            frame = new JFrame("CostManager");
            frame.setSize(900, 900);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    frame = null;
                    System.exit(0);
                }
            });
        }

        /*
         * This inner class implements the main panel (window) by extending the JPanel swing class
         *
         * @Methods  MainPanel() - class C'tor to initiate all swing properties
         *                         and define the class action listenrs for each functional button
         *
         */

        public class MainPanel extends JPanel {

            private JButton btAddCostItem;
            private JButton btAddCategory;
            private JButton btDisplayPie;
            private JButton btDisplayTable;

            public MainPanel() {

                setBorder(new EmptyBorder(10, 10, 10, 10));
                setLayout(new GridBagLayout());

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.anchor = GridBagConstraints.NORTH;
                gbc.insets = new Insets(5, 5, 5, 5);

                add(new JLabel("<html><h1><strong><i>Welcome to cost manager</i></strong></h1><hr></html>"), gbc);

                gbc.anchor = GridBagConstraints.CENTER;
                gbc.fill = GridBagConstraints.BOTH;

                btAddCostItem = new JButton("Add Cost");
                btAddCategory = new JButton("Add Category");
                btDisplayPie = new JButton("Display Pie Chart");
                btDisplayTable = new JButton("Display Table");

                JPanel buttons = new JPanel(new GridBagLayout());

                buttons.add(btAddCostItem, gbc);
                buttons.add(btAddCategory, gbc);
                buttons.add(btDisplayPie, gbc);
                buttons.add(btDisplayTable, gbc);

                btAddCostItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.addCostPanel.cleanInputs();
                        ApplicationUI.this.addCostPanel.updateCategories();
                        ApplicationUI.this.replaceScreen(ApplicationUI.this.addCostPanel);
                    }
                });

                btAddCategory.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.addCategoryPanel.cleanInputs();
                        ApplicationUI.this.replaceScreen(ApplicationUI.this.addCategoryPanel);
                    }
                });

                btDisplayTable.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.dateChoosePanel.cleanInputs();
                        ApplicationUI.this.dateChoosePanel.updateButton("table");
                        ApplicationUI.this.replaceScreen(ApplicationUI.this.dateChoosePanel);
                    }
                });

                btDisplayPie.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.dateChoosePanel.cleanInputs();
                        ApplicationUI.this.dateChoosePanel.updateButton("pie chart");
                        ApplicationUI.this.replaceScreen(ApplicationUI.this.dateChoosePanel);
                    }
                });

                gbc.weighty = 1;
                add(buttons, gbc);
            }

        }

        /*
         * This inner class implements the add cost panel by extending JPanel
         * it shows a form to fill that its details will be sent to the model as new entry in DB.
         *
         * @Methods  AddCostPanel() - class C'tor to initiate all swing items.
         *           cleanInputs() - A method to clean all previous inputs in text boxes.
         *           updateCategories() - A method to add a new category added to the Combo box when added durring app run time.s
         *
         */

        public class AddCostPanel extends JPanel {

            private JComboBox cbChooseCategory;
            private JComboBox cbChooseCurrency;
            private TextField tfEnterAmount;
            private TextField tfEnterDescription;
            private JFormattedTextField tfDate;
            private JButton btSubmit;
            private JLabel jlEnterAmount;
            private JLabel jlEnterDescription;
            private JLabel jlDate;
            private JButton btBackToMainMenu;
            private JLabel jlHeader;
            private Font myFont;

            private String[] categoriesOptions;
            private DefaultComboBoxModel<String> defaultCBmodel;

            private String[] currencyOptions;


            public AddCostPanel() {
                setBorder(new EmptyBorder(10, 10, 10, 10));
                setLayout(new GridBagLayout());

                GridBagConstraints gbc = new GridBagConstraints();

                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.insets = new Insets(5, 5, 5, 5);

                JPanel homePanel = new JPanel(new GridBagLayout());
                btBackToMainMenu = new JButton("Home");
                gbc.weightx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                homePanel.add(btBackToMainMenu, gbc);
                add(homePanel, gbc);

                btBackToMainMenu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.replaceScreen(ApplicationUI.this.mainPanel);
                    }
                });

                gbc.weightx = 0;

                JPanel headerPanel = new JPanel(new GridBagLayout());
                jlHeader = new JLabel("<html><h1><strong><i>Add new cost item</i></strong></h1><hr></html>");
                gbc.anchor = GridBagConstraints.CENTER;
                headerPanel.add(jlHeader, gbc);
                add(headerPanel, gbc);

                gbc.anchor = GridBagConstraints.CENTER;
                gbc.fill = GridBagConstraints.BOTH;

                categoriesOptions = View.this.vm.getCategories();
                defaultCBmodel = new DefaultComboBoxModel<>(categoriesOptions);
                cbChooseCategory = new JComboBox(defaultCBmodel);
                cbChooseCategory.setBackground(Color.white);
                cbChooseCategory.setRenderer(new MyComboBoxRenderer("Category"));
                cbChooseCategory.setSelectedIndex(-1); //By default it selects first item, we don't want any selection


                currencyOptions = View.this.vm.getCurrencies();
                cbChooseCurrency = new JComboBox(currencyOptions);
                cbChooseCurrency.setBackground(Color.white);
                cbChooseCurrency.setRenderer(new MyComboBoxRenderer("Currency"));
                cbChooseCurrency.setSelectedIndex(-1);

                myFont = new Font("Default", Font.PLAIN, 12);
                jlEnterAmount = new JLabel("Enter Amount");
                tfEnterAmount = new TextField(5);
                tfEnterAmount.setFont(myFont);
                jlEnterDescription = new JLabel("Enter Description");
                tfEnterDescription = new TextField(20);
                tfEnterDescription.setFont(myFont);


                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                jlDate = new JLabel("Enter date (yyyy-MM-dd):");
                tfDate = new JFormattedTextField(format);
                tfDate.setFont(myFont);
                btSubmit = new JButton("Submit");


                JPanel form = new JPanel(new GridBagLayout());
                JPanel submit = new JPanel(new GridBagLayout());

                form.add(cbChooseCategory, gbc);
                form.add(cbChooseCurrency, gbc);
                form.add(jlEnterAmount, gbc);
                form.add(tfEnterAmount, gbc);
                form.add(jlEnterDescription, gbc);
                form.add(tfEnterDescription, gbc);
                form.add(jlDate, gbc);
                form.add(tfDate, gbc);
                submit.add(btSubmit, gbc);
                submit.setAlignmentX(RIGHT_ALIGNMENT);


                gbc.weighty = 1;
                add(form, gbc);
                add(submit, gbc);
            }

            public void cleanInputs() {
                tfEnterAmount.setText("");
                tfEnterDescription.setText("");
                cbChooseCategory.setSelectedIndex(-1);
                cbChooseCurrency.setSelectedIndex(-1);
            }

            public void updateCategories() {
                this.categoriesOptions = View.this.vm.getCategories();

                this.defaultCBmodel.removeAllElements();
                for (String name : this.categoriesOptions) {
                    this.defaultCBmodel.addElement(name);
                }

                this.cbChooseCategory.setSelectedIndex(-1);
            }

            /*
             * Inner class to control the combo box of the category combo box element
             *
             * @Methods MyComboBoxRenderer(String title) - constructor, receive  a parameter to set the combo box title (will be shown when combo box closed)
             *          getListCellRendererComponent() - ListCellRenderer methods override to implement adding data to the combo box
             */
            class MyComboBoxRenderer extends JLabel implements ListCellRenderer {
                private String _title;

                public MyComboBoxRenderer(String title) {
                    _title = title;
                }

                @Override
                public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
                    if (index == -1 && value == null) setText(_title);
                    else setText(value.toString());
                    return this;
                }
            }

        }

        /*
         * This inner class implements the add category panel by extending JPanel
         * it shows a form to fill that its details will be sent to the model as new entry in DB.
         *
         * @Methods  AddCategoryPanel() - class C'tor to initiate all swing items.
         *           cleanInputs() - A method to clean all previous inputs in text boxes.
         *
         */

        public class AddCategoryPanel extends JPanel {

            private TextField tfCategoryName;
            private JLabel jlEnterCategory;
            private JButton btSubmit;
            private JButton btBackToMainMenu;
            private JLabel jlHeader;
            private Font myFont;


            public AddCategoryPanel() {
                setBorder(new EmptyBorder(10, 10, 10, 10));
                setLayout(new GridBagLayout());

                GridBagConstraints gbc = new GridBagConstraints();

                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.insets = new Insets(5, 5, 5, 5);

                JPanel homePanel = new JPanel(new GridBagLayout());
                btBackToMainMenu = new JButton("Home");
                gbc.weightx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                homePanel.add(btBackToMainMenu, gbc);
                add(homePanel, gbc);

                btBackToMainMenu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.replaceScreen(ApplicationUI.this.mainPanel);
                    }
                });

                gbc.weightx = 0;

                JPanel headerPanel = new JPanel(new GridBagLayout());
                jlHeader = new JLabel("<html><h1><strong><i>Add new cost item</i></strong></h1><hr></html>");
                gbc.anchor = GridBagConstraints.CENTER;
                headerPanel.add(jlHeader, gbc);
                add(headerPanel, gbc);

                myFont = new Font("Default", Font.PLAIN, 12);
                tfCategoryName = new TextField("", 10);
                tfCategoryName.setFont(myFont);

                jlEnterCategory = new JLabel("<html><h3><strong><i>Enter new category:</i></strong></h3></html>");


                btSubmit = new JButton("Submit");

                JPanel form = new JPanel(new GridBagLayout());
                JPanel submit = new JPanel(new GridBagLayout());


                form.add(jlEnterCategory, gbc);
                form.add(tfCategoryName, gbc);
                submit.add(btSubmit, gbc);

                btSubmit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String categoryName = tfCategoryName.getText();
                        Category category = new Category(categoryName);
                        View.this.vm.addCategory(category);
                        ApplicationUI.this.replaceScreen(ApplicationUI.this.mainPanel);
                    }
                });


                gbc.weighty = 1;
                add(form, gbc);
                add(submit, gbc);
            }

            public void cleanInputs() {
                tfCategoryName.setText("");
            }

        }

        /*
         * This inner class implements the the date choosing panel by extending JPanel
         * it shows a form to fill that its details will be sent as quirey to the DB and will be implemented.
         * this panel is used once for the sending dates to render the date panel and once for the pie chart panel.
         *
         * @Methods DateChoosePanel() - class C'tor to initiate all swing items.
         *          cleanInputs() - A method to clean all previous inputs in text boxes.
         *          updateButton() - because this class is used to pass data for the table panel once and once for the pie chart
         *                           the action listner that renders this panel will call first this method to set a different button each time.
         *
         */

        class DatesChoosePanel extends JPanel {

            private JLabel jlHeader;
            private JFormattedTextField tfFromDate;
            private JLabel jlFromDate;
            private JFormattedTextField tfToDate;
            private JLabel jlToDate;
            private JButton btSubmit;
            private JButton btBackToMainMenu;

            private String buttonName;

            public DatesChoosePanel() {
                setBorder(new EmptyBorder(10, 10, 10, 10));
                setLayout(new GridBagLayout());

                GridBagConstraints gbc = new GridBagConstraints();

                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.insets = new Insets(5, 5, 5, 5);

                JPanel homePanel = new JPanel(new GridBagLayout());
                btBackToMainMenu = new JButton("Home");
                gbc.weightx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                homePanel.add(btBackToMainMenu, gbc);
                add(homePanel, gbc);

                btBackToMainMenu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.replaceScreen(ApplicationUI.this.mainPanel);
                    }
                });

                gbc.weightx = 0;

                JPanel headerPanel = new JPanel(new GridBagLayout());
                jlHeader = new JLabel("<html><h1><strong><i>Choose dates</i></strong></h1><hr></html>");
                gbc.anchor = GridBagConstraints.CENTER;
                headerPanel.add(jlHeader, gbc);
                add(headerPanel, gbc);

                JPanel form = new JPanel(new GridBagLayout());
                JPanel submit = new JPanel(new GridBagLayout());

                jlFromDate = new JLabel("<html><h3><strong><i>From date:</i></strong></h3></html>");
                jlToDate = new JLabel("<html><h3><strong><i>To date:</i></strong></h3></html>");

                Font myFont = new Font("Default", Font.PLAIN, 12);

                DateFormat format = new SimpleDateFormat("yyyy-MM");
                tfFromDate = new JFormattedTextField(format);
                tfToDate = new JFormattedTextField(format);
                tfFromDate.setFont(myFont);
                tfToDate.setFont(myFont);

                btSubmit = new JButton("Submit");

                gbc.fill = GridBagConstraints.HORIZONTAL;

                tfFromDate.setColumns(10);
                tfToDate.setColumns(10);
                form.add(jlFromDate, gbc);
                form.add(tfFromDate, gbc);
                form.add(jlToDate, gbc);
                form.add(tfToDate, gbc);
                submit.add(btSubmit, gbc);

                btSubmit.addActionListener(e -> {
                    if (buttonName.equals("table")) {
                        //When ViewModel will be implemented, the tableData will be gained through it.
                        //At the moment the data is fake and only for View example.
                        String[][] tableData = {
                                {"This", "data", "is", "only", "for example"},
                                {"2019-12", "Food", "780", "ILS", "Rami Levi"},
                                {"2019-12", "Food", "780", "ILS", "Rami Levi"},
                                {"2019-12", "Food", "780", "ILS", "Rami Levi"},
                                {"2019-12", "Food", "780", "ILS", "Rami Levi"},
                                {"2019-12", "Food", "780", "ILS", "Rami Levi"},
                                {"2019-12", "Food", "780", "ILS", "Rami Levi"},
                                {"2019-12", "Food", "780", "ILS", "Rami Levi"},
                                {"2019-12", "Food", "780", "ILS", "Rami Levi"},
                                {"2019-12", "Food", "780", "ILS", "Rami Levi"},
                                {"2019-12", "Food", "780", "ILS", "Rami Levi"},
                                {"2019-12", "Food", "780", "ILS", "Rami Levi"},
                                {"2019-12", "Food", "780", "ILS", "Rami Levi"},
                                {"2019-12", "Food", "780", "ILS", "Rami Levi"},
                                {"2019-12", "Food", "780", "ILS", "Rami Levi"},
                                {"2019-12", "Food", "780", "ILS", "Rami Levi"},
                                {"2020-01", "Food", "600", "ILS", "Rami Levi"},
                                {"2020-02", "Entrainment", "120", "ILS", "Movies"},
                                {"2020-03", "Food", "500", "ILS", "Rami Levi"},
                                {"2020-04", "Food", "580", "ILS", "Rami Levi"}
                        };
                        ApplicationUI.this.tablePanel.updateTableData(tableData);
                        ApplicationUI.this.replaceScreen(ApplicationUI.this.tablePanel);
                    } else if (buttonName.equals("pie chart")) {
                        //replace to pie char screen
                    }
                });

                gbc.weighty = 1;
                add(form, gbc);
                add(submit, gbc);
            }

            public void updateButton(String str) {
                this.buttonName = str;
                btSubmit.setText("Get " + str);
            }

            public void cleanInputs() {
                tfToDate.setText("");
                tfFromDate.setText("");
            }

        }

        /*
         * This inner class implements the the date choosing panel by extending JPanel
         * it shows a form to fill that its details will be sent as quirey to the DB and will be implemented.
         * this panel is used once for the sending dates to render the date panel and once for the pie chart panel.
         *
         * @Methods TablePanel() - class C'tor to initiate all swing items.
         *          updateTableInputs() - This method will be called each time before this panel is rendered to update the table due to the last query sent to model.
         *
         */

        class TablePanel extends JPanel {
            JPanel table;
            GridBagConstraints gbc;
            JScrollPane scrolledTable;
            private JButton btBackToMainMenu;
            private JLabel jlHeader;
            private JTable tableCosts;
            String[] colNames = {"Date", "Category", "Amount", "Currency", "Description"};

            public TablePanel() {
                setBorder(new EmptyBorder(10, 10, 10, 10));
                setLayout(new GridBagLayout());

                gbc = new GridBagConstraints();

                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.insets = new Insets(5, 5, 5, 5);

                JPanel homePanel = new JPanel(new GridBagLayout());
                btBackToMainMenu = new JButton("Home");
                gbc.weightx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                homePanel.add(btBackToMainMenu, gbc);
                add(homePanel, gbc);

                btBackToMainMenu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.replaceScreen(ApplicationUI.this.mainPanel);
                    }
                });

                gbc.weightx = 0;

                JPanel headerPanel = new JPanel(new GridBagLayout());
                jlHeader = new JLabel("<html><h1><strong><i>Costs table</i></strong></h1><hr></html>");
                gbc.anchor = GridBagConstraints.CENTER;
                headerPanel.add(jlHeader, gbc);
                add(headerPanel, gbc);

                table = new JPanel(new GridBagLayout());

                gbc.fill = GridBagConstraints.BOTH;

                gbc.weighty = 1;
                add(table, gbc);
                gbc.weighty = 0;
            }

            public void updateTableData(String[][] data) {
                tableCosts = new JTable(data, this.colNames);
                tableCosts.setPreferredScrollableViewportSize(new Dimension(600, 300));
                tableCosts.setFillsViewportHeight(true);
                scrolledTable = new JScrollPane(tableCosts);
                table.removeAll();
                table.add(scrolledTable, gbc);
            }
        }


        public void displayMainMenu() {
            this.current = mainPanel;
            frame.getContentPane().add(this.current);
            frame.setVisible(true);
        }


        public void replaceScreen(JPanel next) {
            frame.remove(this.current);
            frame.repaint();
            this.current = next;
            frame.add(this.current);
            frame.setVisible(true);
        }

        public void start() {
            displayMainMenu();
        }
    }
}

