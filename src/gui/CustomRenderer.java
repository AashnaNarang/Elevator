package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomRenderer extends DefaultTableCellRenderer{
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		Object object = table.getModel().getValueAt(row, column);
		String s = "";
		if(object != null) {
			s = object.toString();
		}
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
