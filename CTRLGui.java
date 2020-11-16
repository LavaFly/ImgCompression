import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
public class CTRLGui extends JFrame {
    private Manager m;
    private JLabel Label_1 = new JLabel();
    private JLabel Label_2 = new JLabel();
    private JButton choosePicButton = new JButton();
    private JButton compressionButton = new JButton();
    private JButton previewButton = new JButton();
    private JButton chooseFileButton = new JButton();
    private JButton chooseColsButton = new JButton();
    private JButton interpretButton = new JButton();

    private JFileChooser jfc = new JFileChooser();

    private File sourcePic;
    private File compFile;
    private File colors;
    public CTRLGui(){
        super();
        init_gui();
        this.m = new Manager(this);
        setVisible(true);
    }
    private void init_gui(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(580,350);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((d.width/2)-290,(d.height/2)-175);
        setTitle("Bildkompression");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);


        Label_1.setBounds(48,40,195,33);
        Label_1.setText("Bild komprimieren");
        cp.add(Label_1);

        compressionButton.setBounds(48,240,89,33);
        compressionButton.setText("compress");
        compressionButton.setMargin(new Insets(1,1,1,1));
        compressionButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                begin_compression(evt);
            }
        });
        cp.add(compressionButton);

        previewButton.setBounds(144,240,89,33);
        previewButton.setText("preview");
        previewButton.setMargin(new Insets(2,2,2,2));
        previewButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                begin_preview(evt);
            }
        });
        cp.add(previewButton);

        Label_2.setBounds(350,40,195,33);
        Label_2.setText("Datei intepretieren");
        cp.add(Label_2);

        interpretButton.setBounds(376,240,97,33);
        interpretButton.setText("interpret");
        interpretButton.setMargin(new Insets(2,2,2,2));
        interpretButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                begin_interpretation(evt);
            }
        });
        cp.add(interpretButton);

        choosePicButton.setBounds(48,136,195,33);
        choosePicButton.setText("wähle Bild");
        choosePicButton.setMargin(new Insets(2,2,2,2));
        choosePicButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                set_file_for_compression(evt);
            }
        });
        cp.add(choosePicButton);

        chooseColsButton.setBounds(350,170,160,33);
        chooseColsButton.setText("Farben wählen");
        chooseColsButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                set_colors(evt);
            }
        });
        cp.add(chooseColsButton);

        chooseFileButton.setBounds(350,100, 160,33);
        chooseFileButton.setText("Datei wählen");
        chooseFileButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                set_compFile(evt);
            }
        });
        cp.add(chooseFileButton);
    }
    private void begin_compression(ActionEvent evt){
        if(sourcePic != null) {
            m.compression(sourcePic);
            JOptionPane.showMessageDialog(null, "Done");

        } else {
            alert();
        }

    }
    private void begin_preview(ActionEvent evt){
        if(sourcePic != null) {
            m.compression(sourcePic);
            DeEspresso.preview(new File(Manager.convPath), new File(Manager.colsPath));
            JOptionPane.showMessageDialog(null, "Done");

        } else {
            alert();
        }

    }
    private void begin_interpretation(ActionEvent evt){
        if(compFile != null && colors != null){
            Interpeter.intepret(compFile, colors);
        } else {
            alert();
        }

    }
    public static void main(String[] args){
        CTRLGui c = new CTRLGui();


    }
    public void alert(){
        JOptionPane.showMessageDialog(null, "Datei konnte nicht geöffnet werden\nPfad und Namen überprüfen");
    }
    private File jfc_choose(){
        if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            return jfc.getSelectedFile();
        }
        else {
            return null;
        }
    }
    private void set_file_for_compression(ActionEvent evt){
        sourcePic = jfc_choose();
    }
    private void set_colors(ActionEvent evt){
        colors = jfc_choose();
    }
    private void set_compFile(ActionEvent evt){
        compFile = jfc_choose();
    }
}
