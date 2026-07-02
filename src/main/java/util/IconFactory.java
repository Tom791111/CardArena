package util;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class IconFactory {
    public static Icon sports(String type, int size) {
        return new SportIcon(type, size);
    }
    static class SportIcon implements Icon {
        private final String type; private final int size;
        SportIcon(String type, int size){this.type=type;this.size=size;}
        public int getIconWidth(){return size;} public int getIconHeight(){return size;}
        public void paintIcon(Component c, Graphics g, int x, int y){
            Graphics2D g2=(Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color orange=UiStyle.ORANGE, white=UiStyle.WHITE;
            g2.setStroke(new BasicStroke(Math.max(2f,size/18f),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
            g2.setColor(orange); g2.drawRoundRect(x+2,y+2,size-4,size-4,size/4,size/4);
            g2.setColor(white); int cx=x+size/2, cy=y+size/2, r=size/3;
            switch(type){
                case "user": g2.drawOval(cx-r/2, y+size/5, r, r); g2.drawArc(x+size/4, y+size/2, size/2, size/3, 0, 180); break;
                case "card": g2.drawRoundRect(x+size/4,y+size/5,size/2,size*3/5,6,6); g2.drawLine(x+size/3,y+size/3,x+size*2/3,y+size/3); g2.fillOval(cx-3,cy-3,6,6); break;
                case "cart": g2.drawLine(x+size/5,y+size/3,x+size*3/4,y+size/3); g2.drawLine(x+size/4,y+size/3,x+size/3,y+size*2/3); g2.drawLine(x+size/3,y+size*2/3,x+size*3/4,y+size*2/3); g2.drawOval(x+size/3,y+size*3/4,5,5); g2.drawOval(x+size*2/3,y+size*3/4,5,5); break;
                case "camera": g2.drawRoundRect(x+size/5,y+size/3,size*3/5,size/3,6,6); g2.drawOval(cx-r/2,cy-r/2,r,r); g2.drawLine(x+size/3,y+size/3,x+size*2/5,y+size/4); break;
                case "star": Path2D p=new Path2D.Double(); for(int i=0;i<10;i++){ double a=-Math.PI/2+i*Math.PI/5; double rr=(i%2==0)?r:r/2.2; double px=cx+Math.cos(a)*rr, py=cy+Math.sin(a)*rr; if(i==0)p.moveTo(px,py);else p.lineTo(px,py);} p.closePath(); g2.draw(p); break;
                case "chart": g2.drawLine(x+size/4,y+size*3/4,x+size*3/4,y+size/4); g2.drawLine(x+size/2,y+size/4,x+size*3/4,y+size/4); g2.drawLine(x+size*3/4,y+size/4,x+size*3/4,y+size/2); g2.fillOval(x+size/4-2,y+size*3/4-2,5,5); break;
                default: g2.drawOval(cx-r,cy-r,r*2,r*2); g2.drawArc(cx-r,cy-r,r*2,r*2,90,180); g2.drawLine(cx,cy-r,cx,cy+r); break;
            }
            g2.dispose();
        }
    }
}
