import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;



public class RandProductMaker extends JFrame
{

JPanel mainPnl;
JPanel dataPnl;
JPanel buttonPnl;

JTextField nameTF;
JTextField descriptionTF;
JTextField IDTF;
JTextField costTF;

JButton clearBtn;
JButton addBtn;
JTextField recordCountTF;
String delimiter = ", ";
Integer RC = 0;


public RandProductMaker()
{

    mainPnl = new JPanel();
    mainPnl.setLayout(new BorderLayout());
    setTitle("Product Creator");

    createDataPnl();
    mainPnl.add(dataPnl,BorderLayout.NORTH);
    createButtonPnl();
    mainPnl.add(buttonPnl,BorderLayout.SOUTH);
    add(mainPnl);

    setSize(400,400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

}

private void createDataPnl()
{

    dataPnl = new JPanel();
    dataPnl.setLayout(new GridLayout(4,1));
    nameTF = new JTextField(35);
    nameTF.setFont(new Font("Roboto", Font.PLAIN, 16));
    nameTF.setBorder(new TitledBorder(new EtchedBorder(), "Enter name"));
    dataPnl.add(nameTF);
    descriptionTF = new JTextField(75);
    descriptionTF.setFont(new Font("Roboto", Font.PLAIN, 16));
    descriptionTF.setBorder(new TitledBorder(new EtchedBorder(), "Enter description"));
    dataPnl.add(descriptionTF);
    IDTF = new JTextField(6);
    IDTF.setFont(new Font("Roboto", Font.PLAIN, 16));
    IDTF.setBorder(new TitledBorder(new EtchedBorder(), "Enter ID"));
    dataPnl.add(IDTF);
    costTF = new JTextField();
    costTF.setFont(new Font("Roboto", Font.PLAIN, 16));
    costTF.setBorder(new TitledBorder(new EtchedBorder(), "Enter cost"));
    dataPnl.add(costTF);

}

private void createButtonPnl()
{

    buttonPnl = new JPanel();
    buttonPnl.setLayout(new GridLayout(1,3));
    recordCountTF = new JTextField();

    clearBtn = new JButton("Clear");
    clearBtn.setFont(new Font("Roboto", Font.PLAIN, 24));
    clearBtn.addActionListener((ActionEvent ae) ->
    {

        nameTF.setText("");
        descriptionTF.setText("");
        IDTF.setText("");
        costTF.setText("");

    });
    buttonPnl.add(clearBtn);

    addBtn = new JButton("Add");
    addBtn.setFont(new Font("Roboto", Font.PLAIN, 24));
    addBtn.addActionListener((ActionEvent ae) ->
    {

        if (!nameTF.getText().isEmpty())
            if (!descriptionTF.getText().isEmpty())
                if (!IDTF.getText().isEmpty())
                    if (!costTF.getText().isEmpty())
                    {
                        recordCountTF.setText(RC.toString());
                        try {
                            BufferedWriter out = new BufferedWriter(new FileWriter("RandomAccess"));
                            out.write(nameTF.getText());
                            out.write(delimiter);
                            out.write(descriptionTF.getText());
                            out.write(delimiter);
                            out.write(IDTF.getText());
                            out.write(delimiter);
                            out.write(costTF.getText());
                            out.write(System.getProperty("line.separator"));
                            JOptionPane.showMessageDialog(null, "Record successfully created");
                            nameTF.setText("");
                            descriptionTF.setText("");
                            IDTF.setText("");
                            costTF.setText("");
                            RC++;

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null,"One of the boxes may be empty");
                else
                    JOptionPane.showMessageDialog(null,"One of the boxes may be empty");
            else
                JOptionPane.showMessageDialog(null,"One of the boxes may be empty");
        else
            JOptionPane.showMessageDialog(null,"One of the boxes may be empty");
    });
    buttonPnl.add(addBtn);

    buttonPnl.add(recordCountTF);


}

}
