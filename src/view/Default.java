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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import org.omg.CORBA.Environment;

import adapter.JPController;
import adapter.JPViewStates;

public class Default extends JPanel
{

	private static final long serialVersionUID = 7833894194480819845L;
	private JFileChooser fileChoose;
	private JPController base;
	private GridBagLayout layout;
	private JButton importData;
	private JButton clearData;
	private JButton viewData;
	private JButton exportData;
	private JLabel label;
	private JTextField textField;
	private JButton update;
	
	public Default(JPController base)
	{
		this.base = base;
		fileChoose = new JFileChooser();
		layout = new GridBagLayout();
		importData = new JButton(" IMPORT DATA ");
		clearData = new JButton(" CLEAR DATA ");
		viewData = new JButton(" VIEW DATA ");
		exportData = new JButton(" EXPORT DATA ");
		label = new JLabel(" Temp Field ");
		textField = new JTextField();
		update = new JButton(" Update Records ");
		
		setUpLayout();
		setUpListeners();
	}

	private void setUpLayout() 
	{
		layout.rowHeights = new int[]{0, 25, 0, 0, 0, 0, 0, 0};
		layout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
		layout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		layout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
		setLayout(layout);
		setBorder(new LineBorder(new Color(128, 128, 128), 10));
		setForeground(new Color(105, 105, 105));
		setBackground(new Color(0, 0, 0));
		
		importData.setFont(new Font("MV Boli", Font.PLAIN, 25));
		importData.setForeground(new Color(192, 192, 192));
		importData.setBackground(new Color(105, 105, 105));
		importData.setFocusPainted(false);
		importData.setContentAreaFilled(false);
		importData.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		GridBagConstraints gbc_importData = new GridBagConstraints();
		gbc_importData.anchor = GridBagConstraints.NORTHWEST;
		gbc_importData.insets = new Insets(20, 20, 5, 0);
		gbc_importData.gridx = 0;
		gbc_importData.gridy = 0;		
		
		
		clearData.setFont(new Font("MV Boli", Font.PLAIN, 25));
		clearData.setForeground(new Color(192, 192, 192));
		clearData.setBackground(new Color(105, 105, 105));
		clearData.setFocusPainted(false);
		clearData.setContentAreaFilled(false);
		clearData.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		GridBagConstraints gbc_clearData = new GridBagConstraints();
		gbc_clearData.anchor = GridBagConstraints.NORTHWEST;
		gbc_clearData.insets = new Insets(20, 20, 5, 0);
		gbc_clearData.gridx = 1;
		gbc_clearData.gridy = 0;
		
		viewData.setFont(new Font("MV Boli", Font.PLAIN, 25));
		viewData.setForeground(new Color(192, 192, 192));
		viewData.setBackground(new Color(105, 105, 105));
		viewData.setFocusPainted(false);
		viewData.setContentAreaFilled(false);
		viewData.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		GridBagConstraints gbc_viewData = new GridBagConstraints();
		gbc_viewData.anchor = GridBagConstraints.NORTHWEST;
		gbc_viewData.insets = new Insets(20, 20, 5, 0);
		gbc_viewData.gridx = 2;
		gbc_viewData.gridy = 0;
		
		exportData.setFont(new Font("MV Boli", Font.PLAIN, 25));
		exportData.setForeground(new Color(192, 192, 192));
		exportData.setBackground(new Color(105, 105, 105));
		exportData.setFocusPainted(false);
		exportData.setContentAreaFilled(false);
		exportData.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		GridBagConstraints gbc_exportData = new GridBagConstraints();
		gbc_exportData.anchor = GridBagConstraints.SOUTHWEST;
		gbc_exportData.insets = new Insets(20, 20, 5, 20);
		gbc_exportData.gridx = 3;
		gbc_exportData.gridy = 0;
		
		
		label.setForeground(new Color(135, 206, 235));
		label.setFont(new Font("MV Boli", Font.PLAIN, 30));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.gridwidth = 3;
		gbc_label.insets = new Insets(5, 20, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 2;
		
		textField.setFont(new Font("MV Boli", Font.PLAIN, 20));
		textField.setForeground(new Color(173, 216, 230));
		textField.setBackground(new Color(0, 0, 0));
		textField.setToolTipText("Username");
		textField.setBorder(new CompoundBorder(new LineBorder(new Color(30, 144, 255)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 40, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 3;
		
		update.setFont(new Font("MV Boli", Font.PLAIN, 30));
		update.setForeground(new Color(135, 206, 250));
		update.setBackground(new Color(70, 130, 180));
		update.setFocusPainted(false);
		update.setContentAreaFilled(false);
		update.setBorder(new LineBorder(new Color(70, 130, 180), 2, true));
		GridBagConstraints gbc_update = new GridBagConstraints();
		gbc_update.gridwidth = 6;
		gbc_update.anchor = GridBagConstraints.EAST;
		gbc_update.insets = new Insets(0, 0, 20, 20);
		gbc_update.gridx = 1;
		gbc_update.gridy = 8;
		
		add(importData, gbc_importData);
		add(clearData, gbc_clearData);
		add(viewData, gbc_viewData);
		add(exportData, gbc_exportData);
		add(label, gbc_label);
		add(textField, gbc_textField);
		add(update, gbc_update);
	}
	
	private void setUpListeners() 
	{
		
		importData.addActionListener(new ActionListener() 
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
					base.importUsers(fileChoose.getSelectedFile());
				}
			}
		});
		
		clearData.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				JPanel temp = new JPanel();
				int valueReturned = JOptionPane.showConfirmDialog(temp, "Are you sure you want to clear all data?");
				if(valueReturned == JOptionPane.OK_OPTION)
				{
					base.clearData();
				}
				//System.out.println(valueReturned);
			}
		});
		
		viewData.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				base.changeState(JPViewStates.viewRecords);
			}
		});
		
		exportData.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				
			}
		});
	
	}
	
}
