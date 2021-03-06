/**
 * @author Ariana Fairbanks
 * Used for viewing both member and attendance data.
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import adapter.JPController;
import adapter.JPViewStates;
import model.CustomTableModel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class JPViewRecords extends JPanel 
{
	private static final long serialVersionUID = -7386656563185975615L;
	private JPController base;
	private GridBagLayout layout;
	private JButton backButton;
	private JTable dataSet;
	private ListSelectionModel listSelectionModel;
	private JScrollPane scrollPane;
    private TableRowSorter<TableModel> rowSorter;
    private JTextField textField;
    private JLabel totalLabel;
    private int value;
	
	public JPViewRecords(JPController base)
	{
		this.base = base;
		layout = new GridBagLayout();
		backButton = new JButton(" BACK ");
		dataSet = new JTable();
		value = base.dataRequested;

		ResultSet res = base.getMemberData();
		if(value == 1)
		{	res = base.getAttendanceData();	}
		
		try 
		{	dataSet = new JTable(CustomTableModel.buildTableModel(res, value));	}
		catch (SQLException e) { e.printStackTrace(); }
		
		dataSet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rowSorter = new TableRowSorter<>(dataSet.getModel());
		dataSet.setRowSorter(rowSorter);
		listSelectionModel = dataSet.getSelectionModel();
		listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelectionModel.addListSelectionListener(new DefaultTableColumnModel());
		dataSet.setSelectionModel(listSelectionModel);
		textField = new JTextField();
		totalLabel = new JLabel("People: "  + JPController.people
						   + "   Members: " + JPController.members
						   + "   Adults: "  + JPController.adults
						   + "   Feasts: "  + JPController.feasts);
		setUpLayout();
		setUpListeners();
	}
	
	private void setUpLayout() 
	{
		layout.rowHeights = new int[]{0, 0, 0, 0, 0};
		layout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, 20.0};
		layout.columnWidths = new int[]{0, 0, 0};
		layout.columnWeights = new double[]{0.0, 1.0, 1.0};
		setLayout(layout);
		setBorder(new LineBorder(new Color(0, 128, 0), 10));
		setForeground(new Color(0, 255, 255));
		setBackground(new Color(245, 245, 245));
		
		backButton.setFont(new Font("Arial", Font.PLAIN, 25));
		backButton.setForeground(new Color(0, 100, 0));
		backButton.setBackground(new Color(0, 0, 0));
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new LineBorder(new Color(0, 128, 0), 2));
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_backButton.insets = new Insets(20, 20, 5, 5);
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 0;		
		
		textField.setForeground(new Color(0, 100, 0));
		textField.setFont(new Font("Arial", Font.PLAIN, 17));
		textField.setBackground(new Color(245, 250, 245));
		textField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0, 128, 0), 2), BorderFactory.createEmptyBorder(2, 20, 2, 2)));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.insets = new Insets(10, 50, 5, 50);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 2;
		
		dataSet.setForeground(Color.BLACK);
		dataSet.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		dataSet.setBackground(new Color(245, 250, 245));
		dataSet.setCellSelectionEnabled(false);
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
		gbc_dataSet.gridwidth = 3;
		gbc_dataSet.gridy = 4;
		gbc_dataSet.insets = new Insets(0, 20, 20, 20);
		gbc_dataSet.fill = GridBagConstraints.BOTH;
		gbc_dataSet.gridx = 0;
		
		totalLabel.setForeground(new Color(34, 139, 34));
		totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		totalLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_totalLabel = new GridBagConstraints();
		gbc_totalLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_totalLabel.gridwidth = 2;
		gbc_totalLabel.insets = new Insets(20, 0, 5, 20);
		gbc_totalLabel.gridx = 1;
		gbc_totalLabel.gridy = 0;
		
		add(backButton, gbc_backButton);
		add(textField, gbc_textField);
		add(scrollPane, gbc_dataSet);
		add(totalLabel, gbc_totalLabel);
	}
	
	private void setUpListeners() 
	{
		backButton.addActionListener(onClick ->
		{
			base.changeState(JPViewStates.DEFAULT);
			base.dataRequested = 0;
		});
		
		textField.getDocument().addDocumentListener(new DocumentListener()
		{
            @Override
            public void insertUpdate(DocumentEvent e) 
            {
                String text = textField.getText();
                if (text.trim().length() == 0) 
                {	 rowSorter.setRowFilter(null);	} 
                else 
                {	rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));	}
            }

            @Override
            public void removeUpdate(DocumentEvent e) 
            {
                String text = textField.getText();
                if (text.trim().length() == 0) 
                {	rowSorter.setRowFilter(null);	} 
                else 
                {	rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));	}
            }

            @Override
            public void changedUpdate(DocumentEvent e) 
            {	throw new UnsupportedOperationException("Not supported."); }
        });
	}
	
}
