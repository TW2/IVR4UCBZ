package org.wingate.ivr;

import org.wingate.ivr.ui.MainPanel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * IVR is free for all, and you are free to fork this code or make a new one
 * I just want you to put a link or a thank in your app
 * <a href="https://github.com/TW2/IVR4UCBZ">IVR by TW2</a>
 * @author The Wingate 2940
 */
public class MainFrame extends JFrame {

    private final MainPanel mPanel;
    private final JFileChooser fcOpen;

    public MainFrame() throws HeadlessException {
        setJMenuBar(createMenu());

        mPanel = new MainPanel();
        JPanel embedPane = new JPanel();
        fcOpen = new JFileChooser();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(embedPane, BorderLayout.CENTER);

        embedPane.setLayout(new BorderLayout());
        embedPane.add(mPanel, BorderLayout.CENTER);
        mPanel.setBackground(Color.black);
    }

    private JMenuBar createMenu(){
        JMenuBar mnu = new JMenuBar();

        JMenu mFile = new JMenu(IVR.RSX.getString("File"));
        JMenuItem mFileOpen = new JMenuItem(IVR.RSX.getString("Open"));
        JSeparator mFileSep1 = new JSeparator(JSeparator.HORIZONTAL);
        JMenuItem mFileQuit = new JMenuItem(IVR.RSX.getString("Quit"));

        mFile.add(mFileOpen);
        mFile.add(mFileSep1);
        mFile.add(mFileQuit);
        mnu.add(mFile);

        mFileOpen.addActionListener(this::mFileOpenActionListener);
        mFileQuit.addActionListener(this::mFileQuitActionListener);

        return mnu;
    }

    public void mFileOpenActionListener(ActionEvent e){
        for(FileFilter ff : fcOpen.getChoosableFileFilters()){
            fcOpen.removeChoosableFileFilter(ff);
        }
        fcOpen.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().endsWith(".cbz");
            }

            @Override
            public String getDescription() {
                return IVR.RSX.getString("NXX");
            }
        });
        int z = fcOpen.showOpenDialog(this);
        if(z == JFileChooser.APPROVE_OPTION){
            mPanel.setup(fcOpen.getSelectedFile());
        }
    }

    public void mFileQuitActionListener(ActionEvent e){
        System.exit(0);
    }
}
