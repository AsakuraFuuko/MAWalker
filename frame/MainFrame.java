package frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5988513125942516733L;
	  /**
	   * 构造函数
	   *
	   */
	  public MainFrame() {
	    // 设置窗口标题
	    this.setTitle("MAWalker");
	    // 设置窗口大小
	    this.setSize(480, 320);
	    // 定位窗口
	    this.setLocationRelativeTo(null);
	    // 显示窗口
	    setVisible(true);
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	  }

	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
		mf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
