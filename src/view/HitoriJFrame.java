package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.DefaultCaret;
import model.check.Checker;
import model.input.input;
import model.Config;
import model.input.FakeMap;
import model.lib.ExcelApp;
import model.lib.TimeThread;
import model.solver.*;

/**
 * 
 * @author DoDuy
 */
public class HitoriJFrame extends JFrame{  
    /** Creates new form Find */
    private final JPanel hitoriPanel;
    private int[][] value;
    private JButton[][] btnCell;
    private int[][] State;
    private int Rows;
    private int Columns;
    private final input input;
    private final Checker check;
    private ASolver solver;
    private boolean BoErr;
    private final int[] listRows;
    private final int[] listCols;
    private Thread thread;
    private JCheckBox[] multiTabSolver;
    
    public HitoriJFrame() {
        initComponents();
        setComboboxItems();
        this.tplMain.setSelectedIndex(Config.TAB_SELECTED);
        setTabMulti();
        setTabCreateMap();
        ImageIcon icon = new ImageIcon(Config.PATH_ICON_GAME);
        Image image = icon.getImage();
        setIconImage(image);
        hitoriPanel = new JPanel();
        pnlBoard.add(hitoriPanel);
        hitoriPanel.setVisible(true);
        hitoriPanel.setBackground(new java.awt.Color(204, 255, 255));
        input = new input();
        check = new Checker();
        BoErr = false;
        btnCheck.setEnabled(false);
        btnSolver.setEnabled(false);
        btnMore.setEnabled(false);
        lblMg.setText("");
        pbPerDone.setVisible(false);
        File folder = new File(Config.PATH_MAP);
        File[] listOfFiles = folder.listFiles();
        listRows = new int[listOfFiles.length];
        listCols = new int[listOfFiles.length];
        int i,a,b;
        for (i = 0; i < listOfFiles.length; i++)
            if (listOfFiles[i].isFile()) {
              String str = listOfFiles[i].getName();
              str = str.substring(0, str.length()-3);
              a = Integer.parseInt(str.substring(0,str.indexOf("x")));
              b = Integer.parseInt(str.substring(str.indexOf("x")+1,str.length()));
              listRows[i] = a;
              listCols[i] = b;
              //System.out.println(a+"-"+b);
            }
        ((DefaultCaret)cmdViewer.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        cmdViewer.setText("number of map file: "+listOfFiles.length);
        //btnNew.doClick();
        thread = null;
    }
    
    private void setTabMulti(){
        pnlSolverSelect.setLayout(new GridLayout(Config.SOLVER_NAME.length,1));
        multiTabSolver = new JCheckBox[Config.SOLVER_NAME.length];
        for(int i = 0; i < Config.SOLVER_NAME.length; i++){
            multiTabSolver[i] = new JCheckBox();
            multiTabSolver[i].addActionListener((ActionEvent evt) -> {
                boolean all = true;
                for(int j = 0; j < Config.SOLVER_NAME.length; j++)
                    if(!multiTabSolver[j].isSelected()) {
                        all = false;
                        break;
                    }
                chbCheckAll.setSelected(all);
            });
            multiTabSolver[i].setText(Config.SOLVER_NAME[i][1]);
            this.pnlSolverSelect.add(multiTabSolver[i]);
        }
        this.spiNCircle.setValue(Config.TAB_MULTI_SELECTED_NCIRCLE);
        this.spiNMap.setValue(Config.TAB_MULTI_SELECTED_NMAP);
        this.spiNSize.setValue(Config.TAB_MULTI_SELECTED_NMAPSIZE);
    }
    
    private void setTabCreateMap(){
        this.spiNrOfMap.setValue(Config.TAB_CREATE_MAP_SELECTED_NMAP);
        this.spiFrPer.setValue(Config.TAB_CREATE_MAP_SELECTED_FROM_PERCENT);
        this.spiToPer.setValue(Config.TAB_CREATE_MAP_SELECTED_TO_PERCENT);
    }
    
    private void setComboboxItems(){
        int i;
        for(i = 0; i < Config.SOLVER_NAME.length; i++)
            cbSolver.addItem(Config.SOLVER_NAME[i][1]);
        cbSolver.setSelectedIndex(Config.SOLVER_SELECTED - 1);
        for(i = Config.MIN_ROWS; i <= Config.MAX_ROWS; i++)
            cbRows.addItem(i);
        cbRows.setSelectedItem(Config.SELECTED_ROWS);
        for(i = Config.MIN_COLUMNS; i <= Config.MAX_COLUMNS; i++)
            cbColumns.addItem(i);
        cbColumns.setSelectedItem(Config.SELECTED_COLUMNS);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scpBoard = new javax.swing.JScrollPane();
        pnlBoard = new javax.swing.JPanel();
        pbPerDone = new javax.swing.JProgressBar();
        scpMain = new javax.swing.JScrollPane();
        pnlMain = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        cbRows = new javax.swing.JComboBox();
        cbColumns = new javax.swing.JComboBox();
        tplMain = new javax.swing.JTabbedPane();
        pnlSingle = new javax.swing.JPanel();
        btnCheck = new javax.swing.JButton();
        lblMg = new javax.swing.JLabel();
        cbSolver = new javax.swing.JComboBox();
        btnSolver = new javax.swing.JButton();
        btnMore = new javax.swing.JButton();
        pnlMulti = new javax.swing.JPanel();
        pnlSolverSelect = new javax.swing.JPanel();
        lblNCircle = new javax.swing.JLabel();
        spiNCircle = new javax.swing.JSpinner();
        lblNMap = new javax.swing.JLabel();
        spiNMap = new javax.swing.JSpinner();
        Do = new javax.swing.JButton();
        chbCheckAll = new javax.swing.JCheckBox();
        lblNSize = new javax.swing.JLabel();
        spiNSize = new javax.swing.JSpinner();
        lblMTmg = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        cmdViewer = new javax.swing.JTextArea();
        pnlCreateMap = new javax.swing.JPanel();
        lblNrOfMap = new javax.swing.JLabel();
        lblFrPer = new javax.swing.JLabel();
        lblToPer = new javax.swing.JLabel();
        btnCreateSaveMap = new javax.swing.JButton();
        lblCMMessage = new javax.swing.JLabel();
        spiNrOfMap = new javax.swing.JSpinner();
        spiFrPer = new javax.swing.JSpinner();
        spiToPer = new javax.swing.JSpinner();
        btnCreate = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hitori");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(25, 25));
        setName("mainFrame"); // NOI18N
        setPreferredSize(new java.awt.Dimension(354, 450));
        setResizable(false);

        scpBoard.setBackground(new java.awt.Color(255, 255, 255));
        scpBoard.setPreferredSize(new java.awt.Dimension(0, 0));

        pnlBoard.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlBoard.setAutoscrolls(true);
        pnlBoard.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlBoard.setPreferredSize(new java.awt.Dimension(0, 0));

        org.jdesktop.layout.GroupLayout pnlBoardLayout = new org.jdesktop.layout.GroupLayout(pnlBoard);
        pnlBoard.setLayout(pnlBoardLayout);
        pnlBoardLayout.setHorizontalGroup(
            pnlBoardLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 15, Short.MAX_VALUE)
        );
        pnlBoardLayout.setVerticalGroup(
            pnlBoardLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 503, Short.MAX_VALUE)
        );

        scpBoard.setViewportView(pnlBoard);

        scpMain.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scpMain.setMinimumSize(new java.awt.Dimension(750, 200));

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));
        pnlMain.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNew.setBackground(new java.awt.Color(204, 255, 204));
        btnNew.setFont(new java.awt.Font("Viner Hand ITC", 1, 24)); // NOI18N
        btnNew.setForeground(new java.awt.Color(0, 0, 51));
        btnNew.setText("new game");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewgameAction(evt);
            }
        });

        tplMain.setBackground(java.awt.SystemColor.controlHighlight);
        tplMain.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tplMain.setPreferredSize(new java.awt.Dimension(318, 100));

        pnlSingle.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.highlight"));

        btnCheck.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.highlight"));
        btnCheck.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        btnCheck.setForeground(java.awt.Color.red);
        btnCheck.setText("Check");
        btnCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckAction(evt);
            }
        });

        lblMg.setBackground(new java.awt.Color(255, 255, 255));
        lblMg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMg.setForeground(new java.awt.Color(255, 0, 51));
        lblMg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        cbSolver.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbSolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSolverActionChange(evt);
            }
        });

        btnSolver.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.highlight"));
        btnSolver.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        btnSolver.setForeground(java.awt.Color.blue);
        btnSolver.setText("Solve");
        btnSolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SolverAction(evt);
            }
        });

        btnMore.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.highlight"));
        btnMore.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        btnMore.setForeground(java.awt.Color.green);
        btnMore.setText("More");
        btnMore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MoreAction(evt);
            }
        });

        org.jdesktop.layout.GroupLayout pnlSingleLayout = new org.jdesktop.layout.GroupLayout(pnlSingle);
        pnlSingle.setLayout(pnlSingleLayout);
        pnlSingleLayout.setHorizontalGroup(
            pnlSingleLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlSingleLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlSingleLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, cbSolver, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, pnlSingleLayout.createSequentialGroup()
                        .add(btnCheck)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnSolver, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnMore, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(lblMg, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSingleLayout.setVerticalGroup(
            pnlSingleLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlSingleLayout.createSequentialGroup()
                .addContainerGap()
                .add(cbSolver, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlSingleLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnCheck, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnSolver, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnMore, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lblMg, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addContainerGap())
        );

        tplMain.addTab("Single", pnlSingle);

        pnlMulti.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.highlight"));

        pnlSolverSelect.setBackground(java.awt.Color.white);

        org.jdesktop.layout.GroupLayout pnlSolverSelectLayout = new org.jdesktop.layout.GroupLayout(pnlSolverSelect);
        pnlSolverSelect.setLayout(pnlSolverSelectLayout);
        pnlSolverSelectLayout.setHorizontalGroup(
            pnlSolverSelectLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        pnlSolverSelectLayout.setVerticalGroup(
            pnlSolverSelectLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 89, Short.MAX_VALUE)
        );

        lblNCircle.setText("Number circle");

        lblNMap.setText("Number map");

        Do.setBackground(java.awt.Color.white);
        Do.setFont(new java.awt.Font("Bodoni MT Black", 1, 24)); // NOI18N
        Do.setForeground(java.awt.Color.blue);
        Do.setText("Test");
        Do.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DoActionPerformed(evt);
            }
        });

        chbCheckAll.setText("Check all");
        chbCheckAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbCheckAllActionPerformed(evt);
            }
        });

        lblNSize.setText("Number mapsize");

        lblMTmg.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        lblMTmg.setText("relax...");

        cmdViewer.setEditable(false);
        cmdViewer.setBackground(java.awt.Color.darkGray);
        cmdViewer.setColumns(20);
        cmdViewer.setFont(new java.awt.Font("Noto Sans", 1, 10)); // NOI18N
        cmdViewer.setForeground(java.awt.Color.white);
        cmdViewer.setRows(5);
        jScrollPane1.setViewportView(cmdViewer);

        org.jdesktop.layout.GroupLayout pnlMultiLayout = new org.jdesktop.layout.GroupLayout(pnlMulti);
        pnlMulti.setLayout(pnlMultiLayout);
        pnlMultiLayout.setHorizontalGroup(
            pnlMultiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlMultiLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlMultiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, lblMTmg, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(pnlSolverSelect, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(pnlMultiLayout.createSequentialGroup()
                        .add(pnlMultiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(pnlMultiLayout.createSequentialGroup()
                                .add(pnlMultiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, lblNMap, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, lblNCircle, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(lblNSize, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(pnlMultiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(spiNSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(pnlMultiLayout.createSequentialGroup()
                                        .add(pnlMultiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(spiNMap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                            .add(spiNCircle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                        .add(18, 18, 18)
                                        .add(Do, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 99, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                            .add(chbCheckAll))
                        .add(0, 16, Short.MAX_VALUE))
                    .add(jScrollPane1))
                .addContainerGap())
        );
        pnlMultiLayout.setVerticalGroup(
            pnlMultiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlMultiLayout.createSequentialGroup()
                .add(chbCheckAll)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlSolverSelect, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(pnlMultiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(Do, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(pnlMultiLayout.createSequentialGroup()
                        .add(pnlMultiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(spiNCircle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(lblNCircle))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(pnlMultiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(spiNMap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(lblNMap))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(pnlMultiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(spiNSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(lblNSize))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(lblMTmg, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 104, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        tplMain.addTab("Multi", pnlMulti);

        lblNrOfMap.setText("Number of Map:");

        lblFrPer.setText("From percent");

        lblToPer.setText("To percent");

        btnCreateSaveMap.setBackground(java.awt.Color.white);
        btnCreateSaveMap.setFont(new java.awt.Font("Noto Sans", 1, 18)); // NOI18N
        btnCreateSaveMap.setForeground(java.awt.Color.blue);
        btnCreateSaveMap.setText("Create and Save");
        btnCreateSaveMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateSaveMapActionPerformed(evt);
            }
        });

        lblCMMessage.setFont(new java.awt.Font("Noto Sans", 1, 18)); // NOI18N
        lblCMMessage.setText("Relax...");
        lblCMMessage.setToolTipText("");

        btnCreate.setText("Create");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout pnlCreateMapLayout = new org.jdesktop.layout.GroupLayout(pnlCreateMap);
        pnlCreateMap.setLayout(pnlCreateMapLayout);
        pnlCreateMapLayout.setHorizontalGroup(
            pnlCreateMapLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlCreateMapLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlCreateMapLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lblCMMessage, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(pnlCreateMapLayout.createSequentialGroup()
                        .add(pnlCreateMapLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(pnlCreateMapLayout.createSequentialGroup()
                                .add(lblNrOfMap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(spiNrOfMap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(pnlCreateMapLayout.createSequentialGroup()
                                .add(lblFrPer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(spiFrPer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(pnlCreateMapLayout.createSequentialGroup()
                                .add(lblToPer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(spiToPer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(pnlCreateMapLayout.createSequentialGroup()
                                .add(41, 41, 41)
                                .add(btnCreateSaveMap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 196, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(pnlCreateMapLayout.createSequentialGroup()
                                .add(72, 72, 72)
                                .add(btnCreate)
                                .add(28, 28, 28)
                                .add(btnSave)))
                        .add(0, 40, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlCreateMapLayout.setVerticalGroup(
            pnlCreateMapLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlCreateMapLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlCreateMapLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblNrOfMap)
                    .add(spiNrOfMap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlCreateMapLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblFrPer)
                    .add(spiFrPer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlCreateMapLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblToPer)
                    .add(spiToPer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlCreateMapLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnCreate)
                    .add(btnSave))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnCreateSaveMap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(lblCMMessage, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addContainerGap())
        );

        tplMain.addTab("CreateMap", pnlCreateMap);

        org.jdesktop.layout.GroupLayout pnlMainLayout = new org.jdesktop.layout.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .add(btnNew, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 159, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbRows, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbColumns, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
            .add(pnlMainLayout.createSequentialGroup()
                .add(tplMain, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnNew, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cbRows, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cbColumns, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tplMain, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
        );

        scpMain.setViewportView(pnlMain);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(scpMain, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 305, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(scpBoard, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                    .add(pbPerDone, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(scpBoard, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(scpMain, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pbPerDone, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newAction(){
        try {
            btnCheck.setEnabled(true);
            btnSolver.setEnabled(true);
            btnMore.setEnabled(false);
            Rows = Integer.parseInt(cbRows.getSelectedItem().toString());
            Columns = Integer.parseInt(cbColumns.getSelectedItem().toString());            
            try {
                value = input.getinput(Rows,Columns);
            }catch (InputMismatchException ex){
                lblMg.setForeground(Color.red);
                lblMg.setText("<html>"+Config.STROUT_MAP_ERROR+"<br>map "
                        +input.getMapIndex()+" in file "+this.Rows+"x"+this.Columns
                        +".ma<html>");
                return;
            }
            hitoriPanel.removeAll();
            lblMg.setForeground(Color.gray);
            lblMg.setText("<html>   Size " + this.Rows+"x"+this.Columns
                    + " -- map --> " + input.getMapIndex()+"/"+input.getMapMax()
                    + "<br><br>"+Config.STROUT_RULE+"<html>");
            CreateBoard();
        } catch (IOException ex) {
            btnCheck.setEnabled(false);
            btnSolver.setEnabled(false);
            btnMore.setEnabled(false);
            lblMg.setForeground(Color.red);
            lblMg.setText("<html>"+Config.STROUT_NO_PUZZLE+Rows+" x "+Columns+"<html>");
        }
    }
    
    private void NewgameAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewgameAction
        newAction();
    }//GEN-LAST:event_NewgameAction

    public void CreateBoard(){
        int i,j;
        State = new int[Rows][Columns];
        int Cellsize;
        if(400>Rows*45) Cellsize = 400/Rows;
        else Cellsize = 45;
        hitoriPanel.setSize(Rows*Cellsize,Columns*Cellsize);
        hitoriPanel.setLayout(new GridLayout(Rows,Columns));
        btnCell=new JButton[Rows][Columns]; //allocate the size of btnCell
        for(i=0; i<Rows; i++){
            for(j=0; j<Columns; j++){
                btnCell[i][j] = new JButton();  
                btnCell[i][j].putClientProperty( "row", i );
                btnCell[i][j].putClientProperty( "column", j );
                hitoriPanel.add(btnCell[i][j]); //adds button to btnCell
                btnCell[i][j].addActionListener((ActionEvent evt) -> {
                    CellAction(evt);
                });
                btnCell[i][j].setBackground(Color.WHITE);
                btnCell[i][j].setFont(new Font("Tahoma", 1, Cellsize/3-3));
                btnCell[i][j].setText(String.valueOf(value[i][j]));
                State[i][j] = 0;
            }
        }
        hitoriPanel.setBounds(5,5, Columns*Cellsize, Rows*Cellsize);
        int k=1370,h=700;
        if(k>Columns*Cellsize+345) k = Columns*Cellsize+345;
        if(h>Rows*Cellsize+52) h = Rows*Cellsize+52;
        pnlBoard.setPreferredSize(new Dimension(Columns*Cellsize+10,Rows*Cellsize+10));
        this.setSize(k,h);
    }
    
    public void CellAction(ActionEvent e){
        lblMg.setForeground(Color.gray);
        lblMg.setText("<html>" + Config.STROUT_RULE + "<html>");
        int i,j;
        if(BoErr) {
            for(i=0; i<Rows; i++)
                for(j=0; j<Columns; j++){                
                    if(State[i][j] == 0) btnCell[i][j].setBackground(Color.WHITE);
                    else if(State[i][j] == 1) btnCell[i][j].setBackground(Color.BLACK);
                    else if(State[i][j] == 2) btnCell[i][j].setBackground(Color.GRAY);
                }
            BoErr = false;
        }
        i = (Integer)((JButton)e.getSource()).getClientProperty("row");
        j = (Integer)((JButton)e.getSource()).getClientProperty("column");
        if(i >= 0 && i < this.Rows && j >= 0 && j < this.Columns){
            if(State[i][j]==0){
                btnCell[i][j].setBackground(Color.black);
                btnCell[i][j].setForeground(Color.WHITE);
                State[i][j]=1;
            }else
            if(State[i][j]==1){
                btnCell[i][j].setBackground(Color.GRAY);
                btnCell[i][j].setForeground(Color.BLACK);
                State[i][j]=2;
            }else
            if(State[i][j]==2){
                btnCell[i][j].setBackground(Color.WHITE);
                State[i][j]=0;
            }
        }   
    }
    
    private void CheckAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckAction
        if(check.CheckRule(value, State, Rows, Columns)) {
            lblMg.setForeground(Color.magenta);
            lblMg.setText("<html>"+Config.STROUT_CHECK_OK+"<html>");
            return;
        }
        lblMg.setForeground(Color.red);
        lblMg.setText("<html>"+Config.STROUT_CHECK_ERROR+"<html>");
        BoErr = true;
        boolean[][] err = check.getResult();
        int i,j;
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++)
                if(err[i][j])
                    btnCell[i][j].setBackground(Color.pink);
    }//GEN-LAST:event_CheckAction

    private void SolverAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SolverAction
        try {
            Class<?> clazz = Class.forName("model.solver."+Config.SOLVER_NAME[cbSolver.getSelectedIndex()][0]);
            Constructor<?> ctor = clazz.getConstructor(int.class, int.class, int[][].class);
            solver = (ASolver)ctor.newInstance(Rows,Columns,value);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException 
                | InstantiationException | IllegalAccessException | IllegalArgumentException 
                | InvocationTargetException ex) {
            lblMg.setForeground(Color.red);
            lblMg.setText("<html>"+Config.STROUT_ERROR_GETCLASS_FORNAME+"<html>");
            return;
        }
        int sol = solver.Solve();
        String str;
        switch(sol){
            case -1:
                lblMg.setForeground(Color.red);
                lblMg.setText("<html>"+solver.getSatSolverRt()+"<html>");
                break;
            case 0:
                str = solver.getSatSolverRt();
                lblMg.setForeground(Color.red);
                lblMg.setText("<html>"+Config.STROUT_NO_RESULT+"<br><br>"+str+"<html>");
                btnMore.setEnabled(false);
                break;
            case 1:
                str = solver.getSatSolverRt();
                boolean[][] re = solver.getResult();
                int i,j;
                for(i=0;i<Rows;i++)
                    for(j=0;j<Columns;j++)
                        if(re[i][j]) {btnCell[i][j].setBackground(Color.WHITE);
                        btnCell[i][j].setForeground(Color.BLACK);
                        State[i][j] = 0;
                        }
                        else {btnCell[i][j].setBackground(Color.BLACK);
                        btnCell[i][j].setForeground(Color.WHITE);
                        State[i][j] = 1;
                        }
                lblMg.setForeground(Color.blue);
                lblMg.setText(str);
                btnMore.setEnabled(true);
                break;
        }
    }//GEN-LAST:event_SolverAction

    private void MoreAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MoreAction
        int sol = solver.SolveMore();
        String str;
        switch(sol){
            case -1:
                lblMg.setForeground(Color.red);
                lblMg.setText("<html>"+solver.getSatSolverRt()+"<html>");
                break;
            case 0:
                str = solver.getSatSolverRt();
                lblMg.setForeground(Color.red);
                lblMg.setText("<html>"+Config.STROUT_NO_MORE_SOLUTION+"<br><br>"+str+"<html>");
                btnMore.setEnabled(false);
                break;
            case 1:
                str = solver.getSatSolverRt();
                boolean[][] re = solver.getResult();
                int i,j;
                for(i=0;i<Rows;i++)
                    for(j=0;j<Columns;j++)
                        if(re[i][j]) {
                            btnCell[i][j].setForeground(Color.BLACK);
                            btnCell[i][j].setBackground(Color.WHITE);
                            State[i][j] = 0;
                        }
                        else {
                            btnCell[i][j].setForeground(Color.WHITE);
                            btnCell[i][j].setBackground(Color.BLACK);
                            State[i][j] = 1;
                        }
                lblMg.setForeground(Color.green);
                lblMg.setText(str);
                break;
        }
    }//GEN-LAST:event_MoreAction

    private void cbSolverActionChange(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSolverActionChange
        btnMore.setEnabled(false);
        int i,j;
        for(i=0; i<Rows; i++)
                for(j=0; j<Columns; j++){                
                    btnCell[i][j].setBackground(Color.WHITE);
                    btnCell[i][j].setForeground(Color.BLACK);
                    State[i][j] = 0;
                }
        lblMg.setForeground(Color.gray);
        lblMg.setText("<html>" + Config.STROUT_RULE + "<html>");
    }//GEN-LAST:event_cbSolverActionChange

    private void DoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DoActionPerformed
        int numberOfCircle = (int)this.spiNCircle.getValue();
        int numberOfMap = (int)this.spiNMap.getValue();
        int numberOfSize = (int)this.spiNSize.getValue();
        thread = new Thread(() -> {
            lblMTmg.setForeground(Color.red);
            lblMTmg.setText("Running...");
            pbPerDone.setVisible(true);
            Class<?> clazz;
            Constructor<?> ctor;
            TimeThread stuffToDo;
            boolean booTimeOut;
            long timecter = System.nanoTime();
            int i,j,k,h,cellIndex,mapsizeIndex,nboCir;
            ExcelApp excel = new ExcelApp();
            excel.createSheet("Time");
            excel.createSheetAVG("TimeAVG");
            double[] sum = new double[multiTabSolver.length];
            //birds of prey-----------------------------------------------------
            newAction();
            for(j = 0;j < Config.NUMBER_OF_PREY; j++)
                for(h = 0; h < multiTabSolver.length; h++)
                    if(multiTabSolver[h].isSelected()){
                        try {
                            clazz = Class.forName("model.solver."+Config.SOLVER_NAME[h][0]);
                            ctor = clazz.getConstructor(int.class, int.class, int[][].class);
                            solver = (ASolver)ctor.newInstance(Rows,Columns,value);
                            solver.Solve();
                        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException
                                | InstantiationException | IllegalAccessException | IllegalArgumentException
                                | InvocationTargetException ex) {
                            lblMg.setForeground(Color.red);
                            lblMg.setText("<html>"+Config.STROUT_ERROR_GETCLASS_FORNAME+"<html>");
                            return;
                        }
                    }
            //------------------------------------------------------------------
            excel.createRowAVG();
            cellIndex = 0;
            excel.addCell(cellIndex++, "map size");
            excel.addCell(cellIndex++, "number of cells");
            for(h = 0; h < multiTabSolver.length; h++)
                if(multiTabSolver[h].isSelected()){
                    excel.addCell(cellIndex++, Config.SOLVER_NAME[h][1]);
                }
            Rows = Integer.parseInt(cbRows.getSelectedItem().toString());
            Columns = Integer.parseInt(cbColumns.getSelectedItem().toString());
            mapsizeIndex = 0;
            for(h = 0; h < listRows.length; h++)
                if(listRows[h] == Rows && listCols[h] == Columns)
                    mapsizeIndex = h;
            for(k = 0; k < numberOfSize; k++){
                if(mapsizeIndex == listRows.length) mapsizeIndex = 0;
                cbRows.setSelectedItem(listRows[mapsizeIndex]);
                cbColumns.setSelectedItem(listCols[mapsizeIndex]);
                excel.createRow();
                cellIndex = 0;
                excel.addCell(cellIndex++, listRows[mapsizeIndex]+"x"+listCols[mapsizeIndex]);
                for(h = 0; h < multiTabSolver.length; h++)
                    if(multiTabSolver[h].isSelected()){
                        excel.addCell(cellIndex++, Config.SOLVER_NAME[h][1]);
                        sum[h] = 0;
                    }
                for(i = 0; i < numberOfMap; i++){
                    newAction();
                    for(j = 0; j < numberOfCircle; j++){
                        excel.createRow();
                        cellIndex = 0;
                        excel.addCell(cellIndex++, (i*numberOfCircle+j+1) + " - " + input.getMapIndex() + "/" + input.getMapMax());
                        for(h = 0; h < multiTabSolver.length; h++)
                            if(multiTabSolver[h].isSelected()){
                                try {
                                    cmdViewer.append("\n>>R  size: "+listRows[mapsizeIndex]+"x"+listCols[mapsizeIndex]
                                            +"  cycle: "+(j+1) + " - map: " + input.getMapIndex() + "/" + input.getMapMax()
                                            +"  solver: "+Config.SOLVER_NAME[h][1]);//.substring(0, 5));
                                    clazz = Class.forName("model.solver."+Config.SOLVER_NAME[h][0]);
                                    ctor = clazz.getConstructor(int.class, int.class, int[][].class);
                                    solver = (ASolver)ctor.newInstance(Rows,Columns,value);
                                    
                                    //set timeout here
                                    stuffToDo = new TimeThread() {
                                        @Override
                                        public void run() {
                                            solver.Solve();
                                        }
                                    };
                                    booTimeOut = false;
                                    stuffToDo.start();
                                    while(true){
                                        if(System.currentTimeMillis() % 100 != 0) continue;
                                        if(stuffToDo.isInterrupted() || !stuffToDo.isAlive()) break;
                                        if(stuffToDo.isTimeOut()){
                                            stuffToDo.interrupt();
                                            stuffToDo.stop();
                                            booTimeOut = true;
                                            cmdViewer.append("\n   timeout "+Config.TIMEOUT+" s!");
                                        }
                                    }
                                    //end set timeout
                                    if(booTimeOut)
                                    {
                                        excel.addCell(cellIndex++, -1);
                                        sum[h] = -1;
                                    }
                                    else {
                                        excel.addCell(cellIndex++, (solver.getTime()));
                                        if(sum[h] != -1) sum[h]+= (solver.getTime());
                                    }
                                } catch (ClassNotFoundException | NoSuchMethodException | SecurityException
                                        | InstantiationException | IllegalAccessException | IllegalArgumentException
                                        | InvocationTargetException ex) {
                                    lblMg.setForeground(Color.red);
                                    lblMg.setText("<html>"+Config.STROUT_ERROR_GETCLASS_FORNAME+"<html>");
                                    return;
                                }
                            }
                        pbPerDone.setValue((k*numberOfMap*numberOfCircle+i*numberOfCircle+j+1)*100/(numberOfMap*numberOfCircle*numberOfSize));
                    }
                    if(i+1 == input.getMapMax()) i = numberOfMap;
                }
                nboCir = numberOfMap < input.getMapMax()?numberOfMap:input.getMapMax();
                excel.createRow();
                cellIndex = 0;
                excel.addCell(cellIndex++, "");
                for(h = 0; h < multiTabSolver.length; h++)
                    if(multiTabSolver[h].isSelected()){
                        if(sum[h] == -1) excel.addCell(cellIndex++,-1);
                        else excel.addCell(cellIndex++,sum[h]/(numberOfCircle*nboCir));
                    }
                excel.createRowAVG();
                cellIndex = 0;
                excel.addCell(cellIndex++, Rows+"x"+Columns);
                excel.addCell(cellIndex++, Rows*Columns);
                for(h = 0; h < multiTabSolver.length; h++)
                    if(multiTabSolver[h].isSelected()){
                        if(sum[h] == -1) excel.addCell(cellIndex++,-1);
                        else excel.addCell(cellIndex++,sum[h]/(numberOfCircle*nboCir));
                    }
                excel.createRow();
                mapsizeIndex++;
            }
            cellIndex = 0;
            excel.autoSizeColumn(cellIndex);
            excel.autoSizeColumnAVG(cellIndex++);
            excel.autoSizeColumnAVG(cellIndex);
            for(h = 0; h < multiTabSolver.length; h++)
                    if(multiTabSolver[h].isSelected()){
                        excel.autoSizeColumn(cellIndex++);
                    excel.autoSizeColumnAVG(cellIndex);
                }
            excel.createChartAVG(numberOfSize,cellIndex-1);
            excel.finish();
            timecter = System.nanoTime() - timecter;
            lblMTmg.setForeground(Color.blue);
            lblMTmg.setText("Done in "+timecter*0.000000001+"s");
            pbPerDone.setValue(0);
            pbPerDone.setVisible(false);
            cmdViewer.append("\nDONE.");
        });
        thread.start();
    }//GEN-LAST:event_DoActionPerformed

    private void chbCheckAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbCheckAllActionPerformed
        for(int i=0; i<Config.SOLVER_NAME.length; i++)
            this.multiTabSolver[i].setSelected(chbCheckAll.isSelected());
    }//GEN-LAST:event_chbCheckAllActionPerformed

    private void btnCreateSaveMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateSaveMapActionPerformed
        if(thread != null) thread.stop();
        
        thread = new Thread(() -> {
            btnCheck.setEnabled(true);
            btnSolver.setEnabled(true);
            btnMore.setEnabled(false);
            Rows = Integer.parseInt(cbRows.getSelectedItem().toString());
            Columns = Integer.parseInt(cbColumns.getSelectedItem().toString());   FakeMap fakeMap = new FakeMap(Rows,Columns,(int)this.spiFrPer.getValue(),(int)this.spiToPer.getValue());
            this.value = fakeMap.createAndSave((int)this.spiNrOfMap.getValue());
            hitoriPanel.removeAll();
            this.CreateBoard();
            this.lblCMMessage.setText("Number Of Black Cells: "+fakeMap.getNrOfBlackCell());
        });
        thread.start();
    }//GEN-LAST:event_btnCreateSaveMapActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        if(thread != null) thread.stop();
        
        thread = new Thread(() -> {
            btnCheck.setEnabled(true);
            btnSolver.setEnabled(true);
            btnMore.setEnabled(false);
            Rows = Integer.parseInt(cbRows.getSelectedItem().toString());
            Columns = Integer.parseInt(cbColumns.getSelectedItem().toString());   
            FakeMap fakeMap = new FakeMap(Rows,Columns,(int)this.spiFrPer.getValue(),(int)this.spiToPer.getValue());
            this.value = fakeMap.createMap();
            hitoriPanel.removeAll();
            this.CreateBoard();
            this.lblCMMessage.setText("Number Of Black Cells: "+fakeMap.getNrOfBlackCell());
        });
        thread.start();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(value == null) return;
        FakeMap fakeMap = new FakeMap(Rows,Columns,(int)this.spiFrPer.getValue(),(int)this.spiToPer.getValue());
        fakeMap.saveMap(value);
        this.lblCMMessage.setText("Save to file done.");
    }//GEN-LAST:event_btnSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Do;
    private javax.swing.JButton btnCheck;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnCreateSaveMap;
    private javax.swing.JButton btnMore;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSolver;
    private javax.swing.JComboBox cbColumns;
    private javax.swing.JComboBox cbRows;
    private javax.swing.JComboBox cbSolver;
    private javax.swing.JCheckBox chbCheckAll;
    private javax.swing.JTextArea cmdViewer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCMMessage;
    private javax.swing.JLabel lblFrPer;
    private javax.swing.JLabel lblMTmg;
    private javax.swing.JLabel lblMg;
    private javax.swing.JLabel lblNCircle;
    private javax.swing.JLabel lblNMap;
    private javax.swing.JLabel lblNSize;
    private javax.swing.JLabel lblNrOfMap;
    private javax.swing.JLabel lblToPer;
    private javax.swing.JProgressBar pbPerDone;
    private javax.swing.JPanel pnlBoard;
    private javax.swing.JPanel pnlCreateMap;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMulti;
    private javax.swing.JPanel pnlSingle;
    private javax.swing.JPanel pnlSolverSelect;
    private javax.swing.JScrollPane scpBoard;
    private javax.swing.JScrollPane scpMain;
    private javax.swing.JSpinner spiFrPer;
    private javax.swing.JSpinner spiNCircle;
    private javax.swing.JSpinner spiNMap;
    private javax.swing.JSpinner spiNSize;
    private javax.swing.JSpinner spiNrOfMap;
    private javax.swing.JSpinner spiToPer;
    private javax.swing.JTabbedPane tplMain;
    // End of variables declaration//GEN-END:variables
}