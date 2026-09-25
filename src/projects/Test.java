package projects;

import drawer.Canvas;
import drawer.Colors;

//Here I just tested all the functions from draw.
public class Test {
    int x = 0;
    int dir = 1;
    int y = 0;
    int dirY = 1;
    float a = 0;
    float radius = 8;

    public static void main(String[] args){
        Test test = new Test();
        test.start();
    }

    private void start() {
        Canvas drawer = new Canvas();
        drawer.startCanvas(150, 50, true);

        while(true){
            drawer.drawCanvas(false);

            drawer.setColor(Colors.GREEN);
            for(int i = 0; i < 10; i++){
                drawer.drawChar('.', (int) Math.round(Math.random() * (drawer.width -1)) , (int) Math.round(Math.random() * (drawer.height-1)));
            }

            drawer.setColor(Colors.BRIGHT_RED);
            drawer.rect(2,2,5,10,"#");

            drawer.setColor(Colors.BRIGHT_RED);
            drawer.circle(20, 2, 10, "O");
            

            drawer.setColor(Colors.GREEN);
            drawer.line(40,25,x,y);

            a += 0.1;
            if(a > 100){
                a = 0;
            }
            int cX = (int) Math.round(Math.sin(a)*radius);
            int cY = (int) Math.round(Math.cos(a)*radius/2);
            drawer.setColor(Colors.RED);
            drawer.line(25,25,25 + cX,25 + cY);
            
            drawer.setColor(Colors.RED);
            drawer.drawChar('X', x, 30 + cY);
            x += dir;
            y += dirY;
            if(x == 0 || x == drawer.width - 1){
                dir *= -1;
            }
            if(y == 0 || y == drawer.height - 1){
                dirY *= -1;
            }

            drawer.setColorRgb(255,0,0);
            drawer.rect(0,30,5,5,"A");

            drawer.setColorRgb(0,255,0);
            drawer.rect(5,30,5,5,"B");

            drawer.setColorRgb(0,0,255);
            drawer.rect(10,30,5,5,"C");

            drawer.setColorRgb(255,255,0);
            drawer.rect(15,30,5,5,"D");

            drawer.setColorRgb(255,0,255);
            drawer.rect(20,30,5,5,"E");

            drawer.setColorRgb(0,255,255);
            drawer.rect(25,30,5,5,"F");

            for(int i = 0; i < drawer.width; i++){
                int gradient = (int) Math.round(drawer.map(i,0,drawer.width,0,255));
                drawer.setColorRgb(gradient,0,0);
                drawer.drawChar('8',i,1);
                drawer.setColorRgb(0,gradient,0);
                drawer.drawChar('6',i,2);
                drawer.setColorRgb(0,0,gradient);
                drawer.drawChar('Q',i,3);
                drawer.setColorRgb(gradient,gradient,gradient);
                drawer.drawChar('1',i,4);
                drawer.drawChar('2',i,5);
                drawer.drawChar('3',i,6);
                drawer.drawChar('4',i,7);
                drawer.drawChar('5',i,8);
            }

            drawer.delay(10);
        }
    }
}