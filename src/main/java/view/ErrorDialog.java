package view;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

//VS4E -- DO NOT REMOVE THIS LINE!
public class ErrorDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel messageLabel;
	private JButton okButton;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	public ErrorDialog() {
		initComponents();
	}

	public ErrorDialog(Frame parent) {
		super(parent);
		initComponents();
	}

	public ErrorDialog(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public ErrorDialog(Frame parent, String title) {
		super(parent, title);
		initComponents();
	}

	public ErrorDialog(Frame parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}
	
	public ErrorDialog(Frame parent, String title, String message, boolean modal, int posx, int posy) {
		super(parent, title, modal);
		initComponents();
		try {
			UIManager.setLookAndFeel(PREFERRED_LOOK_AND_FEEL);
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}

		setFontAll();
		setLocation(posx, posy);
		messageLabel.setText(message);
	}

	public ErrorDialog(Frame parent, String title, boolean modal, GraphicsConfiguration arg) {
		super(parent, title, modal, arg);
		initComponents();
	}

	public ErrorDialog(Dialog parent) {
		super(parent);
		initComponents();
	}

	public ErrorDialog(Dialog parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public ErrorDialog(Dialog parent, String title) {
		super(parent, title);
		initComponents();
	}

	public ErrorDialog(Dialog parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}

	public ErrorDialog(Dialog parent, String title, boolean modal, GraphicsConfiguration arg) {
		super(parent, title, modal, arg);
		initComponents();
	}

	public ErrorDialog(Window parent) {
		super(parent);
		initComponents();
	}

	public ErrorDialog(Window parent, ModalityType modalityType) {
		super(parent, modalityType);
		initComponents();
	}

	public ErrorDialog(Window parent, String title) {
		super(parent, title);
		initComponents();
	}

	public ErrorDialog(Window parent, String title, ModalityType modalityType) {
		super(parent, title, modalityType);
		initComponents();
	}

	public ErrorDialog(Window parent, String title, ModalityType modalityType, GraphicsConfiguration arg) {
		super(parent, title, modalityType, arg);
		initComponents();
	}

	private void initComponents() {
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setBackground(Color.white);
		setResizable(false);
		setModal(true);
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getMessageLabel(), new Constraints(new Leading(9, 181, 10, 10), new Leading(12, 44, 10, 10)));
		add(getOkButton(), new Constraints(new Leading(77, 6, 6), new Leading(62, 6, 6)));
		setSize(200, 133);
	}

	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("OK");
			okButton.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					okButtonActionActionPerformed(event);
				}
			});
		}
		return okButton;
	}
	
	private void setFontAll() {
		setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
		messageLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
		okButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
	}

	private JLabel getMessageLabel() {
		if (messageLabel == null) {
			messageLabel = new JLabel();
			messageLabel.setVerticalTextPosition(SwingConstants.TOP);
		}
		return messageLabel;
	}

	private void okButtonActionActionPerformed(ActionEvent event) {
		setVisible(false);
	}

}
