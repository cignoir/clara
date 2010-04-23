package view;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import bbs.Thr;
import bbs.ThreadGetter;

//VS4E -- DO NOT REMOVE THIS LINE!
public class ThreadDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JList salonList;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane0;
	private JButton selectSalonButton;
	private JTextField userURL;
	private JButton selectUserTRButton;
	private JButton selectBattleButon;
	private JLabel salonLabel;
	private JLabel battleLabel;
	private JButton cancelButton;
	private JList battleList;
	
	private List<Thr> salonThList;
	private List<Thr> battleThList;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	public ThreadDialog() {
		initComponents();
	}

	public ThreadDialog(Frame parent) {
		super(parent);
		initComponents();
	}

	public ThreadDialog(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public ThreadDialog(Frame parent, String title) {
		super(parent, title);
		initComponents();
	}

	public ThreadDialog(Frame parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
		setFontAll();
		setResizable(false);
		
	}

	private void setFontAll() {
		selectSalonButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
		selectBattleButon.setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
		selectUserTRButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
		salonLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
		battleLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
		salonList.setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
		battleList.setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
		userURL.setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
		cancelButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
	}

	public ThreadDialog(Frame parent, String title, boolean modal, GraphicsConfiguration arg) {
		super(parent, title, modal, arg);
		initComponents();
	}

	public ThreadDialog(Dialog parent) {
		super(parent);
		initComponents();
	}

	public ThreadDialog(Dialog parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public ThreadDialog(Dialog parent, String title) {
		super(parent, title);
		initComponents();
	}

	public ThreadDialog(Dialog parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}

	public ThreadDialog(Dialog parent, String title, boolean modal, GraphicsConfiguration arg) {
		super(parent, title, modal, arg);
		initComponents();
	}

	public ThreadDialog(Window parent) {
		super(parent);
		initComponents();
	}

	public ThreadDialog(Window parent, ModalityType modalityType) {
		super(parent, modalityType);
		initComponents();
	}

	public ThreadDialog(Window parent, String title) {
		super(parent, title);
		initComponents();
	}

	public ThreadDialog(Window parent, String title, ModalityType modalityType) {
		super(parent, title, modalityType);
		initComponents();
	}

	public ThreadDialog(Window parent, String title, ModalityType modalityType, GraphicsConfiguration arg) {
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
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getJScrollPane0(), new Constraints(new Leading(5, 454, 6, 6), new Leading(37, 124, 6, 6)));
		add(getSelectSalonButton(), new Constraints(new Leading(363, 96, 6, 6), new Leading(161, 10, 10)));
		add(getUserURL(), new Constraints(new Leading(6, 452, 6, 6), new Leading(370, 10, 10)));
		add(getSelectUserTRButton(), new Constraints(new Leading(362, 96, 6, 6), new Leading(400, 10, 10)));
		add(getSalonLabel(), new Constraints(new Leading(6, 310, 6, 6), new Leading(15, 6, 6)));
		add(getJScrollPane1(), new Constraints(new Leading(4, 456, 6, 6), new Leading(207, 129, 6, 6)));
		add(getBattleLabel(), new Constraints(new Leading(5, 183, 10, 10), new Leading(185, 6, 6)));
		add(getSelectBattleButon(), new Constraints(new Leading(362, 96, 6, 6), new Leading(336, 6, 6)));
		add(getCancelButton(), new Constraints(new Leading(4, 109, 10, 10), new Leading(434, 6, 6)));
		setSize(483, 500);
	}

	private JList getBattleList() {
		if (battleList == null) {
			battleList = new JList();
			DefaultListModel listModel = new DefaultListModel();
//			listModel.addElement("item0");
//			listModel.addElement("item1");
//			listModel.addElement("item2");
//			listModel.addElement("item3");
			try {
				battleThList = ThreadGetter.getThreadList(3867);
			} catch (IOException e) {
			}
			for(Thr thr: battleThList) {
				listModel.addElement(thr.getName());
			}
			battleList.setModel(listModel);
		}
		return battleList;
	}

	private JLabel getSalonLabel() {
		if (salonLabel == null) {
			salonLabel = new JLabel();
			salonLabel.setText("萃磨選堆 - 東方非想天則掲示板");
		}
		return salonLabel;
	}
	
	private JLabel getBattleLabel() {
		if (battleLabel == null) {
			battleLabel = new JLabel();
			battleLabel.setText("東方非想天則対戦募集用掲示板");
		}
		return battleLabel;
	}

	private JButton getSelectBattleButon() {
		if (selectBattleButon == null) {
			selectBattleButon = new JButton();
			selectBattleButon.setText("決定");
			selectBattleButon.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					selectBattleButonActionActionPerformed(event);
				}
			});
		}
		return selectBattleButon;
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

	private JButton getSelectUserTRButton() {
		if (selectUserTRButton == null) {
			selectUserTRButton = new JButton();
			selectUserTRButton.setText("決定");
			selectUserTRButton.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					selectUserTRButtonActionActionPerformed(event);
				}
			});
		}
		return selectUserTRButton;
	}

	private JTextField getUserURL() {
		if (userURL == null) {
			userURL = new JTextField();
			userURL.setText("http://");
			userURL.addFocusListener(new FocusAdapter() {
	
				public void focusGained(FocusEvent event) {
					userURLFocusFocusGained(event);
				}
			});
		}
		return userURL;
	}

	private JButton getSelectSalonButton() {
		if (selectSalonButton == null) {
			selectSalonButton = new JButton();
			selectSalonButton.setText("決定");
			selectSalonButton.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					selectSalonButtonActionActionPerformed(event);
				}
			});
		}
		return selectSalonButton;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getSalonList());
		}
		return jScrollPane0;
	}

	private JList getSalonList() {
		if (salonList == null) {
			salonList = new JList();
			DefaultListModel listModel = new DefaultListModel();
//			listModel.addElement("item0");
//			listModel.addElement("item1");
//			listModel.addElement("item2");
//			listModel.addElement("item3");
			try {
				salonThList = ThreadGetter.getThreadList(48698);
			} catch (IOException e) {
			}
			for(Thr thr: salonThList) {
				listModel.addElement(thr.getName());
			}
			salonList.setModel(listModel);
		}
		return salonList;
	}

//	private void addItems(int boardNum, DefaultListModel listModel) {
//		try {
//			List<Thr> list =  ThreadGetter.getThreadList(boardNum);
//			for(Thr thr: list) {
//				listModel.addElement(thr.getName());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getBattleList());
		}
		return jScrollPane1;
	}

	private void cancelButtonActionActionPerformed(ActionEvent event) {
		this.setVisible(false);
	}

	private void selectSalonButtonActionActionPerformed(ActionEvent event) {
		MainFrame.thread = salonThList.get(salonList.getSelectedIndex());
		this.setVisible(false);
	}

	private void selectBattleButonActionActionPerformed(ActionEvent event) {
		MainFrame.thread = battleThList.get(battleList.getSelectedIndex());
		this.setVisible(false);
	}

	private void selectUserTRButtonActionActionPerformed(ActionEvent event) {
		MainFrame.thread = new Thr("", userURL.getText(), null);
		this.setVisible(false);
	}

	private void userURLFocusFocusGained(FocusEvent event) {
		userURL.setSelectionStart(0);
	}

}
