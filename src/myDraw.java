

import javax.swing.*;
import java.awt.*;


/*在其中实现以下要求:
建立一个图形界面，在其中绘制一个圆表示太阳，中间显示单词“SUN”，并绘制在环绕太阳的椭圆轨道，轨道上有一颗蓝色星星。
（星星用绘制文本的技术，绘制“★”表示）。
 */
public class myDraw {
    public static void main(String[] args) {
        new Drawing();
    }
}

class Drawing extends JFrame {
    public Drawing() {
        setLayout(null);
        setBounds(100, 100, 600, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        //g2.drawOval(350,150,200,200);
        //g2.fillOval(350,150,200,200);

        //画半椭圆
        g.fillArc(200, 300, 100, 50,0, 360);
        g.fillArc(300, 300, 100, 50,0, 360);
        g.fillArc(200, 400, 100, 50,0, 360);
        g.fillArc(300, 400, 100, 50,0, 360);

        g.fillArc(250, 270, 100, 200,0, 360);

        g.fillArc(275, 200, 50, 100,0, 360);
        g.fillArc(275, 450, 50, 100,0, 360);


        //g.fillArc(275, 180, 330, 90,0, 360);
        //g.fillArc(275, 180, 330, 90,0, 360);
    }
}
