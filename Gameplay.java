import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;//引用資料庫

public class Gameplay extends JPanel implements KeyListener,ActionListener,MouseListener{
	private boolean play = false;//true or flase 的值用來判斷遊戲是否正在運行
	private boolean replay = false;
	private boolean onboard = false;
	private boolean pause = false;
	private boolean sup = false;
	private boolean count = false;
	
	private int score = 0;//分數
	private int countdown;
	
	private int totalBricks = 21;//磚塊總數
	
	private Timer timer;//遊戲計時
	private int delay = 0;
	
	private int playerX=310;//玩家控制的板子X座標
	
	private int ballposX = 0;//球的XY做標
	private int ballposY = 0;
	private int ballXdir = 0;//球的前進方向
	private int ballYdir = 0;
	private double ballspeed = 7;
	private int degrees = 0;
	private int resX = 0;
	private int resY = 0;
	
	private MapGenerator map;//引用其他CLASS作為物件
	
	Container cont;

	Rectangle rect1 = new Rectangle(235, 250, 240, 50);
	Rectangle rect2 = new Rectangle(235, 310, 240, 50);
	Rectangle rect3 = new Rectangle(235, 370, 240, 50);
	Rectangle rect4 = new Rectangle(650, 10, 30, 30);
	Rectangle rect5 = new Rectangle(235, 430, 240, 50);
	
	Counting counting = new Counting();
	Thread thread = new Thread(counting);
	
	private int brickW = 14;
	private int brickH = 7;
	
	public void run() {
		
	}
	
	public void nowplaying() {
		if(play && !replay) {//如果play的值是flase
			play = true;//將play設為true
			ballposX = 364;
			ballposY = 541;//設定球的座標
			ballXdir = 0;
			ballYdir = 0;//設定球的運動方向
			playerX = 310;//設定玩家板子的位置
			score = 0;//分數設為0
			totalBricks = brickW*brickH;//磚塊數設為21個
			map = new MapGenerator(brickH, brickW);//磚塊排列為3X7，將這個數值傳遞到MapGenerator
			repaint();//畫出所有的東西
			thread.start();
		}
	}
	
	public Gameplay() {
		//map = new MapGenerator(3,7);
		addKeyListener(this);//對Gameplay加上按鍵聆聽事件
		addMouseListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {//java在AWT中提供repaint()來觸發這個paint()函式
		if(!play && !replay) {
			g.setColor(Color.black);
			g.fillRect(1, 1, 692, 592);//畫出背景
			//background
			g.setColor(Color.white);
			g.fillRect(235, 250, 240, 50);//畫出背景
			g.setColor(Color.black);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Start", 332, 280);//顯示分數
		}else {
			g.setColor(Color.black);
			g.fillRect(1, 1, 692, 592);//畫出背景
			//background
			
			map.draw((Graphics2D)g);//使用 CLASS MapGenerator中的draw 函式來畫出小磚塊
			//drawMaps
			g.setColor(Color.yellow);
			g.fillRect(0, 0, 3, 592);
			g.fillRect(0, 0, 692, 3);
			g.fillRect(691, 0, 3, 592);////畫出遊戲板邊界
			//borders
			
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString(""+score, 590, 30);//顯示分數
			//score
			g.setColor(Color.green);
			g.fillRect(playerX, 550, 100, 8);//畫出玩家本體
			//paddle
			g.setColor(Color.yellow);
			g.fillOval(ballposX, ballposY, 10, 10);//畫出球
			//ball
			
			if(totalBricks <= 0) {//如果已經沒有磚塊了則結束遊戲並結算成績
					play = false;//將此布林值設為flase
					replay = true;
					ballXdir = 0;//停止球的運動
					ballYdir = 0;
					g.setColor(Color.GREEN);
					g.setFont(new Font("serif", Font.BOLD, 40));
					g.drawString("You Won,Scores:"+score, 190, 300);//顯示"你贏了"的文字
					
					g.setColor(Color.WHITE);
					g.fillRect(235, 310, 240, 50);
					g.setColor(Color.GREEN);
					g.setFont(new Font("serif", Font.BOLD, 25));
					g.drawString("Back to menu", 285, 346);
					
					g.setColor(Color.WHITE);
					g.fillRect(235, 370, 240, 50);
					g.setColor(Color.GREEN);
					g.setFont(new Font("serif", Font.BOLD, 25));
					g.drawString("retry", 324, 403);
			}
			
			if(ballposY > 580 ) {//球掉出板子後的邊界結束遊戲
					play = false;//將此布林值設為flase
					replay = true;
					ballXdir = 0;//停止球的運動
					ballYdir = 0;
					g.setColor(Color.GREEN);
					g.setFont(new Font("serif", Font.BOLD, 40));
					g.drawString("You Lose,Scores:"+score, 190, 300);//顯示"你贏了"的文字
					
					g.setColor(Color.WHITE);
					g.fillRect(235, 310, 240, 50);
					g.setColor(Color.GREEN);
					g.setFont(new Font("serif", Font.BOLD, 25));
					g.drawString("Back to menu", 285, 346);
					
					g.setColor(Color.WHITE);
					g.fillRect(235, 370, 240, 50);
					g.setColor(Color.GREEN);
					g.setFont(new Font("serif", Font.BOLD, 25));
					g.drawString("retry", 324, 403);
			}
			if(pause) {//球掉出板子後的邊界結束遊戲
				resX = ballXdir;
				resY = ballYdir;
				System.out.println(resX);
				System.out.println(resY);
				
				g.setColor(Color.WHITE);
				g.fillRect(235, 310, 240, 50);
				g.setColor(Color.GREEN);
				g.setFont(new Font("serif", Font.BOLD, 25));
				g.drawString("Back to menu", 285, 346);
				
				g.setColor(Color.WHITE);
				g.fillRect(235, 370, 240, 50);
				g.setColor(Color.GREEN);
				g.setFont(new Font("serif", Font.BOLD, 25));
				g.drawString("retry", 324, 403);
				
				g.setColor(Color.WHITE);
				g.fillRect(235, 430, 240, 50);
				g.setColor(Color.GREEN);
				g.setFont(new Font("serif", Font.BOLD, 25));
				g.drawString("resume", 324, 460);
			}
			if(onboard) {
				g.setColor(Color.GREEN);
				g.setFont(new Font("serif", Font.BOLD, 25));
				g.drawString("degree:"+degrees, 560, 477);
			}
			g.setColor(Color.WHITE);
			g.fillRect(650, 10, 30, 30);
			g.setColor(Color.BLACK);
			g.fillRect(656, 16, 6, 18);
			g.setColor(Color.BLACK);
			g.fillRect(668, 16, 6, 18);
			
			
			g.setColor(Color.GREEN);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Time:"+counting.num, 560, 450);
			
			g.dispose();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		if(play&&!count) {//當play為true時
			
			if(new Rectangle(ballposX,ballposY, 10, 10).intersects(new Rectangle(playerX,550,100,8))) {//用函示.intersects來偵測球和玩家板子的碰撞
				if(ballposX + 9<=playerX || ballposX + 1>=playerX+100) {
					ballXdir = -ballXdir;
				}else if(ballposY + 1<=550) {
					ballYdir = -ballYdir;
					if(sup) {
						onboard = true;
						ballXdir = 0;
						ballYdir = 0;
						ballposY = 540;
					}
				}else if(ballposY + 9>=560) {
					ballYdir = -ballYdir;
				}
				//ballYdir = -ballYdir;
			}
			
			A: for(int i = 0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickX = j*map.brickWidth + 80+(j*map.brickbetw-(j-1)*map.brickbetw);
						int brickY = i*map.brickHeight + 50+(i*map.brickbetw-(i-1)*map.brickbetw);
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;//算出一個磚塊的各項數值
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);//將先前算出的數值，放入函式Rectangle中宣告rect物件
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 10, 10);//宣告ballRect物件
						Rectangle brickRect = rect;//將brickRect宣告為rect的物件
						
						if(ballRect.intersects(brickRect)) {//偵測當球的物件和磚塊物件碰撞的時候
							map.setBrickValue(0, i, j);//磚塊被抵銷MapGenerater中的3X7陣列值被設為0
							totalBricks--;//小磚塊的總數-1
							score +=5;//加5分
							ballposY = ballposY - ballYdir;
							ballposX = ballposX - ballXdir;
							
							if(ballposX + 9 <= brickRect.x ) {
								ballXdir = -ballXdir;//撞到磚塊時球反彈，X運動方向反轉
							}else if(ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;//撞到磚塊時球反彈，X運動方向反轉
							}else if(ballposY + 1 <= brickRect.y){
								ballYdir = -ballYdir;//撞到磚塊時球反彈，Y運動方向反轉
							}else if(ballposY + 9 >= brickRect.y + brickRect.height) {
								ballYdir = -ballYdir;//撞到磚塊時球反彈，Y運動方向反轉
							}
							break A;//結束A:標記的迴圈
						}
					}
				}
			}
			
			ballposX += ballXdir;//球的座標+球的運動方向=球的新座標
			ballposY += ballYdir;
			if(ballposX < 0) {
				ballXdir = -ballXdir;//撞到遊戲板邊界時反彈
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;//撞到遊戲板邊界時反彈
			}
			if(ballposX > 676) {
				ballXdir = -ballXdir;//撞到遊戲板邊界時反彈
			}
		}
		repaint();
	}
	public void tri() {
		
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {//先前加入過按鍵聆聽事件，因此任何的按鍵被按下時都會在這裡被捕捉
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {//按下右方向鍵
			if(playerX>=589) {//如果玩家板子超過最右邊的邊界
				playerX=589;//將玩家板子的X設為不能超過邊界的座標
			}else {
				moveRight();//沒有超過邊界，繼續往右移動
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {//按下左方向鍵
			if(playerX<=5) {//如果玩家板子超過最左邊的邊界
				playerX=5;//將玩家板子的X設為不能超過邊界的座標
			}else {
				moveLeft();//沒有超過邊界，繼續往左移動
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE && onboard) {
			onboard = false;
			for(;;) {
				if(!onboard) {
					ballYdir = (int)(-ballspeed*Math.cos(Math.toRadians(degrees)));;//當碰撞時球的Y座標運動方向反轉
					ballXdir = (int)(ballspeed*Math.sin(Math.toRadians(degrees)));
					break;
				}else {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					continue;
				}
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_S) {
			if(!sup) {
				sup = true;
			}else if(sup) {
				sup = false;
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_A) {
			degrees--;
			System.out.println(degrees);
			if(degrees<-80) {
				degrees = -80;
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_D) {
			degrees++;
			System.out.println(degrees);
			if(degrees>80) {
				degrees = 80;
			}
		}
	}
	public void moveRight() {//向右移動
		play = true;
		playerX+=20;
		if(onboard) {
			ballposX+=20;
		}
	}
	public void moveLeft() {//向左移動
		play = true;
		playerX-=20;
		if(onboard) {
			ballposX-=20;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getX()+" "+e.getY());
		if (rect1.contains(e.getX(), e.getY()) && !play && !replay) {
            System.out.println("inside box1");
            play = true;
            nowplaying();
        }
		if (rect2.contains(e.getX(), e.getY()) && !play && replay) {
			System.out.println("inside box2");
			replay = false;
			pause = false;
		}
		if (rect2.contains(e.getX(), e.getY()) && !play && replay && pause) {
			System.out.println("inside box2");
			replay = false;
			pause = false;
		}
		if (rect3.contains(e.getX(), e.getY()) && !play && replay) {
			System.out.println("inside box3");
			play = true;
			replay = false;
			pause = false;
			nowplaying();
    	}
		if (rect3.contains(e.getX(), e.getY()) && !play && replay && pause) {
			System.out.println("inside box3");
			play = true;
			replay = false;
			pause = false;
			nowplaying();
    	}
		if (rect4.contains(e.getX(), e.getY()) && play) {
			System.out.println("inside box4");
			pause = true;
			replay = true;
			play = false;
		}
		if (rect5.contains(e.getX(), e.getY()) && !play && pause) {
			System.out.println("inside box5");
			pause = false;
			play = true;
			replay = false;
			ballXdir = resX;
			ballYdir = resY;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
