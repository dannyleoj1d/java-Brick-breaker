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
		}//將小磚塊的有無以?X?的二維陣列儲存
		
		brickWidth = 540/col;
		brickHeight = 150/row;//將設好的數值除以排和列來得知?X?
	}
	public void draw(Graphics2D g) {//畫出小磚塊
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map[0].length;j++) {//雙層迴圈以指定每一個二維陣列中的資料
				if(map[i][j]>0) {
					g.setColor(Color.white);
					g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
					//fillRect(X起始位置, Y起始位置, 小磚塊寬, 小磚塊長)80為左右間隔 50為上下間隔
					g.setStroke(new BasicStroke(brickbetw));
					g.setColor(Color.black);//當小磚塊被打掉時，讓小磚塊消失
					g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
				}
			}
		}
	}
	public void setBrickValue(int value,int row,int col) {
		map[row][col] = value;
	}
}