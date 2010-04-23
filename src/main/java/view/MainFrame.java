package view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

import enums.ThreadDiv;

import bbs.Res;
import bbs.Thr;
import bbs.ThreadReader;

import util.MemoManager;
import util.SettingsManager;
import util.TencoRateEstimater;



//VS4E -- DO NOT REMOVE THIS LINE!
public class MainFrame extends JFrame {

	
	/**
	 * Component
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel resPanel;
	private JPanel postPanel;
	private JTabbedPane tabbedPane;
	private JTextArea resText;
	private JScrollPane jScrollPane0;
	private JTextArea postText;
	private JScrollPane jScrollPane1;
	private JButton addressButton;
	private JButton postButton;
	private JPanel listPanel;
	private JTable resTable;
	private JScrollPane jScrollPane2;
	private JButton updateButton;
	private JCheckBox autoUpdateCheckBox;
	private JMenuItem jMenuItem0;
	private JMenu jMenu0;
	private JMenuBar jMenuBar0;
	private JTextField threadName;
	private Object[][] tableData;
	private Res[] resArray;
	private DefaultTableModel dmodel;
	private DefaultTableColumnModel cmodel;
	private Dimension defaultSize;
	private Dimension currentSize;
	private JTextField postName;
	private JTextField postMail;
	private JButton selectThreadButton;
	private JButton sendMemoButton;
	private JTextArea memoArea;
	private JScrollPane jScrollPane3;
	private JMenuItem jMenuItem1;
	private JMenu jMenu1;
	private JMenuItem jMenuItem3;
	private JMenu jMenu2;
	private JMenuItem jMenuItem4;
	private JMenuItem jMenuItem5;
	private JMenuItem jMenuItem6;
	private JRadioButtonMenuItem jRadioButtonMenuItem0;
	private JRadioButtonMenuItem jRadioButtonMenuItem1;
	private JMenu jMenu3;
	private JRadioButtonMenuItem jRadioButtonMenuItem2;
	private JRadioButtonMenuItem jRadioButtonMenuItem3;
	private JRadioButtonMenuItem jRadioButtonMenuItem4;
	private JButton clearButton;
	private JMenuItem jMenuItem2;
	private JPanel tencoPanel;
	private JLabel tencoLabel;
	private JTextField profileTF;
	private JLabel profileLabel;
	private JTextArea resultTA;
	private JScrollPane jScrollPane4;
	private JButton tencoResultButton;
	
	private static boolean autoUpdateEnabled = false;
	private static int defaultInterval = 60000;
	public static Thr thread;
	private ThreadReader thReader;
	private boolean afterCallDialog = false;
	private boolean afterCallSettingDialog = false;
	private boolean needThreadSelect = false;
	
	private static final String PATH = "/img/splashimg";
	private static final String ICON_PATH = "/img/iconimg";
	private static final int SPLASH_INTERVAL = 1500;
	private static final int WIN_POS_MINUS = 50;
	private static final String FONT_NAME = "MS UI Gothic";
	private static final int FONT_SIZE = 12;
	private static final Dimension DEFAULT_WIN_SIZE = new Dimension(490, 575);
	private static final Color DEFAULT_BGCOLOR= new Color(245, 245, 245);
	private static final Color BUTTON_COLOR_OK = new Color(102, 255, 153);
	private static final Color BUTTON_COLOR_NG = new Color(255, 153, 80);
	
	private JWindow splashScreen;
	private JLabel  splashLabel;
	
	private Timer timer = new Timer();
	private TimerTask task;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	public MainFrame() {
		
		if(new File("." + ICON_PATH).exists()) {
			setIconImage(new ImageIcon(getClass().getResource(ICON_PATH)).getImage());
		}
		
		try {
			UIManager.setLookAndFeel(PREFERRED_LOOK_AND_FEEL);
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}

		initComponents();
		
		defaultSize = DEFAULT_WIN_SIZE;
		setSize(defaultSize);
		currentSize = this.getSize();
		setResizable(true);
		setMinimumSize(defaultSize);
		getContentPane().setBackground(DEFAULT_BGCOLOR);
		
		setFontAll();
		setBoundsAll();
		resText.setLineWrap(false);
		
		if(new File("settings.ini").exists()) {
			SettingsManager settingManager = new SettingsManager();
			settingManager.load();
			this.setLocation(settingManager.getPosx(), settingManager.getPosy());
			thread = new Thr(settingManager.getThreadName(), settingManager.getThreadUrl(), null);
			threadName.setText(settingManager.getThreadName());
			threadName.setCaretPosition(0);
			defaultInterval = settingManager.getInterval();
			autoUpdateEnabled = settingManager.isAutoUpdate();
			controlRadioState();
			
		}
		
		if(new File("memo.txt").exists()) {
			MemoManager mm = new MemoManager();
			memoArea.setText(mm.load());
		}
		
		loadListData();
		
		if(needThreadSelect) {
			ThreadSelectDialog dialog = new ThreadSelectDialog(this, "読み込むスレッドを選択してください", true);
			dialog.setBounds(this.getX() < WIN_POS_MINUS ? 0 : this.getX() - WIN_POS_MINUS, this.getY() < WIN_POS_MINUS ? 0 : this.getY() - WIN_POS_MINUS, dialog.getWidth(), dialog.getHeight());
			dialog.setVisible(true);
			afterCallDialog = true;
			
			loadListData();
			needThreadSelect = false;
		}
		
		try {
			setTimer();
		} catch (Exception e1) {
		}
		
		File readme = new File("readme.txt");
		if(readme != null && !readme.exists()) {
			jMenuItem3.setEnabled(false);
		}
		File file = new File("." + PATH);
		if(file.exists()) {
			createSplashScreen(PATH);
			showSplashScreen();
		}
		
		if(file.exists()) {
			EventQueue.invokeLater(new Runnable() {
			    public void run() {
			    	try {
						Thread.sleep(SPLASH_INTERVAL);
						setVisible(true);
					} catch (InterruptedException e) {
					}
			      hideSplash();
			    }
			  });
		} else {
			setVisible(true);
		}
		
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				MainFrame frame = new MainFrame();
				frame.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
			}
		});
	}
	
	
	private void setTimer() throws Exception {
		if(task != null) {
			task.cancel();
			task = null;
		}
		
		task = new TimerTask() {

			@Override
			public void run() {
				if(autoUpdateEnabled) {
					loadListData();
					if(resTable.getRowCount() != 0) {
						resTable.setRowSelectionInterval(0, 0);
					}
					
				}
			}
		};
		timer.schedule(task , 0, defaultInterval);
	}
	
	private void initComponents() {
		setTitle("Clara （β） -したらばブラウザ for 東方非想天則-");
		setMinimumSize(DEFAULT_WIN_SIZE);
		setLayout(null);
		add(getListPanel());
		add(getTabbedPane());
		add(getThreadName());
		add(getSelectThreadButton());
		addWindowStateListener(new WindowStateListener() {
	
			public void windowStateChanged(WindowEvent event) {
				windowStateWindowStateChanged(event);
			}
		});
		addComponentListener(new ComponentAdapter() {
	
			public void componentResized(ComponentEvent event) {
				componentComponentResized(event);
			}
		});
		addFocusListener(new FocusAdapter() {
	
			public void focusGained(FocusEvent event) {
				focusFocusGained(event);
			}
		});
		addWindowListener(new WindowAdapter() {
	
			public void windowClosing(WindowEvent event) {
				windowWindowClosing(event);
			}
	
			public void windowActivated(WindowEvent event) {
				windowWindowActivated(event);
			}
	
			public void windowDeactivated(WindowEvent event) {
				windowWindowDeactivated(event);
			}
		});
		setJMenuBar(getMyMenu());
	}


	private JButton getTencoResultButton() {
		if (tencoResultButton == null) {
			tencoResultButton = new JButton();
			tencoResultButton.setText("結果取得");
			tencoResultButton.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					tencoResultButtonActionActionPerformed(event);
				}
			});
		}
		return tencoResultButton;
	}

	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getResultTA());
		}
		return jScrollPane4;
	}

	private JTextArea getResultTA() {
		if (resultTA == null) {
			resultTA = new JTextArea();
			resultTA.setText("");
		}
		return resultTA;
	}

	private JLabel getProfileLabel() {
		if (profileLabel == null) {
			profileLabel = new JLabel();
			profileLabel.setText("プロファイル名");
		}
		return profileLabel;
	}

	private JTextField getProfileTF() {
		if (profileTF == null) {
			profileTF = new JTextField();
			profileTF.setText("");
		}
		return profileTF;
	}

	private JLabel getTencoLabel() {
		if (tencoLabel == null) {
			tencoLabel = new JLabel();
			tencoLabel.setText("非想天則 レート推定（仮） by Tenco!");
			tencoLabel.setVerticalAlignment(SwingConstants.TOP);
		}
		return tencoLabel;
	}

	private JPanel getTencoPanel() {
		if (tencoPanel == null) {
			tencoPanel = new JPanel();
			tencoPanel.setLayout(null);
			tencoPanel.add(getJScrollPane4());
			tencoPanel.add(getTencoResultButton());
			tencoPanel.add(getProfileLabel());
			tencoPanel.add(getProfileTF());
			tencoPanel.add(getTencoLabel());
		}
		return tencoPanel;
	}

	private JMenuItem getJMenuItem2() {
		if (jMenuItem2 == null) {
			jMenuItem2 = new JMenuItem();
			jMenuItem2.setText("手帳の保存");
			jMenuItem2.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jMenuItem2ActionActionPerformed(event);
				}
			});
		}
		return jMenuItem2;
	}

	private JButton getClearButton() {
		if (clearButton == null) {
			clearButton = new JButton();
			clearButton.setText("クリア");
			clearButton.setBounds(240, 160, 73, 28);
			clearButton.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					clearButtonActionActionPerformed(event);
				}
			});
		}
		return clearButton;
	}

	private JRadioButtonMenuItem getJRadioButtonMenuItem4() {
		if (jRadioButtonMenuItem4 == null) {
			jRadioButtonMenuItem4 = new JRadioButtonMenuItem();
			jRadioButtonMenuItem4.setText("自動更新する（5分）");
			jRadioButtonMenuItem4.addItemListener(new ItemListener() {
	
				public void itemStateChanged(ItemEvent event) {
					jRadioButtonMenuItem4ItemItemStateChanged(event);
				}
			});
		}
		return jRadioButtonMenuItem4;
	}

	private JRadioButtonMenuItem getJRadioButtonMenuItem3() {
		if (jRadioButtonMenuItem3 == null) {
			jRadioButtonMenuItem3 = new JRadioButtonMenuItem();
			jRadioButtonMenuItem3.setText("自動更新する（3分）");
			jRadioButtonMenuItem3.addItemListener(new ItemListener() {
	
				public void itemStateChanged(ItemEvent event) {
					jRadioButtonMenuItem3ItemItemStateChanged(event);
				}
			});
		}
		return jRadioButtonMenuItem3;
	}

	private JRadioButtonMenuItem getJRadioButtonMenuItem2() {
		if (jRadioButtonMenuItem2 == null) {
			jRadioButtonMenuItem2 = new JRadioButtonMenuItem();
			jRadioButtonMenuItem2.setText("自動更新する（1分）");
			jRadioButtonMenuItem2.addItemListener(new ItemListener() {
	
				public void itemStateChanged(ItemEvent event) {
					jRadioButtonMenuItem2ItemItemStateChanged(event);
				}
			});
		}
		return jRadioButtonMenuItem2;
	}

	private JMenu getJMenu3() {
		if (jMenu3 == null) {
			jMenu3 = new JMenu();
			jMenu3.setText("自動更新");
			
			jMenu3.add(getJRadioButtonMenuItem0());
			jMenu3.add(getJRadioButtonMenuItem1());
			jMenu3.add(getJRadioButtonMenuItem2());
			jMenu3.add(getJRadioButtonMenuItem3());
			jMenu3.add(getJRadioButtonMenuItem4());
			createButtonGroup();
		}
		return jMenu3;
	}

	private void createButtonGroup() {
		ButtonGroup group = new ButtonGroup();
		group.add(jRadioButtonMenuItem0);
		group.add(jRadioButtonMenuItem1);
		group.add(jRadioButtonMenuItem2);
		group.add(jRadioButtonMenuItem3);
		group.add(jRadioButtonMenuItem4);
		
	}
	
	private JRadioButtonMenuItem getJRadioButtonMenuItem1() {
		if (jRadioButtonMenuItem1 == null) {
			jRadioButtonMenuItem1 = new JRadioButtonMenuItem();
			jRadioButtonMenuItem1.setText("自動更新する（30秒）");
			jRadioButtonMenuItem1.addItemListener(new ItemListener() {
	
				public void itemStateChanged(ItemEvent event) {
					jRadioButtonMenuItem1ItemItemStateChanged(event);
				}
			});
		}
		return jRadioButtonMenuItem1;
	}

	private JRadioButtonMenuItem getJRadioButtonMenuItem0() {
		if (jRadioButtonMenuItem0 == null) {
			jRadioButtonMenuItem0 = new JRadioButtonMenuItem();
			jRadioButtonMenuItem0.setSelected(true);
			jRadioButtonMenuItem0.setText("自動更新しない");
			jRadioButtonMenuItem0.addItemListener(new ItemListener() {
	
				public void itemStateChanged(ItemEvent event) {
					jRadioButtonMenuItem0ItemItemStateChanged(event);
				}
			});
		}
		return jRadioButtonMenuItem0;
	}

	private JMenuItem getJMenuItem6() {
		if (jMenuItem6 == null) {
			jMenuItem6 = new JMenuItem();
			jMenuItem6.setText("アップデート確認");
			jMenuItem6.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jMenuItem6ActionActionPerformed(event);
				}
			});
		}
		return jMenuItem6;
	}

	private JMenuItem getJMenuItem5() {
		if (jMenuItem5 == null) {
			jMenuItem5 = new JMenuItem();
			jMenuItem5.setText("スレを既定のブラウザで開く");
			jMenuItem5.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jMenuItem5ActionActionPerformed(event);
				}
			});
		}
		return jMenuItem5;
	}

	private JMenuItem getJMenuItem4() {
		if (jMenuItem4 == null) {
			jMenuItem4 = new JMenuItem();
			jMenuItem4.setText("設定画面を開く");
			jMenuItem4.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jMenuItem4ActionActionPerformed(event);
				}
			});
		}
		return jMenuItem4;
	}

	private JMenu getJMenu2() {
		if (jMenu2 == null) {
			jMenu2 = new JMenu();
			jMenu2.setText("ヘルプ");
			jMenu2.add(getJMenuItem3());
			jMenu2.add(getJMenuItem6());
		}
		return jMenu2;
	}

	private JMenuItem getJMenuItem3() {
		if (jMenuItem3 == null) {
			jMenuItem3 = new JMenuItem();
			jMenuItem3.setText("readme.txtを開く");
			jMenuItem3.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jMenuItem3ActionActionPerformed(event);
				}
			});
		}
		return jMenuItem3;
	}

	private JMenu getJMenu1() {
		if (jMenu1 == null) {
			jMenu1 = new JMenu();
			jMenu1.setText("設定");
			jMenu1.add(getJMenuItem4());
		}
		jMenu1.setVisible(false);
		return jMenu1;
	}

	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setText("手帳をNotepadで開く");
			jMenuItem1.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jMenuItem1ActionActionPerformed(event);
				}
			});
		}
		return jMenuItem1;
	}

	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getMemoArea());
		}
		return jScrollPane3;
	}

	private JTextArea getMemoArea() {
		if (memoArea == null) {
			memoArea = new JTextArea();
			memoArea.setText("");
		}
		return memoArea;
	}

	private JButton getsendMemoButton() {
		if (sendMemoButton == null) {
			sendMemoButton = new JButton();
			sendMemoButton.setText("まるっとメモ");
			sendMemoButton.setBounds(10, 160, 166, 28);
			sendMemoButton.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					sendMemoButtonActionActionPerformed(event);
				}
			});
		}
		return sendMemoButton;
	}

	private JButton getSelectThreadButton() {
		if (selectThreadButton == null) {
			selectThreadButton = new JButton();
			selectThreadButton.setText("スレ選択");
			selectThreadButton.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					selectThreadButtonActionActionPerformed(event);
				}
			});
		}
		return selectThreadButton;
	}

	private JTextField getPostName() {
		if (postName == null) {
			postName = new JTextField();
			postName.setText("");
		}
		return postName;
	}
	
	private JTextField getPostMail() {
		if (postMail == null) {
			postMail = new JTextField();
			postMail.setText("sage");
			postMail.setBounds(123, 160, 104, 28);
		}
		return postMail;
	}

	public void loadListData() {
		String url = "";
		if(thread != null) {
			url = thread.getUrl();
			try {
				thReader = new ThreadReader(new URL(url));
				resArray = thReader.getResArray();
				if(resArray != null && thReader != null && resArray[thReader.lastReadNum] != null) {
					showRes(resArray[thReader.lastReadNum].getText());
					final int MAX_COLUMUN_IDX = 4;
					tableData = new String[thReader.lastReadNum][MAX_COLUMUN_IDX];
		
					for (int i = 0; i < thReader.lastReadNum; i++) {
						if(resArray[thReader.lastReadNum - i] == null) {
							continue;
						}
						
						for (int j = 0; j < MAX_COLUMUN_IDX; j++) {
							switch (j) {
							case 0:
								int num = resArray[thReader.lastReadNum - i].getResNum();
								String numStr = null;
								if(num < 10) {
									numStr = "00";
								} else if(num < 100) {
									numStr = "0";
								} else {
									numStr = "";
								}
								tableData[i][j] = numStr + String.valueOf(resArray[thReader.lastReadNum - i].getResNum());
								break;
							case 1:
								tableData[i][j] = "";
								if(thread != null && thread.getRank() == ThreadDiv.SALON) {
									break;
								}
								if (resArray[thReader.lastReadNum - i].isRecruitment
										&& resArray[thReader.lastReadNum - i].isOpened) {
									tableData[i][j] = "◯";
								} else if (resArray[thReader.lastReadNum - i].getParentResNum() != -1
										&& resArray[resArray[thReader.lastReadNum - i].getParentResNum()] != null && resArray[resArray[thReader.lastReadNum - i].getParentResNum()].isOpened) {
									if (resArray[resArray[thReader.lastReadNum - i].getParentResNum()].isDone) {
										tableData[i][j] = "済";
									} else {
										tableData[i][j] = "◯";
									}
								}
								break;
							case 2:
								if(resArray[thReader.lastReadNum - i].getTime() == null) {
									tableData[i][j] = "--:--:--";
									break;
								}
								String time = resArray[thReader.lastReadNum - i].getTime().split(" ")[1];
								tableData[i][j] = time.substring(0, time.length() - ":00".length());
								break;
							case 3:
								String str = ThreadReader.removeHtmlTag(resArray[thReader.lastReadNum - i].getText().replace("<br>", " "));
								tableData[i][j] = str.replace("&gt;", ">");
								break;
							}
		
						}
					}
					
					dmodel = new DefaultTableModel(tableData, new String[] { "No", "凸", "時間", "内容" });
					resTable.setModel(dmodel);
				}
			} catch (MalformedURLException e) {
			} catch (NumberFormatException e) {
				resText.setText("");
			}
		
		} else {
			needThreadSelect = true;
		}
		cmodel = (DefaultTableColumnModel) (resTable.getColumnModel());
		cmodel.getColumn(0).setPreferredWidth(40);
		cmodel.getColumn(0).setMaxWidth(40);
		cmodel.getColumn(1).setPreferredWidth(35);
		cmodel.getColumn(1).setMaxWidth(35);
		cmodel.getColumn(2).setPreferredWidth(50);
		cmodel.getColumn(2).setMaxWidth(50);
		cmodel.getColumn(3).setPreferredWidth(305);
		resTable.setShowVerticalLines(false);
		resTable.setShowGrid(false);
		resTable.setAutoscrolls(true);
		resTable.setAutoCreateRowSorter(true);
		jScrollPane2.setViewportView(resTable);
	}

	private JPanel getResPanel() {
		if (resPanel == null) {
			resPanel = new JPanel();
			resPanel.setLayout(null);
			resPanel.add(getAddressButton());
			resPanel.add(getJScrollPane0());
			resPanel.add(getsendMemoButton());
		}
		return resPanel;
	}

	private JTextField getThreadName() {
		if (threadName == null) {
			threadName = new JTextField();
			threadName.setText("");
		}
		return threadName;
	}

	private JMenuBar getMyMenu() {
		if (jMenuBar0 == null) {
			jMenuBar0 = new JMenuBar();
			jMenuBar0.add(getJMenu0());
			jMenuBar0.add(getJMenu3());
			jMenuBar0.add(getJMenu1());
			jMenuBar0.add(getJMenu2());
			
		}
		return jMenuBar0;
	}

	private JMenu getJMenu0() {
		if (jMenu0 == null) {
			jMenu0 = new JMenu();
			jMenu0.setText("ファイル");
			jMenu0.add(getJMenuItem2());
			jMenu0.add(getJMenuItem1());
			jMenu0.add(getJMenuItem5());
			jMenu0.add(getJMenuItem0());
			
		}
		return jMenu0;
	}

	private JMenuItem getJMenuItem0() {
		if (jMenuItem0 == null) {
			jMenuItem0 = new JMenuItem();
			jMenuItem0.setText("終了");
			jMenuItem0.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jMenuItem0ActionActionPerformed(event);
				}
			});
		}
		return jMenuItem0;
	}

	private JCheckBox getAutoUpdateCheckBox() {
		if (autoUpdateCheckBox == null) {
			autoUpdateCheckBox = new JCheckBox();
			autoUpdateCheckBox.setText("自動更新する");
			autoUpdateCheckBox.setBounds(11, 205, 98, 28);
			autoUpdateCheckBox.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					autoUpdateCheckBoxActionActionPerformed(event);
				}
			});
		}
		return autoUpdateCheckBox;
	}

	private JButton getUpdateButton() {
		if (updateButton == null) {
			updateButton = new JButton();
			updateButton.setText("更新");
			updateButton.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					updateButtonActionActionPerformed(event);
				}
			});
		}
		return updateButton;
	}

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setDoubleBuffered(true);
			jScrollPane2.setAutoscrolls(true);
			jScrollPane2.setViewportView(getResTable());
		}
		return jScrollPane2;
	}

	private JTable getResTable() {
		if (resTable == null) {
			resTable = new JTable();
			resTable
			.setModel(new DefaultTableModel(
					new Object[][] {
							, },
					new String[] { "No.", "状態", "時間", "内容", }) {
				private static final long serialVersionUID = 1L;
				Class<?>[] types = new Class<?>[] { Object.class, Object.class, Object.class, Object.class, };

				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}
			});
			resTable.setDoubleBuffered(true);
			resTable.setAutoscrolls(true);
			resTable.setUpdateSelectionOnSort(false);
			resTable.setSurrendersFocusOnKeystroke(true);
			resTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
			resTable.addKeyListener(new KeyAdapter() {
	
				public void keyReleased(KeyEvent event) {
					resTableKeyKeyReleased(event);
				}
	
				public void keyPressed(KeyEvent event) {
					resTableKeyKeyPressed(event);
				}
			});
			resTable.addMouseListener(new MouseAdapter() {
	
				public void mousePressed(MouseEvent event) {
					resTableMouseMousePressed(event);
				}
			});
		}
		return resTable;
	}

	private JPanel getListPanel() {
		if (listPanel == null) {
			listPanel = new JPanel();
			listPanel.setLayout(null);
			listPanel.add(getAutoUpdateCheckBox());
			listPanel.add(getJScrollPane2());
			listPanel.add(getUpdateButton());
		}
		return listPanel;
	}

	private JPanel getPostPanel() {
		if (postPanel == null) {
			postPanel = new JPanel();
			postPanel.setLayout(null);
			postPanel.add(getPostButton());
			postPanel.add(getJScrollPane1());
			postPanel.add(getPostName());
			postPanel.add(getClearButton());
			postPanel.add(getPostMail());
		}
		return postPanel;
	}

	private JButton getPostButton() {
		if (postButton == null) {
			postButton = new JButton();
			postButton.setText("投稿確認");
			postButton.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					postButtonActionActionPerformed(event);
				}
			});
		}
		return postButton;
	}

	private JButton getAddressButton() {
		if (addressButton == null) {
			addressButton = new JButton();
			addressButton.setText("[IP:Port]の抽出＆クリップボードにコピー");
			addressButton.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					addressButtonActionActionPerformed(event);
				}
			});
		}
		return addressButton;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setDoubleBuffered(true);
			jScrollPane1.setViewportView(getPostText());
		}
		return jScrollPane1;
	}

	private JTextArea getPostText() {
		if (postText == null) {
			postText = new JTextArea();
			postText.setText("");
		}
		return postText;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setDoubleBuffered(true);
			jScrollPane0.setAutoscrolls(true);
			jScrollPane0.setViewportView(getResText());
		}
		return jScrollPane0;
	}

	private JTextArea getResText() {
		if (resText == null) {
			resText = new JTextArea();
			resText.setText("");
		}
		resText.setPreferredSize(null);
		return resText;
	}

	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane();
			tabbedPane.setBounds(12, 34, 463, 223);
			tabbedPane.addTab("選択レス", getResPanel());
			tabbedPane.addTab(" 投  稿 ", getPostPanel());
			tabbedPane.addTab("Tenco!", getTencoPanel());
			tabbedPane.addTab(" 手 帳 ", getJScrollPane3());
		}
		return tabbedPane;
	}

	private void showRes(String text) throws NumberFormatException {
		text = text.replace("<br><br>", "<br>");
		text = text.replace("<br>", "\n");
		if (text.startsWith("\n")) {
			text = text.substring("\n".length());
		}
		if (text.endsWith("\n")) {
			text = text.substring(0, text.length() - "\n".length());
		}
		text = text.replace("&gt;", ">");
		resText.setText(ThreadReader.removeHtmlTag(text));
		resText.setCaretPosition(0);
		
		if(text.contains(">>")) {
			List<Integer> list = new ArrayList<Integer>();
			text = text.replace(">>", "@&@>>");
			String[] ary = text.split("@&@");
			for(String str : ary) {
				Pattern pattern = Pattern.compile(">>[0-9]{1,3}");
				Matcher matcher = pattern.matcher(str);
				if (matcher.find()) {
					str = matcher.group().substring(">>".length());
					list.add(Integer.parseInt(str));
				}
			}
			
			StringBuffer tips = new StringBuffer("<html>");
			for(Integer num : list) {
				if(resArray[num] == null) {
					continue;
				}
				String str = resArray[num].getText();
				str = str.replace("<br><br>", "<br>");
				str = str.replace("<br>", "\n");
				if (str.startsWith("\n")) {
					str = str.substring("\n".length());
				}
				if (str.endsWith("\n")) {
					str = str.substring(0, str.length() - "\n".length());
				}
				str = str.replace("&gt;", ">");
				tips.append("【" + resArray[num].getResNum() + "】").append(" ").append(resArray[num].getTime().substring("009/".length())).append("<br>");
				tips.append(ThreadReader.removeHtmlTag(str).replace("\n", "<br>"));
				tips.append("<br><br>");
			}
			
			resText.setToolTipText(tips.toString());
		} else {
			resText.setToolTipText(null);
		}
	}
	
	private void setFontAll() {
		jMenu0.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jMenu1.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jMenu2.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jMenu3.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jRadioButtonMenuItem0.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jRadioButtonMenuItem1.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jRadioButtonMenuItem2.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jRadioButtonMenuItem3.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jRadioButtonMenuItem4.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jMenuBar0.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jMenuItem0.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jMenuItem1.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jMenuItem2.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jMenuItem3.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jMenuItem4.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jMenuItem5.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		jMenuItem6.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		tabbedPane.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		tencoPanel.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		threadName.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		addressButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		postButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		resTable.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		resText.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		sendMemoButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		updateButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		autoUpdateCheckBox.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		postText.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		selectThreadButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		memoArea.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		clearButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		tencoLabel.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		resultTA.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		profileLabel.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		profileTF.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		tencoResultButton.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		
	}
	
	private void setBoundsAll() {
		tabbedPane.setBounds(12, 34, 463, 223);
		threadName.setBounds(12, 6, 325, 28);
		selectThreadButton.setBounds(349, 6, 125, 28);
		listPanel.setBounds(12, 263, 462, 240);
		addressButton.setBounds(191, 160, 259, 28);
		jScrollPane0.setBounds(3, 5, 449, 147);
		sendMemoButton.setBounds(10, 160, 166, 28);
		autoUpdateCheckBox.setBounds(11, 205, 98, 28);
		jScrollPane2.setBounds(5, 5, 451, 188);
		updateButton.setBounds(191, 199, 259, 33);
		jScrollPane1.setBounds(3, 5, 449, 147);
		postName.setBounds(5, 160, 106, 28);
		postMail.setBounds(123, 160, 106, 28);
		postButton.setBounds(325, 160, 120, 28);
		jScrollPane4.setBounds(6, 62, 447, 130);
		tencoResultButton.setBounds(333, 28, 118, 27);
		profileLabel.setBounds(10, 28, 83, 27);
		profileTF.setBounds(95, 28, 217, 29);
		tencoLabel.setBounds(10, 10, 186, 27);
	}

	private void openUserBrowzer() {
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.browse(new URI(thread.getUrl()));
		} catch (IOException e) {
		} catch (URISyntaxException e) {
		}
	}
	
	/**
	 * 	▼イベントリスナ
	 * 
	 */
	
	private void selectThreadButtonActionActionPerformed(ActionEvent event) {
		ThreadSelectDialog dialog = new ThreadSelectDialog(this, "読み込むスレッドを選択してください", true);
		dialog.setBounds(this.getX() < WIN_POS_MINUS ? 0 : this.getX() - WIN_POS_MINUS, this.getY() < WIN_POS_MINUS ? 0 : this.getY() - WIN_POS_MINUS, dialog.getWidth(), dialog.getHeight());
		dialog.setVisible(true);
		afterCallDialog = true;
	}

	private void focusFocusGained(FocusEvent event) {
	}

	private void windowStateWindowStateChanged(WindowEvent event) {
	}

	private void windowWindowActivated(WindowEvent event) {
		if(afterCallDialog && thread != null) {
			if(thReader == null) {
				try {
					thReader = new ThreadReader(new URL(thread.getUrl()));
					if(thReader == null) {
						return;
					}
				} catch (MalformedURLException e) {
				}
			}
			
			thReader.lastReadNum = 0;
			String name = thread.getName();
			Pattern pattern = Pattern.compile("\\([0-9]{1,3}\\)");
			Matcher matcher = pattern.matcher(name);
			name = matcher.replaceAll("");
			threadName.setText(name);
			threadName.setCaretPosition(0);
			loadListData();
			
			afterCallDialog = false;
		} else if(afterCallSettingDialog) {
			afterCallSettingDialog = false;
		}
		
	}

	private void windowWindowDeactivated(WindowEvent event) {
	}

	private void addressButtonActionActionPerformed(ActionEvent event) {
		if(resText.getText() == null || resText.getText().equals("")) {
			return;
		}
		new MyClipboard();
	}
	
	class MyClipboard implements ClipboardOwner
	{
		public MyClipboard() {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			if(resTable.getSelectedRows().length == 0) {
				return;
			}
			int selectedRowindex = resTable.getSelectedRows()[0];
			if(tableData[selectedRowindex][1].equals("◯") == false) {
				return;
			}
			int selectedResNum = Integer.parseInt(tableData[selectedRowindex][0].toString());
			String strToSend = null;
			if(resArray[selectedResNum].getParentResNum() != -1 && resArray[resArray[selectedResNum].getParentResNum()].isRecruitment) {
				strToSend = resArray[resArray[selectedResNum].getParentResNum()].getIp();
			} else if(resArray[selectedResNum].isRecruitment) {
				strToSend = resArray[selectedResNum].getIp();
			}
			
			if(strToSend != null) {
				StringSelection selection = new StringSelection(strToSend);
				clipboard.setContents(selection, this);
				notifyProgressByAppearanceChanges(addressButton, "[IP:Port]の抽出＆クリップボードにコピー", "コピーしました");
			}
		}

		public void lostOwnership(Clipboard clipboard, Transferable contents) {
		}

	}
	
	synchronized private static void notifyProgressByAppearanceChanges(final JButton button, final String defaultMsg, final String endMsg) {
		new Thread() {
			@Override
			public void run() {
				setButtonAppearance(button, endMsg, BUTTON_COLOR_OK);
				try {
					sleep(1500);
				} catch (InterruptedException e) {
				}
				setButtonAppearance(button, defaultMsg, null);
			}
		}.start();
	}
	
	private static void setButtonAppearance(JButton button, String message, Color color) {
		button.setText(message);
		button.setBackground(color);
	}

	private void postButtonActionActionPerformed(ActionEvent event) {
		if(postText.getText().equals("") || postText.getText() == null || thread == null) {
			return;
		}
		
		PostDialog dialog = new PostDialog(this.getX(), this.getY(), thread.getName(), thread.getUrl(), postName.getText(), postMail.getText(), postText.getText());
		dialog.setVisible(true);
	}

	private void windowWindowClosing(WindowEvent event) {
		StringBuffer settings = new StringBuffer();
		settings.append("// Window Position\n");
		settings.append("posx:" + this.getX() + "\n");
		settings.append("posy:" + this.getY() + "\n");
		settings.append("// Auto Update\n");
		settings.append("auto:" + (autoUpdateEnabled?"on":"off") + "\n");
		settings.append("interval:" + defaultInterval + "\n");
		if(thread != null) {
			settings.append("// Thread\n");
			settings.append("name:" + thread.getName() + "\n");
			settings.append("url:" + thread.getUrl() + "\n");
		}
		
		SettingsManager sm = new SettingsManager(settings.toString());
		sm.save();
		
		if(memoArea.getText().equals("") == false || memoArea.getText() != null) {
			MemoManager mm = new MemoManager(memoArea.getText());
			mm.save();
		}
		
	}

	private void sendMemoButtonActionActionPerformed(ActionEvent event) {
		notifyProgressByAppearanceChanges(sendMemoButton, "まるっとメモ", "手帳にコピーしました");
		
		String toSend = "";
		String memo = memoArea.getText();
		if(memo.equals("") || memo == null) {
			toSend = "";
		} else if(memo.endsWith("\n")) {
			toSend += "\n";
		} else {
			toSend += "\n\n";
		}
		
		toSend += resText.getText();
		
		memoArea.setText(memo + toSend);
	}

	private void jMenuItem0ActionActionPerformed(ActionEvent event) {
		StringBuffer settings = new StringBuffer();
		settings.append("// Window Position\n");
		settings.append("posx:" + this.getX() + "\n");
		settings.append("posy:" + this.getY() + "\n");
		settings.append("// Auto Update\n");
		settings.append("auto:" + (autoUpdateEnabled?"on":"off") + "\n");
		settings.append("interval:" + defaultInterval + "\n");
		if(thread != null) {
			settings.append("// Thread\n");
			settings.append("name:" + thread.getName() + "\n");
			settings.append("url:" + thread.getUrl() + "\n");
		}
		
		SettingsManager sm = new SettingsManager(settings.toString());
		sm.save();
		
		if(memoArea.getText().equals("") == false || memoArea.getText() != null) {
			MemoManager mm = new MemoManager(memoArea.getText());
			mm.save();
		}
		this.setVisible(false);
		System.exit(NORMAL);
	}


	
	public void controlRadioState(){
		try {
			if(autoUpdateEnabled) {
				switch(defaultInterval) {
				case 30000:
					jRadioButtonMenuItem1.setSelected(true);
					break;
				case 60000:
					jRadioButtonMenuItem2.setSelected(true);
					break;
				case 180000:
					jRadioButtonMenuItem3.setSelected(true);
					break;
				case 300000:
					jRadioButtonMenuItem4.setSelected(true);
					break;
				}
				
				setTimer();
			}
		} catch(Exception e) {
		}
	}

	public void createSplashScreen(String path) {
		  ImageIcon img = new ImageIcon(getClass().getResource(path));
		  splashLabel   = new JLabel(img);
		  splashScreen  = new JWindow(getFrame());
		  splashScreen.getContentPane().add(splashLabel);
		  splashScreen.pack();
		  splashScreen.setLocationRelativeTo(null);
		}
		public void showSplashScreen() {
		  splashScreen.setVisible(true);
		}
		public void hideSplash() {
		  splashScreen.setVisible(false);
		  splashScreen = null;
		  splashLabel  = null;
		}
		public JFrame getFrame() {
		  return this;
		}
		public void showPanel() {
		  splashScreen.setPreferredSize(new Dimension(500, 400));
		  getContentPane().add(this);
		  setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		  pack();
		  setLocationRelativeTo(null);
		  setVisible(true);
		}
		
		private void updateButtonActionActionPerformed(ActionEvent event) {
			notifyProgressByAppearanceChanges(updateButton, "更新", "更新完了");
			
			loadListData();
			if(resTable.getRowCount() != 0) {
				resTable.setRowSelectionInterval(0, 0);
			}
			
		}

		private void componentComponentResized(ComponentEvent event) {
			if(currentSize == null) {
				return;
			}
			int x_diff = (int)(this.getSize().getWidth() - currentSize.getWidth());
			int y_diff = (int)(this.getSize().getHeight() - currentSize.getHeight());
			
			resizeComponent(tabbedPane, x_diff , 0);
			resizeComponent(resPanel, x_diff , 0);
			resizeComponent(tencoPanel, x_diff , 0);
			resizeComponent(postPanel, x_diff , 0);
			resizeComponent(jScrollPane0, x_diff , 0);
			resizeComponent(jScrollPane1, x_diff , 0);
			resizeComponent(jScrollPane2, x_diff , y_diff);
			resizeComponent(jScrollPane4, x_diff , 0);
			resizeComponent(resultTA, x_diff , 0);
			resizeComponent(profileTF, x_diff , 0);
			resizeComponent(resText, x_diff , 0);
			resizeComponent(postText, x_diff , 0);
			resizeComponent(listPanel, x_diff , y_diff);
			resizeComponent(resTable, x_diff , y_diff);
			dmodel = new DefaultTableModel(tableData, new String[] { "No", "凸", "時間", "内容" });
			resTable.setModel(dmodel);
			cmodel = (DefaultTableColumnModel) (resTable.getColumnModel());
			cmodel.getColumn(0).setPreferredWidth(40);
			cmodel.getColumn(0).setMaxWidth(40);
			cmodel.getColumn(1).setPreferredWidth(35);
			cmodel.getColumn(1).setMaxWidth(35);
			cmodel.getColumn(2).setPreferredWidth(50);
			cmodel.getColumn(2).setMaxWidth(50);
			cmodel.getColumn(3).setPreferredWidth(305);
			
			tencoResultButton.setLocation(tencoResultButton.getX() + x_diff, tencoResultButton.getY());
			postName.setLocation(postName.getX() + x_diff, postName.getY());
			postMail.setLocation(postMail.getX() + x_diff, postMail.getY());
			postButton.setLocation(postButton.getX() + x_diff, postButton.getY());
			clearButton.setLocation(clearButton.getX() + x_diff, clearButton.getY());
			addressButton.setLocation(addressButton.getX() + x_diff, addressButton.getY());
			autoUpdateCheckBox.setLocation(autoUpdateCheckBox.getX(), autoUpdateCheckBox.getY() + y_diff);
			updateButton.setLocation(updateButton.getX() + x_diff, updateButton.getY()+y_diff);
			
			currentSize = this.getSize();
		}
		
		private void clearButtonActionActionPerformed(ActionEvent event) {
			postText.setText("");
		}

		private void jMenuItem2ActionActionPerformed(ActionEvent event) {
			if(memoArea.getText().equals("") == false || memoArea.getText() != null) {
				MemoManager mm = new MemoManager(memoArea.getText());
				mm.save();
			}
		}

		private void tencoResultButtonActionActionPerformed(ActionEvent event) {
			if(profileTF.getText().equals("") || profileTF.getText() == null) {
				return;
			}
			
			resultTA.setText("");
			noticeProgressOnTenco(tencoResultButton, profileTF, resultTA);
		}
		
		synchronized static void noticeProgressOnTenco(final JButton tencoResultButton, final JTextField profileTF, final JTextArea resultTA){
			new Thread() {
				@Override
				public void run() {
					tencoResultButton.setText("取得中");
					tencoResultButton.setEnabled(false);
					try {
						String rate = TencoRateEstimater.getRate(profileTF.getText());
						if(rate != null) {
							resultTA.setText(rate);
							resultTA.setCaretPosition(0);
						} else {
							tencoResultButton.setEnabled(true);
							tencoResultButton.setBackground(BUTTON_COLOR_NG);
							tencoResultButton.setText("取得失敗");
							try {
								sleep(2000);
							} catch (InterruptedException e) {
							}
							tencoResultButton.setBackground(null);
							tencoResultButton.setText("結果取得");
							return;
						}
						
					} catch (IOException e) {
					}
					tencoResultButton.setEnabled(true);
					tencoResultButton.setBackground(BUTTON_COLOR_OK);
					tencoResultButton.setText("取得完了");
					try {
						sleep(2000);
					} catch (InterruptedException e) {
					}
					tencoResultButton.setBackground(null);
					tencoResultButton.setText("結果取得");
				}
			}.start();
		}

		private void jMenuItem6ActionActionPerformed(ActionEvent event) {
			VersionInfoDialog vid = new VersionInfoDialog(this, "アップデート確認", true, this.getX(), this.getY());
			vid.setVisible(true);
		}

		private void jMenuItem3ActionActionPerformed(ActionEvent event) {
			Process proc;
			try {
				proc = Runtime.getRuntime().exec(new String[] {"notepad.exe", "readme.txt"});
				proc.waitFor();
			} catch (IOException e) {
			} catch (InterruptedException e) {
			}
		}
		
		private void jMenuItem4ActionActionPerformed(ActionEvent event) {
			afterCallSettingDialog = true;
		}
		
		private void jMenuItem5ActionActionPerformed(ActionEvent event) {
			openUserBrowzer();
		}

		private void jMenuItem1ActionActionPerformed(ActionEvent event) {
			Process proc;
			try {
				proc = Runtime.getRuntime().exec(new String[] {"notepad.exe", "memo.txt"});
				proc.waitFor();
			} catch (IOException e) {
			} catch (InterruptedException e) {
			}
			
		}

		private void autoUpdateCheckBoxActionActionPerformed(ActionEvent event) {
			try {
				autoUpdateEnabled = autoUpdateCheckBox.isSelected();
				jRadioButtonMenuItem0.setSelected(!autoUpdateEnabled);
				controlRadioState();
			} catch(Exception e) {
			}
		}

		private void jRadioButtonMenuItem0ItemItemStateChanged(ItemEvent event) {
			autoUpdateEnabled = !jRadioButtonMenuItem0.isSelected();
			autoUpdateCheckBox.setSelected(!jRadioButtonMenuItem0.isSelected());
			controlRadioState();
		}

		private void jRadioButtonMenuItem1ItemItemStateChanged(ItemEvent event) {
			autoUpdateEnabled = jRadioButtonMenuItem1.isSelected();
			if(autoUpdateEnabled) {
				defaultInterval = 30000;
			}
			controlRadioState();
		}

		private void jRadioButtonMenuItem2ItemItemStateChanged(ItemEvent event) {
			autoUpdateEnabled = jRadioButtonMenuItem2.isSelected();
			if(autoUpdateEnabled) {
				defaultInterval = 60000;
			}
			controlRadioState();
		}

		private void jRadioButtonMenuItem3ItemItemStateChanged(ItemEvent event) {
			autoUpdateEnabled = jRadioButtonMenuItem3.isSelected();
			if(autoUpdateEnabled) {
				defaultInterval = 180000;
			}
			controlRadioState();
		}

		private void jRadioButtonMenuItem4ItemItemStateChanged(ItemEvent event) {
			autoUpdateEnabled = jRadioButtonMenuItem4.isSelected();
			if(autoUpdateEnabled) {
				defaultInterval = 300000;
			}
			controlRadioState();
		}
		
		private void resTableMouseMousePressed(MouseEvent event) {
			try {
				int num = resTable.getSelectedRows()[0];
				int resNum = Integer.parseInt((String) tableData[num][0]);
				String text = resArray[resNum].getText();
				showRes(text);
			} catch (NumberFormatException e) {
				resText.setText("");
			}
			tabbedPane.setSelectedIndex(0);
			
		}
		
		private void resizeComponent(JComponent component, int x_diff, int y_diff) {
			component.setSize((int)component.getSize().getWidth() + x_diff, (int)component.getSize().getHeight() + y_diff);
		}

		private void resTableKeyKeyPressed(KeyEvent event) {
		}

		private void resTableKeyKeyReleased(KeyEvent event) {
			try {
				int num = resTable.getSelectedRows()[0];
				int resNum = Integer.parseInt((String) tableData[num][0]);
				String text = resArray[resNum].getText();
				
				showRes(text);
			} catch (NumberFormatException e) {
				resText.setText("");
			}
			tabbedPane.setSelectedIndex(0);
		}

}