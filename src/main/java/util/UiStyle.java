package util;
import javax.swing.*;import javax.swing.table.*;import java.awt.*;
public class UiStyle{
 public static final Color BG=new Color(7,13,26),CARD=new Color(20,31,52),ORANGE=new Color(255,115,24),WHITE=new Color(245,248,255),MUTED=new Color(160,174,196),LINE=new Color(54,73,103);
 public static Font font(int size,int style){return new Font("Microsoft JhengHei",style,size);} 
 public static void frame(JFrame f){f.getContentPane().setBackground(BG);f.setSize(1050,650);f.setLocationRelativeTo(null);f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);f.setLayout(null);} 
 public static JLabel label(String s,int x,int y,int w,int h,int size){JLabel l=new JLabel(s);l.setBounds(x,y,w,h);l.setForeground(WHITE);l.setFont(font(size,Font.BOLD));return l;} 
 public static JLabel text(String s,int x,int y,int w,int h,int size){JLabel l=new JLabel(s);l.setBounds(x,y,w,h);l.setForeground(MUTED);l.setFont(font(size,Font.PLAIN));return l;} 
 public static JButton btn(String s,int x,int y,int w,int h){JButton b=new JButton(s);b.setBounds(x,y,w,h);b.setBackground(ORANGE);b.setForeground(Color.WHITE);b.setFocusPainted(false);b.setBorderPainted(false);b.setFont(font(14,Font.BOLD));return b;} 
 public static JTextField field(int x,int y,int w,int h){JTextField t=new JTextField();t.setBounds(x,y,w,h);t.setFont(font(14,Font.PLAIN));return t;}
 public static JTable table(DefaultTableModel m){JTable t=new JTable(m);t.setRowHeight(28);t.setFont(font(13,Font.PLAIN));t.getTableHeader().setFont(font(13,Font.BOLD));t.getTableHeader().setBackground(CARD);t.getTableHeader().setForeground(WHITE);t.setGridColor(new Color(220,225,235));return t;}
 public static JPanel panel(int x,int y,int w,int h){JPanel p=new JPanel(null);p.setBackground(CARD);p.setBounds(x,y,w,h);return p;}
}
