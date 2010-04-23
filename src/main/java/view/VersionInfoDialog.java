package view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

//VS4E -- DO NOT REMOVE THIS LINE!
public class VersionInfoDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	public static final String VERSION = "0.03";
	private JLabel appNameLabel;
	private JButton okButton;
	private JButton jButton0;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";

	public VersionInfoDialog() {
		initComponents();
	}

	public VersionInfoDialog(Frame parent) {
		super(parent);
		initComponents();
	}

	public VersionInfoDialog(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public VersionInfoDialog(Frame parent, String title) {
		super(parent, title);
		initComponents();
	}

	public VersionInfoDialog(Frame parent, String title, boolean modal, int posx, int posy) {
		super(parent, title, modal);
		initComponents();
		setLocation(posx + 50, posy + 50);
		try {
			UIManager.setLookAndFeel(PREFERRED_LOOK_AND_FEEL);
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}

		String newVer = null;
		try {
			URL url = new URL("http://www21.atwiki.jp/clara/pages/13.html");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.contains("<!--0-1-->")) {
					Pattern pattern = Pattern.compile("[0-9]{1}\\.[0-9]{2}+");
					Matcher matcher = pattern.matcher(line);
					if (matcher.find()) {
						newVer = matcher.group();
					}
					break;
				}
			}
			con.disconnect();

		} catch (Exception e) {
			newVer = null;
		}

		if (newVer == null) {
			appNameLabel.setText("バージョン情報の取得に失敗しました");
		} else if (newVer.equals(VERSION)) {
			appNameLabel.setText("最新版の Clara です。(" + VERSION + ")");
		} else {
			jButton0.setVisible(true);
			okButton.setText("閉じる");
			appNameLabel.setText("<html>旧版の Clara です。<br>下記リンク先から最新版をダウンロード出来ます。");
		}
	}

	public VersionInfoDialog(Frame parent, String title, boolean modal, GraphicsConfiguration arg) {
		super(parent, title, modal, arg);
		initComponents();
	}

	public VersionInfoDialog(Dialog parent) {
		super(parent);
		initComponents();
	}

	public VersionInfoDialog(Dialog parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public VersionInfoDialog(Dialog parent, String title) {
		super(parent, title);
		initComponents();
	}

	public VersionInfoDialog(Dialog parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}

	public VersionInfoDialog(Dialog parent, String title, boolean modal, GraphicsConfiguration arg) {
		super(parent, title, modal, arg);
		initComponents();
	}

	public VersionInfoDialog(Window parent) {
		super(parent);
		initComponents();
	}

	public VersionInfoDialog(Window parent, ModalityType modalityType) {
		super(parent, modalityType);
		initComponents();
	}

	public VersionInfoDialog(Window parent, String title) {
		super(parent, title);
		initComponents();
	}

	public VersionInfoDialog(Window parent, String title, ModalityType modalityType) {
		super(parent, title, modalityType);
		initComponents();
	}

	public VersionInfoDialog(Window parent, String title, ModalityType modalityType, GraphicsConfiguration arg) {
		super(parent, title, modalityType, arg);
		initComponents();
	}

	private void initComponents() {
		setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
		setBackground(Color.white);
		setResizable(false);
		setModal(true);
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getAppNameLabel(), new Constraints(new Bilateral(6, 6, 280), new Leading(6, 56, 10, 10)));
		add(getJButton0(), new Constraints(new Leading(9, 199, 6, 6), new Leading(68, 38, 6, 6)));
		add(getOkButton(), new Constraints(new Leading(220, 95, 6, 6), new Leading(66, 40, 6, 6)));
		setSize(340, 152);
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("<html><a href=\"http://www21.atwiki.jp/clara/\">http://www21.atwiki.jp/clara/</a>");
			jButton0.setBorder(BorderFactory.createTitledBorder(null, "＠Clara", TitledBorder.LEADING,
					TitledBorder.ABOVE_TOP, new Font("MS UI Gothic", Font.PLAIN, 12), new Color(59, 59, 59)));
			jButton0.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					jButton0ActionActionPerformed(event);
				}
			});
		}
		return jButton0;
	}

	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("OK");
			okButton.setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
			okButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					okButtonActionActionPerformed(event);
				}
			});
		}
		return okButton;
	}

	private JLabel getAppNameLabel() {
		if (appNameLabel == null) {
			appNameLabel = new JLabel();
			appNameLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
			appNameLabel.setText("Clara （β）　-したらばブラウザ for 東方非想天則-");
			appNameLabel.setVerticalAlignment(SwingConstants.TOP);
			appNameLabel.setVerticalTextPosition(SwingConstants.TOP);
		}
		return appNameLabel;
	}

	private void okButtonActionActionPerformed(ActionEvent event) {
		setVisible(false);
	}

	private void jButton0ActionActionPerformed(ActionEvent event) {
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.browse(new URI("http://www21.atwiki.jp/clara/"));
		} catch (IOException e) {
		} catch (URISyntaxException e) {
		}
		setVisible(false);
	}

}
