package View;

import ViewModel.IViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;


public class View implements IView {

    private IViewModel vm;
    private ApplicationUI ui;

    @Override
    public void setViewModel(IViewModel vm) {
        this.vm = vm;
    }

    public View() {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View.this.ui = new View.ApplicationUI();
                View.this.ui.start();
            }
        });
    }

    public class ApplicationUI {

        //General frame component
        private JFrame frame;
        private JPanel current;
        private MainPanel mainPanel;
        private AddCostPanel addCostPanel;
        private AddCategoryPanel addCategoryPanel;

        public ApplicationUI() {
            mainPanel = new MainPanel();
            addCostPanel = new AddCostPanel();
            addCategoryPanel = new AddCategoryPanel();
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
         * Inner class to implement the main panel of the program
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
                        ApplicationUI.this.replaceScreen(ApplicationUI.this.addCostPanel);
                    }
                });

                btAddCategory.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.replaceScreen(ApplicationUI.this.addCategoryPanel);
                    }
                });

                gbc.weighty = 1;
                add(buttons, gbc);
            }

        }

        /*
         * Inner class to implement the add cost panel of the program
         */

        public class AddCostPanel extends JPanel {

            private JComboBox cbChooseCategory;
            private JComboBox cbChooseCurrency;
            private TextField tfEnterAmount;
            private TextField tfEnterDescription;
            private JButton btSubmit;
            private JLabel jlEnterAmount;
            private JLabel jlEnterDescription;
            private JButton btBackToMainMenu;
            private JLabel jlHeader;
            private Font myFont;


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

                String[] option = {"Shopping", "Food"};
                cbChooseCategory = new JComboBox(option);
                cbChooseCategory.setBackground(Color.white);
                cbChooseCategory.setRenderer(new MyComboBoxRenderer("Category"));
                cbChooseCategory.setSelectedIndex(-1); //By default it selects first item, we don't want any selection

                String[] option2 = {"USD", "NZD", "ILS"};
                cbChooseCurrency = new JComboBox(option2);
                cbChooseCurrency.setBackground(Color.white);
                cbChooseCurrency.setRenderer(new MyComboBoxRenderer("Currency"));
                cbChooseCurrency.setSelectedIndex(-1);

                myFont = new Font("Default", Font.PLAIN, 12 );
                jlEnterAmount = new JLabel("Enter Amount");
                tfEnterAmount = new TextField(5);
                tfEnterAmount.setFont(myFont);
                jlEnterDescription = new JLabel("Enter Description");
                tfEnterDescription = new TextField(20);
                tfEnterDescription.setFont(myFont);

                btSubmit = new JButton("Submit");


                JPanel form = new JPanel(new GridBagLayout());
                JPanel submit = new JPanel(new GridBagLayout());

                form.add(cbChooseCategory, gbc);
                form.add(cbChooseCurrency, gbc);
                form.add(jlEnterAmount, gbc);
                form.add(tfEnterAmount, gbc);
                form.add(jlEnterDescription, gbc);
                form.add(tfEnterDescription, gbc);
                submit.add(btSubmit, gbc);
                submit.setAlignmentX(RIGHT_ALIGNMENT);


                gbc.weighty = 1;
                add(form, gbc);
                add(submit, gbc);
            }

            /*
             * Inner class to control the combo box of the category combo box element
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
         * Inner class to implement the
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

                myFont = new Font("Default", Font.PLAIN, 12 );
                tfCategoryName = new TextField("Shopping..", 10);
                tfCategoryName.setFont(myFont);

                jlEnterCategory = new JLabel("<html><h3><strong><i>Enter new category:</i></strong></h3></html>");


                btSubmit = new JButton("Submit");

                JPanel form = new JPanel(new GridBagLayout());
                JPanel submit = new JPanel(new GridBagLayout());


                form.add(jlEnterCategory, gbc);
                form.add(tfCategoryName, gbc);
                submit.add(btSubmit, gbc);


                gbc.weighty = 1;
                add(form, gbc);
                add(submit, gbc);
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
