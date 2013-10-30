package frame;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import walker.Info.EventType;
import walker.Process;

public class PartyrankFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5380941212025403694L;
	private JPanel contentPane;
	private static JTextArea txtrLogtext;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartyrankFrame frame = new PartyrankFrame();
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
	public PartyrankFrame() {
		setResizable(false);
		setTitle("团员贡献");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 569, 338);
		setSize(570, 338);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
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
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("加载");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Process.info.events.add(EventType.partyRank);
				JOptionPane.showMessageDialog(PartyrankFrame.this,
						"读取团贡事件已加入队列..");
			}
		});
		panel.add(btnNewButton);
	}

}
