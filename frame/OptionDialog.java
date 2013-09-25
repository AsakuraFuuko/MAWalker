package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.FontUIResource;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.border.EtchedBorder;
import javax.swing.JCheckBox;

public class OptionDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;

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
			OptionDialog dialog = new OptionDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OptionDialog() {
		setTitle("设置");
		setModal(true);
		setUIFont(new FontUIResource("Tohoma", Font.PLAIN, 12));
		setSize(815, 376);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			textField = new JTextField();
			textField.setBounds(92, 10, 255, 21);
			contentPanel.add(textField);
			textField.setColumns(10);
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
							if (f.getName().endsWith(".xml")
									|| f.isDirectory()) {
								return true;
							}
							return false;
						}

						public String getDescription() {
							return "配置文件(*.xml)";
						}
					});
					jf.setCurrentDirectory(new File(System.getProperty("user.dir")));
					int result = jf.showOpenDialog(OptionDialog.this);
					jf.setVisible(true);
					File selectedFile = null;
					if (result == JFileChooser.APPROVE_OPTION) {
					    selectedFile = jf.getSelectedFile();
					    if (selectedFile.exists()) {
					    	textField.setText(selectedFile.toString());
					    }
					}
				}
			});
			button.setBounds(360, 9, 64, 23);
			contentPanel.add(button);
		}
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 38, 414, 256);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("是否只跑1AP的图");
		chckbxNewCheckBox.setBounds(6, 6, 124, 23);
		panel.add(chckbxNewCheckBox);
		
		JCheckBox checkBox = new JCheckBox("优先考虑妖精战");
		checkBox.setBounds(6, 31, 124, 23);
		panel.add(checkBox);
		{
			JCheckBox chckbxbc = new JCheckBox("强制跑图，不管bc是否充足，默认是当你放的怪死了以后才跑图");
			chckbxbc.setBounds(6, 56, 372, 23);
			panel.add(chckbxbc);
		}
		{
			JCheckBox checkBox_1 = new JCheckBox("自动加点");
			checkBox_1.setBounds(132, 6, 81, 23);
			panel.add(checkBox_1);
		}
		{
			JCheckBox checkBox_1 = new JCheckBox("允许舔同一个怪");
			checkBox_1.setBounds(132, 31, 118, 23);
			panel.add(checkBox_1);
		}
		{
			JCheckBox checkBox_1 = new JCheckBox("夜间模式");
			checkBox_1.setBounds(252, 31, 103, 23);
			panel.add(checkBox_1);
		}
		{
			JCheckBox checkBox_1 = new JCheckBox("自动收集妖精战礼物");
			checkBox_1.setBounds(252, 6, 141, 23);
			panel.add(checkBox_1);
		}
		{
			JLabel lblhp = new JLabel("判断外敌战胜利HP的比例(默认0.51)");
			lblhp.setBounds(10, 109, 203, 15);
			panel.add(lblhp);
		}
		{
			textField_1 = new JTextField();
			textField_1.setBounds(223, 106, 66, 21);
			panel.add(textField_1);
			textField_1.setColumns(10);
		}
		{
			JLabel label = new JLabel("出击书保留数量(默认8张) ");
			label.setBounds(10, 134, 161, 15);
			panel.add(label);
		}
		{
			textField_2 = new JTextField();
			textField_2.setBounds(223, 131, 66, 21);
			panel.add(textField_2);
			textField_2.setColumns(10);
		}
		{
			JCheckBox chckbxNewCheckBox_1 = new JCheckBox("debug输出xml(开发者使用) ");
			chckbxNewCheckBox_1.setBounds(6, 195, 185, 23);
			panel.add(chckbxNewCheckBox_1);
		}
		{
			JCheckBox chckbxlogdebug = new JCheckBox("保存运行时的log,当debug勾选时忽略此处设置强制启用");
			chckbxlogdebug.setBounds(6, 220, 325, 23);
			panel.add(chckbxlogdebug);
		}
		{
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
				textField_3 = new JTextField();
				textField_3.setBounds(88, 63, 257, 21);
				panel_1.add(textField_3);
				textField_3.setColumns(10);
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
				textField_4 = new JTextField();
				textField_4.setBounds(57, 7, 107, 21);
				panel_1.add(textField_4);
				textField_4.setColumns(10);
			}
			{
				textField_5 = new JTextField();
				textField_5.setBounds(209, 7, 136, 21);
				panel_1.add(textField_5);
				textField_5.setColumns(10);
			}
			{
				textField_6 = new JTextField();
				textField_6.setBounds(88, 35, 257, 21);
				panel_1.add(textField_6);
				textField_6.setColumns(10);
			}
		}
		{
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_1.setBounds(434, 117, 355, 177);
			contentPanel.add(panel_1);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("确定");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
