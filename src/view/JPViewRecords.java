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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import adapter.JPController;
import adapter.JPViewStates;
import model.JPSQLiteData;

public class JPViewRecords extends JPanel 
{
	private static final long serialVersionUID = -7386656563185975615L;
	private JPController base;
	private GridBagLayout layout;
	private JLabel header;
	private JButton backButton;
	private JTable studentRecordsSet;
	
	public JPViewRecords(JPController base)
	{
		this.base = base;
		layout = new GridBagLayout();
		backButton = new JButton(" BACK ");
		studentRecordsSet = new JTable();

		if(JPSQLiteData.hasData)
		{
			ResultSet res = base.getData();
			try 
			{	studentRecordsSet = new JTable(buildTableModel(res));	}
			catch (SQLException e) { e.printStackTrace(); }
		}
		removeAll();
		revalidate();
		repaint();
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
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(0, 255, 255));
		setBackground(new Color(0, 0, 0));
		
		backButton.setFont(new Font("MV Boli", Font.PLAIN, 25));
		backButton.setForeground(new Color(135, 206, 250));
		backButton.setBackground(new Color(0, 0, 0));
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_backButton.insets = new Insets(20, 20, 5, 5);
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 0;		
		
		studentRecordsSet.setForeground(new Color(176, 224, 230));
		studentRecordsSet.setFont(new Font("Arial", Font.PLAIN, 20));
		studentRecordsSet.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		studentRecordsSet.setBackground(new Color(0, 0, 0));
		GridBagConstraints gbc_studentRecordsSet = new GridBagConstraints();
		gbc_studentRecordsSet.gridwidth = 5;
		gbc_studentRecordsSet.gridy = 4;
		gbc_studentRecordsSet.insets = new Insets(0, 20, 20, 20);
		gbc_studentRecordsSet.fill = GridBagConstraints.BOTH;
		gbc_studentRecordsSet.gridx = 0;
		
		add(backButton, gbc_backButton);
		add(studentRecordsSet, gbc_studentRecordsSet);
	}
	
	private void setUpListeners() 
	{
		backButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick)
			{	base.changeState(JPViewStates.def);	}
		});
	}
	
	private static DefaultTableModel buildTableModel(ResultSet studentRecords) throws SQLException 
	{
		ResultSetMetaData metaData = studentRecords.getMetaData();

	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) 
	    {	columnNames.add(metaData.getColumnName(column));	}

	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (studentRecords.next()) 
	    {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) 
	        {
	            vector.add(studentRecords.getObject(columnIndex));
	            //System.out.println(studentRecords.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    return new DefaultTableModel(data, columnNames);
	}
}
