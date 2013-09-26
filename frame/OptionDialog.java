package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.FontUIResource;

import walker.Config;
import walker.Config.autoUseType;
import walker.GetConfig;
import walker.Go;
import walker.Process;

public class OptionDialog extends JDialog {

	private static JCheckBox allow_attack_same_fairy;
	private static JCheckBox allow_bc_insuffient;
	private static JTextField ap_full_low;
	private static JRadioButton apall;
	private static JRadioButton apfull;
	private static JRadioButton aphalf;
	private static JTextField aplow;
	private static JCheckBox auto_add_point;
	private static JCheckBox auto_use_bc;
	private static JTextField bc_full_low;
	private static JRadioButton bcall;
	private static JRadioButton bcfull;
	private static JRadioButton bchalf;
	private static JTextField bclow;
	private static JTextField configPath;
	private static JCheckBox debug;
	private static JCheckBox fairy_battle_first;
	private static JTextField FairyDeckBc;
	private static JTextField FairyDeckNo;
	private static JTextField FriendFairyBattleNormalBc;
	private static JTextField FriendFairyBattleNormalNo;
	private static JTextField FriendFairyBattleRareBc;
	private static JTextField FriendFairyBattleRareNo;
	private static JTextField guild_battle_percent;
	private static JTextField GuildFairyDeckBc;
	private static JTextField GuildFairyDeckNo;
	private static JTextField keep_guild_battle_tickets;
	private static JTextField LowerBCDeckBc;
	private static JTextField LowerBCDeckNo;
	private static JCheckBox night_mode;
	private static JCheckBox one_ap_only;

	private static JPasswordField password;

	private static JCheckBox rare_fairy_use_normal_deck;

	private static JTextField RareFairyDeckBc;

	private static JTextField RareFairyDeckNo;
	private static JCheckBox receive_battle_present;
	/**
	 * 
	 */
	private static final long serialVersionUID = -510067771228671362L;
	private static JTextField sessionId;
	private static JTextField user_agent;
	private static JTextField username;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			OptionDialog dialog = new OptionDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	private static JCheckBox auto_use_ap;
	private final JPanel contentPanel = new JPanel();
	private static JCheckBox savelog;
	private static ButtonGroup apbg;
	private static ButtonGroup bcbg;
	{
		getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			JButton okButton = new JButton("确定");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (Go.configFile.isEmpty()) {
						JOptionPane.showMessageDialog(OptionDialog.this,
								"请先选择一个配置文件..");
						return;
					}
					updateConfig();
					Config.saveConfig(Go.configFile);
					JOptionPane.showMessageDialog(OptionDialog.this, "保存完成..");
					dispose();
				}
			});
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}
	}

	/**
	 * Create the dialog.
	 */
	public OptionDialog() {
		setResizable(false);
		setTitle("设置");
		setUIFont(new FontUIResource("Tohoma", Font.PLAIN, 12));
		setSize(803, 407);
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			configPath = new JTextField();
			configPath.setName("configPath");
			configPath.setBounds(92, 10, 255, 21);
			contentPanel.add(configPath);
			configPath.setColumns(10);
		}
		{
			JLabel label = new JLabel("配置文件路径");
			label.setBounds(10, 13, 72, 15);
			contentPanel.add(label);
		}
		{
			JButton button = new JButton("浏览");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser jf = new JFileChooser("选择配置文件");
					jf.setDialogTitle("选择配置文件...");
					jf.setFileFilter(new javax.swing.filechooser.FileFilter() {
						public boolean accept(File f) { // 设定可用的文件的后缀名
							if (f.getName().endsWith(".xml") || f.isDirectory()) {
								return true;
							}
							return false;
						}

						public String getDescription() {
							return "配置文件(*.xml)";
						}
					});
					jf.setCurrentDirectory(new File(System
							.getProperty("user.dir")));
					int result = jf.showOpenDialog(OptionDialog.this);
					jf.setVisible(true);
					File selectedFile = null;
					if (result == JFileChooser.APPROVE_OPTION) {
						selectedFile = jf.getSelectedFile();
						if (selectedFile.exists()) {
							Go.configFile = selectedFile.toString();
							configPath.setText(Go.configFile);
							try {
								GetConfig.parse(Process.ParseXMLBytes1(Config
										.ReadFileAll(Go.configFile)));
								parseConfig();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			});
			button.setBounds(360, 9, 64, 23);
			contentPanel.add(button);
		}

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 38, 414, 189);
		contentPanel.add(panel);
		panel.setLayout(null);

		one_ap_only = new JCheckBox("是否只跑1AP的图");
		one_ap_only.setSelected(true);
		one_ap_only.setBounds(6, 6, 124, 23);
		panel.add(one_ap_only);

		fairy_battle_first = new JCheckBox("优先考虑妖精战");
		fairy_battle_first.setBounds(6, 31, 124, 23);
		panel.add(fairy_battle_first);

		allow_bc_insuffient = new JCheckBox("强制跑图");
		allow_bc_insuffient.setToolTipText("不勾选则是当你放的怪死了以后才跑图");
		allow_bc_insuffient.setBounds(6, 56, 81, 23);
		panel.add(allow_bc_insuffient);

		auto_add_point = new JCheckBox("自动加点");
		auto_add_point.setBounds(132, 6, 81, 23);
		panel.add(auto_add_point);

		allow_attack_same_fairy = new JCheckBox("允许舔同一个怪");
		allow_attack_same_fairy.setBounds(132, 31, 118, 23);
		panel.add(allow_attack_same_fairy);

		night_mode = new JCheckBox("夜间模式");
		night_mode.setSelected(true);
		night_mode.setBounds(252, 31, 103, 23);
		panel.add(night_mode);

		receive_battle_present = new JCheckBox("自动收集妖精战礼物");
		receive_battle_present.setSelected(true);
		receive_battle_present.setBounds(252, 6, 141, 23);
		panel.add(receive_battle_present);

		{
			JLabel lblhp = new JLabel("判断外敌战胜利HP的比例(默认0.51)");
			lblhp.setBounds(10, 88, 203, 15);
			panel.add(lblhp);
		}

		guild_battle_percent = new JTextField();
		guild_battle_percent.setText("0.51");
		guild_battle_percent.setBounds(219, 85, 66, 21);
		panel.add(guild_battle_percent);
		guild_battle_percent.setColumns(10);

		{
			JLabel label = new JLabel("出击书保留数量(默认8张) ");
			label.setBounds(10, 113, 161, 15);
			panel.add(label);
		}

		keep_guild_battle_tickets = new JTextField();
		keep_guild_battle_tickets.setText("8");
		keep_guild_battle_tickets.setBounds(219, 110, 66, 21);
		panel.add(keep_guild_battle_tickets);
		keep_guild_battle_tickets.setColumns(10);

		debug = new JCheckBox("debug输出xml(开发者使用) ");
		debug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (debug.isSelected()) {
					savelog.setSelected(true);
					savelog.setEnabled(false);
					// 如果被选中
				} else {
					savelog.setEnabled(true);
					// 未被选中
				}
			}
		});
		debug.setBounds(6, 134, 185, 23);
		panel.add(debug);

		savelog = new JCheckBox("保存运行时的log,当debug勾选时忽略此处设置强制启用");
		savelog.setSelected(true);
		savelog.setBounds(6, 159, 325, 23);
		panel.add(savelog);

		rare_fairy_use_normal_deck = new JCheckBox("使用打普妖卡组来攻击觉醒怪");
		rare_fairy_use_normal_deck.setBounds(132, 56, 185, 23);
		panel.add(rare_fairy_use_normal_deck);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(434, 10, 355, 97);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("User-Agent");
			lblNewLabel.setBounds(10, 66, 68, 15);
			panel_1.add(lblNewLabel);
		}
		{
			user_agent = new JTextField();
			user_agent
					.setText("Million/235 (t03gchn; t03gzc; 4.1.2) samsung/t03gzc/t03gchn:4.1.2/JZO54K/N7100ZCDMD3:user/release-keys GooglePlay");
			user_agent.setBounds(88, 63, 257, 21);
			panel_1.add(user_agent);
			user_agent.setColumns(10);
		}
		{
			JLabel lblid = new JLabel("用户ID");
			lblid.setBounds(10, 10, 54, 15);
			panel_1.add(lblid);
		}
		{
			JLabel label = new JLabel("密码");
			label.setBounds(174, 10, 54, 15);
			panel_1.add(label);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("SessionID");
			lblNewLabel_1.setBounds(10, 38, 59, 15);
			panel_1.add(lblNewLabel_1);
		}
		{
			username = new JTextField();
			username.setBounds(57, 7, 107, 21);
			panel_1.add(username);
			username.setColumns(10);
		}
		{
			sessionId = new JTextField();
			sessionId.setBounds(88, 35, 257, 21);
			panel_1.add(sessionId);
			sessionId.setColumns(10);
		}

		password = new JPasswordField();
		password.setBounds(209, 7, 136, 21);
		panel_1.add(password);

		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_11.setBounds(434, 117, 355, 110);
		contentPanel.add(panel_11);
		panel_11.setLayout(null);

		auto_use_ap = new JCheckBox("自动吃AP药");
		auto_use_ap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (auto_use_ap.isSelected()) {
					aplow.setEnabled(true);
					ap_full_low.setEnabled(true);
					aphalf.setEnabled(true);
					apall.setEnabled(true);
					apfull.setEnabled(true);
					// 如果被选中
				} else {
					aplow.setEnabled(false);
					ap_full_low.setEnabled(false);
					aphalf.setEnabled(false);
					apall.setEnabled(false);
					apfull.setEnabled(false);
					// 未被选中
				}
			}
		});
		auto_use_ap.setBounds(6, 6, 93, 23);
		panel_11.add(auto_use_ap);

		{
			JLabel label = new JLabel("低于此值吃药");
			label.setBounds(105, 10, 72, 15);
			panel_11.add(label);
		}

		aplow = new JTextField();
		aplow.setText("1");
		aplow.setEnabled(false);
		aplow.setBounds(187, 7, 41, 21);
		panel_11.add(aplow);
		aplow.setColumns(10);

		apbg = new ButtonGroup();
		apfull = new JRadioButton("只吃全药");
		apfull.setEnabled(false);
		apfull.setBounds(6, 31, 81, 23);
		panel_11.add(apfull);

		aphalf = new JRadioButton("只吃半药");
		aphalf.setSelected(true);
		aphalf.setEnabled(false);
		aphalf.setBounds(85, 31, 81, 23);
		panel_11.add(aphalf);

		apall = new JRadioButton("都吃(先吃半药，再吃全药)");
		apall.setEnabled(false);
		apall.setBounds(168, 31, 169, 23);
		panel_11.add(apall);
		apbg.add(apfull);
		apbg.add(aphalf);
		apbg.add(apall);

		bcbg = new ButtonGroup();

		auto_use_bc = new JCheckBox("自动吃BC药");
		auto_use_bc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (auto_use_bc.isSelected()) {
					bclow.setEnabled(true);
					bc_full_low.setEnabled(true);
					bchalf.setEnabled(true);
					bcall.setEnabled(true);
					bcfull.setEnabled(true);
					// 如果被选中
				} else {
					bclow.setEnabled(false);
					bc_full_low.setEnabled(false);
					bchalf.setEnabled(false);
					bcall.setEnabled(false);
					bcfull.setEnabled(false);
					// 未被选中
				}
			}
		});
		auto_use_bc.setBounds(6, 56, 93, 23);
		panel_11.add(auto_use_bc);

		JLabel label = new JLabel("低于此值吃药");
		label.setBounds(105, 60, 72, 15);
		panel_11.add(label);

		bclow = new JTextField();
		bclow.setText("50");
		bclow.setEnabled(false);
		bclow.setColumns(10);
		bclow.setBounds(187, 57, 41, 21);
		panel_11.add(bclow);

		bcfull = new JRadioButton("只吃全药");
		bcfull.setEnabled(false);
		bcfull.setBounds(6, 81, 81, 23);
		panel_11.add(bcfull);

		bchalf = new JRadioButton("只吃半药");
		bchalf.setEnabled(false);
		bchalf.setSelected(true);
		bchalf.setBounds(85, 81, 81, 23);
		panel_11.add(bchalf);

		bcall = new JRadioButton("都吃(先吃半药，再吃全药)");
		bcall.setEnabled(false);
		bcall.setBounds(168, 81, 169, 23);
		panel_11.add(bcall);
		bcbg.add(bcfull);
		bcbg.add(bchalf);
		bcbg.add(bcall);
		{
			JLabel lblNewLabel_4 = new JLabel("保留全药");
			lblNewLabel_4.setBounds(238, 10, 48, 15);
			panel_11.add(lblNewLabel_4);
		}

		ap_full_low = new JTextField();
		ap_full_low.setText("10");
		ap_full_low.setEnabled(false);
		ap_full_low.setBounds(296, 7, 41, 21);
		panel_11.add(ap_full_low);
		ap_full_low.setColumns(10);

		{
			JLabel label_1 = new JLabel("保留全药");
			label_1.setBounds(238, 59, 48, 15);
			panel_11.add(label_1);
		}

		bc_full_low = new JTextField();
		bc_full_low.setText("10");
		bc_full_low.setEnabled(false);
		bc_full_low.setColumns(10);
		bc_full_low.setBounds(296, 56, 41, 21);
		panel_11.add(bc_full_low);

		JPanel panel_111 = new JPanel();
		panel_111.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_111.setBounds(10, 237, 779, 101);
		contentPanel.add(panel_111);
		panel_111.setLayout(null);
		{
			JLabel lblNewLabel_2 = new JLabel("卡组设置");
			lblNewLabel_2.setBounds(10, 10, 54, 15);
			panel_111.add(lblNewLabel_2);
		}
		{
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_2.setBounds(123, 10, 99, 81);
			panel_111.add(panel_2);
			panel_2.setLayout(null);
			{
				JLabel lblNewLabel_3 = new JLabel("打妖精用");
				lblNewLabel_3.setBounds(0, 0, 48, 15);
				panel_2.add(lblNewLabel_3);
			}
			{
				FairyDeckNo = new JTextField();
				FairyDeckNo.setText("0");
				FairyDeckNo.setBounds(39, 22, 50, 21);
				panel_2.add(FairyDeckNo);
				FairyDeckNo.setColumns(10);
			}
			{
				JLabel lblId = new JLabel("No.");
				lblId.setBounds(10, 25, 19, 15);
				panel_2.add(lblId);
			}
			{
				JLabel lblBc = new JLabel("BC");
				lblBc.setBounds(10, 50, 19, 15);
				panel_2.add(lblBc);
			}
			{
				FairyDeckBc = new JTextField();
				FairyDeckBc.setText("0");
				FairyDeckBc.setBounds(39, 47, 50, 21);
				panel_2.add(FairyDeckBc);
				FairyDeckBc.setColumns(10);
			}
		}
		{
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_2.setLayout(null);
			panel_2.setBounds(232, 10, 99, 81);
			panel_111.add(panel_2);
			{
				JLabel label1 = new JLabel("打觉醒用");
				label1.setBounds(0, 0, 48, 15);
				panel_2.add(label1);
			}
			{
				RareFairyDeckNo = new JTextField();
				RareFairyDeckNo.setText("0");
				RareFairyDeckNo.setColumns(10);
				RareFairyDeckNo.setBounds(39, 22, 50, 21);
				panel_2.add(RareFairyDeckNo);
			}
			{
				JLabel lblNo = new JLabel("No.");
				lblNo.setBounds(10, 25, 19, 15);
				panel_2.add(lblNo);
			}
			{
				JLabel label1 = new JLabel("BC");
				label1.setBounds(10, 50, 19, 15);
				panel_2.add(label1);
			}
			{
				RareFairyDeckBc = new JTextField();
				RareFairyDeckBc.setText("0");
				RareFairyDeckBc.setColumns(10);
				RareFairyDeckBc.setBounds(39, 47, 50, 21);
				panel_2.add(RareFairyDeckBc);
			}
		}
		{
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_2.setLayout(null);
			panel_2.setBounds(341, 10, 99, 81);
			panel_111.add(panel_2);
			{
				JLabel label1 = new JLabel("打外敌用");
				label1.setBounds(0, 0, 48, 15);
				panel_2.add(label1);
			}
			{
				GuildFairyDeckNo = new JTextField();
				GuildFairyDeckNo.setText("0");
				GuildFairyDeckNo.setColumns(10);
				GuildFairyDeckNo.setBounds(39, 22, 50, 21);
				panel_2.add(GuildFairyDeckNo);
			}
			{
				JLabel lblNo_1 = new JLabel("No.");
				lblNo_1.setBounds(10, 25, 19, 15);
				panel_2.add(lblNo_1);
			}
			{
				JLabel label1 = new JLabel("BC");
				label1.setBounds(10, 50, 19, 15);
				panel_2.add(label1);
			}
			{
				GuildFairyDeckBc = new JTextField();
				GuildFairyDeckBc.setText("0");
				GuildFairyDeckBc.setColumns(10);
				GuildFairyDeckBc.setBounds(39, 47, 50, 21);
				panel_2.add(GuildFairyDeckBc);
			}
		}
		{
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_2.setLayout(null);
			panel_2.setBounds(450, 10, 99, 81);
			panel_111.add(panel_2);
			{
				JLabel label1 = new JLabel("打好友觉醒用");
				label1.setBounds(0, 0, 72, 15);
				panel_2.add(label1);
			}
			{
				FriendFairyBattleRareNo = new JTextField();
				FriendFairyBattleRareNo.setText("0");
				FriendFairyBattleRareNo.setColumns(10);
				FriendFairyBattleRareNo.setBounds(39, 22, 50, 21);
				panel_2.add(FriendFairyBattleRareNo);
			}
			{
				JLabel lblNo_2 = new JLabel("No.");
				lblNo_2.setBounds(10, 25, 19, 15);
				panel_2.add(lblNo_2);
			}
			{
				JLabel label1 = new JLabel("BC");
				label1.setBounds(10, 50, 19, 15);
				panel_2.add(label1);
			}
			{
				FriendFairyBattleRareBc = new JTextField();
				FriendFairyBattleRareBc.setText("0");
				FriendFairyBattleRareBc.setColumns(10);
				FriendFairyBattleRareBc.setBounds(39, 47, 50, 21);
				panel_2.add(FriendFairyBattleRareBc);
			}
		}
		{
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_2.setLayout(null);
			panel_2.setBounds(559, 10, 99, 81);
			panel_111.add(panel_2);
			{
				JLabel label1 = new JLabel("打好友妖精用");
				label1.setBounds(0, 0, 72, 15);
				panel_2.add(label1);
			}
			{
				FriendFairyBattleNormalNo = new JTextField();
				FriendFairyBattleNormalNo.setText("0");
				FriendFairyBattleNormalNo.setColumns(10);
				FriendFairyBattleNormalNo.setBounds(39, 22, 50, 21);
				panel_2.add(FriendFairyBattleNormalNo);
			}
			{
				JLabel lblNo_3 = new JLabel("No.");
				lblNo_3.setBounds(10, 25, 19, 15);
				panel_2.add(lblNo_3);
			}
			{
				JLabel label1 = new JLabel("BC");
				label1.setBounds(10, 50, 19, 15);
				panel_2.add(label1);
			}
			{
				FriendFairyBattleNormalBc = new JTextField();
				FriendFairyBattleNormalBc.setText("0");
				FriendFairyBattleNormalBc.setColumns(10);
				FriendFairyBattleNormalBc.setBounds(39, 47, 50, 21);
				panel_2.add(FriendFairyBattleNormalBc);
			}
		}
		{
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_2.setLayout(null);
			panel_2.setBounds(670, 10, 99, 81);
			panel_111.add(panel_2);
			{
				JLabel label1 = new JLabel("舔妖卡组");
				label1.setBounds(0, 0, 48, 15);
				panel_2.add(label1);
			}
			{
				LowerBCDeckNo = new JTextField();
				LowerBCDeckNo.setText("0");
				LowerBCDeckNo.setColumns(10);
				LowerBCDeckNo.setBounds(39, 22, 50, 21);
				panel_2.add(LowerBCDeckNo);
			}
			{
				JLabel lblNo_4 = new JLabel("No.");
				lblNo_4.setBounds(10, 25, 19, 15);
				panel_2.add(lblNo_4);
			}
			{
				JLabel label1 = new JLabel("BC");
				label1.setBounds(10, 50, 19, 15);
				panel_2.add(label1);
			}
			{
				LowerBCDeckBc = new JTextField();
				LowerBCDeckBc.setText("0");
				LowerBCDeckBc.setColumns(10);
				LowerBCDeckBc.setBounds(39, 47, 50, 21);
				panel_2.add(LowerBCDeckBc);
			}
		}
		{
			JTextPane textPane = new JTextPane();
			textPane.setEditable(false);
			textPane.setBackground(SystemColor.control);
			textPane.setText("0：卡组1\r\n1：卡组2\r\n2：卡组3\r\n3：推荐卡组");
			textPane.setBounds(10, 25, 103, 66);
			panel_111.add(textPane);
		}

		if (Go.configFile != null && !Go.configFile.isEmpty()) {
			configPath.setText(Go.configFile);
			try {
				GetConfig.parse(Process.ParseXMLBytes1(Config
						.ReadFileAll(Go.configFile)));
				parseConfig();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		setModal(true);
	}

	public static void parseConfig() {
		username.setText(Config.LoginId);
		password.setText(Config.LoginPw);
		one_ap_only.setSelected(Config.OneAPOnly);
		auto_add_point.setSelected(Config.AutoAddp);
		receive_battle_present.setSelected(Config.receiveBattlePresent);
		fairy_battle_first.setSelected(Config.FairyBattleFirst);
		allow_attack_same_fairy.setSelected(Config.AllowAttackSameFairy);
		night_mode.setSelected(Config.nightModeSwitch);
		allow_bc_insuffient.setSelected(Config.AllowBCInsuffient);
		rare_fairy_use_normal_deck.setSelected(Config.RareFairyUseNormalDeck);
		guild_battle_percent.setText(String.format("%.2f",
				Config.GuildBattlePercent));
		keep_guild_battle_tickets.setText(String.format("%d",
				Config.keepGuildBattleTicksts));

		debug.setSelected(Config.debug);
		savelog.setSelected(Config.saveLog);
		if (debug.isSelected()) {
			savelog.setSelected(true);
			savelog.setEnabled(false);
		}

		sessionId.setText(Config.sessionId);
		user_agent.setText(Config.UserAgent);

		auto_use_ap.setSelected(Config.autoUseAp);
		aplow.setText(String.format("%d", Config.autoApLow));
		ap_full_low.setText(String.format("%d", Config.autoApFullLow));
		switch (Config.autoApType) {
		case FULL_ONLY:
			apfull.setSelected(true);
			break;
		case ALL:
			apall.setSelected(true);
			break;
		case HALF_ONLY:
		default:
			aphalf.setSelected(true);
			break;
		}

		auto_use_bc.setSelected(Config.autoUseBc);
		bclow.setText(String.format("%d", Config.autoBcLow));
		bc_full_low.setText(String.format("%d", Config.autoBcFullLow));
		switch (Config.autoBcType) {
		case FULL_ONLY:
			bcfull.setSelected(true);
			break;
		case ALL:
			bcall.setSelected(true);
			break;
		case HALF_ONLY:
		default:
			bchalf.setSelected(true);
			break;
		}

		FairyDeckNo.setText(Config.PrivateFairyBattleNormal.No);
		FairyDeckBc.setText(String.format("%d",
				Config.PrivateFairyBattleNormal.BC));

		RareFairyDeckNo.setText(Config.PrivateFairyBattleRare.No);
		RareFairyDeckBc.setText(String.format("%d",
				Config.PrivateFairyBattleRare.BC));

		GuildFairyDeckNo.setText(Config.PublicFairyBattle.No);
		GuildFairyDeckBc.setText(String.format("%d",
				Config.PublicFairyBattle.BC));

		FriendFairyBattleRareNo.setText(Config.FriendFairyBattleRare.No);
		FriendFairyBattleRareBc.setText(String.format("%d",
				Config.FriendFairyBattleRare.BC));

		FriendFairyBattleNormalNo.setText(Config.FriendFairyBattleNormal.No);
		FriendFairyBattleNormalBc.setText(String.format("%d",
				Config.FriendFairyBattleNormal.BC));

		LowerBCDeckNo.setText(Config.LowerBCDeck.No);
		LowerBCDeckBc.setText(String.format("%d", Config.LowerBCDeck.BC));
	}

	public static void updateConfig() {
		Config.LoginId = username.getText();
		Config.LoginPw = String.valueOf(password.getPassword());
		Config.OneAPOnly = one_ap_only.isSelected();
		Config.AutoAddp = auto_add_point.isSelected();
		Config.receiveBattlePresent = receive_battle_present.isSelected();
		Config.FairyBattleFirst = fairy_battle_first.isSelected();
		Config.AllowAttackSameFairy = allow_attack_same_fairy.isSelected();
		Config.nightModeSwitch = night_mode.isSelected();
		Config.AllowBCInsuffient = allow_bc_insuffient.isSelected();
		Config.RareFairyUseNormalDeck = rare_fairy_use_normal_deck.isSelected();
		Config.GuildBattlePercent = Double.parseDouble(guild_battle_percent
				.getText());
		Config.keepGuildBattleTicksts = Integer
				.parseInt(keep_guild_battle_tickets.getText());

		Config.debug = debug.isSelected();
		Config.saveLog = savelog.isSelected();
		if (debug.isSelected()) {
			Config.saveLog = true;
		}

		Config.sessionId = sessionId.getText();
		Config.UserAgent = user_agent.getText();

		Config.autoUseAp = auto_use_ap.isSelected();
		Config.autoApLow = Integer.parseInt(aplow.getText());
		Config.autoApFullLow = Integer.parseInt(ap_full_low.getText());
		if (apfull.isSelected())
			Config.autoApType = autoUseType.FULL_ONLY;
		else if (apall.isSelected())
			Config.autoApType = autoUseType.ALL;
		else
			Config.autoApType = autoUseType.HALF_ONLY;

		Config.autoUseBc = auto_use_bc.isSelected();
		Config.autoBcLow = Integer.parseInt(bclow.getText());
		Config.autoBcFullLow = Integer.parseInt(bc_full_low.getText());
		if (bcfull.isSelected())
			Config.autoBcType = autoUseType.FULL_ONLY;
		else if (bcall.isSelected())
			Config.autoBcType = autoUseType.ALL;
		else
			Config.autoBcType = autoUseType.HALF_ONLY;

		Config.PrivateFairyBattleNormal.No = FairyDeckNo.getText();
		Config.PrivateFairyBattleNormal.BC = Integer.parseInt(FairyDeckBc
				.getText());
		Config.PrivateFairyBattleRare.No = RareFairyDeckNo.getText();
		Config.PrivateFairyBattleRare.BC = Integer.parseInt(RareFairyDeckBc
				.getText());
		Config.PublicFairyBattle.No = GuildFairyDeckNo.getText();
		Config.PublicFairyBattle.BC = Integer.parseInt(GuildFairyDeckBc
				.getText());
		Config.FriendFairyBattleRare.No = FriendFairyBattleRareNo.getText();
		Config.FriendFairyBattleRare.BC = Integer
				.parseInt(FriendFairyBattleRareBc.getText());
		Config.FriendFairyBattleNormal.No = FriendFairyBattleNormalNo.getText();
		Config.FriendFairyBattleNormal.BC = Integer
				.parseInt(FriendFairyBattleNormalBc.getText());
		Config.LowerBCDeck.No = LowerBCDeckNo.getText();
		Config.LowerBCDeck.BC = Integer.parseInt(LowerBCDeckBc.getText());
	}
}
