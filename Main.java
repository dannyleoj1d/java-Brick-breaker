import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame obj=new JFrame();//宣告視窗物件
		Gameplay gamePlay=new Gameplay();
		obj.setBounds(10,10,710,630);//設定視窗的大小
		obj.setTitle("Breakout Ball");//設定視窗的標題
		obj.setResizable(false);//設定視窗是否可調整
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);//加入CLASS Gameplay的物件
	}
}