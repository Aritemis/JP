/**
 *	@author Ariana Fairbanks
 */

package view;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import adapter.JPController;
import adapter.JPViewStates;

public class Frame extends JFrame
{
	public static Dimension DIMENSIONS;
	private static final long serialVersionUID = -2248105492340561524L;
	private JPController base;
	private JPanel panel;
	private Default def;
	private Dimension minSize;
	

	public Frame(JPController base)
	{
		this.base = base;
		def = new Default(base);
		panel = def;
		minSize = new Dimension(960, 600);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);
		setMinimumSize(minSize);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
		DIMENSIONS = minSize;
	}
	
	public void updateState(JPViewStates state)
	{
		switch(state)
		{
			case DEFAULT:
				panel.removeAll();
				panel = new Default(base);
				break;
			case VIEWDATA:
				panel.removeAll();
				panel = new JPViewRecords(base);
				break;
			default:
				panel.removeAll();
				panel = new Default(base);
				break;
		}	
		setContentPane(panel);
		panel.revalidate();
		panel.repaint();
	}
	
	
}
