package io.flatbufferx.plugin;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class FlatBufferXDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane scrollView;
    private JLabel classNameLabel;
    private RSyntaxTextArea textArea;
    private JPanel panel1;
    private AnActionEvent mAnActionEvent;
    String errorMsg = "";
    private String mPackageName;
    private String mSourcePath;

    public FlatBufferXDialog(AnActionEvent anActionEvent, String packegeName, String path) {
        this.mPackageName = packegeName;
        this.mAnActionEvent = anActionEvent;
        this.mSourcePath = path;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        if (mAnActionEvent == null) {
            return;
        }
        String content = textArea.getText();
        if (content.isEmpty()) {
            return;
        }

        //remove namespace
        int nameSpaceIndex = content.indexOf("namespace");
        if (nameSpaceIndex != -1) {
            content = content.substring((nameSpaceIndex + content.substring(nameSpaceIndex).indexOf(";")) + 1);
        }
        if (mAnActionEvent.getProject() == null) {
            return;
        }
        String path = mAnActionEvent.getProject().getBasePath() + File.separator + "test.fbs";
        FileWriter fileWriter = null;
        boolean isSuc = false;
        try {
            fileWriter = new FileWriter(path);
            fileWriter.write(content);
            fileWriter.flush();
            isSuc = true;
        } catch (IOException e) {
            e.printStackTrace();
            errorMsg = e.getMessage();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!isSuc) {
            Messages.showErrorDialog(errorMsg, "Error Display");
            return;
        }

        File flatcFile = new File(mAnActionEvent.getProject().getBasePath() + File.separator + "flatc");
        if (!flatcFile.exists()) {
            if (!copyFile()) {
                Messages.showErrorDialog(errorMsg, "Error Display");
                return;
            }
        }
        String sbCmd = flatcFile.getAbsolutePath() + " --java -o " + mSourcePath + File.separator + mPackageName.replace(".", File.separator) + " " + path;
        System.out.println("cmd:" + sbCmd);
        try {
            Process processqx = Runtime.getRuntime().exec("chmod 777 " + flatcFile.getAbsolutePath());
            processqx.waitFor();


            Process process = Runtime.getRuntime().exec(sbCmd);
            BufferedInputStream bis = new BufferedInputStream(
                    process.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(bis));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();

            bis.close();
            br.close();
            Messages.showMessageDialog("Succeed", "succeed Display", Messages.getInformationIcon());
        } catch (Exception e) {
            Messages.showErrorDialog(e.getMessage(), "Error Display");
            e.printStackTrace();
        }
        dispose();
    }

    private boolean copyFile() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream("/lib/flatc");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            outputStream = new FileOutputStream(new File(mAnActionEvent.getProject().getBasePath() + File.separator + "flatc"));
            outputStream.write(buffer);
            outputStream.close();
            inputStream.close();
            return true;
        } catch (Exception e) {
            errorMsg = e.getMessage();
            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onCancel() {
        // add your code here if necessary

        System.out.println(textArea.getText());

        dispose();
    }

    public static void main(String[] args) {
        FlatBufferXDialog dialog = new FlatBufferXDialog(null, "", "");
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        textArea.setCodeFoldingEnabled(true);

        try {
            Theme theme = Theme.load(getClass().getResourceAsStream(
                    "/org/fife/ui/rsyntaxtextarea/themes/monokai.xml"));
            theme.apply(textArea);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
