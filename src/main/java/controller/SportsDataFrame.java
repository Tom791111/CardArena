package controller;

import util.*;
import model.Session;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class SportsDataFrame extends JFrame {
    private final String mode;
    private final DefaultTableModel model = new DefaultTableModel();
    private JTable table;
    private JTextField f1,f2,f3,f4;
    private JLabel hint;

    public SportsDataFrame(String mode){
        this.mode=mode;
        setTitle("CardArena - "+mode);
        UiStyle.frame(this);
        addHeader(); addForm(); addTable(); load();
    }
    private void addHeader(){
        Icon icon = IconFactory.sports(iconType(),42);
        JLabel ic=new JLabel(icon); ic.setBounds(42,30,50,50); add(ic);
        add(UiStyle.label(mode,100,28,350,50,28));
        add(UiStyle.text(subtitle(),100,75,650,28,14));
        JButton back=UiStyle.btn("返回首頁",880,35,120,36); add(back); back.addActionListener(e->dispose());
    }
    private void addForm(){
        JPanel p=UiStyle.panel(45,120,950,150); add(p);
        hint=UiStyle.text("",25,12,760,24,13); p.add(hint);
        f1=UiStyle.field(25,58,190,34); f2=UiStyle.field(235,58,190,34); f3=UiStyle.field(445,58,190,34); f4=UiStyle.field(655,58,190,34);
        p.add(lbl(label1(),25,35)); p.add(lbl(label2(),235,35)); p.add(lbl(label3(),445,35)); p.add(lbl(label4(),655,35));
        p.add(f1);p.add(f2);p.add(f3);p.add(f4);
        JButton add=UiStyle.btn("新增",25,105,90,34); JButton upd=UiStyle.btn("修改",130,105,90,34); JButton del=UiStyle.btn("刪除",235,105,90,34); JButton ref=UiStyle.btn("重新整理",340,105,110,34);
        p.add(add);p.add(upd);p.add(del);p.add(ref);
        add.addActionListener(e->insert()); upd.addActionListener(e->update()); del.addActionListener(e->delete()); ref.addActionListener(e->load());
    }
    private JLabel lbl(String s,int x,int y){ JLabel l=UiStyle.text(s,x,y,190,20,13); return l; }
    private void addTable(){
        table=UiStyle.table(model); JScrollPane sp=new JScrollPane(table); sp.setBounds(45,290,950,285); add(sp);
        table.getSelectionModel().addListSelectionListener(e->{ if(!e.getValueIsAdjusting()) fillFromRow(); });
    }
    private int memberId(){ return Session.loginMember==null?1:Session.loginMember.getId(); }
    private void load(){
        model.setRowCount(0); model.setColumnCount(0);
        try(Connection c=DBUtil.getConnection(); Statement s=c.createStatement(); ResultSet rs=s.executeQuery(selectSql())){
            ResultSetMetaData md=rs.getMetaData(); for(int i=1;i<=md.getColumnCount();i++) model.addColumn(md.getColumnLabel(i));
            while(rs.next()){Object[] row=new Object[md.getColumnCount()]; for(int i=1;i<=row.length;i++)row[i-1]=rs.getObject(i); model.addRow(row);} 
            hint.setText("已連線 MySQL｜資料表："+tableName()+"｜共 "+model.getRowCount()+" 筆資料");
        }catch(Exception ex){JOptionPane.showMessageDialog(this,"讀取失敗："+ex.getMessage());}
    }
    private void insert(){ try(Connection c=DBUtil.getConnection(); PreparedStatement ps=c.prepareStatement(insertSql())){ bind(ps,false); ps.executeUpdate(); load(); clear(); }catch(Exception ex){JOptionPane.showMessageDialog(this,"新增失敗："+ex.getMessage());}}
    private void update(){ int id=selectedId(); if(id<0)return; try(Connection c=DBUtil.getConnection(); PreparedStatement ps=c.prepareStatement(updateSql())){ bind(ps,true); ps.setInt(paramCount(),id); ps.executeUpdate(); load(); }catch(Exception ex){JOptionPane.showMessageDialog(this,"修改失敗："+ex.getMessage());}}
    private void delete(){ int id=selectedId(); if(id<0)return; try(Connection c=DBUtil.getConnection(); PreparedStatement ps=c.prepareStatement("DELETE FROM "+tableName()+" WHERE id=?")){ ps.setInt(1,id); ps.executeUpdate(); load(); clear(); }catch(Exception ex){JOptionPane.showMessageDialog(this,"刪除失敗："+ex.getMessage());}}
    private int selectedId(){ int r=table.getSelectedRow(); if(r<0){JOptionPane.showMessageDialog(this,"請先選擇一筆資料");return -1;} return Integer.parseInt(table.getValueAt(r,0).toString()); }
    private void clear(){f1.setText("");f2.setText("");f3.setText("");f4.setText("");}
    private void fillFromRow(){ int r=table.getSelectedRow(); if(r<0)return; try{ switch(mode){
        case "購物車 / 訂單": f1.setText(v(r,"card_id")); f2.setText(v(r,"quantity")); f3.setText(v(r,"price")); f4.setText(v(r,"created_at")); break;
        case "拍照查詢": f1.setText(v(r,"image_path")); f2.setText(v(r,"keyword")); f3.setText(v(r,"result_text")); f4.setText(v(r,"created_at")); break;
        case "我的收藏": f1.setText(v(r,"card_id")); f2.setText(v(r,"card_name")); f3.setText(v(r,"player_name")); f4.setText(v(r,"created_at")); break;
        case "市場行情分析": f1.setText(v(r,"card_id")); f2.setText(v(r,"market_price")); f3.setText(v(r,"record_date")); f4.setText(v(r,"note")); break;
    }}catch(Exception ignore){} }
    private String v(int row,String col){ for(int i=0;i<table.getColumnCount();i++) if(table.getColumnName(i).equalsIgnoreCase(col)){Object o=table.getValueAt(row,i); return o==null?"":o.toString();} return ""; }

    private String iconType(){ if(mode.contains("購物"))return "cart"; if(mode.contains("拍照"))return "camera"; if(mode.contains("收藏"))return "star"; return "chart"; }
    private String tableName(){ if(mode.contains("購物"))return "cart_items"; if(mode.contains("拍照"))return "scan_records"; if(mode.contains("收藏"))return "favorites"; return "price_history"; }
    private String subtitle(){ if(mode.contains("購物"))return "加入購物車、修改數量與價格，後續可擴充結帳訂單流程"; if(mode.contains("拍照"))return "記錄球員卡圖片路徑、辨識關鍵字與查詢結果"; if(mode.contains("收藏"))return "收藏喜歡的球員卡，並與市場資料連動"; return "記錄球員卡行情、日期與備註，作為價格趨勢分析"; }
    private String label1(){ if(mode.contains("拍照"))return "圖片路徑"; return "球員卡ID"; }
    private String label2(){ if(mode.contains("購物"))return "數量"; if(mode.contains("拍照"))return "關鍵字"; if(mode.contains("收藏"))return "卡片名稱(顯示用)"; return "市場價格"; }
    private String label3(){ if(mode.contains("購物"))return "價格"; if(mode.contains("拍照"))return "辨識結果"; if(mode.contains("收藏"))return "球員名稱(顯示用)"; return "日期 yyyy-mm-dd"; }
    private String label4(){ if(mode.contains("市場"))return "備註"; return "建立時間(自動)"; }
    private String selectSql(){
        if(mode.contains("收藏")) return "SELECT f.id,f.member_id,f.card_id,p.card_name,p.player_name,p.price,f.created_at FROM favorites f LEFT JOIN player_cards p ON f.card_id=p.id WHERE f.member_id="+memberId()+" ORDER BY f.id DESC";
        if(mode.contains("購物")) return "SELECT c.id,c.member_id,c.card_id,p.card_name,c.quantity,c.price,(c.quantity*c.price) subtotal,c.created_at FROM cart_items c LEFT JOIN player_cards p ON c.card_id=p.id WHERE c.member_id="+memberId()+" ORDER BY c.id DESC";
        if(mode.contains("拍照")) return "SELECT id,member_id,image_path,keyword,result_text,created_at FROM scan_records WHERE member_id="+memberId()+" ORDER BY id DESC";
        return "SELECT h.id,h.card_id,p.card_name,p.player_name,h.market_price,h.record_date,h.note FROM price_history h LEFT JOIN player_cards p ON h.card_id=p.id ORDER BY h.record_date DESC,h.id DESC";
    }
    private String insertSql(){ if(mode.contains("購物"))return "INSERT INTO cart_items(member_id,card_id,quantity,price) VALUES(?,?,?,?)"; if(mode.contains("拍照"))return "INSERT INTO scan_records(member_id,image_path,keyword,result_text) VALUES(?,?,?,?)"; if(mode.contains("收藏"))return "INSERT INTO favorites(member_id,card_id) VALUES(?,?)"; return "INSERT INTO price_history(card_id,market_price,record_date,note) VALUES(?,?,?,?)"; }
    private String updateSql(){ if(mode.contains("購物"))return "UPDATE cart_items SET member_id=?,card_id=?,quantity=?,price=? WHERE id=?"; if(mode.contains("拍照"))return "UPDATE scan_records SET member_id=?,image_path=?,keyword=?,result_text=? WHERE id=?"; if(mode.contains("收藏"))return "UPDATE favorites SET member_id=?,card_id=? WHERE id=?"; return "UPDATE price_history SET card_id=?,market_price=?,record_date=?,note=? WHERE id=?"; }
    private int paramCount(){ if(mode.contains("收藏")) return 3; return 5; }
    private void bind(PreparedStatement ps, boolean update) throws Exception{
        if(mode.contains("購物")){ps.setInt(1,memberId()); ps.setInt(2,Integer.parseInt(f1.getText().trim())); ps.setInt(3,Integer.parseInt(f2.getText().trim())); ps.setDouble(4,Double.parseDouble(f3.getText().trim()));}
        else if(mode.contains("拍照")){ps.setInt(1,memberId()); ps.setString(2,f1.getText()); ps.setString(3,f2.getText()); ps.setString(4,f3.getText());}
        else if(mode.contains("收藏")){ps.setInt(1,memberId()); ps.setInt(2,Integer.parseInt(f1.getText().trim()));}
        else {ps.setInt(1,Integer.parseInt(f1.getText().trim())); ps.setDouble(2,Double.parseDouble(f2.getText().trim())); String d=f3.getText().trim().isEmpty()? LocalDate.now().toString():f3.getText().trim(); ps.setString(3,d); ps.setString(4,f4.getText());}
    }
}
