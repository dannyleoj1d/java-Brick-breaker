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
import javax.swing.Timer;//�ޥθ�Ʈw

public class Gameplay extends JPanel implements KeyListener,ActionListener,MouseListener{
	private boolean play = false;//true or flase ���ȥΨӧP�_�C���O�_���b�B��
	private boolean replay = false;
	private boolean onboard = false;
	private boolean pause = false;
	private boolean sup = false;
	private boolean count = false;
	
	private int score = 0;//����
	private int countdown;
	
	private int totalBricks = 21;//�j���`��
	
	private Timer timer;//�C���p��
	private int delay = 0;
	
	private int playerX=310;//���a����O�lX�y��
	
	private int ballposX = 0;//�y��XY����
	private int ballposY = 0;
	private int ballXdir = 0;//�y���e�i��V
	private int ballYdir = 0;
	private double ballspeed = 7;
	private int degrees = 0;
	private int resX = 0;
	private int resY = 0;
	
	private MapGenerator map;//�ޥΨ�LCLASS�@������
	
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
		if(play && !replay) {//�p�Gplay���ȬOflase
			play = true;//�Nplay�]��true
			ballposX = 364;
			ballposY = 541;//�]�w�y���y��
			ballXdir = 0;
			ballYdir = 0;//�]�w�y���B�ʤ�V
			playerX = 310;//�]�w���a�O�l����m
			score = 0;//���Ƴ]��0
			totalBricks = brickW*brickH;//�j���Ƴ]��21��
			map = new MapGenerator(brickH, brickW);//�j���ƦC��3X7�A�N�o�Ӽƭȶǻ���MapGenerator
			repaint();//�e�X�Ҧ����F��
			thread.start();
		}
	}
	
	public Gameplay() {
		//map = new MapGenerator(3,7);
		addKeyListener(this);//��Gameplay�[�W�����ť�ƥ�
		addMouseListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {//java�bAWT������repaint()��Ĳ�o�o��paint()�禡
		if(!play && !replay) {
			g.setColor(Color.black);
			g.fillRect(1, 1, 692, 592);//�e�X�I��
			//background
			g.setColor(Color.white);
			g.fillRect(235, 250, 240, 50);//�e�X�I��
			g.setColor(Color.black);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Start", 332, 280);//��ܤ���
		}else {
			g.setColor(Color.black);
			g.fillRect(1, 1, 692, 592);//�e�X�I��
			//background
			
			map.draw((Graphics2D)g);//�ϥ� CLASS MapGenerator����draw �禡�ӵe�X�p�j��
			//drawMaps
			g.setColor(Color.yellow);
			g.fillRect(0, 0, 3, 592);
			g.fillRect(0, 0, 692, 3);
			g.fillRect(691, 0, 3, 592);////�e�X�C���O���
			//borders
			
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString(""+score, 590, 30);//��ܤ���
			//score
			g.setColor(Color.green);
			g.fillRect(playerX, 550, 100, 8);//�e�X���a����
			//paddle
			g.setColor(Color.yellow);
			g.fillOval(ballposX, ballposY, 10, 10);//�e�X�y
			//ball
			
			if(totalBricks <= 0) {//�p�G�w�g�S���j���F�h�����C���õ��⦨�Z
					play = false;//�N�����L�ȳ]��flase
					replay = true;
					ballXdir = 0;//����y���B��
					ballYdir = 0;
					g.setColor(Color.GREEN);
					g.setFont(new Font("serif", Font.BOLD, 40));
					g.drawString("You Won,Scores:"+score, 190, 300);//���"�AĹ�F"����r
					
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
			
			if(ballposY > 580 ) {//�y���X�O�l�᪺��ɵ����C��
					play = false;//�N�����L�ȳ]��flase
					replay = true;
					ballXdir = 0;//����y���B��
					ballYdir = 0;
					g.setColor(Color.GREEN);
					g.setFont(new Font("serif", Font.BOLD, 40));
					g.drawString("You Lose,Scores:"+score, 190, 300);//���"�AĹ�F"����r
					
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
			if(pause) {//�y���X�O�l�᪺��ɵ����C��
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
		if(play&&!count) {//��play��true��
			
			if(new Rectangle(ballposX,ballposY, 10, 10).intersects(new Rectangle(playerX,550,100,8))) {//�Ψ��.intersects�Ӱ����y�M���a�O�l���I��
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
						int brickHeight = map.brickHeight;//��X�@�ӿj�����U���ƭ�
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);//�N���e��X���ƭȡA��J�禡Rectangle���ŧirect����
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 10, 10);//�ŧiballRect����
						Rectangle brickRect = rect;//�NbrickRect�ŧi��rect������
						
						if(ballRect.intersects(brickRect)) {//������y������M�j������I�����ɭ�
							map.setBrickValue(0, i, j);//�j���Q��PMapGenerater����3X7�}�C�ȳQ�]��0
							totalBricks--;//�p�j�����`��-1
							score +=5;//�[5��
							ballposY = ballposY - ballYdir;
							ballposX = ballposX - ballXdir;
							
							if(ballposX + 9 <= brickRect.x ) {
								ballXdir = -ballXdir;//����j���ɲy�ϼu�AX�B�ʤ�V����
							}else if(ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;//����j���ɲy�ϼu�AX�B�ʤ�V����
							}else if(ballposY + 1 <= brickRect.y){
								ballYdir = -ballYdir;//����j���ɲy�ϼu�AY�B�ʤ�V����
							}else if(ballposY + 9 >= brickRect.y + brickRect.height) {
								ballYdir = -ballYdir;//����j���ɲy�ϼu�AY�B�ʤ�V����
							}
							break A;//����A:�аO���j��
						}
					}
				}
			}
			
			ballposX += ballXdir;//�y���y��+�y���B�ʤ�V=�y���s�y��
			ballposY += ballYdir;
			if(ballposX < 0) {
				ballXdir = -ballXdir;//����C���O��ɮɤϼu
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;//����C���O��ɮɤϼu
			}
			if(ballposX > 676) {
				ballXdir = -ballXdir;//����C���O��ɮɤϼu
			}
		}
		repaint();
	}
	public void tri() {
		
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {//���e�[�J�L�����ť�ƥ�A�]�����󪺫���Q���U�ɳ��|�b�o�̳Q����
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {//���U�k��V��
			if(playerX>=589) {//�p�G���a�O�l�W�L�̥k�䪺���
				playerX=589;//�N���a�O�l��X�]������W�L��ɪ��y��
			}else {
				moveRight();//�S���W�L��ɡA�~�򩹥k����
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {//���U����V��
			if(playerX<=5) {//�p�G���a�O�l�W�L�̥��䪺���
				playerX=5;//�N���a�O�l��X�]������W�L��ɪ��y��
			}else {
				moveLeft();//�S���W�L��ɡA�~�򩹥�����
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE && onboard) {
			onboard = false;
			for(;;) {
				if(!onboard) {
					ballYdir = (int)(-ballspeed*Math.cos(Math.toRadians(degrees)));;//��I���ɲy��Y�y�йB�ʤ�V����
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
	public void moveRight() {//�V�k����
		play = true;
		playerX+=20;
		if(onboard) {
			ballposX+=20;
		}
	}
	public void moveLeft() {//�V������
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
