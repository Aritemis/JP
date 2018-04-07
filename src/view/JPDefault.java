/**
 * @author Ariana Fairbanks
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import adapter.JPController;
import adapter.JPViewStates;
import model.CustomTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

public class JPDefault extends JPanel
{
	private static final long serialVersionUID = 7833894194480819845L;
	private JFileChooser fileChoose;
	private JPController base;
	private GridBagLayout layout;
	private JButton clearMemberData;
	private JButton exportMemberData;
	private JButton viewMemberData;
	private JButton importMembers;
	private JButton clearAttendanceData;
	private JButton exportAttendanceData;
	private JButton viewAttendanceData;
	private JButton updateButton;
	private JComboBox<String> comboBoxA;
	private JComboBox<String> comboBoxB;
	private JButton helpButton;
	private JLabel lblFirstName;
	private JTextField firstNameField;
	private JLabel lblLastName;
	private JTextField lastNameField;
	private JLabel lblSCAName;
	private JTextField SCANameField;
	private JLabel lblMemberNumber;
	private JFormattedTextField memberNumberField;
	private JLabel lblExpDate;
	private JTextField expDateField;
	private JCheckBox adult;
	private JCheckBox feast;
	private JTable dataSet;
	private JScrollPane scrollPane;
    private TableRowSorter<TableModel> rowSorter;
    private JTextField selectionField;
	private ListSelectionModel listSelectionModel;
	private String firstName;
	private String lastName;
	private String SCAName;
	private int membershipNumber;
	private String expirationDate;
	private boolean isAdult;
	private boolean hadFeast;
	private int valueA;
	private int valueB;
	
	public JPDefault(JPController base)
	{
		this.base = base;
		fileChoose = new JFileChooser();
		layout = new GridBagLayout();
		clearMemberData = new JButton(" CLEAR MEMBER DATA ");
		exportMemberData = new JButton(" EXPORT MEMBER DATA ");
		viewMemberData = new JButton(" VIEW MEMBERS ");
		importMembers = new JButton(" IMPORT MEMBERS ");
		clearAttendanceData = new JButton(" CLEAR ATND DATA ");
		exportAttendanceData = new JButton(" EXPORT ATND DATA ");
		viewAttendanceData = new JButton(" VIEW ATND RECORDS ");
		comboBoxA = new JComboBox<String>();
		comboBoxA.setModel(new DefaultComboBoxModel<String>(new String[] {" Add New", " Delete", " Update"}));
		comboBoxB = new JComboBox<String>();
		comboBoxB.setModel(new DefaultComboBoxModel<String>(new String[] {" Attendee Record", " Membership Record", " Both"}));
		helpButton = new JButton(" ? ");
		lblFirstName = new JLabel("First Name:");
		firstNameField = new JTextField();
		lblLastName = new JLabel("Last Name:");
		lastNameField = new JTextField();
		lblSCAName = new JLabel("SCA Name:");
		SCANameField = new JTextField();
		lblMemberNumber = new JLabel("Member #:");
		memberNumberField = new JFormattedTextField();
		lblExpDate = new JLabel("Exp. Date:");
		expDateField = new JTextField();
		adult = new JCheckBox();
		feast = new JCheckBox();
		updateButton = new JButton("  Add Record  ");
		dataSet = new JTable();
        valueA = 0;
        valueB = 0;

        setUpTable();
	}
	
	private void setUpTable()
	{
		ResultSet res = null;
		int value = 0;
		if(valueB == 1)
		{	
			res = base.getAttendanceData();	
			value = 1;
		}
		else
		{	res = base.getMemberData();	}
		
		try 
		{	dataSet = new JTable(CustomTableModel.buildTableModel(res, value));	}
		catch (SQLException e) { e.printStackTrace(); }
		
		rowSorter = new TableRowSorter<>(dataSet.getModel());
		dataSet.setRowSorter(rowSorter);
		selectionField = new JTextField();
		listSelectionModel = dataSet.getSelectionModel();
		listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
        dataSet.setSelectionModel(listSelectionModel);
        
        resetValues();
        removeAll();
        revalidate();
        repaint();
        setUpLayout();
        setUpListeners();
	}

	private void setUpLayout() 
	{
		layout.rowHeights = new int[]{0, 0, 36, 0, 0, 0, 0, 0, 0, 0};
		layout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
		layout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		layout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0};
		setLayout(layout);
		setBorder(new LineBorder(new Color(0, 100, 0), 10));
		setForeground(new Color(105, 105, 105));
		setBackground(new Color(245, 245, 245));
				
		clearMemberData.setFont(new Font("Arial", Font.PLAIN, 18));
		clearMemberData.setForeground(new Color(0, 128, 0));
		clearMemberData.setBackground(new Color(255, 255, 255));
		clearMemberData.setFocusPainted(false);
		clearMemberData.setContentAreaFilled(false);
		clearMemberData.setBorder(new LineBorder(new Color(0, 128, 0), 2));
		clearMemberData.setToolTipText("Clears all data from the table containing membership records.");
		GridBagConstraints gbc_clearMemberData = new GridBagConstraints();
		gbc_clearMemberData.fill = GridBagConstraints.HORIZONTAL;
		gbc_clearMemberData.gridwidth = 3;
		gbc_clearMemberData.anchor = GridBagConstraints.NORTH;
		gbc_clearMemberData.insets = new Insets(20, 20, 0, 5);
		gbc_clearMemberData.gridx = 0;
		gbc_clearMemberData.gridy = 0;
		
		exportMemberData.setFont(new Font("Arial", Font.PLAIN, 18));
		exportMemberData.setForeground(new Color(0, 128, 0));
		exportMemberData.setBackground(new Color(105, 105, 105));
		exportMemberData.setFocusPainted(false);
		exportMemberData.setContentAreaFilled(false);
		exportMemberData.setBorder(new LineBorder(new Color(0, 128, 0), 2));
		exportMemberData.setToolTipText("Export all membership records in the database as a CSV file.");
		GridBagConstraints gbc_exportMemberData = new GridBagConstraints();
		gbc_exportMemberData.fill = GridBagConstraints.HORIZONTAL;
		gbc_exportMemberData.gridwidth = 2;
		gbc_exportMemberData.anchor = GridBagConstraints.NORTH;
		gbc_exportMemberData.insets = new Insets(20, 15, 0, 5);
		gbc_exportMemberData.gridx = 3;
		gbc_exportMemberData.gridy = 0;
		
		viewMemberData.setFont(new Font("Arial", Font.PLAIN, 18));
		viewMemberData.setForeground(new Color(0, 128, 0));
		viewMemberData.setBackground(new Color(105, 105, 105));
		viewMemberData.setFocusPainted(false);
		viewMemberData.setContentAreaFilled(false);
		viewMemberData.setBorder(new LineBorder(new Color(0, 128, 0), 2));
		viewMemberData.setToolTipText("View and search all records in the member table.");
		GridBagConstraints gbc_viewMemberData = new GridBagConstraints();
		gbc_viewMemberData.gridwidth = 2;
		gbc_viewMemberData.fill = GridBagConstraints.HORIZONTAL;
		gbc_viewMemberData.anchor = GridBagConstraints.NORTH;
		gbc_viewMemberData.insets = new Insets(20, 15, 0, 5);
		gbc_viewMemberData.gridx = 5;
		gbc_viewMemberData.gridy = 0;
		
		importMembers.setFont(new Font("Arial", Font.PLAIN, 18));
		importMembers.setForeground(new Color(0, 128, 0));
		importMembers.setBackground(new Color(105, 105, 105));
		importMembers.setFocusPainted(false);
		importMembers.setContentAreaFilled(false);
		importMembers.setBorder(new LineBorder(new Color(0, 128, 0), 2));
		importMembers.setToolTipText("Import member data via CSV file. Importing an already existing member will update their information.");
		GridBagConstraints gbc_importMembers = new GridBagConstraints();
		gbc_importMembers.gridwidth = 3;
		gbc_importMembers.fill = GridBagConstraints.HORIZONTAL;
		gbc_importMembers.anchor = GridBagConstraints.NORTH;
		gbc_importMembers.insets = new Insets(20, 15, 0, 20);
		gbc_importMembers.gridx = 7;
		gbc_importMembers.gridy = 0;	
		
		clearAttendanceData.setFont(new Font("Arial", Font.PLAIN, 18));
		clearAttendanceData.setForeground(new Color(0, 128, 0));
		clearAttendanceData.setBackground(new Color(255, 255, 255));
		clearAttendanceData.setFocusPainted(false);
		clearAttendanceData.setContentAreaFilled(false);
		clearAttendanceData.setBorder(new LineBorder(new Color(0, 128, 0), 2));
		clearAttendanceData.setToolTipText("Clears all data from the table containing attendance records.");
		GridBagConstraints gbc_clearAttendanceData = new GridBagConstraints();
		gbc_clearAttendanceData.fill = GridBagConstraints.HORIZONTAL;
		gbc_clearAttendanceData.gridwidth = 3;
		gbc_clearAttendanceData.anchor = GridBagConstraints.NORTH;
		gbc_clearAttendanceData.insets = new Insets(10, 20, 5, 5);
		gbc_clearAttendanceData.gridx = 0;
		gbc_clearAttendanceData.gridy = 1;
		
		exportAttendanceData.setFont(new Font("Arial", Font.PLAIN, 18));
		exportAttendanceData.setForeground(new Color(0, 128, 0));
		exportAttendanceData.setBackground(new Color(105, 105, 105));
		exportAttendanceData.setFocusPainted(false);
		exportAttendanceData.setContentAreaFilled(false);
		exportAttendanceData.setBorder(new LineBorder(new Color(0, 128, 0), 2));
		exportAttendanceData.setToolTipText("Export all attendance records in the database as a CSV file.");
		GridBagConstraints gbc_exportAttendanceData = new GridBagConstraints();
		gbc_exportAttendanceData.gridwidth = 2;
		gbc_exportAttendanceData.fill = GridBagConstraints.HORIZONTAL;
		gbc_exportAttendanceData.anchor = GridBagConstraints.NORTH;
		gbc_exportAttendanceData.insets = new Insets(10, 15, 5, 5);
		gbc_exportAttendanceData.gridx = 3;
		gbc_exportAttendanceData.gridy = 1;
		
		viewAttendanceData.setFont(new Font("Arial", Font.PLAIN, 18));
		viewAttendanceData.setForeground(new Color(0, 128, 0));
		viewAttendanceData.setBackground(new Color(105, 105, 105));
		viewAttendanceData.setFocusPainted(false);
		viewAttendanceData.setContentAreaFilled(false);
		viewAttendanceData.setBorder(new LineBorder(new Color(0, 128, 0), 2));
		viewAttendanceData.setToolTipText("View and search all event attendance data.");
		GridBagConstraints gbc_viewAttendanceData = new GridBagConstraints();
		gbc_viewAttendanceData.gridwidth = 2;
		gbc_viewAttendanceData.fill = GridBagConstraints.HORIZONTAL;
		gbc_viewAttendanceData.anchor = GridBagConstraints.NORTH;
		gbc_viewAttendanceData.insets = new Insets(10, 15, 5, 5);
		gbc_viewAttendanceData.gridx = 5;
		gbc_viewAttendanceData.gridy = 1;

		comboBoxA.setForeground(new Color(0, 128, 0));
		comboBoxA.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_comboBoxA = new GridBagConstraints();
		gbc_comboBoxA.anchor = GridBagConstraints.WEST;
		gbc_comboBoxA.insets = new Insets(10, 20, 5, 5);
		gbc_comboBoxA.gridx = 0;
		gbc_comboBoxA.gridy = 2;
		
		comboBoxB.setForeground(new Color(0, 128, 0));
		comboBoxB.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_comboBoxB = new GridBagConstraints();
		gbc_comboBoxB.gridwidth = 2;
		gbc_comboBoxB.anchor = GridBagConstraints.WEST;
		gbc_comboBoxB.insets = new Insets(0, 20, 15, 5);
		gbc_comboBoxB.gridx = 0;
		gbc_comboBoxB.gridy = 3;
		
		helpButton.setFont(new Font("Arial", Font.PLAIN, 10));
		helpButton.setForeground(new Color(0, 128, 0));
		helpButton.setBackground(new Color(105, 105, 105));
		helpButton.setFocusPainted(false);
		helpButton.setContentAreaFilled(false);
		helpButton.setBorder(null);
		setHelpText();
		GridBagConstraints gbc_helpButton = new GridBagConstraints();
		gbc_helpButton.anchor = GridBagConstraints.WEST;
		gbc_helpButton.insets = new Insets(0, 5, 15, 5);
		gbc_helpButton.gridx = 2;
		gbc_helpButton.gridy = 3;
		
		lblFirstName.setFont(new Font("Arial", Font.PLAIN, 16));
		lblFirstName.setForeground(new Color(0, 100, 0));
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.WEST;
		gbc_lblFirstName.insets = new Insets(0, 20, 5, 5);
		gbc_lblFirstName.gridx = 0;
		gbc_lblFirstName.gridy = 4;
		
		firstNameField.setFont(new Font("Arial", Font.PLAIN, 16));
		firstNameField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 4;	
		
		lblLastName.setFont(new Font("Arial", Font.PLAIN, 16));
		lblLastName.setForeground(new Color(0, 100, 0));
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.EAST;
		gbc_lblLastName.insets = new Insets(0, 15, 5, 5);
		gbc_lblLastName.gridx = 3;
		gbc_lblLastName.gridy = 4;
		
		lastNameField.setFont(new Font("Arial", Font.PLAIN, 16));
		lastNameField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lastNameField = new GridBagConstraints();
		gbc_lastNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastNameField.insets = new Insets(0, 0, 5, 5);
		gbc_lastNameField.gridx = 4;
		gbc_lastNameField.gridy = 4;
		
		lblSCAName.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSCAName.setForeground(new Color(0, 100, 0));
		GridBagConstraints gbc_lblSCAName = new GridBagConstraints();
		gbc_lblSCAName.anchor = GridBagConstraints.EAST;
		gbc_lblSCAName.insets = new Insets(0, 15, 5, 5);
		gbc_lblSCAName.gridx = 5;
		gbc_lblSCAName.gridy = 4;
		
		SCANameField.setFont(new Font("Arial", Font.PLAIN, 16));
		SCANameField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_SCANameField = new GridBagConstraints();
		gbc_SCANameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_SCANameField.insets = new Insets(0, 0, 5, 5);
		gbc_SCANameField.gridx = 6;
		gbc_SCANameField.gridy = 4;
		
		lblMemberNumber.setFont(new Font("Arial", Font.PLAIN, 16));
		lblMemberNumber.setForeground(new Color(0, 100, 0));
		lblMemberNumber.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblMemberNumber = new GridBagConstraints();
		gbc_lblMemberNumber.anchor = GridBagConstraints.WEST;
		gbc_lblMemberNumber.insets = new Insets(0, 20, 5, 5);
		gbc_lblMemberNumber.gridx = 0;
		gbc_lblMemberNumber.gridy = 5;
		
		memberNumberField.setFont(new Font("Arial", Font.PLAIN, 16));
		memberNumberField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_memberNumberField = new GridBagConstraints();
		gbc_memberNumberField.fill = GridBagConstraints.HORIZONTAL;
		gbc_memberNumberField.gridwidth = 2;
		gbc_memberNumberField.insets = new Insets(0, 0, 5, 5);
		gbc_memberNumberField.gridx = 1;
		gbc_memberNumberField.gridy = 5;
		
		lblExpDate.setFont(new Font("Arial", Font.PLAIN, 16));
		lblExpDate.setForeground(new Color(0, 100, 0));
		lblExpDate.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblExpDate = new GridBagConstraints();
		gbc_lblExpDate.anchor = GridBagConstraints.WEST;
		gbc_lblExpDate.insets = new Insets(0, 15, 5, 5);
		gbc_lblExpDate.gridx = 3;
		gbc_lblExpDate.gridy = 5;
		
		expDateField.setFont(new Font("Arial", Font.PLAIN, 16));
		expDateField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_expDateField = new GridBagConstraints();
		gbc_expDateField.fill = GridBagConstraints.HORIZONTAL;
		gbc_expDateField.insets = new Insets(0, 0, 5, 5);
		gbc_expDateField.gridx = 4;
		gbc_expDateField.gridy = 5;
		
		adult.setSelected(true);
		adult.setText(" Adult");
		adult.setFont(new Font("Arial", Font.PLAIN, 16));
		adult.setForeground(new Color(0, 100, 0));
		adult.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_adult = new GridBagConstraints();
		gbc_adult.fill = GridBagConstraints.HORIZONTAL;
		gbc_adult.insets = new Insets(10, 15, 5, 5);
		gbc_adult.gridx = 5;
		gbc_adult.gridy = 5;
		
		feast.setSelected(false);
		feast.setText(" Feast    ");
		feast.setFont(new Font("Arial", Font.PLAIN, 16));
		feast.setForeground(new Color(0, 100, 0));
		GridBagConstraints gbc_feast = new GridBagConstraints();
		gbc_feast.anchor = GridBagConstraints.WEST;
		gbc_feast.insets = new Insets(10, 5, 5, 5);
		gbc_feast.gridx = 6;
		gbc_feast.gridy = 5;
		
		updateButton.setFont(new Font("Arial", Font.PLAIN, 16));
		updateButton.setForeground(new Color(0, 128, 0));
		updateButton.setBackground(new Color(105, 105, 105));
		updateButton.setFocusPainted(false);
		updateButton.setContentAreaFilled(false);
		updateButton.setBorder(new LineBorder(new Color(0, 128, 0), 2, true));
		GridBagConstraints gbc_updateButton = new GridBagConstraints();
		gbc_updateButton.anchor = GridBagConstraints.WEST;
		gbc_updateButton.insets = new Insets(0, 15, 5, 5);
		gbc_updateButton.gridx = 7;
		gbc_updateButton.gridy = 4;
		
		selectionField.setForeground(new Color(0, 100, 0));
		selectionField.setFont(new Font("Arial", Font.PLAIN, 17));
		selectionField.setBackground(new Color(245, 250, 245));
		selectionField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0, 128, 0), 2),
                BorderFactory.createEmptyBorder(2, 20, 2, 2)));
		GridBagConstraints gbc_selectionField = new GridBagConstraints();
		gbc_selectionField.gridwidth = 10;
		gbc_selectionField.insets = new Insets(15, 50, 15, 50);
		gbc_selectionField.fill = GridBagConstraints.HORIZONTAL;
		gbc_selectionField.gridx = 0;
		gbc_selectionField.gridy = 6;
		
		dataSet.setForeground(Color.BLACK);
		dataSet.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		dataSet.setBackground(new Color(245, 250, 245));
		JTableHeader header = dataSet.getTableHeader();
		header.setReorderingAllowed(false);
		header.setForeground(new Color(0, 100, 0));
		header.setFont(new Font("Arial", Font.PLAIN, 20));
		header.setBackground(new Color(245, 245, 245));
		header.setBorder(new LineBorder(new Color(0, 128, 0), 2));
		scrollPane = new JScrollPane(dataSet);
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 100, 0)));
		scrollPane.getViewport().setForeground(Color.BLACK);
		scrollPane.getViewport().setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane.getViewport().setBackground(new Color(245, 250, 245));
		scrollPane.setBorder(new LineBorder(new Color(0, 128, 0), 2));
		GridBagConstraints gbc_dataSet = new GridBagConstraints();
		gbc_dataSet.gridheight = 3;
		gbc_dataSet.gridwidth = 10;
		gbc_dataSet.gridy = 7;
		gbc_dataSet.insets = new Insets(0, 20, 20, 20);
		gbc_dataSet.fill = GridBagConstraints.BOTH;
		gbc_dataSet.gridx = 0;
		
		add(clearMemberData, gbc_clearMemberData);
		add(exportMemberData, gbc_exportMemberData);
		add(viewMemberData, gbc_viewMemberData);	
		add(importMembers, gbc_importMembers);
		add(clearAttendanceData, gbc_clearAttendanceData);
		add(exportAttendanceData, gbc_exportAttendanceData);
		add(viewAttendanceData, gbc_viewAttendanceData);
		add(comboBoxA, gbc_comboBoxA);
		add(comboBoxB, gbc_comboBoxB);
		add(helpButton, gbc_helpButton);
		add(lblFirstName, gbc_lblFirstName);
		add(firstNameField, gbc_textField);
		add(lblLastName, gbc_lblLastName);
		add(lastNameField, gbc_lastNameField);
		add(lblSCAName, gbc_lblSCAName);
		add(SCANameField, gbc_SCANameField);
		add(lblMemberNumber, gbc_lblMemberNumber);
		add(memberNumberField, gbc_memberNumberField);
		add(lblExpDate, gbc_lblExpDate);
		add(expDateField, gbc_expDateField);
		add(adult, gbc_adult);
		add(feast, gbc_feast);
		add(updateButton, gbc_updateButton);
		add(selectionField, gbc_selectionField);
		add(scrollPane, gbc_dataSet);
	}
	
	private void setUpListeners() 
	{	
		clearMemberData.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				JPanel temp = new JPanel();
				int valueReturned = JOptionPane.showConfirmDialog(temp, "Are you sure you want to clear all member data from the database?");
				if(valueReturned == JOptionPane.OK_OPTION)
				{	base.clearMemberData();	}
			}
		});
		
		exportMemberData.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				JPanel temp = new JPanel();
			    fileChoose.setFileFilter(new FileFilter() 
			    {
			        @Override
			        public boolean accept(File f) 
			        {	return f.getName().endsWith(".csv");	}
			        @Override
			        public String getDescription() 
			        {	return "CSV files";	}
			    });
			    File defaultFile = new File("newFile.csv");
			    fileChoose.setSelectedFile(defaultFile);
				int valueReturned = fileChoose.showOpenDialog(temp);
				if(valueReturned == JFileChooser.APPROVE_OPTION)
				{
					JTable dataSet = new JTable();
					ResultSet res = base.getMemberData();
					try 
					{	dataSet = new JTable(CustomTableModel.buildTableModel(res, 0));	}
					catch (SQLException e) { e.printStackTrace(); }
					base.exportMembers(dataSet, fileChoose.getSelectedFile());
				}
			}
		});
		
		viewMemberData.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{	base.changeState(JPViewStates.VIEWDATA);	}
		});
		
		importMembers.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
			    fileChoose.setFileFilter(new FileFilter() 
			    {
			        @Override
			        public boolean accept(File f) 
			        {	return f.getName().endsWith(".csv") || f.getName().endsWith(".txt");	}
			        @Override
			        public String getDescription() 
			        {	return "CSV files";	}
			    });
				int valueReturned = fileChoose.showOpenDialog(JPController.errorPanel);
				if(valueReturned == JFileChooser.APPROVE_OPTION)
				{	
					base.importMembers(fileChoose.getSelectedFile());	
					setUpTable();
				}
			}
		});
		
		clearAttendanceData.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				int valueReturned = JOptionPane.showConfirmDialog(JPController.errorPanel, "Are you sure you want to clear all attendance data from the database?");
				if(valueReturned == JOptionPane.OK_OPTION)
				{	
					base.clearAttendaceData();	
					setUpTable();
				}
			}
		});
		
		exportAttendanceData.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
			    fileChoose.setFileFilter(new FileFilter() 
			    {
			        @Override
			        public boolean accept(File f) 
			        {	return f.getName().endsWith(".csv");	}
			        @Override
			        public String getDescription() 
			        {	return "CSV files";	}
			    });
			    File defaultFile = new File("newFile.csv");
			    fileChoose.setSelectedFile(defaultFile);
				int valueReturned = fileChoose.showOpenDialog(JPController.errorPanel);
				if(valueReturned == JFileChooser.APPROVE_OPTION)
				{
					JTable dataSet = new JTable();
					ResultSet res = base.getAttendanceData();
					try 
					{	dataSet = new JTable(CustomTableModel.buildTableModel(res, 1));	}
					catch (SQLException e) { e.printStackTrace(); }
					System.out.println(fileChoose.getSelectedFile().getPath());
					File newFile = fileChoose.getSelectedFile();
					valueReturned = JOptionPane.showConfirmDialog(JPController.errorPanel, "Do you also wish to clear existing data?");
					if(valueReturned == JOptionPane.OK_OPTION)
					{	base.clearMemberData();	}
					base.exportAttendaceData(dataSet, newFile);
				}
			}
		});
		
		viewAttendanceData.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{	
				base.dataRequested = 1;
				base.changeState(JPViewStates.VIEWDATA);	
			}
		});
		
		comboBoxA.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(comboBoxA.getSelectedIndex() != valueA)
				{	updateUpdateButtonText();	}
			}
		});
		
		comboBoxB.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{	
				if(comboBoxB.getSelectedIndex() != valueB)
				{	updateUpdateButtonText();	}
			}
		});
		
		selectionField.getDocument().addDocumentListener(new DocumentListener()
		{
            @Override
            public void insertUpdate(DocumentEvent e) 
            {
                String text = selectionField.getText();
                if (text.trim().length() == 0) 
                {	 rowSorter.setRowFilter(null);	} 
                else 
                {	rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));	}
            }
            @Override
            public void removeUpdate(DocumentEvent e) 
            {
                String text = selectionField.getText();
                if (text.trim().length() == 0) 
                {	rowSorter.setRowFilter(null);	} 
                else 
                {	rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));	}
            }
            @Override
            public void changedUpdate(DocumentEvent e) 
            {	throw new UnsupportedOperationException("Not supported."); }
        });
		
		updateButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick)
			{	
				assignValues();
				boolean result = false;
				boolean firstAndLast = firstName.length() > 1 && lastName.length() > 1;
				if(!firstAndLast)
				{	}
				else
				{
					switch(valueA)
					{
						case 0:
							switch(valueB)
							{
								case 0:
									result = base.addAttendanceData(lastName, firstName, isAdult, hadFeast);
									break;
								case 1:
									result = base.addMemberData(lastName, firstName, SCAName, membershipNumber, expirationDate, isAdult);
									break;
								case 2:
									result = true;
									base.addMemberData(lastName, firstName, SCAName, membershipNumber, expirationDate, isAdult);
									base.addAttendanceData(lastName, firstName, isAdult, hadFeast);
									break;
							}
							break;
						case 1:
							switch(valueB)
							{
								case 0:
									result = base.deleteAttendanceData(lastName, firstName);
									break;
								case 1:
									result = base.deleteMemberData(lastName, firstName);
									break;
								case 2:
									result = true;
									base.deleteAttendanceData(lastName, firstName);
									base.deleteMemberData(lastName, firstName);
									break;
							}
							break;
						case 2:
							result = true;
							switch(valueB)
							{
								case 0:
									base.deleteAttendanceData(lastName, firstName);
									base.addAttendanceData(lastName, firstName, isAdult, hadFeast);
									break;
								case 1:
									base.deleteMemberData(lastName, firstName);
									base.addMemberData(lastName, firstName, SCAName, membershipNumber, expirationDate, isAdult);
									break;
								case 2:
									base.deleteAttendanceData(lastName, firstName);
									base.deleteMemberData(lastName, firstName);
									base.addMemberData(lastName, firstName, SCAName, membershipNumber, expirationDate, isAdult);
									base.addAttendanceData(lastName, firstName, isAdult, hadFeast);
									break;
							}
							break;
					}
				}
				if(result)
				{
					resetFields();
					assignValues();
					setUpTable();
				}
			}
		});
	}
	
	private void assignValues()
	{
		String tempNumber = memberNumberField.getText().trim();
		lastName = lastNameField.getText();
		firstName = firstNameField.getText();
		SCAName = SCANameField.getText();
		if(tempNumber.length() > 0)
		{	membershipNumber = Integer.parseInt(memberNumberField.getText());	}
		else
		{	membershipNumber = 0;	}
		expirationDate = expDateField.getText();
		isAdult = adult.isSelected();
		hadFeast = feast.isSelected();
	}
	
	private void resetFields()
	{
		firstNameField.setText("");
		lastNameField.setText("");
		SCANameField.setText("");
		memberNumberField.setText("");
		expDateField.setText("");
		adult.setSelected(true);
		feast.setSelected(false);
		selectionField.setText("");
	}
	
	private void updateFields()
	{
		firstNameField.setText(firstName);
		lastNameField.setText(lastName);
		SCANameField.setText(SCAName);
		memberNumberField.setText(membershipNumber + "");
		expDateField.setText(expirationDate);
		adult.setSelected(isAdult);
	}
	
	private void updateValues()
	{
		if(valueA != 2 && valueB == 0)
		{
			int row = dataSet.getSelectedRow();
			lastName = (String) dataSet.getValueAt(row, 0);
			firstName = (String) dataSet.getValueAt(row, 1);
			Integer tempInteger = (Integer) dataSet.getValueAt(row, 2);
			if(tempInteger.intValue() == 1)
			{	isAdult = true;	}
			else
			{	isAdult = false;	}
			tempInteger = (Integer) dataSet.getValueAt(row, 4);
			if(tempInteger.intValue() == 1)
			{	hadFeast = true;	}
			else
			{	hadFeast = false;	}	
		}
		else
		{	
			int row = dataSet.getSelectedRow();
			lastName = (String) dataSet.getValueAt(row, 0);
			firstName = (String) dataSet.getValueAt(row, 1);
			SCAName = (String) dataSet.getValueAt(row, 2);
			Integer temp = (Integer) dataSet.getValueAt(row, 3);
			membershipNumber = temp.intValue();
			expirationDate = (String) dataSet.getValueAt(row, 4);
			temp = (Integer) dataSet.getValueAt(row, 5);
			if(temp.intValue() == 1)
			{	isAdult = true;	}
			else
			{	isAdult = false;	}
		}
		updateFields();
	}
	
	private void updateUpdateButtonText()
	{
		valueA = comboBoxA.getSelectedIndex();
		valueB = comboBoxB.getSelectedIndex();
		String newText = null;
		switch(valueA)
		{
			case 0:
				newText = " Add Record";
				break;
			case 1:
				newText = " Delete Record";
				break;
			case 2:
				newText = " Update Record";
				break;
		}
		if(valueB == 2)
		{	newText += "s";	}
		newText += " ";
		updateButton.setText(newText);
		resetFields();
		setUpTable();
	}
	
	private void setHelpText()
	{
		String newText = "";
		switch(valueA)
		{
			case 0:
				switch(valueB)
				{
					case 0:
						newText = "Ignores the member # and exp. date fields. "
								+ "Will not update any information for existing records. "
								+ "It will automatically detect if someone is a member through their first and last names.";
						break;
					case 1:
						newText = "Add a membership record. "
								+ "Will add a new member so long as a member with the same first and last names doesn't already exist.";
						break;
					case 2:
						newText = "Add new membership and attendance records for the same person.";
						break;
				}
				break;
			case 1:
				switch(valueB)
				{
					case 0:
						newText = "Delete an attendance record.";
						break;
					case 1:
						newText = "Delete a membership record. Does not change any existing attendance records.";
						break;
					case 2:
						newText = "Delete a membership record as well as any attendance records they may have.";
						break;
				}
				break;
			case 2:
				switch(valueB)
				{
					case 0:
						newText = "Ignores any fields not stored in an attendance record. "
								+ "Does not ignore any empty fields usually stored in attendance records.";
						break;
					case 1:
						newText = "Ignores any fields not stored in a membership record. "
								+ "Does not ignore any empty fields usually stored in membership records.";
						break;
					case 2:
						newText = "Update a membership record as well as any attendance records they may have.";
						break;
				}
				break;
		}
		helpButton.setToolTipText(newText);
	}
	
	private void resetValues()
	{
		firstName = "";
		lastName = "";
		SCAName = "";
		membershipNumber = 0;
		expirationDate = "";
		isAdult = true;
		hadFeast = false;
	}
	
	private class SharedListSelectionHandler implements ListSelectionListener 
	{		
	    public void valueChanged(ListSelectionEvent e) 
	    {
	        ListSelectionModel lsm = (ListSelectionModel)e.getSource();;
	        if (lsm.isSelectionEmpty()){	} 
	        else 
	        {	updateValues();	}
	    }
	}
	
}