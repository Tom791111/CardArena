package dao.impl;

import dao.MemberDao;
import exception.AppException;
import model.Member;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MemberDaoImpl implements MemberDao {

    @Override
    public Member login(String account, String password) {
        String sql = "SELECT * FROM members WHERE TRIM(account)=? AND TRIM(password)=?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, account == null ? "" : account.trim());
            ps.setString(2, password == null ? "" : password.trim());

            try (ResultSet r = ps.executeQuery()) {
                if (r.next()) {
                    return map(r);
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("登入查詢失敗：" + rootMessage(e), e);
        }
    }

    @Override
    public void add(Member m) {
        String sql = "INSERT INTO members(account,password,name,email,phone,role) VALUES(?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, safe(m.getAccount()));
            ps.setString(2, safe(m.getPassword()));
            ps.setString(3, safe(m.getName()).isEmpty() ? m.getAccount() : m.getName());
            ps.setString(4, safe(m.getEmail()));
            ps.setString(5, safe(m.getPhone()));
            ps.setString(6, safe(m.getRole()).isEmpty() ? "USER" : m.getRole());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("新增會員失敗：" + rootMessage(e), e);
        }
    }

    @Override
    public List<Member> findAll() {
        List<Member> list = new ArrayList<>();
        String sql = "SELECT * FROM members ORDER BY id";
        try (Connection c = DBUtil.getConnection();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {
                list.add(map(r));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("查詢會員失敗：" + rootMessage(e), e);
        }
    }

    @Override
    public void update(Member m) {
        String sql = "UPDATE members SET account=?,password=?,name=?,email=?,phone=?,role=? WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, safe(m.getAccount()));
            ps.setString(2, safe(m.getPassword()));
            ps.setString(3, safe(m.getName()));
            ps.setString(4, safe(m.getEmail()));
            ps.setString(5, safe(m.getPhone()));
            ps.setString(6, safe(m.getRole()));
            ps.setInt(7, m.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("修改會員失敗：" + rootMessage(e), e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM members WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("刪除會員失敗：" + rootMessage(e), e);
        }
    }

    private Member map(ResultSet r) throws Exception {
        return new Member(
                r.getInt("id"),
                r.getString("account"),
                r.getString("password"),
                r.getString("name"),
                r.getString("email"),
                r.getString("phone"),
                r.getString("role")
        );
    }

    private String safe(String s) {
        return s == null ? "" : s.trim();
    }

    private String rootMessage(Throwable e) {
        Throwable t = e;
        while (t.getCause() != null) {
            t = t.getCause();
        }
        return t.getMessage() == null ? t.getClass().getName() : t.getMessage();
    }
}
