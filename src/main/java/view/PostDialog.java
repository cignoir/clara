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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import bbs.PostToBBS;

//VS4E -- DO NOT REMOVE THIS LINE!
public class PostDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel nameLabel;
	private JLabel mailLabel;
	private JTextField nameTF;
	private JTextField mailTF;
	private JTextArea messageTA;
	private JScrollPane scrollPane;
	private JButton postButton;
	private JButton cancelButton;
	private String url;
	private String name;
	private String mail;
	private String message;
	private static final String FONT_NAME = "MS UI Gothic";
	private static final int FONT_SIZE = 12;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	
	public PostDialog(int posx, int posy, String threadName, String url, String name, String mail, String message) {
		this.url = url;
		this.name = name;
		this.mail = mail;
		this.message = message;
		initComponents();
		setFontAll();
		setResizable(false);
		setTitle("投稿確認画面 ： " + threadName);
		setLocation(posx, posy);
		
	}

	public PostDialog(Frame parent) {
		super(parent);
		initComponents();
	}

	public PostDialog(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public PostDialog(Frame parent, String title) {
		super(parent, title);
		initComponents();
	}

	public PostDialog(Frame parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}

	public PostDialog(Frame parent, String title, boolean modal, GraphicsConfiguration arg) {
		super(parent, title, modal, arg);
		initComponents();
	}

	public PostDialog(Dialog parent) {
		super(parent);
		initComponents();
	}

	public PostDialog(Dialog parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public PostDialog(Dialog parent, String title) {
		super(parent, title);
		initComponents();
	}

	public PostDialog(Dialog parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}

	public PostDialog(Dialog parent, String title, boolean modal, GraphicsConfiguration arg) {
		super(parent, title, modal, arg);
		initComponents();
	}

	public PostDialog(Window parent) {
		super(parent);
		initComponents();
	}

	public PostDialog(Window parent, ModalityType modalityType) {
		super(parent, modalityType);
		initComponents();
	}

	public PostDialog(Window parent, String title) {
		super(parent, title);
		initComponents();
	}

	public PostDialog(Window parent, String title, ModalityType modalityType) {
		super(parent, title, modalityType);
		initComponents();
	}

	public PostDialog(Window parent, String title, ModalityType modalityType, GraphicsConfiguration arg) {
		super(parent, title, modalityType, arg);
		initComponents();
	}

	private void initComponents() {
		try {
			UIManager.setLookAndFeel(PREFERRED_LOOK_AND_FEEL);
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setBackground(Color.white);
		setResizable(false);
		setModal(true);
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getNameLabel(), new Constraints(new Leading(20, 10, 10), new Leading(21, 10, 10)));
		add(getMailLabel(), new Constraints(new Leading(20, 6, 6), new Leading(55, 6, 6)));
		add(getScrollPane(), new Constraints(new Leading(19, 498, 10, 10), new Leading(92, 251, 10, 10)));
		add(getPostButton(), new Constraints(new Leading(19, 190, 6, 6), new Leading(359, 10, 10)));
		add(getCancelButton(), new Constraints(new Leading(332, 185, 10, 10), new Leading(359, 6, 6)));
		add(getMailTF(), new Constraints(new Leading(73, 230, 10, 10), new Leading(55, 6, 6)));
		add(getNameTF(), new Constraints(new Leading(73, 230, 6, 6), new Leading(19, 6, 6)));
		setSize(541, 450);
	}

	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("キャンセル");
			cancelButton.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					cancelButtonActionActionPerformed(event);
				}
			});
		}
		return cancelButton;
	}

	private JButton getPostButton() {
		if (postButton == null) {
			postButton = new JButton();
			postButton.setText("投稿");
			postButton.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					postButtonActionActionPerformed(event);
				}
			});
		}
		return postButton;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getMessageTA());
		}
		return scrollPane;
	}

	private JTextArea getMessageTA() {
		if (messageTA == null) {
			messageTA = new JTextArea();
			messageTA.setText(message);
		}
		return messageTA;
	}

	private JTextField getNameTF() {
		if (nameTF == null) {
			nameTF = new JTextField();
			nameTF.setText(name);
		}
		return nameTF;
	}
	
	private JTextField getMailTF() {
		if (mailTF == null) {
			mailTF = new JTextField();
			mailTF.setText(mail);
		}
		return mailTF;
	}

	private JLabel getMailLabel() {
		if (mailLabel == null) {
			mailLabel = new JLabel();
			mailLabel.setText("メール");
		}
		return mailLabel;
	}

	private JLabel getNameLabel() {
		if (nameLabel == null) {
			nameLabel = new JLabel();
			nameLabel.setText("名前");
		}
		return nameLabel;
	}
	
	private void setFontAll() {
		nameLabel.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		nameTF.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		mailLabel.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		mailTF.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		messageTA.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		postButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		cancelButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		
	}

	public PostDialog() {
		initComponents();
	}

	private void postButtonActionActionPerformed(ActionEvent event) {
		startPostingThread(this, postButton);
		new PostToBBS(url, nameTF.getText(), mailTF.getText(), messageTA.getText());
		
	}
	
	synchronized static void startPostingThread(final PostDialog dialog, final JButton postButton) {
		new Thread() {
			@Override
			public void run() {
				postButton.setText("投稿中");
				postButton.setEnabled(false);
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				postButton.setEnabled(true);
				postButton.setBackground(new Color(102, 255, 153));
				postButton.setText("投稿しました");
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				postButton.setBackground(null);
				postButton.setText("投稿");
				dialog.setVisible(false);
			}
		}.start();
	}

	private void cancelButtonActionActionPerformed(ActionEvent event) {
		this.setVisible(false);
	}

}
