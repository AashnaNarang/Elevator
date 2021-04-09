package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * A custom cell renderer that forces cells to have a colored background when
 * it is issued the text "Elevator Here
 *
 */
public class CustomRenderer extends DefaultTableCellRenderer{
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		Object object = table.getModel().getValueAt(row, column);
		String s = "";
		//Check that the cell is not null
		if(object != null) {
			s = object.toString();
		}
		//Color the cell RED or WHITE depending on the text in the cell
		if(s.equals("Elevator Here")) {
			c.setBackground(Color.RED);
			c.setForeground(Color.RED);
		}
		else {
			c.setBackground(Color.WHITE);
			c.setForeground(Color.BLACK);
		}
		return c;
	}
}
