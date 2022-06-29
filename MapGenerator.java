import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	public int brickbetw = 1;
	public MapGenerator(int row, int col) {
		map = new int[row][col];
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map[0].length;j++) {
				map[i][j]=1;
			}
		}//�N�p�j�������L�H?X?���G���}�C�x�s
		
		brickWidth = 540/col;
		brickHeight = 150/row;//�N�]�n���ƭȰ��H�ƩM�C�ӱo��?X?
	}
	public void draw(Graphics2D g) {//�e�X�p�j��
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map[0].length;j++) {//���h�j��H���w�C�@�ӤG���}�C�������
				if(map[i][j]>0) {
					g.setColor(Color.white);
					g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
					//fillRect(X�_�l��m, Y�_�l��m, �p�j���e, �p�j����)80�����k���j 50���W�U���j
					g.setStroke(new BasicStroke(brickbetw));
					g.setColor(Color.black);//��p�j���Q�����ɡA���p�j������
					g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
				}
			}
		}
	}
	public void setBrickValue(int value,int row,int col) {
		map[row][col] = value;
	}
}