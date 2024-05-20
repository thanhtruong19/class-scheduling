package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrangChuForm81 extends JFrame {
    private JButton btnLenLichLHP;
    private JButton btnDangXuat;

    public TrangChuForm81() {
        setTitle("Trang Chủ");
        setSize(800, 500); // Set the same size as LenLichLopHocPhanForm81
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        btnLenLichLHP = new JButton("Lên Lịch Lớp Học Phần");
        btnDangXuat = new JButton("Đăng Xuất");

        btnLenLichLHP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LenLichLopHocPhanForm81().setVisible(true);
                dispose();
            }
        });

        btnDangXuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login().setVisible(true);
                dispose();
            }
        });

        panel.add(btnLenLichLHP);
        panel.add(btnDangXuat);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TrangChuForm81().setVisible(true);
            }
        });
    }
}
