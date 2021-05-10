
/* Name: Jean Seda
Course: CNT 4714 – Spring 2021
Assignment title: Project 1 – Event-driven Enterprise Simulation Date: Sunday January 31, 2021
*/

package project1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class Main implements ActionListener {

	JPanel panel = new JPanel();
	JPanel panel2 = new JPanel();
	JPanel panel3 = new JPanel();
	JButton button1 = new JButton("Process Item");
	JButton button2 = new JButton("Confirm Item");
	JButton button3 = new JButton("View Order");
	JButton button4 = new JButton("Finish Order");
	JButton button5 = new JButton("New Order");
	JButton button6 = new JButton("Exit");
	JTextField textfield1;
	JTextField textfield2;
	JTextField textfield3;
	JTextField textfield4;
	JTextField textfield5;
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JLabel label4;
	JLabel label5;
	JFrame frame = new JFrame("Nile Dot Com - Spring 2021 ");

	Scanner scan;

	ArrayList<String> orderList = new ArrayList<>();

	static String itemId, description, stockStatus;
	static Double price;
	boolean stockStatusboo;

	int count = 1;

	String id = null;
	String quantity, orderQty;
	String line = null, line2 = null;
	double qty, total, overallTotal, discTotal, tempOverall;
	int discount = 0, orderQty2;

	public Main() {

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(800, 400);
		frame.getContentPane().setBackground(Color.black);
		frame.setResizable(true);

		panel.setBounds(0, 300, 800, 100);
		panel.setOpaque(true);
		panel.setBackground(Color.lightGray);

		panel2.setBounds(300, 0, 500, 300);
		panel2.setOpaque(true);
		panel2.setBackground(Color.green);
		panel2.setLayout(new FlowLayout(0, 20, 4));

		panel3.setBounds(0, 0, 300, 300);
		panel3.setOpaque(true);
		panel3.setBackground(Color.lightGray);
		panel3.setLayout(new FlowLayout(0, 20, 25));

		if (count == 1) {
			button2.setEnabled(false);
			button3.setEnabled(false);
			button4.setEnabled(false);
		}

		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);
		panel.add(button5);
		panel.add(button6);

		textfield1 = new JTextField();
		textfield1.setPreferredSize(new Dimension(470, 40));

		textfield2 = new JTextField();
		textfield2.setPreferredSize(new Dimension(470, 40));

		textfield3 = new JTextField();
		textfield3.setPreferredSize(new Dimension(470, 40));

		textfield4 = new JTextField();
		textfield4.setPreferredSize(new Dimension(470, 40));
		textfield4.setEditable(false);

		textfield5 = new JTextField();
		textfield5.setPreferredSize(new Dimension(470, 40));
		textfield5.setEditable(false);

		label1 = new JLabel("Enter number of items in this order:      ");

		panel3.add(label1);

		panel2.add(textfield1);

		label2 = new JLabel("Enter item ID for item #" + count + ":     ");
		panel3.add(label2);
		panel2.add(textfield2);

		label3 = new JLabel(" Enter quantity for item #" + count + ":    ");
		panel3.add(label3);
		panel2.add(textfield3);

		label4 = new JLabel("item # " + count + " info:");
		panel3.add(label4);
		panel2.add(textfield4);

		label5 = new JLabel("Order subtotal for " + count + " item(s):    ");
		panel3.add(label5);
		panel2.add(textfield5);

		frame.add(panel);
		frame.add(panel2);
		frame.add(panel3);

		// giving functionality to the buttons

		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);

		frame.setVisible(true);

	}

	public static void main(String[] args) {
		// an item id (a string),
		// a quoted string containing the description of the item,
		// in stock status (a string),
		// and the unit price for one of the item (a double).

		new Main();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == button1) { // Process item button

			try {

				scan = new Scanner(new File("inventory.txt")); // Opening the file
				System.out.print("The file opened\n");
			} catch (FileNotFoundException ex) {

				System.out.println("Problem opening file\n");

			}

			// get item id from user input at button 1
			itemId = textfield2.getText();
			quantity = textfield3.getText(); // quantity of the desired item

			do {
				try {
					id = scan.next();
					id = id.substring(0, id.length() - 1);
					// System.out.print(id);
				} catch (NoSuchElementException ex) {

					// System.out.print("item not found\n");
					id = null;

				}
			} while (!itemId.equals(id) && id != null);

			if (id != null) {

				line = scan.nextLine();

				StringTokenizer stk = new StringTokenizer(line, "\""); // Extracting the description
				stk.nextToken();
				description = stk.nextToken();
				StringTokenizer stk2 = new StringTokenizer(line, ",");
				stk2.nextToken();
				stockStatus = stk2.nextToken();
				stockStatus = stockStatus.trim();

				stockStatusboo = Boolean.parseBoolean(stockStatus); // converting string to boolean

				if (stockStatusboo == false) {
					JOptionPane.showMessageDialog(null,
							"Sorry.. The item selected is out of stock, please try another item");
					return;
				}

				price = Double.parseDouble(stk2.nextToken());
				qty = Double.parseDouble(quantity);

				if (qty >= 1 && qty <= 4) { // Setting the discount value
					discount = 0;

				} else if (qty >= 5 && qty <= 9) {

					discount = 10;
				} else if (qty >= 10 && qty <= 14) {

					discount = 15;

				} else if (qty >= 15) {

					discount = 20;

				}

				total = qty * price;

				// Applying the discount

				if (discount > 0) {
					discTotal = (total * discount) / 100;
					discTotal = total - discTotal;
					System.out.println(discTotal);
				} else if (discount == 0) {

					discTotal = total;
				}

				line2 = id + ", " + "\"" + description + "\"" + ", " + price + ", " + quantity + ", " + discount + "%"
						+ ", " + discTotal;
				textfield4.setText(line2);

				if (count == 1) { // making sure the total is displayed correctly
					textfield5.setText(" " + total);
				} else {
					// Making sure total is displayed correctly in label
					tempOverall = discTotal + overallTotal;
					textfield5.setText("" + tempOverall);

				}

				button2.setEnabled(true);
				button3.setEnabled(true);
				button4.setEnabled(true);

			}

			else {

				JOptionPane.showMessageDialog(null, "Item id " + itemId + " not found");
				button2.setEnabled(false);
				// count = count-1;

			}

		}

		else if (e.getSource() == button2) { // confirming the item

			JOptionPane.showMessageDialog(null, "Item # " + count + " accepted");

			orderQty = textfield1.getText(); // converting string to int

			orderQty2 = Integer.parseInt(orderQty);

			textfield1.setEditable(false); // first entry is disabled after the number of items is entered
			if (count == orderQty2) {

				button1.setEnabled(false);
				textfield2.setEditable(false);
				textfield3.setEditable(false);

			}

			overallTotal = discTotal + overallTotal;

			count++; // incrementing count to be displayed

			label2.setText("Enter item ID for item #" + count + ":     ");
			label3.setText(" Enter quantity for item #" + count + ":    ");
			label4.setText("item # " + (count - 1) + " info:");
			label5.setText("Order subtotal for " + (count - 1) + " item(s):    ");

			button2.setEnabled(false);
			orderList.add(line2);
			// reset buttons for next input
			textfield2.setText("");
			textfield3.setText("");

		} else if (e.getSource() == button3) {
			System.out.print("this is button 3");

			StringBuilder strBuilder = new StringBuilder("");
			for (int i = 0; i < orderList.size(); i++) {
				strBuilder.append((i + 1) + "." + " ");
				strBuilder.append(orderList.get(i));
				strBuilder.append("\n");
			}
			strBuilder.append(" ");
			JOptionPane.showMessageDialog(null, strBuilder.toString(), "View Order", JOptionPane.INFORMATION_MESSAGE);

		} else if (e.getSource() == button4) {

			double taxAmount;
			long time;
			StringBuilder strBuilder = new StringBuilder("");

			time = System.currentTimeMillis(); // working with the time stamp
			SimpleDateFormat format = new SimpleDateFormat(" MM/dd/yy', 'hh:mm:ss a z");
			Date date = new Date(time);

			strBuilder.append("Date:" + format.format(date) + "\n\n");

			strBuilder.append("Number of line items: " + count + " \n\n");

			strBuilder.append("Item# /ID /Title /Price /Qty /Disc % /Subtotal:\n\n");

			// finishing order print the results

			for (int i = 0; i < orderList.size(); i++) {
				strBuilder.append((i + 1) + "." + " ");
				strBuilder.append(orderList.get(i));
				strBuilder.append("\n");
			}

			strBuilder.append("\n\nOrder subtotal: " + "$" + overallTotal + "\n\n");

			strBuilder.append("Tax Rate:    6%\n\n");
			taxAmount = overallTotal * 0.06; // calculating tax amount

			strBuilder.append("Tax Amount:   " + taxAmount + "\n\n");

			strBuilder.append("Order Total  " + (overallTotal + taxAmount) + "\n\n");

			strBuilder.append("Thanks for shopping at Nile Dot Com!");

			// Specify the file name
			File fileOut = new File("transactions.txt");
			FileWriter fw = null;

			if (!fileOut.exists()) {
				try {
					fileOut.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			// True for appending to the file
			try {
				fw = new FileWriter(fileOut, true);
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			BufferedWriter bw = new BufferedWriter(fw);

			for (int i = 0; i < orderList.size(); i++) {
				try {
					bw.write(time + ", " + orderList.get(i) + "," + format.format(date) + "\n");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
			try {
				bw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			JOptionPane.showMessageDialog // displaying information window with the string
			(null, strBuilder.toString(), "View Order", JOptionPane.INFORMATION_MESSAGE);

		} else if (e.getSource() == button5) {

			// restart the transactions
			// new Main();
			frame.dispose();
			new Main();
		} else if (e.getSource() == button6) {

			System.exit(0); // exit the GUI

		}

	}

}
