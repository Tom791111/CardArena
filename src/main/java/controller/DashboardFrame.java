package controller;

import javax.swing.*;
import java.awt.*;
import util.*;
import model.Session;

public class DashboardFrame extends JFrame{
 public DashboardFrame(){
  setTitle("CardArena 畢業專題版 - 首頁"); UiStyle.frame(this); setDefaultCloseOperation(EXIT_ON_CLOSE);
  add(UiStyle.label("CARD ARENA｜球員卡交易・購物・拍照查詢系統",110,18,780,38,28));
  JLabel logo=new JLabel(IconFactory.sports("ball",58)); logo.setBounds(35,15,60,60); add(logo);
  String user=Session.loginMember==null?"未登入":Session.loginMember.getName()+" / "+Session.loginMember.getRole();
  add(UiStyle.text("目前登入："+user+"｜MySQL：player_card_db",150,62,650,28,15));
  card("會員管理","會員資料 CRUD / 權限角色",55,125,"user",()->new MemberFrame().setVisible(true));
  card("球員卡市場","上架、查詢、修改、刪除、加入購物車",385,125,"card",()->new PlayerCardFrame().setVisible(true));
  card("購物車 / 訂單","加入購物車、修改數量、交易明細",715,125,"cart",()->new SportsDataFrame("購物車 / 訂單").setVisible(true));
  card("拍照查詢","圖片路徑、關鍵字、辨識結果紀錄",55,350,"camera",()->new SportsDataFrame("拍照查詢").setVisible(true));
  card("我的收藏","收藏清單、卡片資料連動查詢",385,350,"star",()->new SportsDataFrame("我的收藏").setVisible(true));
  card("市場行情分析","價格趨勢、估價、行情紀錄 CRUD",715,350,"chart",()->new SportsDataFrame("市場行情分析").setVisible(true));
 }
 void card(String t,String d,int x,int y,String icon,Runnable r){
  JPanel p=UiStyle.panel(x,y,300,170); add(p);
  JLabel ic=new JLabel(IconFactory.sports(icon,48)); ic.setBounds(25,22,52,52); p.add(ic);
  p.add(UiStyle.label(t,82,24,200,35,22));
  JLabel des=UiStyle.text(d,25,80,250,42,14); p.add(des);
  JButton b=UiStyle.btn("進入",25,120,120,38); p.add(b); b.addActionListener(e->r.run());
 }
}
