package views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import config.App;
import models.Borrower;
import models.Student;
import models.Teacher;

public class BorrowerListView extends View {
    JPanel borrowerList;
    JScrollPane scrollPane;

    public BorrowerListView() {
        super("Borrower List", App.getwWidth(100), App.getwWidth(100));
        borrowerList = new JPanel(new FlowLayout(FlowLayout.CENTER, App.getwWidth(1.5), App.getwHeight(1.5)));
        borrowerList.setBackground(App.PRIMARY);

        scrollPane = new JScrollPane(borrowerList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        panel.add(scrollPane);
    }

    public void createBorrowersList(String category, List<Borrower> borrowers) {
        borrowerList.setPreferredSize(
                new Dimension(App.getwWidth(100), borrowerList.getPreferredSize().height + App.getwHeight(20)));

        JLabel label = new JLabel(category);
        label.setFont(new Font(App.FONT, Font.BOLD, App.TITLE));
        label.setForeground(App.ACCENT1);
        label.setBorder(new EmptyBorder(0, 120, 0, 0));
        label.setPreferredSize(new Dimension(getvWidth(100), label.getPreferredSize().height));
        borrowerList.add(label);

        for (int i = 0; i < borrowers.size(); i++) {
            Borrower borrower = borrowers.get(i);
            JButton borrowerView = (borrower.type.equals("Teacher")) ? createTeacherBorrower((Teacher) borrower)
                    : createStudentBorrower((Student) borrower);
            borrowerList.add(borrowerView);
            borrowerList.setPreferredSize(
                    new Dimension(borrowerList.getPreferredSize().width,
                            borrowerList.getPreferredSize().height + borrowerView.getPreferredSize().height + 5));
        }
    }

    public JButton createTeacherBorrower(Teacher teacher) {
        JLabel name = new JLabel(teacher.name);
        name.setFont(new Font(App.FONT, Font.BOLD, App.TEXT));
        name.setForeground(App.ACCENT1);
        name.setBorder(new EmptyBorder(0, 0, 5, 0));

        JLabel id = new JLabel("employee id: " + teacher.ID);
        id.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
        id.setForeground(App.ACCENT1);

        JLabel department = new JLabel("department: " + teacher.department);
        department.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
        department.setForeground(App.ACCENT1);

        JButton button = new JButton();
        button.setName(teacher.name);
        button.setActionCommand("View Borrower Information");
        button.setLayout(new GridLayout(2, 2, 1, 3));
        button.setEnabled(false);
        button.setBackground(App.ACCENT2);
        button.setPreferredSize(new Dimension(getvWidth(80), App.getwHeight(15)));
        button.add(name);
        button.add(id);
        button.add(department);
        jButtons.add(button);

        return button;
    }

    public JButton createStudentBorrower(Student student) {
        JLabel name = new JLabel(student.name);
        name.setFont(new Font(App.FONT, Font.BOLD, App.TEXT));
        name.setForeground(App.ACCENT1);
        name.setBorder(new EmptyBorder(0, 0, 5, 0));

        JLabel id = new JLabel("student id: " + student.ID);
        id.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
        id.setForeground(App.ACCENT1);

        JLabel yearLevel = new JLabel("year level: " + student.yearLevel);
        yearLevel.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
        yearLevel.setForeground(App.ACCENT1);

        JLabel section = new JLabel("section: " + student.section);
        section.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
        section.setForeground(App.ACCENT1);

        JButton button = new JButton();
        button.setName(student.name);
        button.setActionCommand("View Borrower Information");
        button.setLayout(new GridLayout(2, 2, 1, 3));
        button.setEnabled(false);
        button.setBackground(App.ACCENT2);
        button.setPreferredSize(new Dimension(getvWidth(80), App.getwHeight(15)));
        button.add(name);
        button.add(id);
        button.add(yearLevel);
        button.add(section);
        jButtons.add(button);

        return button;
    }
}
