import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame obj=new JFrame();//�ŧi��������
		Gameplay gamePlay=new Gameplay();
		obj.setBounds(10,10,710,630);//�]�w�������j�p
		obj.setTitle("Breakout Ball");//�]�w���������D
		obj.setResizable(false);//�]�w�����O�_�i�վ�
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);//�[�JCLASS Gameplay������
	}
}