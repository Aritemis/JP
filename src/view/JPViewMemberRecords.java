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
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import adapter.JPController;
import adapter.JPViewStates;
import model.JPSQLiteData;

public class JPViewMemberRecords extends JPanel 
{
	private static final long serialVersionUID = -7386656563185975615L;
	private JPController base;
	private GridBagLayout layout;
	private JButton backButton;
	private JTable dataSet;
	private JScrollPane scrollPane;
	
	public JPViewMemberRecords(JPController base)
	{
		this.base = base;
		layout = new GridBagLayout();
		backButton = new JButton(" BACK ");
		dataSet = new JTable();

		if(JPSQLiteData.hasData)
		{
			ResultSet res = base.getMemberData();
			try 
			{	dataSet = new JTable(JPController.buildTableModel(res));	}
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
		setBorder(new LineBorder(new Color(0, 128, 0), 10));
		setForeground(new Color(0, 255, 255));
		setBackground(new Color(255, 255, 255));
		
		backButton.setFont(new Font("Arial", Font.PLAIN, 20));
		backButton.setForeground(new Color(46, 139, 87));
		backButton.setBackground(new Color(0, 0, 0));
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new LineBorder(new Color(0, 100, 0), 2));
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_backButton.insets = new Insets(20, 20, 5, 5);
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 0;		
		
		dataSet.setForeground(Color.BLACK);
		dataSet.setFont(new Font("Arial", Font.PLAIN, 15));
		dataSet.setBackground(Color.WHITE);
		//dataSet.s
		JTableHeader header = dataSet.getTableHeader();
		header.setForeground(new Color(46, 139, 87));
		header.setFont(new Font("Arial", Font.PLAIN, 20));
		header.setBackground(Color.WHITE);
		header.setBorder(new LineBorder(Color.LIGHT_GRAY));
		scrollPane = new JScrollPane(dataSet);
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 100, 0)));
		scrollPane.getViewport().setForeground(Color.BLACK);
		scrollPane.getViewport().setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBorder(new LineBorder(new Color(0, 128, 0), 2));
		GridBagConstraints gbc_dataSet = new GridBagConstraints();
		gbc_dataSet.gridwidth = 5;
		gbc_dataSet.gridy = 4;
		gbc_dataSet.insets = new Insets(0, 20, 20, 20);
		gbc_dataSet.fill = GridBagConstraints.BOTH;
		gbc_dataSet.gridx = 0;
		
		add(backButton, gbc_backButton);
		add(scrollPane, gbc_dataSet);
	}
	
	private void setUpListeners() 
	{
		backButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick)
			{	
				dataSet = null;
				base.changeState(JPViewStates.DEFAULT);	
			}
		});
	}
}
