import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Event;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;
@SuppressWarnings("unchecked")

class Paint4 extends Frame implements MouseListener,MouseMotionListener,ActionListener{
		int x,y;
        Vector<Figure> objList;
        CheckboxGroup cbgForm,cbgColor,cbgFill;
        Checkbox c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11;
        Button end,save,load;
        int mode = 0;
        Figure obj;
	public static void main(String args[]){
		Paint4 f = new Paint4();
		f.setSize(720,600);
		f.setTitle("Paint Sample");
		f.addWindowListener(new WindowAdapter(){
			@Override public void windowClosing(WindowEvent e){
			System.exit(0);
			}});
		f.setVisible(true);
	}
	Paint4(){

		objList = new Vector<Figure>();

		cbgForm = new CheckboxGroup();
		cbgColor = new CheckboxGroup();
		cbgFill = new CheckboxGroup();


		c1 = new Checkbox("丸",cbgForm,true);
		c2 = new Checkbox("円",cbgForm,false);
		c3 = new Checkbox("四角",cbgForm,false);
		c4 = new Checkbox("線",cbgForm,false);
		c5 = new Checkbox("長方形",cbgForm,false);
		c6 = new Checkbox("楕円",cbgForm,false);
		c7 = new Checkbox("三角形",cbgForm,false);

		c8 = new Checkbox("赤",cbgColor,false);
		c9 = new Checkbox("緑",cbgColor,false);
		c10 = new Checkbox("青",cbgColor,false);

		c11 = new Checkbox("塗りつぶし",cbgFill,false);


		save = new Button("保存");
		load = new Button("読み込み");

		end = new Button("終了");
		c1.setBounds(560,30,60,30);
		c2.setBounds(560,60,60,30);
		c3.setBounds(560,90,60,30);
		c4.setBounds(560,120,60,30);
		c5.setBounds(560,150,60,30);
		c6.setBounds(560,180,60,30);
		c7.setBounds(560,210,60,30);

		c8.setBounds(560,260,60,30);
		c9.setBounds(560,290,60,30);
		c10.setBounds(560,320,60,30);

		c11.setBounds(560,370,60,30);

		save.setBounds(560,440,60,30);
		load.setBounds(560,470,60,30);

		end.setBounds(560,520,60,30);
		setLayout(null);
		add(c1);
		add(c2);
		add(c3);
		add(c4);
		add(c5);
		add(c6);
		add(c7);
		
		add(c8);
		add(c9);
		add(c10);

		add(c11);

		add(save);
		add(load);

		add(end);
		addMouseListener(this);
		addMouseMotionListener(this);
		end.addActionListener(this);
	}

	public void save(String fname){
		try{
			FileOutputStream fos = new FileOutputStream(fname);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(objList);
			oos.close();
			fos.close();
		}catch(IOException e){
		}
	}
	public void load(String fname){
		try{
			FileInputStream fis = new FileInputStream(fname);
			ObjectInputStream ois = new ObjectInputStream(fis);
			objList = (Vector<Figure>)ois.readObject();
			ois.close();
			fis.close();
		}catch(IOException e){
		}catch(ClassNotFoundException e){
		}
		repaint();
	}
	@Override public boolean action(Event e,Object o){

		if(o.equals("保存")){
			FileDialog f_dialog = new FileDialog(new Frame(),"FileDialog",FileDialog.SAVE);
			f_dialog.setVisible(true);
			save(f_dialog.getFile());
	    }
		if(o.equals("読み込み")){
			FileDialog f_dialog = new FileDialog(new Frame(),"FileDialog",FileDialog.LOAD);
			f_dialog.setVisible(true);
			load(f_dialog.getFile());
	    }
	    repaint();
	    return true;
	}
	@Override public void paint(Graphics g){
		Figure f;
		for(int i=0;i<objList.size();i++){
			f=(Figure)objList.elementAt(i);
			f.paint(g);
		}
		if(mode >= 1)obj.paint(g);
	}
	@Override public void actionPerformed(ActionEvent e){
		save("paint.dat");
		System.exit(0);
	}
	public void mousePressed(MouseEvent e){
		Checkbox Form,Color,Fill;
		int color=0;
		int fill =0;
		x = e.getX();
		y = e.getY();
		Form = cbgForm.getSelectedCheckbox();
		Color = cbgColor.getSelectedCheckbox();
		Fill = cbgFill.getSelectedCheckbox();

		obj = null;

		if(Color==c8){
			color=1;
		}else if(Color==c9){
			color=2;
		}else if(Color==c10){
			color=3;
		}

		if(Fill==c11){
			fill=1;
		}

		if(Form == c1){
			mode = 1;
			obj = new Ring(color,fill);
		}
		if(Form == c2){
			mode = 2;
			obj = new Circle(color,fill);
		}
		if(Form == c3){
			mode = 2;
			obj = new Box(color,fill);
		}
		if(Form == c4){
			mode = 2;
			obj = new Line(color);
		}
		if(Form == c5){
			mode = 2;
			obj = new Rectangle(color,fill);
		}
		if(Form == c6){
			mode = 2;
			obj = new ellipse(color,fill);
		}
		if(Form == c7){
			mode = 2;
			obj = new Triangle(color,fill);
		}
		if(obj != null){
			obj.moveto(x,y);
			repaint();
		}
	}
	public void mouseReleased(MouseEvent e){
		x = e.getX();
		y = e.getY();
		if(mode == 1)			obj.moveto(x,y);
		else if(mode == 2)		obj.setWH(x - obj.x, y - obj.y);
		if(mode >= 1){
			objList.add(0,obj);
			obj = null;
		}
		mode = 0;
		repaint();
	}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseDragged(MouseEvent e){
		x = e.getX();
		y = e.getY();

		if(mode == 1)			obj.moveto(x,y);
		else if(mode == 2)		obj.setWH(x - obj.x, y - obj.y);
		repaint();
	}
	public void mouseMoved(MouseEvent e){}
}
class Coord implements Serializable{
	int x,y;

	Coord(){
		x=y=0;
	}
	public void move(int dx,int dy){
		x += dx;
		y += dy;
	}
	public void moveto(int x,int y){
		this.x = x;
		this.y = y;
	}
}
class Figure extends Coord implements Serializable{
	int color,fill;
	int w,h;
	Figure(){
		fill = 0;
		color = 0;
		w = h = 0;
	}
	public void paint(Graphics g){}
	public void setWH(int w,int h){
		this.w = w;
		this.h = h;
	}
}
class Ring extends Figure implements Serializable{
	int size;
	Ring(int c,int f){
		size = 10;
		color = c;
		fill = f;
	}
	@Override public void paint(Graphics g){

		if(color==1){
			g.setColor(Color.red);
		}else if(color==2){
			g.setColor(Color.green);
		}else if(color==3){
			g.setColor(Color.blue);
		}

		if(fill==1){
			g.fillOval(x - size/2,y - size/2,size,size);
		}else{
			g.drawOval(x - size/2,y - size/2,size,size);
		}
	}
}
class Circle extends Figure implements Serializable{
	Circle(int c,int f){
		fill = f;
		color = c;
	}
	@Override public void paint(Graphics g){

		if(color==1){
			g.setColor(Color.red);
		}else if(color==2){
			g.setColor(Color.green);
		}else if(color==3){
			g.setColor(Color.blue);
		}

		int r = (int)Math.sqrt((double)(w * w + h * h));

		if(fill==1){
			g.fillOval(x - r,y - r,r*2,r*2);
		}else{
			g.drawOval(x - r,y - r,r*2,r*2);
		}
	}
}

class Box extends Figure implements Serializable{
	Box(int c,int f){
		fill = f;
		color = c;
	}
	@Override public void paint(Graphics g){
		if(color==1){
			g.setColor(Color.red);
		}else if(color==2){
			g.setColor(Color.green);
		}else if(color==3){
			g.setColor(Color.blue);
		}
		if(fill==1){
			g.fillRect(x,y,w,h);
		}else{
			g.drawRect(x,y,w,h);
		}

	}
}

class Line extends Figure implements Serializable{
	Line(int c){
		color = c;
	}
	@Override public void paint(Graphics g){

		if(color==1){
			g.setColor(Color.red);
		}else if(color==2){
			g.setColor(Color.green);
		}else if(color==3){
			g.setColor(Color.blue);
		}

		g.drawLine(x, y, x + w, y + h);
	}
}
class Rectangle extends Figure implements Serializable{
	Rectangle(int c,int f){
		fill = f;
		color = c;
	}
	@Override public void paint(Graphics g){

		if(color==1){
			g.setColor(Color.red);
		}else if(color==2){
			g.setColor(Color.green);
		}else if(color==3){
			g.setColor(Color.blue);
		}

		if(fill==1){
			if(w>0 && h>0){
				g.fillRect(x,y,w,h);
			}else if(w<0 && h>0){
				g.fillRect(x+w,y,-w,h);
			}else if(w>0 && h<0){
				g.fillRect(x,y+h,w,-h);
			}else if(w<0 && h<0){
				g.fillRect(x+w,y+h,-w,-h);
			}
		}else{
			if(w>0 && h>0){
				g.drawRect(x,y,w,h);
			}else if(w<0 && h>0){
				g.drawRect(x+w,y,-w,h);
			}else if(w>0 && h<0){
				g.drawRect(x,y+h,w,-h);
			}else if(w<0 && h<0){
				g.drawRect(x+w,y+h,-w,-h);
			}
		}
	}
}

class ellipse extends Figure implements Serializable{
		ellipse(int c,int f){
		fill = f;
		color = c;
	}
	@Override public void paint(Graphics g){
		if(color==1){
			g.setColor(Color.red);
		}else if(color==2){
			g.setColor(Color.green);
		}else if(color==3){
			g.setColor(Color.blue);
		}
		int w1 = (int)(Math.sqrt(2)*w);
		int h1 = (int)(Math.sqrt(2)*h);
		if(fill==1){
			if(w>0 && h>0){
				g.fillOval(x-w1,y-h1,2*w1,2*h1);
			}else if(w<0 && h>0){
				g.fillOval(x+w1,y-h1,-2*w1,2*h1);
			}else if(w>0 && h<0){
				g.fillOval(x-w1,y+h1,2*w1,-2*h1);
			}else if(w<0 && h<0){
				g.fillOval(x+w1,y+h1,-2*w1,-2*h1);
			}
		}else{
			if(w>0 && h>0){
				g.drawOval(x-w1,y-h1,2*w1,2*h1);
			}else if(w<0 && h>0){
				g.drawOval(x+w1,y-h1,-2*w1,2*h1);
			}else if(w>0 && h<0){
				g.drawOval(x-w1,y+h1,2*w1,-2*h1);
			}else if(w<0 && h<0){
				g.drawOval(x+w1,y+h1,-2*w1,-2*h1);
			}
		}
	}
}

class Triangle extends Figure implements Serializable{
	Triangle(int c,int f){
		fill = f;
		color = c;
	}
	@Override public void paint(Graphics g){

		if(color==1){
			g.setColor(Color.red);
		}else if(color==2){
			g.setColor(Color.green);
		}else if(color==3){
			g.setColor(Color.blue);
		}

		if(fill==1){
				int []xPoints =	{x,x+w,x+w/2};
				int []yPoints ={y,y,y+h};
				g.fillPolygon(xPoints,yPoints,3);
		}else{
			int []xPoints =	{x,x+w,x+w/2};
			int []yPoints ={y,y,y+h};
			g.drawPolygon(xPoints,yPoints,3);
		}
	}
}

