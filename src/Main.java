import java.awt.Dimension;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class Main implements ListSelectionListener {

  String[] headings = { "Name", "Customer ID", "Order #", "Status" };

  Object[][] data = { { "A", new Integer(3), "0", new Date() },
      { "B", new Integer(6), "4", new Date() }, { "C", new Integer(9), "9", new Date() },
      { "D", new Integer(7), "1", new Date() }, { "E", new Integer(4), "1", new Date() },
      { "F", new Integer(8), "2", new Date() }, { "G", new Integer(6), "1", new Date() } };

  JTable jtabOrders = new JTable(data, headings);

  TableModel tm;

  Main() {
    JFrame jfrm = new JFrame("JTable Event Demo");
    jfrm.setSize(400, 200);
    jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    

    jtabOrders.setPreferredScrollableViewportSize(new Dimension(420, 62));

    ListSelectionModel rowSelMod = jtabOrders.getSelectionModel();

    ListSelectionModel colSelMod = jtabOrders.getColumnModel().getSelectionModel();

    rowSelMod.addListSelectionListener(this);
    colSelMod.addListSelectionListener(this);

    tm = jtabOrders.getModel();

    tm.addTableModelListener(new TableModelListener() {

      public void tableChanged(TableModelEvent tme) {
        if (tme.getType() == TableModelEvent.UPDATE) {
          System.out.println("Cell " + tme.getFirstRow() + ", " + tme.getColumn() + " changed."
              + " The new value: " + tm.getValueAt(tme.getFirstRow(), tme.getColumn()));
        }
      }
    });
    jfrm.add(new JScrollPane(jtabOrders));
    jfrm.setVisible(true);

  }

  public void valueChanged(ListSelectionEvent le) {
    String str = "Selected Row(s): ";
    int[] rows = jtabOrders.getSelectedRows();
    for (int i = 0; i < rows.length; i++)
      str += rows[i] + " ";

    str += "Selected Column(s): ";
    int[] cols = jtabOrders.getSelectedColumns();

    for (int i = 0; i < cols.length; i++)
      str += cols[i] + " ";

    str += "Selected Cell: " + jtabOrders.getSelectedRow() + ", "
        + jtabOrders.getSelectedColumn();
    System.out.println(str);
  }

  public static void main(String args[]) {
    new Main();
  }
}