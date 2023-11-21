import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.CREATE;

public class RandProductSearch extends JFrame
{

    JPanel mainPnl;
    JPanel dataPnl;
    JPanel searchPnl;
    JPanel buttonPnl;

    JTextField searchTF;
    JTextArea enteredDataTA;
    JScrollPane enteredDataSP;
    JTextArea filteredDataTA;
    JScrollPane filteredDataSP;

    JButton searchBtn;
    JButton quitBtn;

    Path file;


    public RandProductSearch()
    {

        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        setTitle("Product Search");

        createDataPnl();
        mainPnl.add(dataPnl,BorderLayout.NORTH);
        createSearchPnl();
        mainPnl.add(searchPnl,BorderLayout.CENTER);
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
        enteredDataTA = new JTextArea();
        enteredDataTA.setEditable(false);
        enteredDataSP = new JScrollPane(enteredDataTA);
        enteredDataSP.setBorder(new TitledBorder(new EtchedBorder(), "Unfiltered text"));
        enteredDataSP.setPreferredSize(new Dimension(1500,500));
        dataPnl.add(enteredDataSP);

    }

    private void createSearchPnl()
    {

        searchPnl = new JPanel();
        searchPnl.setLayout(new BorderLayout());
        searchTF = new JTextField(35);
        searchTF.setFont(new Font("Roboto", Font.PLAIN, 16));
        searchTF.setBorder(new TitledBorder(new EtchedBorder(), "Enter search criteria"));
        searchPnl.add(searchTF,BorderLayout.NORTH);

        filteredDataTA = new JTextArea();
        filteredDataTA.setEditable(false);
        filteredDataSP = new JScrollPane(filteredDataTA);
        filteredDataSP.setBorder(new TitledBorder(new EtchedBorder(), "Filtered text"));
        filteredDataSP.setPreferredSize(new Dimension(1500,300));
        searchPnl.add(filteredDataSP,BorderLayout.SOUTH);

    }


    private void createButtonPnl()
    {

        buttonPnl = new JPanel();
        buttonPnl.setLayout(new GridLayout(1,2));

        JFileChooser fileChoose = new JFileChooser();

        searchBtn = new JButton("Search");
        searchBtn.setFont(new Font("Roboto", Font.PLAIN, 24));
        searchBtn.addActionListener((ActionEvent ae) ->
        {
            try {
                File workingDirectory = new File(System.getProperty("user.dir"));

                fileChoose.setCurrentDirectory(workingDirectory);

                if (fileChoose.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File toImport = fileChoose.getSelectedFile();
                    file = toImport.toPath();
                    InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                } else {
                    System.out.println("Failed to choose file. Try again.");
                    System.exit(0);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found!!!");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (Stream<String> lines = Files.lines(Paths.get(String.valueOf(file))))
            {
                enteredDataTA.append(lines.collect(Collectors.joining("\n")));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Invalid");
            }


            try (Stream<String> lines = Files.lines(Paths.get(String.valueOf(file))))
            {
                filteredDataTA.append(lines.filter(w -> w.contains(searchTF.getText())).collect(Collectors.joining("\n")));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Invalid");
            }
            enteredDataTA.append("\n");
            filteredDataTA.append("\n");


        });
        buttonPnl.add(searchBtn);

        quitBtn = new JButton("Quit");
        quitBtn.setFont(new Font("Roboto", Font.PLAIN, 24));
        quitBtn.addActionListener((ActionEvent ae) ->
        {int quit = JOptionPane.showConfirmDialog(null,"Are you sure you want to quit?","Quit Confirm", JOptionPane.YES_NO_OPTION);
            if(quit == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        });

        buttonPnl.add(quitBtn);

    }
}
