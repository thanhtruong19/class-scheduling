package Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Models.GioHoc81;
import Models.LopHocPhan81;
import Models.MonHoc81;
import Models.PhongHoc81;
import Control.DatabaseConnector;

public class LenLichLopHocPhanDAO81{

    public static List<MonHoc81> getCourses() throws SQLException {
        List<MonHoc81> courses = new ArrayList<>();
        Connection conn = DatabaseConnector.getConnection();
        String query = "SELECT maMon,tenMon, soTinChi FROM monhoc";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            courses.add(new MonHoc81(rs.getString("maMon"),
                                     rs.getString("tenMon"),
                                     rs.getInt("soTinChi")));
        }
        rs.close();
        stmt.close();
        conn.close();
        return courses;
    }

    public static List<PhongHoc81> getRooms() throws SQLException {
        List<PhongHoc81> rooms = new ArrayList<>();
        Connection conn = DatabaseConnector.getConnection();
        String query = "SELECT idPhongHoc, tenPhongHoc FROM phonghoc";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {

            rooms.add(new PhongHoc81(rs.getString("idPhongHoc"),
                                     rs.getString("tenPhongHoc")));
        }
        rs.close();
        stmt.close();
        conn.close();
        return rooms;
    }

    public static List<GioHoc81> getTimeSlots() throws SQLException {
        List<GioHoc81> timeSlots = new ArrayList<>();
        Connection conn = DatabaseConnector.getConnection();
        String query = "SELECT idGioHoc, KhungGio FROM giohoc ORDER BY idGioHoc";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            timeSlots.add(new GioHoc81(rs.getString("idGioHoc"),
                                        rs.getString("KhungGio")));
        }
        rs.close();
        stmt.close();
        conn.close();
        return timeSlots;
    }

     public static List<Object[]> getClassSchedules(String selectedCourse) throws SQLException {
        List<Object[]> schedules = new ArrayList<>();
        Connection conn = DatabaseConnector.getConnection();
        String query = "SELECT tenLop, phongHoc, khungGio FROM lophocphan where tenLop = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, selectedCourse);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            schedules.add(new Object[]{rs.getString("tenLop"), rs.getString("phongHoc"), rs.getString("khungGio"), "Sửa Xóa"});
        }
        rs.close();
        stmt.close();
        conn.close();
        return schedules;
    }
     
     public static void saveClassSchedule(LopHocPhan81 lophocphan) throws SQLException {
        Connection conn = DatabaseConnector.getConnection();
        String query = "INSERT INTO lophocphan (maLop, tenLop, soLuongSinhVien, phongHoc, khungGio) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, lophocphan.getMaLop());
        stmt.setString(2, lophocphan.getMaMon());
        stmt.setInt(3, lophocphan.getSoSinhVien());
        stmt.setString(4, lophocphan.getIdPhongHoc());
        stmt.setString(5, lophocphan.getIdGioHoc());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public static void deleteClassSchedule(String tenLop, String phongHoc, String khungGio) throws SQLException {
        Connection conn = DatabaseConnector.getConnection();
        String query = "DELETE FROM lophocphan WHERE tenLop = ? AND phongHoc = ? AND khungGio = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, tenLop);
        stmt.setString(2, phongHoc);
        stmt.setString(3, khungGio);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}

