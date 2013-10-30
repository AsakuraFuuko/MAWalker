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
import javax.swing.JOptionPane;
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
import walker.Config;
import walker.DbFile;
import walker.GetConfig;
import walker.Go;
import walker.Info.EventType;
import walker.Process;
import walker.SQLiteCRUD;
import walker.SQLiteConn;
import javax.swing.JMenuItem;

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
	private static JLabel exp;
	private static JLabel cards;
	private static JLabel guidename;
	private static JLabel guidelv;
	private static JProgressBar ownhp;
	private static JProgressBar rivalhp;
	private static JProgressBar totalhp;
	private static JLabel redtea;
	private static JLabel redtube;
	private static JLabel greentea;
	private static JLabel greentube;
	private static JLabel todaytube;
	private static JLabel chaincount;
	private static JLabel weak;

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
		try {
		    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		    UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Tohoma", Font.PLAIN, 12));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					Config.dbfile = new DbFile("config.db");
					Config.sqliteconn = new SQLiteConn(Config.dbfile);
					Config.sqlitecrud = new SQLiteCRUD(Config.sqliteconn);
					Config.sqlitecrud
							.createTable("CREATE TABLE 'config' ("
									+ "'id'  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
									+ "'username'  TEXT NOT NULL,"
									+ "'password'  TEXT NOT NULL,"
									+ "'sessionId'  TEXT,"
									+ "'user_agent'  TEXT NOT NULL DEFAULT 'Million/236 (t03gchn; t03gzc; 4.1.2) samsung/t03gzc/t03gchn:4.1.2/JZO54K/N7100ZCDMD3:user/release-keys GooglePlay',"
									+ "'fairy_battle_first'  BOOL NOT NULL DEFAULT false,"
									+ "'rare_fairy_use_normal_deck'  BOOL NOT NULL DEFAULT false,"
									+ "'allow_bc_insuffient'  BOOL NOT NULL DEFAULT false,"
									+ "'one_ap_only'  BOOL NOT NULL DEFAULT true,"
									+ "'auto_add_point'  BOOL NOT NULL DEFAULT false,"
									+ "'allow_attack_same_fairy'  BOOL NOT NULL DEFAULT false,"
									+ "'night_mode'  BOOL NOT NULL DEFAULT true,"
									+ "'receive_battle_present'  BOOL NOT NULL DEFAULT true,"
									+ "'guild_battle_percent'  DOUBLE NOT NULL DEFAULT 0.51,"
									+ "'keep_guild_battle_tickets'  INTEGER NOT NULL DEFAULT 8,"
									+ "'debug'  BOOL NOT NULL DEFAULT false,"
									+ "'savelog'  BOOL NOT NULL DEFAULT true,"
									+ "'auto_use_ap'  BOOL NOT NULL DEFAULT false,"
									+ "'aplow'  INTEGER NOT NULL DEFAULT 1,"
									+ "'ap_full_low'  INTEGER NOT NULL DEFAULT 10,"
									+ "'autoApType'  INTEGER NOT NULL DEFAULT 1,"
									+ "'auto_use_bc'  BOOL NOT NULL DEFAULT false,"
									+ "'bclow'  INTEGER NOT NULL DEFAULT 50,"
									+ "'bc_full_low'  INTEGER NOT NULL DEFAULT 10,"
									+ "'autoBcType'  INTEGER NOT NULL DEFAULT 1,"
									+ "'FairyDeckNo'  INTEGER NOT NULL DEFAULT 0,"
									+ "'FairyDeckBc'  INTEGER NOT NULL DEFAULT 0,"
									+ "'RareFairyDeckNo'  INTEGER NOT NULL DEFAULT 0,"
									+ "'RareFairyDeckBc'  INTEGER NOT NULL DEFAULT 0,"
									+ "'GuildFairyDeckNo'  INTEGER NOT NULL DEFAULT 0,"
									+ "'GuildFairyDeckBc'  INTEGER NOT NULL DEFAULT 0,"
									+ "'FriendFairyBattleRareNo'  INTEGER NOT NULL DEFAULT 0,"
									+ "'FriendFairyBattleRareBc'  INTEGER NOT NULL DEFAULT 0,"
									+ "'FriendFairyBattleNormalNo'  INTEGER NOT NULL DEFAULT 0,"
									+ "'FriendFairyBattleNormalBc'  INTEGER NOT NULL DEFAULT 0,"
									+ "'LowerBCDeckNo'  INTEGER NOT NULL DEFAULT 0,"
									+ "'LowerBCDeckBc'  INTEGER NOT NULL DEFAULT 0,"
									+ "CONSTRAINT 'uni' UNIQUE ('username' ASC)"
									+ ");");
					Config.sqlitecrud
							.createTable("CREATE TABLE 'last' ('id'  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,'last'  TEXT);");
					if (Config.sqlitecrud.getTableCount("last") != 0) {
						String last = Config.sqlitecrud.select("last").get(0)
								.get(1).toString();
						GetConfig.fromDB(last);
						if (!Config.LoginId.isEmpty()) {
							Go.log(String.format("加载了%s的设置...\n",
									Config.LoginId));
						} else {
							Go.log("请先添加一个用户...");
						}
					}
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
		//setUIFont(new FontUIResource("Tohoma", Font.PLAIN, 12));
		setTitle("MaWalker Gui");
		setName("MainFrame");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(720, 438);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("设置");
		mnNewMenu.setDoubleBuffered(true);
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("用户设置");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OptionDialog od = new OptionDialog();
				od.setVisible(true);
				Go.log(String.format("加载了%s的设置...\n", Config.LoginId));
			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenu menu = new JMenu("附加0.0");
		menuBar.add(menu);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("手动更新信息");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Process.info.events.add(EventType.updateInfo);
				JOptionPane
						.showMessageDialog(MainFrame.this, "更新事件已加入队列..");
			}
		});
		menu.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("查看团成员贡献");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Process.info.events.add(EventType.partyRank);
				JOptionPane.showMessageDialog(MainFrame.this,
						"读取团贡事件已加入队列..");
			}
		});
		menu.add(mntmNewMenuItem_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.SOUTH);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setVisible(false);
		toolBar.add(textField);
		textField.setColumns(10);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 410, 0 };
		gbl_panel.rowHeights = new int[] { 108, 0, 0 };
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
		lblAp.setBounds(182, 25, 20, 15);
		panel_1.add(lblAp);

		ap = new JLabel("250/250");
		ap.setDoubleBuffered(true);
		ap.setBounds(202, 25, 50, 15);
		panel_1.add(ap);

		JLabel lblBc = new JLabel("BC");
		lblBc.setBounds(262, 25, 20, 15);
		panel_1.add(lblBc);

		bc = new JLabel("250/250");
		bc.setDoubleBuffered(true);
		bc.setBounds(282, 25, 50, 15);
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
							while (!isExit) {
								try {
									proc.auto();
								} catch (Exception ex) {
									Go.log(ex.getMessage());
									Process.info.events
											.add(EventType.cookieLogin);
									Go.log("Restart");
								}
							}
							Go.log("已停止...");
							BUTTON.setEnabled(true);
							BUTTON_1.setEnabled(false);
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
					Go.log("正在停止...");
				}
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
		label.setBounds(341, 25, 45, 15);
		panel_1.add(label);

		tickets = new JLabel("20");
		tickets.setBounds(385, 25, 20, 15);
		panel_1.add(tickets);

		JLabel lblexp = new JLabel("升级还需Exp");
		lblexp.setBounds(50, 25, 76, 15);
		panel_1.add(lblexp);

		exp = new JLabel("1000000");
		exp.setBounds(124, 25, 60, 15);
		panel_1.add(exp);

		JLabel label_1 = new JLabel("卡片数");
		label_1.setBounds(231, 50, 40, 15);
		panel_1.add(label_1);

		cards = new JLabel("250/250");
		cards.setBounds(273, 50, 54, 15);
		panel_1.add(cards);

		JLabel label_2 = new JLabel("外敌名称");
		label_2.setBounds(404, 0, 54, 15);
		panel_1.add(label_2);

		guidename = new JLabel("xxxxxxxxxxxx");
		guidename.setBounds(473, 0, 80, 15);
		panel_1.add(guidename);

		JLabel lblNewLabel_3 = new JLabel("外敌等级");
		lblNewLabel_3.setBounds(404, 25, 54, 15);
		panel_1.add(lblNewLabel_3);

		guidelv = new JLabel("250");
		guidelv.setBounds(473, 25, 54, 15);
		panel_1.add(guidelv);

		JLabel label_4 = new JLabel("攻击血量");
		label_4.setBounds(404, 50, 48, 15);
		panel_1.add(label_4);

		ownhp = new JProgressBar();
		ownhp.setBounds(497, 51, 76, 14);
		panel_1.add(ownhp);

		rivalhp = new JProgressBar();
		rivalhp.setBounds(584, 51, 76, 14);
		panel_1.add(rivalhp);

		JLabel label_6 = new JLabel("我方");
		label_6.setBounds(460, 50, 27, 15);
		panel_1.add(label_6);

		JLabel label_7 = new JLabel("对方");
		label_7.setBounds(670, 50, 24, 15);
		panel_1.add(label_7);

		JLabel label_8 = new JLabel("红茶");
		label_8.setBounds(0, 75, 34, 15);
		panel_1.add(label_8);

		redtea = new JLabel("1000");
		redtea.setBounds(29, 75, 34, 15);
		panel_1.add(redtea);

		JLabel label_10 = new JLabel("红试管");
		label_10.setBounds(69, 75, 36, 15);
		panel_1.add(label_10);

		redtube = new JLabel("1000");
		redtube.setBounds(110, 75, 34, 15);
		panel_1.add(redtube);

		JLabel label_12 = new JLabel("绿茶");
		label_12.setBounds(145, 75, 34, 15);
		panel_1.add(label_12);

		greentea = new JLabel("1000");
		greentea.setBounds(180, 75, 34, 15);
		panel_1.add(greentea);

		JLabel label_14 = new JLabel("绿试管");
		label_14.setBounds(219, 75, 36, 15);
		panel_1.add(label_14);

		greentube = new JLabel("1000");
		greentube.setBounds(262, 75, 34, 15);
		panel_1.add(greentube);

		JLabel label_16 = new JLabel("外敌残血量");
		label_16.setBounds(404, 75, 60, 15);
		panel_1.add(label_16);

		totalhp = new JProgressBar();
		totalhp.setBounds(473, 76, 221, 14);
		panel_1.add(totalhp);

		JLabel label_9 = new JLabel("今日剩余");
		label_9.setBounds(296, 75, 54, 15);
		panel_1.add(label_9);

		todaytube = new JLabel("5红5绿");
		todaytube.setBounds(351, 75, 45, 15);
		panel_1.add(todaytube);

		JLabel label_11 = new JLabel("连击数");
		label_11.setBounds(508, 25, 45, 15);
		panel_1.add(label_11);

		chaincount = new JLabel("999");
		chaincount.setBounds(552, 25, 25, 15);
		panel_1.add(chaincount);

		JLabel label_13 = new JLabel("弱点");
		label_13.setBounds(567, 0, 24, 15);
		panel_1.add(label_13);

		weak = new JLabel("未知");
		weak.setBounds(597, 0, 34, 15);
		panel_1.add(weak);

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
		exp.setText(String.valueOf(Process.info.exp));
		cards.setText(String.format("%d/%d", Process.info.cardList.size(),
				Process.info.cardMax));
		guidename.setText(Process.info.gfairy.FairyName);
		guidelv.setText(Process.info.gfairy.FairyLevel);

		double ora = (double) Process.info.gfairy.OwnGuildHP
				/ (double) Process.info.gfairy.GuildTotalHP;
		double rra = (double) Process.info.gfairy.RivalGuildHP
				/ (double) Process.info.gfairy.GuildTotalHP;

		ownhp.setMaximum((int) (Config.GuildBattlePercent * 100));
		ownhp.setValue((int) (ora * 100));
		rivalhp.setMaximum((int) (Config.GuildBattlePercent * 100));
		rivalhp.setValue((int) (rra * 100));
		totalhp.setMaximum((int) Process.info.gfairy.GuildTotalHP);
		totalhp.setValue((int) (Process.info.gfairy.GuildTotalHP
				- Process.info.gfairy.OwnGuildHP - Process.info.gfairy.RivalGuildHP));

		redtea.setText(String.valueOf(Process.info.fullBc));
		redtube.setText(String.valueOf(Process.info.halfBc));
		greentea.setText(String.valueOf(Process.info.fullAp));
		greentube.setText(String.valueOf(Process.info.halfAp));
		todaytube.setText(String.format("%d红%d绿", Process.info.halfBcToday,
				Process.info.halfApToday));
		weak.setText(Process.info.gfairy.weak);

		chaincount.setText(Process.info.gfairy.ChainCounter);
	}
}
