
// ΑΘΑΝΑΣΙΟΣ ΜΗΔΕΛΙΑΣ 185229 - ΗΛΙΑΣ ΒΑΡΒΑΡΟΥΣΗΣ 2020019

//import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarculaLaf;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

public class NewJFrame extends javax.swing.JFrame {
    javax.swing.JTextArea reader;
    DefaultTableModel orderModel;
    DefaultTableModel menuModel;
    FileReader fileReader;
    FileWriter fileWriter;
    ArrayList<DefaultListModel> tablesModelList = new ArrayList<>();
    Map<String, DefaultTableModel> test = new HashMap<>();
    DefaultListModel<String> areasModel = new DefaultListModel<>();
    String folderPath = System.getProperty("user.dir");
    String[] header = new String[] {"ΠΡΟΙΟΝ", "ΤΙΜΗ"};
    
    public NewJFrame() {
        menuModel = new DefaultTableModel(0,0) {
            boolean[] canEdit = new boolean[]{
                false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        
        menuModel.setColumnIdentifiers(header);
        
        initComponents();
        menuTable.setModel(menuModel);
        areasJList.setModel(areasModel);
        reader = new JTextArea();
        initAreas();
        initTables();
        
        
        
        orderModel = new DefaultTableModel(0, 0) {
            boolean[] canEdit = new boolean[]{
                false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        
        orderModel.setColumnIdentifiers(header);
        loadMenuFile();
        
        
        orderTable.setModel(orderModel);
        tableOrderButton.setEnabled(false);
        
    }
    
    private void addTables(String[] tablesArea) {
        DefaultListModel<String> tablesModel = new DefaultListModel<>();
        String[] header = new String[] {"ΠΡΟΙΟΝ", "ΤΙΜΗ"};
        for (String table : tablesArea) {             
            tablesModel.addElement(table);
            DefaultTableModel a = new DefaultTableModel(0, 0) {
                boolean[] canEdit = new boolean[]{
                    false, false
                };

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };
            a.setColumnIdentifiers(header);
            test.put(table, a);
        }
        tablesModelList.add(tablesModel);
    }
    
    private void addAreas(String[] areas) {
        for (String area : areas) {
            areasModel.addElement(area);       
        }
    }
    
    private void initAreas() {
        try {
            fileReader = new FileReader(folderPath + "/src/areas.txt");
            reader.read(fileReader, null);
            System.out.println(folderPath + "|/src/areas.txt" + "\n" + reader.getText());
            fileReader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        String str = reader.getText();
        String[] strsplit = str.split("\n+");
        addAreas(strsplit);
        reader.setText("");
    }
    
    private void initTables() {
        try {
            fileReader = new FileReader(folderPath + "/src/tables.txt");
            reader.read(fileReader, null);
            fileReader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        String str = reader.getText();
        String[] strsplit = str.split("\n+");
        String[] strsplit1;
        for (String string : strsplit) {
            strsplit1 = string.split(",");
            addTables(strsplit1);
        }
        reader.setText("");
    }
    
    private void modifyAreasFile() {
        try {
            fileWriter = new FileWriter(folderPath + "/src/areas.txt");
        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        StringBuilder areasFile = new StringBuilder();
        for (int i = 0; i < areasModel.getSize(); i++) {
            areasFile.append(areasModel.getElementAt(i)).append("\n");
        }
        System.out.println(areasFile.toString());
        reader.setText(areasFile.toString());
        try {
            reader.write(fileWriter);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void modifyTablesFile() {
        try {
            fileWriter = new FileWriter(folderPath + "/src/tables.txt");
        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        StringBuilder tablesFile = new StringBuilder();
        for (DefaultListModel a : tablesModelList) {
            for (int i = 0; i < a.size(); i++) {
                if (i != a.size() - 1)
                    tablesFile.append(a.get(i)).append(",");              
                else
                    tablesFile.append(a.get(i)).append("\n");
            }
        }
        System.out.println(tablesFile);
        reader.setText(tablesFile.toString());
        try {
            reader.write(fileWriter);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadMenuFile() {
        try {
            fileReader = new FileReader(folderPath + "/src/menu.txt");
            reader.read(fileReader, null);
            fileReader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        String str = reader.getText();
        String[] strsplit = str.split("\n");
        for (String listing : strsplit) {
            String[] strsplit1 = listing.split(",");
            menuModel.addRow(new Object[] {strsplit1[0], Double.parseDouble(strsplit1[1])});
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuFrame = new javax.swing.JFrame();
        jPanel5 = new javax.swing.JPanel();
        addMenuItemButton = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        menuTable = new javax.swing.JTable();
        closeMenuDialogButton = new javax.swing.JButton();
        menuItemQuantity = new javax.swing.JSpinner();
        orderFrame = new javax.swing.JFrame();
        jPanel4 = new javax.swing.JPanel();
        valueTextField = new javax.swing.JTextField();
        addItemButton = new javax.swing.JButton();
        removeItemButton = new javax.swing.JButton();
        cancelOrderButton = new javax.swing.JButton();
        printOrderButton = new javax.swing.JButton();
        valueLabel = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areasJList = new javax.swing.JList<>();
        addAreaButton = new javax.swing.JButton();
        removeAreaButton = new javax.swing.JButton();
        newAreaTextField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablesJList = new javax.swing.JList<>();
        addTableButton = new javax.swing.JButton();
        removeTableButton = new javax.swing.JButton();
        newTableTextField = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        tableOrderButton = new javax.swing.JButton();

        menuFrame.setTitle("Menu");

        addMenuItemButton.setText("ΠΡΟΣΘΗΚΗ");
        addMenuItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMenuItemButtonActionPerformed(evt);
            }
        });

        jScrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder("Κατάλογος"));

        menuTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"φραπες",  new Double(2.0)},
                {"νες",  new Double(2.0)},
                {"φρεντο",  new Double(3.0)},
                {"καπουτσινο",  new Double(3.0)}
            },
            new String [] {
                "ΠΡΟΪΟΝ", "ΑΞΙΑ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        menuTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        menuTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(menuTable);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        closeMenuDialogButton.setText("ΑΚΥΡΩΣΗ");
        closeMenuDialogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeMenuDialogButtonActionPerformed(evt);
            }
        });

        menuItemQuantity.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(addMenuItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeMenuDialogButton, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(menuItemQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuItemQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addMenuItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeMenuDialogButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout menuFrameLayout = new javax.swing.GroupLayout(menuFrame.getContentPane());
        menuFrame.getContentPane().setLayout(menuFrameLayout);
        menuFrameLayout.setHorizontalGroup(
            menuFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuFrameLayout.setVerticalGroup(
            menuFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        orderFrame.setTitle("Order");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Παραγγελία"));

        addItemButton.setText("Προσθήκη");
        addItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemButtonActionPerformed(evt);
            }
        });

        removeItemButton.setText("Αφαίρεση");
        removeItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeItemButtonActionPerformed(evt);
            }
        });

        cancelOrderButton.setText("Ακύρωση");
        cancelOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelOrderButtonActionPerformed(evt);
            }
        });

        printOrderButton.setText("Εκτύπωση");
        printOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printOrderButtonActionPerformed(evt);
            }
        });

        valueLabel.setText("Αξία:");

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ΠΡΟΙΟΝ", "ΤΙΜΗ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(orderTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(valueLabel)
                        .addGap(18, 18, 18)
                        .addComponent(valueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(printOrderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addItemButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeItemButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelOrderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(addItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(removeItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cancelOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(107, 107, 107)
                        .addComponent(printOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(valueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(valueLabel))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout orderFrameLayout = new javax.swing.GroupLayout(orderFrame.getContentPane());
        orderFrame.getContentPane().setLayout(orderFrameLayout);
        orderFrameLayout.setHorizontalGroup(
            orderFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        orderFrameLayout.setVerticalGroup(
            orderFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GUI");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Χώροι"));

        areasJList.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        areasJList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                areasJListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(areasJList);

        addAreaButton.setText("Προσθήκη Χώρου");
        addAreaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAreaButtonActionPerformed(evt);
            }
        });

        removeAreaButton.setText("Αφαίρεση Χώρου");
        removeAreaButton.setToolTipText("Αφαίρεση του επιλεγμένου χώρου από την λίστα.");
        removeAreaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAreaButtonActionPerformed(evt);
            }
        });

        newAreaTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        newAreaTextField.setText("Όνομα νέου χώρου");
        newAreaTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                newAreaTextFieldFocusLost(evt);
            }
        });
        newAreaTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newAreaTextFieldMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addAreaButton, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                    .addComponent(removeAreaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(newAreaTextField))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newAreaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addAreaButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(removeAreaButton)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Τραπέζια Χώρου"));

        tablesJList.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablesJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablesJList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                tablesJListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(tablesJList);

        addTableButton.setText("Προσθήκη Τραπεζιού");
        addTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTableButtonActionPerformed(evt);
            }
        });

        removeTableButton.setText("Αφαίρεση Τραπεζιού");
        removeTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeTableButtonActionPerformed(evt);
            }
        });

        newTableTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        newTableTextField.setText("Όνομα νέου τραπεζιού");
        newTableTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                newTableTextFieldFocusLost(evt);
            }
        });
        newTableTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newTableTextFieldMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(addTableButton, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                    .addComponent(removeTableButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newTableTextField))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newTableTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addTableButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(removeTableButton)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Πληροφορίες Επιλεγμένου Τραπεζιού"));

        tableOrderButton.setText("Παραγγελία Τραπεζιού");
        tableOrderButton.setToolTipText("");
        tableOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableOrderButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableOrderButton, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void areasJListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_areasJListValueChanged
        try {
            tablesJList.setModel(tablesModelList.get(areasJList.getSelectedIndex()));
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.toString() + " | Method: areasJListValueChanged");
            tablesJList.setModel(new DefaultListModel<>()); //adds an empty model
        }
    }//GEN-LAST:event_areasJListValueChanged

    private void removeAreaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAreaButtonActionPerformed
        if (areasJList.getSelectedIndex() < 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Επέλεξε τον χώρο που θες να αφαιρέσεις.","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            tablesModelList.remove(areasJList.getSelectedIndex());
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e + " | Area has no tables to remove");
        }
        areasModel.remove(areasJList.getSelectedIndex());
        modifyAreasFile();
        modifyTablesFile();
    }//GEN-LAST:event_removeAreaButtonActionPerformed

    private void removeTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeTableButtonActionPerformed
        if (tablesJList.getSelectedIndex() < 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Επέλεξε το τραπέζι που θες να αφαιρέσεις.","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            test.remove(tablesJList.getSelectedValue());
            tablesModelList.get(areasJList.getSelectedIndex()).remove(tablesJList.getSelectedIndex());
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.toString() + " | Method: removeTableJButtonActionPerformed");
        }
        modifyTablesFile();
    }//GEN-LAST:event_removeTableButtonActionPerformed

    private void tablesJListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_tablesJListValueChanged
        if (tablesJList.getSelectedIndex() == -1) { // Handles case where user attempts to add an item to an order without having selected a table.
            tableOrderButton.setEnabled(false);
        } else {
            tableOrderButton.setEnabled(true);
            try {
                orderTable.setModel(test.get(tablesJList.getSelectedValue()));
            } catch (IndexOutOfBoundsException e) {
                System.err.println(e.toString() + " | Method: tablesJListValueChanged");
            }

            // updates order value based on selected table
            double sum = 0;
            DefaultTableModel a = test.get(tablesJList.getSelectedValue());
            for (int i = 0; i < a.getRowCount(); i++) {
                sum += (Double) a.getValueAt(i, 1);
            }
            valueTextField.setText(String.format("%.2f", sum));
        }               
    }//GEN-LAST:event_tablesJListValueChanged

    private void newAreaTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newAreaTextFieldMouseClicked
        if (newAreaTextField.getText().equals("Όνομα νέου χώρου")) {
            newAreaTextField.setText("");
        }
    }//GEN-LAST:event_newAreaTextFieldMouseClicked

    private void newAreaTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_newAreaTextFieldFocusLost
        if (newAreaTextField.getText().equals("")) {
            newAreaTextField.setText("Όνομα νέου χώρου");
        }
    }//GEN-LAST:event_newAreaTextFieldFocusLost

    private void addAreaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAreaButtonActionPerformed
        if (newAreaTextField.getText().equals("Όνομα νέου χώρου")) {
            javax.swing.JOptionPane.showMessageDialog(null, "Συμπλήρωσε το όνομα του χώρου που θες να προσθέσεις.","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String[] newArea = {newAreaTextField.getText()};
        addAreas(newArea);
        modifyAreasFile();
        addTables(new String[] {});
        modifyTablesFile();
        newAreaTextField.setText("Όνομα νέου χώρου");
    }//GEN-LAST:event_addAreaButtonActionPerformed

    private void newTableTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_newTableTextFieldFocusLost
        if (newTableTextField.getText().equals("")) {
            newTableTextField.setText("Όνομα νέου τραπεζιού");
        }
    }//GEN-LAST:event_newTableTextFieldFocusLost

    private void newTableTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newTableTextFieldMouseClicked
        if (newTableTextField.getText().equals("Όνομα νέου τραπεζιού")) {
            newTableTextField.setText("");
        }
    }//GEN-LAST:event_newTableTextFieldMouseClicked

    private void addTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTableButtonActionPerformed
        if (newTableTextField.getText().equals("Όνομα νέου τραπεζιού")) {
            javax.swing.JOptionPane.showMessageDialog(null, "Συμπλήρωσε το όνομα του τραπεζιού που θες να προσθέσεις.","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (test.containsKey(newTableTextField.getText())) {
            javax.swing.JOptionPane.showMessageDialog(null, "Υπάρχει ήδη τραπέζι με αυτό το όνομα, χρησιμοποίησε κάποιο διαφορετικό όνομα.","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (areasJList.getSelectedIndex() >= tablesModelList.size()) { // Handles case where adding a table to an area with zero tables leads to indexOutofBounds
            String[] newTable = {newTableTextField.getText()};
            addTables(newTable);
            tablesJList.setModel(tablesModelList.get(areasJList.getSelectedIndex()));
        } else {
            tablesModelList.get(areasJList.getSelectedIndex()).addElement(newTableTextField.getText());
            DefaultTableModel a = new DefaultTableModel(0, 0) {
                boolean[] canEdit = new boolean[]{
                    false, false
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };
            a.setColumnIdentifiers(header);
            test.put(newTableTextField.getText(), a);
        }
        
        modifyTablesFile();
        
        newTableTextField.setText("Όνομα νέου τραπεζιού");
    }//GEN-LAST:event_addTableButtonActionPerformed

    private void addMenuItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMenuItemButtonActionPerformed
        int quantity = (Integer) menuItemQuantity.getValue();
        for (int i = 0; i < quantity; i++) {
            test.get(tablesJList.getSelectedValue()).addRow(new Object[] { menuTable.getValueAt(menuTable.getSelectedRow(), 0), menuTable.getValueAt(menuTable.getSelectedRow(), 1)});
            System.out.println(menuTable.getValueAt(menuTable.getSelectedRow(), 1));
        } 
        orderTable.setModel(test.get(tablesJList.getSelectedValue()));
        double sum = 0;
        DefaultTableModel a = test.get(tablesJList.getSelectedValue());
        for (int i = 0; i < a.getRowCount(); i++) {
            sum += (Double) a.getValueAt(i, 1);
        }
        valueTextField.setText(String.format("%.2f", sum)); // round to 2 decimal digits
        menuItemQuantity.setValue(1); // resets spinner when an item is added
    }//GEN-LAST:event_addMenuItemButtonActionPerformed

    private void closeMenuDialogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeMenuDialogButtonActionPerformed
        menuFrame.setVisible(false);
        menuItemQuantity.setValue(1); // resets spinner when closing the window
    }//GEN-LAST:event_closeMenuDialogButtonActionPerformed

    private void addItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItemButtonActionPerformed
        menuItemQuantity.setValue(1); // resets item quantity spinner
        menuFrame.setVisible(true);
        menuFrame.setSize(850, 600);
    }//GEN-LAST:event_addItemButtonActionPerformed

    private void removeItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeItemButtonActionPerformed
        if (orderTable.getSelectedRow() < 0 && test.get(tablesJList.getSelectedValue()).getRowCount() > 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Δεν έχει επιλεχθεί αντικείμενο στην παραγγελία.", "WARNING", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (test.get(tablesJList.getSelectedValue()).getRowCount() > 0) {
            test.get(tablesJList.getSelectedValue()).removeRow(orderTable.getSelectedRow());
            double sum = 0;
            DefaultTableModel a = test.get(tablesJList.getSelectedValue());
            for (int i = 0; i < a.getRowCount(); i++) {
                sum += (Double) a.getValueAt(i, 1);
            }
            valueTextField.setText(String.format("%.2f", sum)); // round to 2 decimal digits
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Δεν υπάρχουν αντικείμενα στην παραγγελία.","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_removeItemButtonActionPerformed

    private void printOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printOrderButtonActionPerformed
        if (test.get(tablesJList.getSelectedValue()).getRowCount() > 0) {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("1.txt"));
                DefaultTableModel a = test.get(tablesJList.getSelectedValue());

                String receipt;
                receipt = a.getValueAt(0, 0) + "   " + a.getValueAt(0, 1) + "\n";
                out.write(receipt);
                double sum = (Double) a.getValueAt(0, 1);
                for (int i = 1; i < a.getRowCount(); i++) {
                    receipt = a.getValueAt(i, 0) + "   " + a.getValueAt(i, 1) + "\n";
                    out.write(receipt);
                    sum += (Double) a.getValueAt(i, 1);
                }   
                out.write("Σύνολο: " + String.format("%.2f", sum));
                out.flush();
                File ff = new File("1.txt");
                Desktop desktop = Desktop.getDesktop();
                System.out.println(desktop.isSupported(Desktop.Action.PRINT));
                desktop.print(ff);
            } catch (IOException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                javax.swing.JOptionPane.showMessageDialog(null, "Για να εκτελεστεί η εκτύπωση πρέπει το προεπιλεγμένο πρόγραμμα για άνοιγμα αρχείων txt να είναι το notepad.","ERROR",javax.swing.JOptionPane.ERROR_MESSAGE);
            } catch (UnsupportedOperationException e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Η εκτύπωση δεν υποστηρίζεται σε αυτήν την συσκευή","ERROR",javax.swing.JOptionPane.ERROR_MESSAGE);
            } 
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Δεν υπάρχουν αντικείμενα στην παραγγελία.","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_printOrderButtonActionPerformed

    private void tableOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableOrderButtonActionPerformed
        orderFrame.setVisible(true);
        orderFrame.setSize(850, 450);
    }//GEN-LAST:event_tableOrderButtonActionPerformed

    private void cancelOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelOrderButtonActionPerformed
        if (test.get(tablesJList.getSelectedValue()).getRowCount() > 0) {
            DefaultTableModel a = test.get(tablesJList.getSelectedValue());
            int i = a.getRowCount() - 1;
            while (a.getRowCount() != 0) {
                a.removeRow(i);
                i--;
            }
            valueTextField.setText("0,00");
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Δεν υπάρχουν αντικείμενα στην παραγγελία.","WARNING",javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_cancelOrderButtonActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                System.out.println("theme: " + info.getName());
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
            UIManager.put("Button.arc", 999);
            UIManager.put("Component.arc", 999);
            UIManager.put("CheckBox.arc", 999);
            UIManager.put("ProgressBar.arc", 999);
            UIManager.put("TextComponent.arc", 999);
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                System.out.println("theme: " + info.getName());
                if ("Nimbus".equals(info.getName())) {
                    try {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    } catch (ClassNotFoundException ex1) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex1);
                    } catch (InstantiationException ex1) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex1);
                    } catch (IllegalAccessException ex1) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex1);
                    } catch (UnsupportedLookAndFeelException ex1) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    break;
                }
            }
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAreaButton;
    private javax.swing.JButton addItemButton;
    private javax.swing.JButton addMenuItemButton;
    private javax.swing.JButton addTableButton;
    private javax.swing.JList<String> areasJList;
    private javax.swing.JButton cancelOrderButton;
    private javax.swing.JButton closeMenuDialogButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JFrame menuFrame;
    private javax.swing.JSpinner menuItemQuantity;
    private javax.swing.JTable menuTable;
    private javax.swing.JTextField newAreaTextField;
    private javax.swing.JTextField newTableTextField;
    private javax.swing.JFrame orderFrame;
    private javax.swing.JTable orderTable;
    private javax.swing.JButton printOrderButton;
    private javax.swing.JButton removeAreaButton;
    private javax.swing.JButton removeItemButton;
    private javax.swing.JButton removeTableButton;
    private javax.swing.JButton tableOrderButton;
    private javax.swing.JList<String> tablesJList;
    private javax.swing.JLabel valueLabel;
    private javax.swing.JTextField valueTextField;
    // End of variables declaration//GEN-END:variables
}
