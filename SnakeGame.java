import javax.swing.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

class Node
{
    int x, y;
    Node next, previous;
}

class Snake
{
    Node head, tail;
    int foodX, foodY;
    int d;
    int points;
    public Snake()
    {
        generateFood();
        head = tail = null;
        points = 0;
    }

    public void generateFood()
    {
        foodX = (int)(38 * Math.random()) + 1;
        foodY = (int)(38 * Math.random()) + 1;
        foodX *= 10;
        foodY *= 10;
    }

    public int tailPosY()
    {
        return tail.y;
    }
    
    public int tailPosX()
    {
        return tail.x;
    }

    public boolean hitWall()
    {
        if (head.x == 0 || head.x == 390)
        return true;
        if (head.y == 0 || head.y == 390)
        return true;
        return false;
    }
    
    public void expand(int a)
    {
        Node temp;
        temp = new Node();
        temp.previous = null;
        temp.next = null;
        if (head == null && tail == null)
        {
            temp.x = 30;
            temp.y = 30;
            head = temp;
            tail = temp; 
            return;
        }
        int b, c;
        b = tailPosX();
        c = tailPosY();
        switch(a)
        {
            case 1 : c += 10; break;
            case 2 : c -= 10; break;
            case 3 : b += 10; break;
            case 4 : b -= 10; break;         
        }
        temp.x = b;
        temp.y = c;
        temp.previous = tail;
        tail.next = temp;
        tail = temp;
    }

    public boolean foodEaten()
    {
        if (head.x == foodX && head.y == foodY)
        return true;
        return false;
    }

    public void move(int a, Graphics g)
    {
        d = a;
        Node temp;
        temp = tail;
        while (temp.previous != null)
        {
            temp.x = temp.previous.x;
            temp.y = temp.previous.y;
            temp = temp.previous;
        }
    
        switch (d)
        {
            case 1 : head.y -= 10; break;
            case 2 : head.y += 10; break;
            case 3 : head.x -= 10; break;
            case 4 : head.x += 10; break; 
        }
        
        temp = head;
        while (temp != null)
        {
            if (temp == head)
            {
                g.setColor(Color.red);
            }
            else
            {   
                g.setColor(Color.black);
            }
            if (temp != head && temp.x == head.x && temp.y == head.y)
            {   
                tail = temp;
                temp.next = null;
            }               
            g.fillOval(temp.x, temp.y, 10, 10);
            temp = temp.next;
        }

        if (!foodEaten())
        {
            g.setColor(Color.blue);
            g.fillRect(foodX, foodY, 10, 10);    
        }
        else
        {
            points ++;
            g.clearRect(foodX, foodY, 10, 10);
            g.setColor(Color.red);
            g.fillOval(foodX, foodY, 10, 10);
            expand(d);
            generateFood();
        }
    }
}

public class SnakeGame extends Applet implements KeyListener, ActionListener
{
    Timer t;
    Snake s;
    int move = 4;
    public void init()
    {
        resize(400, 400);
        setBackground(Color.orange);
        s = new Snake();
        addKeyListener(this);
        setFocusable(true);
        s.expand(3);
        s.expand(3);
        s.expand(3);
        t = new Timer (70, this);
        t.start();
    }
    
    public void paint(Graphics g)
    {
        s.move(move, g);
    }

    public void actionPerformed(ActionEvent ae)
    {
        if (!s.hitWall())
        repaint();
    }    

    public void keyTyped(KeyEvent ke)
    {
    }

    public void keyPressed(KeyEvent ke)
    {
        int x = ke.getKeyCode();
        switch(x)
        {
            case KeyEvent.VK_RIGHT :
                move = 4;
                break;
            case KeyEvent.VK_LEFT :
                move = 3;
                break;
            case KeyEvent.VK_DOWN :
                move = 2;
                break;
            case KeyEvent.VK_UP :
                move = 1;
                break;
        }
    }

    public void keyReleased(KeyEvent ke)
    {
    }

}   