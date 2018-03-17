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
import javax.swing.JFileChooser;
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
	private JLabel label;
	private JTextField textField;
	private JButton updateButton;
	private JComboBox comboBox;
	
	public Default(JPController base)
	{
		this.base = base;
		fileChoose = new JFileChooser();
		layout = new GridBagLayout();
		clearAttendanceData = new JButton(" CLEAR ATND DATA ");
		viewAttendanceData = new JButton(" VIEW ATND RECORDS ");
		exportAttendanceData = new JButton(" EXPORT ATND DATA ");
		label = new JLabel(" Temp Field ");
		textField = new JTextField();
		updateButton = new JButton(" Update Records ");
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {" Add New Attendee Record", " Add New Member", " Update Attendee Record", " Update Membership Record", " Delete Existing Attendee", " Delete Existing Member"}));
		
		setUpLayout();
		setUpListeners();
	}

	private void setUpLayout() 
	{
		layout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		layout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
		layout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		layout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		setLayout(layout);
		setBorder(new LineBorder(new Color(128, 128, 128), 10));
		setForeground(new Color(105, 105, 105));
		setBackground(new Color(0, 0, 0));
		
		
		clearAttendanceData.setFont(new Font("Arial", Font.PLAIN, 18));
		clearAttendanceData.setForeground(Color.LIGHT_GRAY);
		clearAttendanceData.setBackground(new Color(105, 105, 105));
		clearAttendanceData.setFocusPainted(false);
		clearAttendanceData.setContentAreaFilled(false);
		clearAttendanceData.setBorder(new LineBorder(Color.GRAY, 2));
		clearAttendanceData.setToolTipText("Clears all data from the table containing attendance records.");
		GridBagConstraints gbc_clearAttendanceData = new GridBagConstraints();
		gbc_clearAttendanceData.fill = GridBagConstraints.HORIZONTAL;
		gbc_clearAttendanceData.gridwidth = 2;
		gbc_clearAttendanceData.anchor = GridBagConstraints.NORTH;
		gbc_clearAttendanceData.insets = new Insets(5, 20, 5, 0);
		gbc_clearAttendanceData.gridx = 0;
		gbc_clearAttendanceData.gridy = 1;
		
		viewAttendanceData.setFont(new Font("Arial", Font.PLAIN, 18));
		viewAttendanceData.setForeground(Color.LIGHT_GRAY);
		viewAttendanceData.setBackground(new Color(105, 105, 105));
		viewAttendanceData.setFocusPainted(false);
		viewAttendanceData.setContentAreaFilled(false);
		viewAttendanceData.setBorder(new LineBorder(Color.GRAY, 2));
		viewAttendanceData.setToolTipText("View and search all event attendance data.");
		GridBagConstraints gbc_viewAttendanceData = new GridBagConstraints();
		gbc_viewAttendanceData.fill = GridBagConstraints.HORIZONTAL;
		gbc_viewAttendanceData.anchor = GridBagConstraints.NORTH;
		gbc_viewAttendanceData.insets = new Insets(5, 15, 5, 0);
		gbc_viewAttendanceData.gridx = 4;
		gbc_viewAttendanceData.gridy = 1;
		
		exportAttendanceData.setFont(new Font("Arial", Font.PLAIN, 18));
		exportAttendanceData.setForeground(Color.LIGHT_GRAY);
		exportAttendanceData.setBackground(new Color(105, 105, 105));
		exportAttendanceData.setFocusPainted(false);
		exportAttendanceData.setContentAreaFilled(false);
		exportAttendanceData.setBorder(new LineBorder(Color.GRAY, 2));
		exportAttendanceData.setToolTipText("Export all membership records in the database as a CSV file.");
		GridBagConstraints gbc_exportAttendanceData = new GridBagConstraints();
		gbc_exportAttendanceData.gridwidth = 2;
		gbc_exportAttendanceData.fill = GridBagConstraints.HORIZONTAL;
		gbc_exportAttendanceData.anchor = GridBagConstraints.NORTH;
		gbc_exportAttendanceData.insets = new Insets(5, 15, 5, 0);
		gbc_exportAttendanceData.gridx = 2;
		gbc_exportAttendanceData.gridy = 1;
		
		
		
		label.setForeground(new Color(135, 206, 235));
		label.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(5, 20, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 4;
		
		textField.setFont(new Font("Arial", Font.PLAIN, 20));
		textField.setForeground(new Color(173, 216, 230));
		textField.setBackground(new Color(0, 0, 0));
		textField.setToolTipText("Username");
		textField.setBorder(new CompoundBorder(new LineBorder(new Color(30, 144, 255)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 10, 5, 10);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 4;
		
		
		
		updateButton.setFont(new Font("Arial", Font.PLAIN, 20));
		updateButton.setForeground(Color.LIGHT_GRAY);
		updateButton.setBackground(new Color(105, 105, 105));
		updateButton.setFocusPainted(false);
		updateButton.setContentAreaFilled(false);
		updateButton.setBorder(new LineBorder(Color.GRAY, 2, true));
		GridBagConstraints gbc_updateButton = new GridBagConstraints();
		gbc_updateButton.gridwidth = 6;
		gbc_updateButton.anchor = GridBagConstraints.EAST;
		gbc_updateButton.insets = new Insets(0, 0, 20, 20);
		gbc_updateButton.gridx = 2;
		gbc_updateButton.gridy = 8;
		
		comboBox.setForeground(Color.WHITE);
		comboBox.setBackground(Color.BLACK);
		comboBox.setEditable(true);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 4;
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.insets = new Insets(10, 20, 10, 5);
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 3;
		clearMemberData = new JButton(" CLEAR MEMBER DATA ");
		
		
		clearMemberData.setFont(new Font("Arial", Font.PLAIN, 18));
		clearMemberData.setForeground(Color.LIGHT_GRAY);
		clearMemberData.setBackground(new Color(105, 105, 105));
		clearMemberData.setFocusPainted(false);
		clearMemberData.setContentAreaFilled(false);
		clearMemberData.setBorder(new LineBorder(Color.GRAY, 2));
		clearMemberData.setToolTipText("Clears all data from the table containing membership records.");
		GridBagConstraints gbc_clearMemberData = new GridBagConstraints();
		gbc_clearMemberData.gridwidth = 2;
		gbc_clearMemberData.anchor = GridBagConstraints.NORTHWEST;
		gbc_clearMemberData.insets = new Insets(20, 20, 5, 0);
		gbc_clearMemberData.gridx = 0;
		gbc_clearMemberData.gridy = 0;
		add(clearMemberData, gbc_clearMemberData);
		exportMemberData = new JButton(" EXPORT MEMBER DATA ");
		
		exportMemberData.setFont(new Font("Arial", Font.PLAIN, 18));
		exportMemberData.setForeground(Color.LIGHT_GRAY);
		exportMemberData.setBackground(new Color(105, 105, 105));
		exportMemberData.setFocusPainted(false);
		exportMemberData.setContentAreaFilled(false);
		exportMemberData.setBorder(new LineBorder(Color.GRAY, 2));
		exportMemberData.setToolTipText("Export all membership records in the database as a CSV file.");
		GridBagConstraints gbc_exportMemberData = new GridBagConstraints();
		gbc_exportMemberData.gridwidth = 2;
		gbc_exportMemberData.anchor = GridBagConstraints.SOUTHWEST;
		gbc_exportMemberData.insets = new Insets(20, 15, 5, 0);
		gbc_exportMemberData.gridx = 2;
		gbc_exportMemberData.gridy = 0;
		add(exportMemberData, gbc_exportMemberData);
		viewMemberData = new JButton(" VIEW MEMBERS ");
		
		viewMemberData.setFont(new Font("Arial", Font.PLAIN, 18));
		viewMemberData.setForeground(Color.LIGHT_GRAY);
		viewMemberData.setBackground(new Color(105, 105, 105));
		viewMemberData.setFocusPainted(false);
		viewMemberData.setContentAreaFilled(false);
		viewMemberData.setBorder(new LineBorder(Color.GRAY, 2));
		viewMemberData.setToolTipText("View and search all records in the member table.");
		GridBagConstraints gbc_viewMemberData = new GridBagConstraints();
		gbc_viewMemberData.fill = GridBagConstraints.HORIZONTAL;
		gbc_viewMemberData.anchor = GridBagConstraints.NORTH;
		gbc_viewMemberData.insets = new Insets(20, 15, 5, 0);
		gbc_viewMemberData.gridx = 4;
		gbc_viewMemberData.gridy = 0;
		add(viewMemberData, gbc_viewMemberData);
		importMembers = new JButton(" IMPORT MEMBERS ");
		
		importMembers.setFont(new Font("Arial", Font.PLAIN, 18));
		importMembers.setForeground(Color.LIGHT_GRAY);
		importMembers.setBackground(new Color(105, 105, 105));
		importMembers.setFocusPainted(false);
		importMembers.setContentAreaFilled(false);
		importMembers.setBorder(new LineBorder(Color.GRAY, 2));
		importMembers.setToolTipText("Import member data via CSV file. Importing an already existing member will update their information.");
		GridBagConstraints gbc_importMembers = new GridBagConstraints();
		gbc_importMembers.anchor = GridBagConstraints.NORTHWEST;
		gbc_importMembers.insets = new Insets(20, 20, 5, 20);
		gbc_importMembers.gridx = 5;
		gbc_importMembers.gridy = 0;		
		
		add(importMembers, gbc_importMembers);
		add(clearAttendanceData, gbc_clearAttendanceData);
		add(viewAttendanceData, gbc_viewAttendanceData);
		add(exportAttendanceData, gbc_exportAttendanceData);
		add(label, gbc_label);
		add(textField, gbc_textField);
		add(updateButton, gbc_updateButton);
		add(comboBox, gbc_comboBox);
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
			        {	return f.getName().endsWith(".csv");	}
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
				{
					base.clearData();
				}
			}
		});
		
		viewMemberData.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				base.changeState(JPViewStates.VIEWMEMBERDATA);
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
					{	dataSet = new JTable(JPController.buildTableModel(res));	}
					catch (SQLException e) { e.printStackTrace(); }
					base.exportMembers(dataSet, fileChoose.getSelectedFile());
				}
			}
		});
	
	}
	
}
