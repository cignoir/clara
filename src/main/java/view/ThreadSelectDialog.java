package view;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import bbs.Thr;
import bbs.ThreadGetter;

//VS4E -- DO NOT REMOVE THIS LINE!
public class ThreadSelectDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JList boardList;
	private JScrollPane jScrollPane0;
	private JList threadList;
	private JScrollPane jScrollPane1;
	private JButton selectButton;
	private JButton cancelButton;
	private List<Thr> threads;
	private int selectedBoardIndex = 0;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	private static final String FONT_NAME = "MS UI Gothic";
	private static final int FONT_SIZE = 12;

	public ThreadSelectDialog() {
		initComponents();
	}

	public ThreadSelectDialog(Frame parent) {
		super(parent);
		initComponents();
	}

	public ThreadSelectDialog(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public ThreadSelectDialog(Frame parent, String title) {
		super(parent, title);
		initComponents();
	}

	public ThreadSelectDialog(Frame parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}

	public ThreadSelectDialog(Frame parent, String title, boolean modal,
			GraphicsConfiguration arg) {
		super(parent, title, modal, arg);
		initComponents();
	}

	public ThreadSelectDialog(Dialog parent) {
		super(parent);
		initComponents();
	}

	public ThreadSelectDialog(Dialog parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public ThreadSelectDialog(Dialog parent, String title) {
		super(parent, title);
		initComponents();
	}

	public ThreadSelectDialog(Dialog parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}

	public ThreadSelectDialog(Dialog parent, String title, boolean modal,
			GraphicsConfiguration arg) {
		super(parent, title, modal, arg);
		initComponents();
	}

	public ThreadSelectDialog(Window parent) {
		super(parent);
		initComponents();
	}

	public ThreadSelectDialog(Window parent, ModalityType modalityType) {
		super(parent, modalityType);
		initComponents();
	}

	public ThreadSelectDialog(Window parent, String title) {
		super(parent, title);
		initComponents();
	}

	public ThreadSelectDialog(Window parent, String title,
			ModalityType modalityType) {
		super(parent, title, modalityType);
		initComponents();
	}

	public ThreadSelectDialog(Window parent, String title,
			ModalityType modalityType, GraphicsConfiguration arg) {
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
		setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
		setBackground(Color.white);
		setResizable(false);
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getJScrollPane0(), new Constraints(new Leading(20, 206, 10, 10),
				new Leading(31, 216, 10, 10)));
		add(getJScrollPane1(), new Constraints(new Leading(252, 420, 10, 10),
				new Leading(31, 215, 10, 10)));
		add(getSelectButton(), new Constraints(new Leading(465, 207, 10, 10),
				new Leading(265, 29, 10, 10)));
		add(getCancelButton(), new Constraints(new Leading(288, 159, 10, 10),
				new Leading(265, 29, 6, 6)));
		setSize(702, 350);
	}

	private JList getBoardList() {
		if (boardList == null) {
			boardList = new JList();
			boardList.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
			DefaultListModel listModel = new DefaultListModel();
			listModel.addElement("萃磨選堆　東方非想天則掲示板");
			listModel.addElement("東方非想天則　対戦募集用掲示板");
			boardList.setModel(listModel);
			boardList.addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent event) {
					boardListListSelectionValueChanged(event);
				}
			});
		}
		return boardList;
	}

	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
			cancelButton.setText("キャンセル");
			cancelButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					cancelButtonActionActionPerformed(event);
				}
			});
		}
		return cancelButton;
	}

	private JButton getSelectButton() {
		if (selectButton == null) {
			selectButton = new JButton();
			selectButton.setEnabled(false);
			selectButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
			selectButton.setText("決定");
			selectButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					selectButtonActionActionPerformed(event);
				}
			});
		}
		return selectButton;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getThreadList());
		}
		return jScrollPane1;
	}

	private JList getThreadList() {
		if (threadList == null) {
			threadList = new JList();
			threadList.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
			DefaultListModel listModel = new DefaultListModel();
			try {
				threads = ThreadGetter.getThreadList(ThreadGetter.GAME);
				for (Thr th : threads) {
					listModel.addElement(th.getName());
				}
			} catch (IOException e) {
			}
			threadList.setModel(listModel);
			threadList.addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent event) {
					threadListListSelectionValueChanged(event);
				}
			});
		}
		return threadList;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getBoardList());
		}
		return jScrollPane0;
	}

	private void boardListListSelectionValueChanged(ListSelectionEvent event) {
		if (selectedBoardIndex == boardList.getSelectedIndex()) {
			return;
		}
		int selectedBoradNumber = 0;
		switch (boardList.getSelectedIndex()) {
		case 0:
			selectedBoradNumber = ThreadGetter.GAME;
			break;
		case 1:
			selectedBoradNumber = ThreadGetter.NETGAME;
			break;
		}
		selectedBoardIndex = boardList.getSelectedIndex();
		try {
			threads = ThreadGetter.getThreadList(selectedBoradNumber);
		} catch (IOException e) {
		}

		DefaultListModel listModel = new DefaultListModel();
		for (Thr th : threads) {
			listModel.addElement(th.getName());
		}
		threadList.setModel(listModel);
	}

	private void cancelButtonActionActionPerformed(ActionEvent event) {
		this.setVisible(false);
	}

	private void selectButtonActionActionPerformed(ActionEvent event) {
		MainFrame.thread = threads.get(threadList.getSelectedIndex());
		this.setVisible(false);
	}

	private void threadListListSelectionValueChanged(ListSelectionEvent event) {
		if (threadList.getSelectedIndex() == -1) {
			selectButton.setEnabled(false);
		} else {
			selectButton.setEnabled(true);
		}
	}

}
