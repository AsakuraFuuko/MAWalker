package frame;

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
	    // 定位窗口
	    this.setLocation(20, 20);
	    // 设置窗口大小
	    this.setSize(480, 320);
	    // 显示窗口
	    setVisible(true);
	  }
	  
	  public static void main(String[] args){
	    new MainFrame();
	  }
}
