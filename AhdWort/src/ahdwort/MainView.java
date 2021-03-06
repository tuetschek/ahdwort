/* BSD-Licence
Copyright (c) 2009, Ondrej Dusek
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of Ondrej Dusek nor the
      names of his contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Ondrej Dusek ''AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Ondrej Dusek BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ahdwort;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Vector;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * The application's main frame, including the dictionary search functions.
 */
public class MainView extends FrameView {

    /** How many consecutive entries should be displayed on one page ? */
    private static final int ENTRIES_TO_LOAD = 10;

    /**
     * Aproximate number of dictionary entries (doesn't matter much, just speeds up the
     * loading).
     */
    private static final int INDEX_APPROX_SIZE = 1000;

    /** Name of the index file in JAR */
    private static final String INDEX_NAME = "/index.dat";

    /** Name of the dictionary data file in JAR */
    private static final String DATA_NAME = "/words.dat";

    /** The dictionary file, when loaded into memory */
    private byte [] dictData;
    /** Search index */
    private Vector<Pair<String, Integer>> index;

    /** Currently displayed entry number */
    private int current = -1;

    /**
     * Creates the main frame.
     *
     * @param app The application
     * @throws java.net.URISyntaxException Should never be thrown, unless the data files are corrupt
     * @throws java.io.FileNotFoundException Should never be thrown, unless the data files are corrupt
     * @throws java.io.IOException Should never be thrown, unless the data files are corrupt
     */
    public MainView(SingleFrameApplication app) throws URISyntaxException, FileNotFoundException, IOException {
        super(app);

        initComponents();
        loadDictionary();
    }

    /**
     * Shows the "About" dialog.
     */
    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = Main.getApplication().getMainFrame();
            aboutBox = new MainAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        Main.getApplication().show(aboutBox);
    }

    /**
     * Tries to display the no-th entry and several following ones.
     *
     * @param no Which entry to show
     */
    private void displayWord(int no) throws IOException {

        int lo, hi;
 
        lo = this.index.get(no).second;
        hi = (no < this.index.size() - ENTRIES_TO_LOAD) ? this.index.get(no + ENTRIES_TO_LOAD).second : 
            (int) this.dictData.length;

        this.epOutput.setText("<html>" + new String(this.dictData, lo, hi - lo, "utf8") + "</html>");
        this.epOutput.setCaretPosition(0);

        this.current = no;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     *
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        lbSearch = new javax.swing.JLabel();
        tfSearch = new javax.swing.JTextField();
        btSearch = new javax.swing.JButton();
        spOutput = new javax.swing.JScrollPane();
        epOutput = new javax.swing.JEditorPane();
        btPrev = new javax.swing.JButton();
        btNext = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ahdwort.Main.class).getContext().getResourceMap(MainView.class);
        lbSearch.setText(resourceMap.getString("lbSearch.text")); // NOI18N
        lbSearch.setName("lbSearch"); // NOI18N

        tfSearch.setText(resourceMap.getString("tfSearch.text")); // NOI18N
        tfSearch.setName("tfSearch"); // NOI18N
        tfSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfSearchKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfSearchKeyTyped(evt);
            }
        });

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(ahdwort.Main.class).getContext().getActionMap(MainView.class, this);
        btSearch.setAction(actionMap.get("search")); // NOI18N
        btSearch.setText(resourceMap.getString("btSearch.text")); // NOI18N
        btSearch.setName("btSearch"); // NOI18N

        spOutput.setName("spOutput"); // NOI18N

        epOutput.setName("epOutput"); // NOI18N
        spOutput.setViewportView(epOutput);

        btPrev.setAction(actionMap.get("prev")); // NOI18N
        btPrev.setText(resourceMap.getString("btPrev.text")); // NOI18N
        btPrev.setName("btPrev"); // NOI18N

        btNext.setAction(actionMap.get("next")); // NOI18N
        btNext.setText(resourceMap.getString("btNext.text")); // NOI18N
        btNext.setName("btNext"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btNext)
                .addContainerGap(118, Short.MAX_VALUE))
            .addComponent(spOutput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbSearch)
                    .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSearch)
                    .addComponent(btPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btNext))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spOutput, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        fileMenu.add(aboutMenuItem);

        menuBar.add(fileMenu);

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Simulates pressing the "Go!" button when typing "Enter" into the search field.
     *
     * @param evt Keypress event
     */
    private void tfSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchKeyTyped

        if (evt.getKeyChar() == '\n'){
            this.btSearch.doClick();
        }
    }//GEN-LAST:event_tfSearchKeyTyped

    /**
     * Moves the dictionary when pressing PgUp, PgDn, Up or Down in the search field.
     *
     * @param evt Keypress event
     */
    private void tfSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchKeyPressed

        // simulate PgUp & PgDn
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN){
            this.btNext.doClick();
        }
        else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP){
            this.btPrev.doClick();
        }
        // simulate Up & Down
        else if (evt.getKeyCode() == KeyEvent.VK_UP){
            Point vp = this.spOutput.getViewport().getViewPosition();
            vp.y = Math.max(0, vp.y - 50);
            this.spOutput.getViewport().setViewPosition(vp);
        }
        else if (evt.getKeyCode() == KeyEvent.VK_DOWN){
            Point vp = this.spOutput.getViewport().getViewPosition();
            int hBound = this.epOutput.getHeight() - this.spOutput.getViewport().getViewRect().height;
            vp.y = Math.min(hBound, vp.y + 50);
            this.spOutput.getViewport().setViewPosition(vp);
        }
    }//GEN-LAST:event_tfSearchKeyPressed

    /**
     * The actual dictionary search, according to the contents of the search field.
     *
     * Uses the binary search algorithm above the index. Steps one entry back if the exact word is not found.
     */
    @Action
    public void search() throws IOException {

        int lo = 0, mid, hi = this.index.size();
        String term = this.tfSearch.getText();

        // the binary search
        while (lo < hi)
        {
            mid = (lo + hi) / 2;

            if (term.compareTo(this.index.get(mid).first) < 0)
                hi = mid - 1;
            else if (term.compareTo(this.index.get(mid).first) > 0)
                lo = mid + 1;
            else
                lo = hi = mid;
        }

        // return to the first synonym
        while (lo > 0 && this.index.get(lo).first.equals(this.index.get(lo-1).first))
            lo--;

        // return one word back if the first found word is not exactly what we've been
        // looking for
        if (lo > 0 && term.compareTo(this.index.get(lo).first) < 0)
            lo--;

        // tries to display the results
        displayWord(lo);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btNext;
    private javax.swing.JButton btPrev;
    private javax.swing.JButton btSearch;
    private javax.swing.JEditorPane epOutput;
    private javax.swing.JLabel lbSearch;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JScrollPane spOutput;
    private javax.swing.JTextField tfSearch;
    // End of variables declaration//GEN-END:variables

    private JDialog aboutBox;

    /**
     * Initialization -- loads the index and the dictionary into the memory.
     *
     * @throws java.io.FileNotFoundException Should never be thrown, unless the data files are corrupt
     * @throws java.net.URISyntaxException Should never be thrown, unless the data files are corrupt
     * @throws java.io.IOException Should never be thrown, unless the data files are corrupt
     */
    private void loadDictionary() throws FileNotFoundException, URISyntaxException, IOException {

        BufferedReader indexReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(INDEX_NAME)));
        String line;

        // parses the index
        this.index = new Vector<Pair<String, Integer>>(INDEX_APPROX_SIZE);
        while ((line = indexReader.readLine()) != null){

            String [] entryPos = line.split("\\t+");

            this.index.add(new Pair<String, Integer>(entryPos[0], Integer.parseInt(entryPos[1])));
        }

        // reads the dictionary contents into the memory
        InputStream data = this.getClass().getResourceAsStream(DATA_NAME);
        int pos = 0;

        this.dictData = new byte [data.available()];
        while(pos < this.dictData.length){
            pos += data.read(this.dictData, pos, this.dictData.length - pos);
        }
                
        this.epOutput.setContentType("text/html;");
    }

    /**
     * Shows the previous page of the dictionary.
     */
    @Action
    public void prev() throws IOException {
        if (current < ENTRIES_TO_LOAD){
            this.displayWord(0);
        }
        else {
            this.displayWord(this.current - ENTRIES_TO_LOAD);
        }
    }

    /**
     * Shows the next page of the dictionary.
     */
    @Action
    public void next() throws IOException {
        if (current >= this.index.size() - ENTRIES_TO_LOAD){
            this.displayWord(this.index.size() - ENTRIES_TO_LOAD);
        }
        else {
            this.displayWord(this.current + ENTRIES_TO_LOAD);
        }
    }
}
