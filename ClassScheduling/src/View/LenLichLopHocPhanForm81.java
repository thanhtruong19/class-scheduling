package View;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import Control.LenLichLopHocPhanDAO81;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import static Models.LopHocPhan81.STTLHP;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LenLichLopHocPhanForm81 extends JFrame {
    private JComboBox<String> monHocComboBox;
    private JComboBox<String> phongHocComboBox;
    private JComboBox<String> thoiGianComboBox;
    private JButton buttonThemLHP;
    private JButton buttonLuuLHP;
    private JLabel messageLabel;
    private JTable table;
    private MyTableModel tableModel;
    private List<Object[]> allRows; // Store all rows for filtering

    public LenLichLopHocPhanForm81() {
        setTitle("Lên Lịch Học Cho Lớp Học Phần");
        setSize(800, 500); // Increased the size of the interface
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton homeButton = new JButton("Trang chủ");
        JButton logoutButton = new JButton("Đăng xuất");

        buttonPanel.add(homeButton);
        buttonPanel.add(logoutButton);
        topPanel.add(buttonPanel, BorderLayout.WEST);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TrangChuForm81().setVisible(true);
                dispose();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login().setVisible(true);
                dispose();
            }
        });

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel monHocLabel = new JLabel("Môn học:");
        monHocComboBox = new JComboBox<>();
        JLabel phongHocLabel = new JLabel("Phòng học:");
        phongHocComboBox = new JComboBox<>();
        JLabel thoiGianLabel = new JLabel("Giờ học:");
        thoiGianComboBox = new JComboBox<>();

        // Populate the combo boxes
        try {
            List<MonHoc81> courses = LenLichLopHocPhanDAO81.getCourses();
            List<PhongHoc81> rooms = LenLichLopHocPhanDAO81.getRooms();
            List<GioHoc81> timeSlots = LenLichLopHocPhanDAO81.getTimeSlots();

            monHocComboBox.addItem("Chọn môn học");
            for (MonHoc81 course : courses) {
                monHocComboBox.addItem(course.getTenMon() );
            }

            phongHocComboBox.addItem("Chọn phòng học");
            for (PhongHoc81 room : rooms) {
                phongHocComboBox.addItem(room.getTenPhongHoc());
            }

            thoiGianComboBox.addItem("Chọn khung giờ");
            for (GioHoc81 timeSlot : timeSlots) {
                thoiGianComboBox.addItem(timeSlot.getKhungGio());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Disable the combo boxes initially
        phongHocComboBox.setEnabled(false);
        thoiGianComboBox.setEnabled(false);

        buttonThemLHP = new JButton("Thêm lớp học phần");
        buttonLuuLHP = new JButton("Lưu");

        messageLabel = new JLabel("");
        messageLabel.setPreferredSize(new Dimension(0, 30)); // Fixed height
        messageLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Enable the combo boxes when buttonThemLHP is clicked
        buttonThemLHP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                phongHocComboBox.setEnabled(true);
                thoiGianComboBox.setEnabled(true);
                messageLabel.setText("");
            }
        });

        // Action listener for the Luu button
        buttonLuuLHP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (monHocComboBox.getSelectedIndex() == 0 || phongHocComboBox.getSelectedIndex() == 0 || thoiGianComboBox.getSelectedIndex() == 0) {
                    messageLabel.setText("Vui lòng chọn đủ thông tin!");
                    messageLabel.setForeground(Color.RED);
                } else try {
                    if (isDuplicateSchedule(phongHocComboBox.getSelectedItem().toString(), thoiGianComboBox.getSelectedItem().toString())){
                        messageLabel.setText("Đã tồn tại lớp học phần có phòng học và giờ học tương tự, vui lòng chọn phòng học hoặc khung giờ học khác.");
                        messageLabel.setForeground(Color.RED);
                    } else {
                        try {
                            String tenMon = monHocComboBox.getSelectedItem().toString();
                            String phongHoc = phongHocComboBox.getSelectedItem().toString();
                            String khungGio = thoiGianComboBox.getSelectedItem().toString();
                            LopHocPhan81 lopHocPhan = new LopHocPhan81( STTLHP, tenMon, 70 , phongHoc, khungGio);
                            STTLHP++;
                            LenLichLopHocPhanDAO81.saveClassSchedule(lopHocPhan);
                            messageLabel.setText("Lưu lớp học phần thành công.");
                            messageLabel.setForeground(Color.GREEN);
                            // Add functionality to save the data here
                            Object[] rowData = new Object[]{
                                monHocComboBox.getSelectedItem(),
                                phongHocComboBox.getSelectedItem(),
                                thoiGianComboBox.getSelectedItem(),
                                "Sửa Xóa"
                            };
                            allRows.add(rowData); // Add to all rows
                            tableModel.addRow(rowData);
                            filterTable();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(LenLichLopHocPhanForm81.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Action listener for the monHocComboBox
        monHocComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTable();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(monHocLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(monHocComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(phongHocLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(phongHocComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(thoiGianLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(thoiGianComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(buttonThemLHP, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(buttonLuuLHP, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(messageLabel, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        JLabel tableLabel = new JLabel("Bảng thông tin lớp học phần");
        tableLabel.setHorizontalAlignment(SwingConstants.LEFT);
        tablePanel.add(tableLabel, BorderLayout.NORTH);

        allRows = new ArrayList<>(); // Initialize allRows list

        tableModel = new MyTableModel();
        table = new JTable(tableModel);
        table.getColumn("Hành động").setCellRenderer(new ButtonRenderer());
        table.getColumn("Hành động").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane tablePane = new JScrollPane(table);
        tablePanel.add(tablePane, BorderLayout.CENTER);

        panel.add(tablePanel, BorderLayout.CENTER);

        mainPanel.add(panel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static boolean isDuplicateSchedule(String phongHoc, String thoiGian) throws SQLException {
        Connection conn = DatabaseConnector.getConnection();
        String query = "SELECT COUNT(*) FROM lophocphan WHERE phongHoc = (SELECT phongHoc FROM phonghoc WHERE tenPhongHoc = ?) AND khungGio = (SELECT khungGio FROM giohoc WHERE khungGio = ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, phongHoc);
        stmt.setString(2, thoiGian);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        boolean isDuplicate = rs.getInt(1) > 0;
        rs.close();
        stmt.close();
        conn.close();
        return isDuplicate;
    }

    private void filterTable() {
        String selectedCourse = monHocComboBox.getSelectedItem().toString();
        try {
            if (selectedCourse.equals("Chọn môn học")) {
                allRows = new ArrayList<>();
                tableModel.setRows(allRows);
            } else {
                allRows = LenLichLopHocPhanDAO81.getClassSchedules(selectedCourse);
                tableModel.setRows(allRows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"Môn học", "Phòng học", "Khung giờ học", "Hành động"};
        private List<Object[]> data = new ArrayList<>();

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data.get(row)[col];
        }

        public boolean isCellEditable(int row, int col) {
            return col == 3;
        }

        public void setValueAt(Object value, int row, int col) {
            data.get(row)[col] = value;
            fireTableCellUpdated(row, col);
        }

        public void removeRow(int row) {
            data.remove(row);
            fireTableRowsDeleted(row, row);
        }

        public void addRow(Object[] rowData) {
            data.add(rowData);
            fireTableRowsInserted(data.size() - 1, data.size() - 1);
        }

        public void setRows(List<Object[]> rows) {
            this.data = rows;
            fireTableDataChanged();
        }

        public Object[] getRow(int row) {
            return data.get(row);
        }
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton editButton;
        private JButton deleteButton;

        public ButtonRenderer() {
            setLayout(new GridLayout(1, 2));
            editButton = new JButton("Sửa");
            deleteButton = new JButton("Xóa");
            add(editButton);
            add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton editButton;
        private JButton deleteButton;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new GridLayout(1, 2));
            editButton = new JButton("Sửa");
            deleteButton = new JButton("Xóa");

            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    int row = table.getSelectedRow();
                    Object[] rowData = tableModel.getRow(row);
                    monHocComboBox.setSelectedItem(rowData[0]);
                    phongHocComboBox.setSelectedItem(rowData[1]);
                    thoiGianComboBox.setSelectedItem(rowData[2]);
                    phongHocComboBox.setEnabled(true);
                    thoiGianComboBox.setEnabled(true);
                    messageLabel.setText("");
                }
            });

            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    int row = table.getSelectedRow();
                    // Execute deletion in a background thread to keep the UI responsive
                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            try {
                                LenLichLopHocPhanDAO81.deleteClassSchedule(
                                        tableModel.getValueAt(row, 0).toString(),
                                        tableModel.getValueAt(row, 1).toString(),
                                        tableModel.getValueAt(row, 2).toString()
                                );
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void done() {
                            // After deletion, remove the row from the table model and refresh the table
                            allRows.remove(row); // Remove from allRows list
                            tableModel.removeRow(row);
                            filterTable();
                        }
                    };
                    worker.execute();
                }
            });

            panel.add(editButton);
            panel.add(deleteButton);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            return panel;
        }

        public Object getCellEditorValue() {
            return "";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LenLichLopHocPhanForm81().setVisible(true);
            }
        });
    }
}
