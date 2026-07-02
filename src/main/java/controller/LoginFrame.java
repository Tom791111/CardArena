package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import model.*;
import service.*;
import service.impl.*;
import util.*;

public class LoginFrame extends JFrame {
    private JTextField acc = new JTextField("admin");
    private JPasswordField pwd = new JPasswordField("1234");
    private MemberService service = new MemberServiceImpl();

    public LoginFrame() {
        setTitle("CardArena Final 2026 - 登入");
        setSize(1050, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        HeroPanel root = new HeroPanel();
        root.setLayout(null);
        setContentPane(root);

        JPanel loginCard = new RoundedPanel(new Color(15, 25, 45), 28);
        loginCard.setLayout(null);
        loginCard.setBounds(650, 105, 330, 430);
        loginCard.setBorder(BorderFactory.createLineBorder(new Color(255, 115, 24, 110), 1));
        root.add(loginCard);

        JLabel tag = new JLabel("MEMBER ACCESS");
        tag.setBounds(35, 28, 220, 24);
        tag.setForeground(new Color(255, 115, 24));
        tag.setFont(UiStyle.font(13, Font.BOLD));
        loginCard.add(tag);

        JLabel title = new JLabel("會員登入");
        title.setBounds(35, 58, 220, 45);
        title.setForeground(Color.WHITE);
        title.setFont(UiStyle.font(30, Font.BOLD));
        loginCard.add(title);

        JLabel sub = new JLabel("登入後管理交易、收藏與球員卡行情");
        sub.setBounds(35, 103, 280, 24);
        sub.setForeground(new Color(168, 180, 202));
        sub.setFont(UiStyle.font(13, Font.PLAIN));
        loginCard.add(sub);

        loginCard.add(fieldLabel("帳號", 35, 145));
        acc.setBounds(35, 172, 260, 44);
        styleField(acc);
        loginCard.add(acc);

        loginCard.add(fieldLabel("密碼", 35, 225));
        pwd.setBounds(35, 252, 260, 44);
        styleField(pwd);
        loginCard.add(pwd);

        JButton b = orangeButton("登入系統", 35, 320, 260, 48);
        loginCard.add(b);

        JButton r = darkButton("快速建立一般會員", 35, 378, 260, 40);
        loginCard.add(r);

        b.addActionListener(e -> doLogin());
        r.addActionListener(e -> new RegisterFrame().setVisible(true));
    }

    private JLabel fieldLabel(String text, int x, int y) {
        JLabel l = new JLabel(text);
        l.setBounds(x, y, 100, 25);
        l.setForeground(new Color(230, 238, 255));
        l.setFont(UiStyle.font(13, Font.BOLD));
        return l;
    }

    private void styleField(JTextField f) {
        f.setFont(UiStyle.font(16, Font.PLAIN));
        f.setBackground(Color.WHITE);
        f.setForeground(new Color(15, 25, 45));
        f.setCaretColor(new Color(255, 115, 24));
        f.setOpaque(true);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255), 1),
                BorderFactory.createEmptyBorder(0, 12, 0, 12)));
    }

    private JButton orangeButton(String text, int x, int y, int w, int h) {
        JButton b = new JButton(text);
        b.setBounds(x, y, w, h);
        b.setBackground(new Color(255, 115, 24));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(UiStyle.font(15, Font.BOLD));
        return b;
    }

    private JButton darkButton(String text, int x, int y, int w, int h) {
        JButton b = new JButton(text);
        b.setBounds(x, y, w, h);
        b.setBackground(new Color(31, 48, 78));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(UiStyle.font(14, Font.BOLD));
        return b;
    }

    void doLogin() {
        try {
            Member m = service.login(acc.getText().trim(), new String(pwd.getPassword()));
            if (m == null) {
                JOptionPane.showMessageDialog(this, "登入失敗：資料庫可連線，但 members 沒有這組帳密。\n請確認 SQL 已匯入，帳號 admin / 密碼 1234。", "登入失敗", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Session.loginMember = m;
            new DashboardFrame().setVisible(true);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex.getMessage(), "登入錯誤", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    static class RoundedPanel extends JPanel {
        private Color bg;
        private int radius;
        RoundedPanel(Color bg, int radius) {
            this.bg = bg;
            this.radius = radius;
            setOpaque(false);
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    static class HeroPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint gp = new GradientPaint(0, 0, new Color(6, 10, 22), 1050, 650, new Color(12, 21, 38));
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(255, 255, 255, 18));
            for (int x = 0; x < getWidth(); x += 42) g2.drawLine(x, 0, x + 120, getHeight());
            for (int y = 0; y < getHeight(); y += 42) g2.drawLine(0, y, getWidth(), y + 120);

            g2.setColor(new Color(255, 115, 24));
            g2.setFont(new Font("Arial Black", Font.BOLD, 48));
            g2.drawString("CARD ARENA", 65, 130);

            g2.setColor(Color.WHITE);
            g2.setFont(UiStyle.font(20, Font.BOLD));
            g2.drawString("SPORTS CARDS, COLLECTIBLES, AND TRADING SYSTEM", 68, 170);
            g2.setColor(new Color(180, 194, 220));
            g2.setFont(UiStyle.font(15, Font.PLAIN));
            g2.drawString("Marketplace  /  Scanner  /  Collection  /  Price Trend", 68, 202);

            drawBadge(g2, 70, 245, "NBA"); drawBadge(g2, 150, 245, "MLB"); drawBadge(g2, 230, 245, "PSA"); drawBadge(g2, 310, 245, "RC");
            drawPhone(g2, 380, 225);
            drawCard(g2, 260, 360, -12, new Color(22, 120, 235), "ROOKIE", "PSA 10");
            drawCard(g2, 365, 345, 8, new Color(255, 115, 24), "LEGEND", "GEM MT");
            drawCard(g2, 470, 370, -5, new Color(28, 190, 130), "LIMITED", "AUTO");
            g2.setStroke(new BasicStroke(4f));
            g2.setColor(new Color(255, 115, 24, 190));
            g2.drawArc(305, 210, 270, 270, 35, 285);
            g2.setColor(new Color(255, 255, 255, 120));
            g2.drawArc(320, 225, 240, 240, 210, 120);
            g2.dispose();
        }
        private void drawBadge(Graphics2D g2, int x, int y, String text) { g2.setColor(new Color(255,115,24)); g2.fillRoundRect(x,y,58,34,16,16); g2.setColor(new Color(6,10,22)); g2.fillRoundRect(x+3,y+3,52,28,13,13); g2.setColor(Color.WHITE); g2.setFont(new Font("Arial",Font.BOLD,14)); g2.drawString(text,x+13,y+23); }
        private void drawPhone(Graphics2D g2, int x, int y) { g2.setColor(new Color(15,25,45)); g2.fillRoundRect(x,y,92,180,24,24); g2.setColor(new Color(255,255,255,180)); g2.drawRoundRect(x,y,92,180,24,24); g2.setColor(new Color(255,115,24)); g2.fillRoundRect(x+14,y+22,64,20,8,8); g2.setColor(new Color(22,120,235)); g2.fillRoundRect(x+14,y+55,64,44,10,10); g2.setColor(new Color(28,190,130)); g2.fillRoundRect(x+14,y+110,64,44,10,10); }
        private void drawCard(Graphics2D g2, int x, int y, double angle, Color color, String title, String grade) { Graphics2D c=(Graphics2D)g2.create(); c.rotate(Math.toRadians(angle),x+42,y+58); c.setColor(Color.WHITE); c.fillRoundRect(x,y,84,116,14,14); c.setColor(color); c.fillRoundRect(x+8,y+8,68,72,10,10); c.setColor(new Color(15,25,45)); c.setFont(new Font("Arial",Font.BOLD,11)); c.drawString(title,x+12,y+97); c.setColor(new Color(255,115,24)); c.setFont(new Font("Arial",Font.BOLD,10)); c.drawString(grade,x+12,y+110); c.dispose(); }
    }
}
