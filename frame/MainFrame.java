package frame;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;

import walker.Config;
import walker.Go;
import walker.Info.EventType;
import walker.Process;
import walker.Profile2;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4823802658310012975L;
	private JPanel contentPane;
	private JTextField textField;
	public static JTextArea txtrLogtext;
	public static Thread td;
	private static JButton BUTTON_1;
	private static JButton BUTTON;
	public static boolean isExit = false;
	private static JLabel username;
	private static JLabel lv;
	private static JLabel ap;
	private static JLabel bc;
	private static JLabel gather;
	private static JLabel week;
	private static JLabel tickets;

	public static void setUIFont(javax.swing.plaf.FontUIResource f) {
		//
		// sets the default font for all Swing components.
		// ex.
		// setUIFont (new
		// javax.swing.plaf.FontUIResource("Serif",Font.ITALIC,12));
		//
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource)
				UIManager.put(key, f);
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setUIFont(new FontUIResource("Tohoma", Font.PLAIN, 12));
		setTitle("MaWalker");
		setName("MainFrame");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(720, 427);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("设置");
		mnNewMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON1) {
					OptionDialog od = new OptionDialog();
					od.setVisible(true);
					Go.log(String.format("加载了配置文件：%s\n", Go.configFile));
				}
			}
		});
		mnNewMenu.setDoubleBuffered(true);
		menuBar.add(mnNewMenu);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.SOUTH);

		textField = new JTextField();
		textField.setEditable(false);
		toolBar.add(textField);
		textField.setColumns(10);

		JProgressBar progressBar = new JProgressBar();
		toolBar.add(progressBar);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 410, 0 };
		gbl_panel.rowHeights = new int[] { 79, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);

		JLabel lblNewLabel = new JLabel("用户名");
		lblNewLabel.setBounds(0, 0, 45, 15);
		panel_1.add(lblNewLabel);

		username = new JLabel("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		username.setDoubleBuffered(true);
		username.setBounds(50, 0, 300, 15);
		panel_1.add(username);

		JLabel lblNewLabel_2 = new JLabel("LV");
		lblNewLabel_2.setBounds(0, 25, 20, 15);
		panel_1.add(lblNewLabel_2);

		lv = new JLabel("250");
		lv.setDoubleBuffered(true);
		lv.setBounds(20, 25, 25, 15);
		panel_1.add(lv);

		JLabel lblAp = new JLabel("AP");
		lblAp.setBounds(55, 25, 20, 15);
		panel_1.add(lblAp);

		ap = new JLabel("250/250");
		ap.setDoubleBuffered(true);
		ap.setBounds(75, 25, 50, 15);
		panel_1.add(ap);

		JLabel lblBc = new JLabel("BC");
		lblBc.setBounds(145, 25, 20, 15);
		panel_1.add(lblBc);

		bc = new JLabel("250/250");
		bc.setDoubleBuffered(true);
		bc.setBounds(165, 25, 50, 15);
		panel_1.add(bc);

		JLabel label_3 = new JLabel("收集物");
		label_3.setBounds(0, 50, 45, 15);
		panel_1.add(label_3);

		gather = new JLabel("10000000");
		gather.setBounds(45, 50, 60, 15);
		panel_1.add(gather);

		JLabel label_5 = new JLabel("团贡");
		label_5.setBounds(110, 50, 34, 15);
		panel_1.add(label_5);

		BUTTON = new JButton("启动");
		BUTTON.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				td = new Thread() {
					public void run() {
						while (!isExit) {
							Process proc = new Process();
							Profile2 prof = new Profile2();
							while (!isExit) {
								try {
									switch (Config.Profile) {
									case 1:
										proc.auto();
										break;
									case 2:
										prof.auto();
										break;
									}
								} catch (Exception ex) {
									Go.log(ex.getMessage());
									Process.info.events
											.add(EventType.cookieLogin);
									Go.log("Restart");
								}
							}
						}
					}
				};
				isExit = false;
				td.start();
				Go.log("启动...");
				BUTTON.setEnabled(false);
				BUTTON_1.setEnabled(true);
			}
		});
		BUTTON.setDoubleBuffered(true);
		BUTTON.setBounds(641, 0, 60, 20);
		panel_1.add(BUTTON);

		BUTTON_1 = new JButton("停止");
		BUTTON_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (td.isAlive()) {
					isExit = true;
				}
				Go.log("停止...");
				BUTTON.setEnabled(true);
				BUTTON_1.setEnabled(false);
			}
		});
		BUTTON_1.setDoubleBuffered(true);
		BUTTON_1.setEnabled(false);
		BUTTON_1.setBounds(641, 25, 60, 20);
		panel_1.add(BUTTON_1);

		week = new JLabel("1000000000");
		week.setBounds(145, 50, 76, 15);
		panel_1.add(week);

		JLabel label = new JLabel("出击券");
		label.setBounds(225, 25, 45, 15);
		panel_1.add(label);

		tickets = new JLabel("20");
		tickets.setBounds(269, 25, 20, 15);
		panel_1.add(tickets);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setDoubleBuffered(true);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel.add(scrollPane, gbc_scrollPane);

		txtrLogtext = new JTextArea();
		txtrLogtext.setLineWrap(true);
		txtrLogtext.setDoubleBuffered(true);
		txtrLogtext.setEditable(false);
		txtrLogtext.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent mouseEvent) {
				txtrLogtext.setCursor(new Cursor(Cursor.TEXT_CURSOR)); // 鼠标进入Text区后变为文本输入指针
			}

			public void mouseExited(MouseEvent mouseEvent) {
				txtrLogtext.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 鼠标离开Text区后恢复默认形态
			}
		});
		txtrLogtext.getCaret().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				txtrLogtext.getCaret().setVisible(true); // 使Text区的文本光标显示
			}
		});
		scrollPane.setViewportView(txtrLogtext);
	}

	public static void Update() {
		username.setText(String.format("%s (%s)", Process.info.username,
				Config.LoginId));
		lv.setText(String.valueOf(Process.info.lv));
		ap.setText(String.format("%d/%d", Process.info.ap, Process.info.apMax));
		bc.setText(String.format("%d/%d", Process.info.bc, Process.info.bcMax));
		gather.setText(String.valueOf(Process.info.gather));
		week.setText(String.valueOf(Process.info.week));
		tickets.setText(String.valueOf(Process.info.ticket));
	}
}
