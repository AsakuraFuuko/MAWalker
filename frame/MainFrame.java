package frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.JTextPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JSplitPane;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4823802658310012975L;
	private JPanel contentPane;
	private JTextField textField;

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

		JLabel lblNewLabel_1 = new JLabel(
				"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		lblNewLabel_1.setDoubleBuffered(true);
		lblNewLabel_1.setBounds(50, 0, 300, 15);
		panel_1.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("LV");
		lblNewLabel_2.setBounds(0, 25, 20, 15);
		panel_1.add(lblNewLabel_2);

		JLabel label = new JLabel("250");
		label.setDoubleBuffered(true);
		label.setBounds(20, 25, 25, 15);
		panel_1.add(label);

		JLabel lblAp = new JLabel("AP");
		lblAp.setBounds(55, 25, 20, 15);
		panel_1.add(lblAp);

		JLabel label_1 = new JLabel("250/250");
		label_1.setDoubleBuffered(true);
		label_1.setBounds(75, 25, 50, 15);
		panel_1.add(label_1);

		JLabel lblBc = new JLabel("BC");
		lblBc.setBounds(145, 25, 20, 15);
		panel_1.add(lblBc);

		JLabel label_2 = new JLabel("250/250");
		label_2.setDoubleBuffered(true);
		label_2.setBounds(165, 25, 50, 15);
		panel_1.add(label_2);

		JLabel label_3 = new JLabel("收集物");
		label_3.setBounds(0, 50, 45, 15);
		panel_1.add(label_3);

		JLabel label_4 = new JLabel("10000000");
		label_4.setBounds(45, 50, 60, 15);
		panel_1.add(label_4);

		JLabel label_5 = new JLabel("团贡");
		label_5.setBounds(110, 50, 34, 15);
		panel_1.add(label_5);

		JButton button = new JButton("启动");
		button.setDoubleBuffered(true);
		button.setBounds(641, 0, 60, 20);
		panel_1.add(button);

		JButton button_1 = new JButton("停止");
		button_1.setDoubleBuffered(true);
		button_1.setEnabled(false);
		button_1.setBounds(641, 25, 60, 20);
		panel_1.add(button_1);

		JLabel lblNewLabel_3 = new JLabel("1000000000");
		lblNewLabel_3.setBounds(145, 50, 76, 15);
		panel_1.add(lblNewLabel_3);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setDoubleBuffered(true);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel.add(scrollPane, gbc_scrollPane);

		final JTextArea txtrLogtext = new JTextArea();
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
}
