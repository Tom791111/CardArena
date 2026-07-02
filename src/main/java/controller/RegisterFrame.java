package controller;

import javax.swing.*;
import java.awt.*;
import model.Member;
import service.impl.MemberServiceImpl;
import util.UiStyle;

public class RegisterFrame extends JFrame {
    private JTextField account = new JTextField();
    private JPasswordField password = new JPasswordField();
    private JTextField name = new JTextField();
    private JTextField email = new JTextField();
    private JTextField phone = new JTextField();

    public RegisterFrame() {
        setTitle("CardArena - 建立會員");
        setSize(520, 560);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(6, 10, 22));

        JPanel card = new JPanel(null);
        card.setBounds(45, 35, 420, 460);
        card.setBackground(new Color(15, 25, 45));
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 115, 24), 1));
        add(card);

        JLabel title = new JLabel("建立 CardArena 會員");
        title.setBounds(45, 30, 320, 40);
        title.setForeground(Color.WHITE);
        title.setFont(UiStyle.font(25, Font.BOLD));
        card.add(title);

        JLabel sub = new JLabel("註冊後可使用收藏、購物車、拍照查詢與行情分析");
        sub.setBounds(45, 70, 340, 25);
        sub.setForeground(new Color(168, 180, 202));
        sub.setFont(UiStyle.font(13, Font.PLAIN));
        card.add(sub);

        addRow(card, "帳號", account, 120);
        addRow(card, "密碼", password, 175);
        addRow(card, "姓名", name, 230);
        addRow(card, "Email", email, 285);
        addRow(card, "電話", phone, 340);

        JButton submit = new JButton("完成註冊");
        submit.setBounds(100, 400, 220, 42);
        submit.setBackground(new Color(255, 115, 24));
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.setBorderPainted(false);
        submit.setFont(UiStyle.font(15, Font.BOLD));
        card.add(submit);

        submit.addActionListener(e -> register());
    }

    private void addRow(JPanel parent, String label, JTextField field, int y) {
        JLabel l = new JLabel(label);
        l.setBounds(45, y, 70, 32);
        l.setForeground(Color.WHITE);
        l.setFont(UiStyle.font(14, Font.BOLD));
        parent.add(l);
        field.setBounds(120, y, 240, 36);
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(15, 25, 45));
        field.setFont(UiStyle.font(15, Font.PLAIN));
        field.setOpaque(true);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE), BorderFactory.createEmptyBorder(0, 10, 0, 10)));
        parent.add(field);
    }

    private void register() {
        Member m = new Member(0, account.getText().trim(), new String(password.getPassword()), name.getText().trim(), email.getText().trim(), phone.getText().trim(), "USER");
        new MemberServiceImpl().register(m);
        JOptionPane.showMessageDialog(this, "註冊成功");
        dispose();
    }
}
