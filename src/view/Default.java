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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import adapter.JPController;
import adapter.JPViewStates;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

public class Default extends JPanel
{
	private static final long serialVersionUID = 7833894194480819845L;
	private JFileChooser fileChoose;
	private JPController base;
	private GridBagLayout layout;
	private JButton importMembers;
	private JButton clearMemberData;
	private JButton viewMemberData;
	private JButton exportMemberData;
	private JButton clearAttendanceData;
	private JButton viewAttendanceData;
	private JButton exportAttendanceData;
	private JButton updateButton;
	private JComboBox<String> comboBoxA;
	private JComboBox<String> comboBoxB;
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
	private JLabel lblAdult;
	private JCheckBox adult;
	private JLabel lblFeast;
	private JCheckBox feast;
	private String firstName;
	private String lastName;
	private String SCAName;
	private String membershipNumber;
	private String expirationDate;
	private boolean isAdult;
	private boolean hadFeast;
	
	
	public Default(JPController base)
	{
		this.base = base;
		fileChoose = new JFileChooser();
		layout = new GridBagLayout();
		clearMemberData = new JButton(" CLEAR MEMBER DATA ");
		exportMemberData = new JButton(" EXPORT MEMBER DATA ");
		viewMemberData = new JButton(" VIEW MEMBERS ");
		importMembers = new JButton(" IMPORT MEMBERS ");
		clearAttendanceData = new JButton(" CLEAR ATND DATA ");
		viewAttendanceData = new JButton(" VIEW ATND RECORDS ");
		exportAttendanceData = new JButton(" EXPORT ATND DATA ");
		updateButton = new JButton(" Update Records ");
		comboBoxA = new JComboBox<String>();
		comboBoxA.setModel(new DefaultComboBoxModel<String>(new String[] {" Add New", " Update", " Delete"}));
		comboBoxB = new JComboBox<String>();
		comboBoxB.setModel(new DefaultComboBoxModel<String>(new String[] {" Attendee Record", " Membership Record", " Both"}));
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
		lblAdult = new JLabel("Adult");
		adult = new JCheckBox();
		lblFeast = new JLabel("Feast");
		feast = new JCheckBox();
		
		setUpLayout();
		setUpListeners();
	}

	private void setUpLayout() 
	{
		layout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		layout.rowWeights = new double[]{0.0, 0.0, 2.0, 0.0, 0.0, 4.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 35.0};
		layout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		layout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0};
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
		gbc_clearMemberData.gridwidth = 2;
		gbc_clearMemberData.anchor = GridBagConstraints.NORTH;
		gbc_clearMemberData.insets = new Insets(20, 20, 5, 5);
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
		gbc_exportMemberData.insets = new Insets(20, 15, 5, 5);
		gbc_exportMemberData.gridx = 2;
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
		gbc_viewMemberData.insets = new Insets(20, 15, 5, 5);
		gbc_viewMemberData.gridx = 4;
		gbc_viewMemberData.gridy = 0;
		
		importMembers.setFont(new Font("Arial", Font.PLAIN, 18));
		importMembers.setForeground(new Color(0, 128, 0));
		importMembers.setBackground(new Color(105, 105, 105));
		importMembers.setFocusPainted(false);
		importMembers.setContentAreaFilled(false);
		importMembers.setBorder(new LineBorder(new Color(0, 128, 0), 2));
		importMembers.setToolTipText("Import member data via CSV file. Importing an already existing member will update their information.");
		GridBagConstraints gbc_importMembers = new GridBagConstraints();
		gbc_importMembers.gridwidth = 2;
		gbc_importMembers.fill = GridBagConstraints.HORIZONTAL;
		gbc_importMembers.anchor = GridBagConstraints.NORTH;
		gbc_importMembers.insets = new Insets(20, 15, 5, 20);
		gbc_importMembers.gridx = 6;
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
		gbc_clearAttendanceData.gridwidth = 2;
		gbc_clearAttendanceData.anchor = GridBagConstraints.NORTH;
		gbc_clearAttendanceData.insets = new Insets(5, 20, 5, 5);
		gbc_clearAttendanceData.gridx = 0;
		gbc_clearAttendanceData.gridy = 1;
		
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
		gbc_viewAttendanceData.insets = new Insets(5, 15, 5, 5);
		gbc_viewAttendanceData.gridx = 4;
		gbc_viewAttendanceData.gridy = 1;
		
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
		gbc_exportAttendanceData.insets = new Insets(5, 15, 5, 5);
		gbc_exportAttendanceData.gridx = 2;
		gbc_exportAttendanceData.gridy = 1;
	
		updateButton.setFont(new Font("Arial", Font.PLAIN, 25));
		updateButton.setForeground(new Color(0, 128, 0));
		updateButton.setBackground(new Color(105, 105, 105));
		updateButton.setFocusPainted(false);
		updateButton.setContentAreaFilled(false);
		updateButton.setBorder(new LineBorder(new Color(0, 128, 0), 2, true));
		GridBagConstraints gbc_updateButton = new GridBagConstraints();
		gbc_updateButton.gridwidth = 6;
		gbc_updateButton.anchor = GridBagConstraints.EAST;
		gbc_updateButton.insets = new Insets(0, 0, 20, 20);
		gbc_updateButton.gridx = 2;
		gbc_updateButton.gridy = 14;
		
		comboBoxA.setForeground(new Color(0, 128, 0));
		comboBoxA.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_comboBoxA = new GridBagConstraints();
		gbc_comboBoxA.gridwidth = 2;
		gbc_comboBoxA.anchor = GridBagConstraints.WEST;
		gbc_comboBoxA.insets = new Insets(15, 20, 5, 5);
		gbc_comboBoxA.gridx = 0;
		gbc_comboBoxA.gridy = 3;
		
		comboBoxB.setForeground(new Color(0, 128, 0));
		comboBoxB.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_comboBoxB = new GridBagConstraints();
		gbc_comboBoxB.gridwidth = 2;
		gbc_comboBoxB.anchor = GridBagConstraints.WEST;
		gbc_comboBoxB.insets = new Insets(0, 20, 20, 5);
		gbc_comboBoxB.gridx = 0;
		gbc_comboBoxB.gridy = 4;
		
		lblFirstName.setFont(new Font("Arial", Font.PLAIN, 16));
		lblFirstName.setForeground(new Color(0, 100, 0));
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.WEST;
		gbc_lblFirstName.insets = new Insets(0, 20, 5, 5);
		gbc_lblFirstName.gridx = 0;
		gbc_lblFirstName.gridy = 6;
		
		firstNameField.setFont(new Font("Arial", Font.PLAIN, 16));
		firstNameField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 6;	
		
		lblLastName.setFont(new Font("Arial", Font.PLAIN, 16));
		lblLastName.setForeground(new Color(0, 100, 0));
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.EAST;
		gbc_lblLastName.insets = new Insets(0, 15, 5, 5);
		gbc_lblLastName.gridx = 2;
		gbc_lblLastName.gridy = 6;
		
		lastNameField.setFont(new Font("Arial", Font.PLAIN, 16));
		lastNameField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lastNameField = new GridBagConstraints();
		gbc_lastNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastNameField.insets = new Insets(0, 0, 5, 5);
		gbc_lastNameField.gridx = 3;
		gbc_lastNameField.gridy = 6;
		
		lblSCAName.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSCAName.setForeground(new Color(0, 100, 0));
		GridBagConstraints gbc_lblSCAName = new GridBagConstraints();
		gbc_lblSCAName.anchor = GridBagConstraints.EAST;
		gbc_lblSCAName.insets = new Insets(0, 15, 5, 5);
		gbc_lblSCAName.gridx = 4;
		gbc_lblSCAName.gridy = 6;
		
		SCANameField.setFont(new Font("Arial", Font.PLAIN, 16));
		SCANameField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_SCANameField = new GridBagConstraints();
		gbc_SCANameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_SCANameField.insets = new Insets(0, 0, 5, 5);
		gbc_SCANameField.gridx = 5;
		gbc_SCANameField.gridy = 6;
		
		lblMemberNumber.setFont(new Font("Arial", Font.PLAIN, 16));
		lblMemberNumber.setForeground(new Color(0, 100, 0));
		lblMemberNumber.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblMemberNumber = new GridBagConstraints();
		gbc_lblMemberNumber.anchor = GridBagConstraints.WEST;
		gbc_lblMemberNumber.insets = new Insets(0, 20, 5, 5);
		gbc_lblMemberNumber.gridx = 0;
		gbc_lblMemberNumber.gridy = 8;
		
		memberNumberField.setFont(new Font("Arial", Font.PLAIN, 16));
		memberNumberField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_memberNumberField = new GridBagConstraints();
		gbc_memberNumberField.fill = GridBagConstraints.HORIZONTAL;
		gbc_memberNumberField.insets = new Insets(0, 0, 5, 5);
		gbc_memberNumberField.gridx = 1;
		gbc_memberNumberField.gridy = 8;
		
		lblExpDate.setFont(new Font("Arial", Font.PLAIN, 16));
		lblExpDate.setForeground(new Color(0, 100, 0));
		lblExpDate.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblExpDate = new GridBagConstraints();
		gbc_lblExpDate.anchor = GridBagConstraints.WEST;
		gbc_lblExpDate.insets = new Insets(0, 15, 5, 5);
		gbc_lblExpDate.gridx = 2;
		gbc_lblExpDate.gridy = 8;
		
		expDateField.setFont(new Font("Arial", Font.PLAIN, 16));
		expDateField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_expDateField = new GridBagConstraints();
		gbc_expDateField.fill = GridBagConstraints.HORIZONTAL;
		gbc_expDateField.insets = new Insets(0, 0, 5, 5);
		gbc_expDateField.gridx = 3;
		gbc_expDateField.gridy = 8;
		
		lblAdult.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAdult.setForeground(new Color(0, 100, 0));
		lblAdult.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblAdult = new GridBagConstraints();
		gbc_lblAdult.anchor = GridBagConstraints.EAST;
		gbc_lblAdult.insets = new Insets(10, 20, 5, 5);
		gbc_lblAdult.gridx = 0;
		gbc_lblAdult.gridy = 10;
		
		adult.setSelected(true);
		GridBagConstraints gbc_adult = new GridBagConstraints();
		gbc_adult.anchor = GridBagConstraints.WEST;
		gbc_adult.insets = new Insets(10, 5, 5, 5);
		gbc_adult.gridx = 1;
		gbc_adult.gridy = 10;
		
		lblFeast.setFont(new Font("Arial", Font.PLAIN, 16));
		lblFeast.setForeground(new Color(0, 100, 0));
		lblFeast.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblFeast = new GridBagConstraints();
		gbc_lblFeast.anchor = GridBagConstraints.EAST;
		gbc_lblFeast.insets = new Insets(0, 20, 5, 5);
		gbc_lblFeast.gridx = 0;
		gbc_lblFeast.gridy = 12;
		
		GridBagConstraints gbc_feast = new GridBagConstraints();
		gbc_feast.anchor = GridBagConstraints.WEST;
		gbc_feast.insets = new Insets(0, 5, 5, 5);
		gbc_feast.gridx = 1;
		gbc_feast.gridy = 12;
		
		add(clearMemberData, gbc_clearMemberData);
		add(exportMemberData, gbc_exportMemberData);
		add(viewMemberData, gbc_viewMemberData);		
		add(importMembers, gbc_importMembers);
		add(clearAttendanceData, gbc_clearAttendanceData);
		add(viewAttendanceData, gbc_viewAttendanceData);
		add(exportAttendanceData, gbc_exportAttendanceData);
		add(updateButton, gbc_updateButton);
		add(comboBoxA, gbc_comboBoxA);
		add(comboBoxB, gbc_comboBoxB);
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
		add(lblAdult, gbc_lblAdult);
		add(adult, gbc_adult);
		add(lblFeast, gbc_lblFeast);
		add(feast, gbc_feast);
	}
	
	private void setUpListeners() 
	{	
		importMembers.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				JPanel temp = new JPanel();
			    fileChoose.setFileFilter(new FileFilter() 
			    {
			        @Override
			        public boolean accept(File f) 
			        {	return f.getName().endsWith(".csv") || f.getName().endsWith(".txt");	}
			        @Override
			        public String getDescription() 
			        {	return "CSV files";	}
			    });
				int valueReturned = fileChoose.showOpenDialog(temp);
				if(valueReturned == JFileChooser.APPROVE_OPTION)
				{
					base.importMembers(fileChoose.getSelectedFile());
				}
			}
		});
		
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
		
		viewMemberData.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{	base.changeState(JPViewStates.VIEWDATA);	}
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
					{	dataSet = new JTable(JPController.buildTableModel(res));	}
					catch (SQLException e) { e.printStackTrace(); }
					base.exportMembers(dataSet, fileChoose.getSelectedFile());
				}
			}
		});
		
		clearAttendanceData.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				JPanel temp = new JPanel();
				int valueReturned = JOptionPane.showConfirmDialog(temp, "Are you sure you want to clear all attendance data from the database?");
				if(valueReturned == JOptionPane.OK_OPTION)
				{	base.clearAttendaceData();	}
			}
		});
		
		viewAttendanceData.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{	
				JPController.dataRequested = 1;
				base.changeState(JPViewStates.VIEWDATA);	
			}
		});
		
		exportAttendanceData.addActionListener(new ActionListener()
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
					ResultSet res = base.getAttendanceData();
					try 
					{	dataSet = new JTable(JPController.buildTableModel(res));	}
					catch (SQLException e) { e.printStackTrace(); }
					System.out.println(fileChoose.getSelectedFile().getPath());
					base.exportMembers(dataSet, fileChoose.getSelectedFile());
				}
			}
		});
	
	}
	
	private void assignValues()
	{
		firstName = firstNameField.getText();
		lastName = lastNameField.getText();
		SCAName = SCANameField.getText();
		membershipNumber = memberNumberField.getText();
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
	}
	
}
