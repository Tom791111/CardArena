package controller;

import javax.swing.SwingUtilities;

/**
 * CardArena 系統啟動入口
 * 在 Eclipse 中請右鍵此檔案 → Run As → Java Application
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
