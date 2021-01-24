package snakeGame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
public class Gamepanel extends JPanel implements Runnable,KeyListener{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static final int WIDTH=500,HEIGHT=500;
private Thread thread;
private boolean running;
private BodyPart b; 
private ArrayList<BodyPart> snake;
private int score=0;
private Apple apple;
private ArrayList<Apple> apples;
private Random r;
private int xCoor=10,yCoor=10,size=5;
private int ticks=0;
private boolean right=true,left=false,up=false,down=false;
public Gamepanel()
{
	setFocusable(true);
	setPreferredSize(new Dimension(WIDTH,HEIGHT));
	snake=new ArrayList<BodyPart>();
	apples=new ArrayList<Apple>();
	r=new Random();
	addKeyListener(this);
	start();
}    
public void start() {
	running =true;
	thread=new Thread(this);
	thread.start() ;
}
public void stop() {
	running=false; 
	try {
		thread.join();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public void tick()
{
	if(snake.size()==0)
	{
		b=new BodyPart(xCoor,yCoor,10);
		snake.add(b);
	}
	ticks++;
	if(ticks>250000)
	{
		
		if(right)xCoor++;
		if(left) xCoor--;
		if(up) yCoor--;
		if(down) yCoor++;
		ticks=0;
		b=new BodyPart(xCoor,yCoor,10);
		snake.add(b);
		if(snake.size()>size)
		{
			snake.remove(0);
		}
	}
	if(apples.size()==0 )
	{
		int acood=r.nextInt(49);
		int bcood=r.nextInt(49);
		System.out.println(acood+" "+bcood);
		
		apple=new Apple(acood,bcood,10);
		apples.add(apple);
		System.out.println(apples.get(0).getxCoor());
	}
	for(int i=0;i<apples.size();i++)
	{
		if(xCoor==apples.get(i).getxCoor()&&yCoor==apples.get(i).getyCoor())
		{
			score++;
			size++;
			apples.remove(i);
			i++;
		}
	}
	
	if(xCoor<0)
	{
	xCoor=49;

	}else if(xCoor>49)
	{
		xCoor=0;
	}
	if(yCoor<0)
	{
	yCoor=49;

	}else if(yCoor>49)
	{
		yCoor=0;
	}
	for(int i=0;i<snake.size();i++)
	{
		if(xCoor==snake.get(i).getxCoor()&&yCoor==snake.get(i).getyCoor())
		{if(i!=snake.size()-1)
		{
			System.out.println("GameOver");
			stop();
		}
			
		}
	}
	
}
public void paint(Graphics g)
{
	
	
	g.setColor(Color.BLACK );
	g.fillRect(0,0,WIDTH,HEIGHT);
	for(int i=0;i<WIDTH/10;i++)
	{
		g.drawLine(i*10,0,i*10,HEIGHT);
	}
	for(int i=0;i<HEIGHT/10;i++)
	{
		g.drawLine(0, i*10, WIDTH,i*10);
	}
	for(int i=0;i<snake.size();i++)
	{
		snake.get(i).draw(g);
	}
	for(int i=0;i<apples.size();i++)
	{
		apples.get(i).draw(g);
	}
	String s=Integer.toString(score);
	g.drawString("Score = "+s, 400,20);
	
}
@Override
public void run() {
	while(running)
	{
		tick();
		repaint();
	}
	
}
@Override
public void keyTyped(KeyEvent e) {
	
	
	
}
@Override
public void keyPressed(KeyEvent e) {
	int key=e.getKeyCode();
	if(key==KeyEvent.VK_RIGHT&&!left)
	{
		right=true;
		up=false;
		down=false;
	}
		if(key==KeyEvent.VK_LEFT&&!right)
		{
			left=true;
			up=false;
			down=false;
		}
		if(key==KeyEvent.VK_DOWN&&!up)
		{
		down=true;
		left=false;
		right=false;
		}
		if(key==KeyEvent.VK_UP&&!down)
		{
			up=true;
			left=false;
			right=false;
		}
	
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
}
