//With the JTable class you can display tables of data, optionally allowing the user to edit the data. JTable does not contain or cache data; it is simply a view of your data. 

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

public class Main implements ListSelectionListener 
{

	String[] headings = { "Name", "Customer ID", "Order #", "Status" };  //headings --> intestazioni; è la prima riga della tabella, non ci posso cliccare sopra. VETTORE DI STRINGHE
	
	//matrice di oggetti
	Object[][] data = { { "A",   new Integer(3),    "0",   new Date() },   //le lettere sotto Name, i new Integer sotto Customer ID, i numeri tra "" sotto Order#, new Date sotto Status 
						{ "B",   new Integer(6),    "4",   new Date() },   //new Date() stampa data e ora del momento in cui si avvia il debug
						{ "C",   new Integer(9),    "9",   new Date() },   //headings e data sono il contenuto della tabella
						{ "D", 	 new Integer(7),    "1",   new Date() },
						{ "E",   new Integer(4),    "1",   new Date() },
						{ "F",   new Integer(8),    "2",   new Date() }, 
						{ "G",   new Integer(6),    "1",   new Date() } };

	JTable jtabOrders = new JTable(data, headings);   //JTable è il contenuto della tabella  (è il contrario della lista, DefaultListModel)
	
	TableModel tm;  //tableModel è la parte visiva della tabella  (JList)

	Main()  //costruttore
	{
		JFrame jfrm = new JFrame("JTable Event Demo");
		jfrm.setSize(750, 200);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jtabOrders.setPreferredScrollableViewportSize(new Dimension(420, 62));

		//ListSelectionModel praticamente sarebbe una lista  
		ListSelectionModel rowSelMod = jtabOrders.getSelectionModel();    //getSelectionModel() --> Returns the ListSelectionModel that is used to maintain row selection state. per le righe
		//per certi versi si può dire che una tabella è due o più liste, quindi ha le stesse caratteristiche delle liste (per esempio di possono selezionare le linee) 

		ListSelectionModel colSelMod = jtabOrders.getColumnModel().getSelectionModel();  //getColumnModel() --> Returns the TableColumnModel that contains all column information of this table.
		//getSelectionModel() --> Returns the current selection model.
		
		rowSelMod.addListSelectionListener(this);  //Add a listener to the list that's notified each time a change to the selection occurs.
		colSelMod.addListSelectionListener(this);

		tm = jtabOrders.getModel();   //getModel() --> Returns the TableModel that provides the data displayed by this JTable

		tm.addTableModelListener(new TableModelListener() //TableModelListener defines the interface for an object that listens to changes in a TableModel.
		{
			//TableModelEvent is used to notify listeners that a table model has changed.
			//The model event describes changes to a TableModel and all references to rows and columns are in the co-ordinate system of the model
			public void tableChanged(TableModelEvent tme)
			{
				if (tme.getType() == TableModelEvent.UPDATE) //getType() --> Returns the type of event - one of: INSERT, UPDATE and DELETE
				{
					System.out.println("Cell " + tme.getFirstRow() + ", " + tme.getColumn() + " changed."  //getFirstRow() --> Returns the first row that changed
							+ " The new value: " + tm.getValueAt(tme.getFirstRow(), tme.getColumn()));
				}
			}
		});
		jfrm.add(new JScrollPane(jtabOrders));
		jfrm.setVisible(true);

	}

	public void valueChanged(ListSelectionEvent le)
	{
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

	public static void main(String args[]) 
	{
		new Main();
	}
}