/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.aniverse.view;

import com.aniverse.controller.BinarySearch;
import com.aniverse.controller.InsertionSort;
import com.aniverse.controller.MergeSort;
import com.aniverse.controller.SelectionSort;
import com.aniverse.model.AnimeModel;
import com.aniverse.util.ValidationUtil;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pranav kattel
 * @LMU ID:
 */
public class AnimeHouse extends javax.swing.JFrame {

    public java.awt.CardLayout cardLayout;
    private List<AnimeModel> animeList;
    private boolean isAscending = true;

    /**
     * Constructor for AnimeHouse. Initializes components, layout, and anime
     * data.
     */
    public AnimeHouse() {
        initComponents();
        initializeLayout();
        initializeAnimeData();

    }

    /**
     * Initializes the layout using CardLayout and sets up the default screens.
     */
    private void initializeLayout() {
        cardLayout = new java.awt.CardLayout();
        getContentPane().setLayout(cardLayout);
        pnlAddAnimes.setVisible(false);
        pnlDeleteAnime.setVisible(false);
        getContentPane().add(pnlLoadingScreen, "LoadingScreen");
        getContentPane().add(pnlLoginScreen, "LoginScreen");
        getContentPane().add(pnlMainScreen, "MainScreen");

        // Start with the Login screen
        loadScreen("LoginScreen");
    }

    /**
     * Initializes the anime data by populating the list with sample entries.
     */
    private void initializeAnimeData() {
        animeList = new LinkedList<>();

        registerAnime(new AnimeModel("Naruto", "Action, Adventure", "TV", 720, "Completed", "Studio Pierrot", "R", 9));
        registerAnime(new AnimeModel("One Piece", "Action, Adventure", "TV", 1070, "Ongoing", "Toei Animation", "PG-13", 9));
        registerAnime(new AnimeModel("Your Name", "Romance, Drama", "Movie", 1, "Completed", "CoMix Wave Films", "R", 9));
        registerAnime(new AnimeModel("Attack on Titan", "Action, Mystery, Supernatural", "TV", 87, "Completed", "MAPPA", "R", 9));
        registerAnime(new AnimeModel("Demon Slayer", "Action, Fantasy, Adventure", "TV", 44, "Ongoing", "ufotable", "PG-13", 9));
        registerAnime(new AnimeModel("Steins;Gate", "Sci-Fi, Thriller, Drama", "TV", 24, "Completed", "White Fox", "PG-13", 10));
        registerAnime(new AnimeModel("My Hero Academia", "Action, Comedy, Supernatural", "TV", 138, "Ongoing", "Bones", "PG-13", 8));
        registerAnime(new AnimeModel("Death Note", "Mystery, Supernatural, Thriller", "TV", 37, "Completed", "Madhouse", "R", 10));
        registerAnime(new AnimeModel("Spirited Away", "Fantasy, Adventure, Slice of Life", "Movie", 1, "Completed", "Studio Ghibli", "PG", 10));
        registerAnime(new AnimeModel("Jujutsu Kaisen", "Action, Fantasy, Supernatural", "TV", 48, "Ongoing", "MAPPA", "R", 9));

        setupTableSelectionListener();
    }

    /**
     * Adds an anime to the list and updates the UI table.
     *
     * @param anime The anime to be added.
     */
    private void registerAnime(AnimeModel anime) {
        animeList.add(anime);
        DefaultTableModel model = (DefaultTableModel) jtblAnimeList.getModel();
        model.addRow(new Object[]{
            anime.getTitle(), anime.getGenre(), anime.getType(),
            anime.getEpisodes(), anime.getStatus(), anime.getStudio(),
            anime.getRatings(), anime.getScore()
        });
        updateMetrics();

    }

    /**
     * Sets up a listener for the table selection to populate form fields with
     * selected anime data.
     */
    private void setupTableSelectionListener() {
        jtblAnimeList.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = jtblAnimeList.getSelectedRow();
                if (selectedRow != -1) {
                    // Retrieve the data from the selected row
                    String title = (String) jtblAnimeList.getValueAt(selectedRow, 0);
                    String genre = (String) jtblAnimeList.getValueAt(selectedRow, 1);
                    String type = (String) jtblAnimeList.getValueAt(selectedRow, 2);
                    int episodes = (int) jtblAnimeList.getValueAt(selectedRow, 3);
                    String status = (String) jtblAnimeList.getValueAt(selectedRow, 4);
                    String studio = (String) jtblAnimeList.getValueAt(selectedRow, 5);
                    String rating = (String) jtblAnimeList.getValueAt(selectedRow, 6);
                    int score = (int) jtblAnimeList.getValueAt(selectedRow, 7);

                    // Populate the form fields with the retrieved data
                    txtFldTitle.setText(title);
                    txtFldTitleDelete.setText(title);
                    txtFldGenere.setText(genre);
                    comboxType.setSelectedItem(type);
                    spinEpisodes.setValue(episodes);
                    comboxStatus.setSelectedItem(status);
                    txtFldStudio.setText(studio);
                    comboxRating.setSelectedItem(rating);
                    spinScore.setValue(score);
                }
            }
        });
    }

    /**
     * Loads the anime list into the table for display.
     *
     * @param animeList The list of anime to load into the table.
     */
    private void loadAnimeListToTable(List<AnimeModel> animeList) {
        DefaultTableModel tableModel = (DefaultTableModel) jtblAnimeList.getModel();
        tableModel.setRowCount(0); // Clear the table

        for (AnimeModel anime : animeList) {
            tableModel.addRow(new Object[]{
                anime.getTitle(),
                anime.getGenre(),
                anime.getType(),
                anime.getEpisodes(),
                anime.getStatus(),
                anime.getStudio(),
                anime.getRatings(),
                anime.getScore()
            });
        }
    }

    /**
     * Starts the loading bar animation. Switches to the main screen after
     * completion.
     */
    private void startLoadingBar() {
        javax.swing.SwingWorker<Void, Integer> worker;
        worker = new javax.swing.SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(30); // Simulated delay for progress bar
                    publish(i); // Publish progress
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int progress = chunks.get(chunks.size() - 1);
                lol.setValue(progress);
            }

            @Override
            protected void done() {
                loadScreen("MainScreen"); // Switch to login screen
            }
        };
        worker.execute(); // Start the worker thread
    }

    /**
     * Switches the view to a specific screen by name.
     *
     * @param screenName The name of the screen to display.
     */
    private void loadScreen(String screenName) {
        cardLayout.show(getContentPane(), screenName);
    }

    /**
     * Checks if an anime with the same title already exists in the list.
     *
     * @param newAnime The anime object to check for duplicates.
     * @return true if a duplicate exists, false otherwise.
     */
    private boolean checkDuplicateAnime(AnimeModel newAnime) {
        for (AnimeModel anime : animeList) {
            if (anime.getTitle().equalsIgnoreCase(newAnime.getTitle())) {
                return true; // Duplicate found
            }
        }
        return false; // No duplicate
    }

    /**
     * Clears the form fields for adding/editing anime data.
     */
    private void clearAnimeForm() {
        // Clear text fields
        txtFldTitle.setText("");
        txtFldGenere.setText("");
        txtFldStudio.setText("");

        // Reset combo boxes to their default selection (e.g., first item)
        comboxType.setSelectedIndex(0);
        comboxStatus.setSelectedIndex(0);
        comboxRating.setSelectedIndex(0);

        // Reset spinners to their default values
        spinEpisodes.setValue(1); // Assuming 1 is the default for episodes
        spinScore.setValue(0);    // Assuming 0 is the default for score

        // Optional: Reset focus to the first field (if needed)
        txtFldTitle.requestFocus();
    }

    /**
     * Displays a message dialog box.
     *
     * @param message The message to display in the dialog box.
     * @param title The title of the dialog box.
     * @param messageType The type of message (e.g., JOptionPane.ERROR_MESSAGE).
     */
    private void showDialogBox(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    /**
     * Updates the anime metrics (e.g., total episodes, average score).
     */
    private void updateMetrics() {
        int totalEpisodes = 0;
        int completedAnimes = 0;
        int ongoingAnimes = 0;
        int totalScore = 0;

        // Calculate metrics
        for (AnimeModel anime : animeList) {
            totalEpisodes += anime.getEpisodes();

            if (anime.getStatus().equalsIgnoreCase("Completed")) {
                completedAnimes++;
            } else if (anime.getStatus().equalsIgnoreCase("Ongoing")) {
                ongoingAnimes++;
            }

            totalScore += anime.getScore();
        }

        // Calculate total hours (assuming 1 episode = 24 minutes)
        double totalHoursWatched = totalEpisodes * 24.0 / 60.0;

        // Calculate average score
        double averageScore = animeList.isEmpty() ? 0 : (double) totalScore / animeList.size();

        // Update labels
        lblTotalHoursWatched.setText(String.format("%.2f", totalHoursWatched));
        lblTotalEpisodes.setText(String.valueOf(totalEpisodes));
        lblCompletedAnimes.setText(String.valueOf(completedAnimes));
        lblOngoingAnimes.setText(String.valueOf(ongoingAnimes));
        lblAverageScore.setText(String.format("%.2f", averageScore));

        // Total Anime Entries
        lblAnimeEntries.setText(String.valueOf(animeList.size()));
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMainScreen = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        lblSubTitle = new javax.swing.JLabel();
        pnlDeleteAnime = new javax.swing.JPanel();
        lblDeleteAnime = new javax.swing.JLabel();
        txtFldTitleDelete = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        btnDeleteAnimes = new javax.swing.JButton();
        btnExitDelete = new javax.swing.JButton();
        lblBackgroundDelete = new javax.swing.JLabel();
        pnlAddAnimes = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtFldTitle = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtFldGenere = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        comboxType = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        spinEpisodes = new javax.swing.JSpinner();
        jLabel18 = new javax.swing.JLabel();
        comboxStatus = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        txtFldStudio = new javax.swing.JTextField();
        comboxRating = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        spinScore = new javax.swing.JSpinner();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        lblChangable = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnSignOut = new javax.swing.JButton();
        lblMainLogo = new javax.swing.JLabel();
        jlblSmallBanner = new javax.swing.JLabel();
        jtabAnimeList = new javax.swing.JTabbedPane();
        pnlHome = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        lblAnimeEntries = new javax.swing.JLabel();
        lblTotalHoursWatched = new javax.swing.JLabel();
        lblTotalEpisodes = new javax.swing.JLabel();
        lblCompletedAnimes = new javax.swing.JLabel();
        lblOngoingAnimes = new javax.swing.JLabel();
        lblAverageScore = new javax.swing.JLabel();
        pnlAnimeList = new javax.swing.JPanel();
        pnlAnimeTable = new javax.swing.JPanel();
        jscrListTable = new javax.swing.JScrollPane();
        jtblAnimeList = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        btnUpdateAnime = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAddAnimes = new javax.swing.JButton();
        comBoxSort = new javax.swing.JComboBox<>();
        btnAcsDsc = new javax.swing.JButton();
        pnlSearch = new javax.swing.JPanel();
        lblCrossSearch = new javax.swing.JLabel();
        txtFldSearchAnime = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        lblBackgroundFooter = new javax.swing.JLabel();
        lblBackgroundImage = new javax.swing.JLabel();
        pnlLoadingScreen = new javax.swing.JPanel();
        lol = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        pnlLoginScreen = new javax.swing.JPanel();
        pnllogin = new javax.swing.JPanel();
        lblBigSignIn = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtFldUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        chBoxRemember = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        btnLogIn = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        pwdFldPassword = new javax.swing.JPasswordField();
        pnlSignInError = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        pnlMainScreen.setBackground(new java.awt.Color(255, 255, 255));
        pnlMainScreen.setMinimumSize(new java.awt.Dimension(1370, 768));
        pnlMainScreen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Sitka Banner", 1, 42)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(204, 0, 153));
        jLabel23.setText("ANIVERSE");
        pnlMainScreen.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, -1, 50));

        lblSubTitle.setFont(new java.awt.Font("Times New Roman", 1, 39)); // NOI18N
        lblSubTitle.setForeground(new java.awt.Color(204, 204, 255));
        lblSubTitle.setText("Your Anime Universe");
        pnlMainScreen.add(lblSubTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, 410, 60));

        pnlDeleteAnime.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDeleteAnime.setBackground(new java.awt.Color(204, 204, 255));
        lblDeleteAnime.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        lblDeleteAnime.setForeground(new java.awt.Color(204, 204, 255));
        lblDeleteAnime.setText("Delete Animes");
        pnlDeleteAnime.add(lblDeleteAnime, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 180, 30));
        pnlDeleteAnime.add(txtFldTitleDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 295, 30));

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(204, 204, 255));
        jLabel22.setText("Title");
        pnlDeleteAnime.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));

        btnDeleteAnimes.setBackground(new java.awt.Color(204, 153, 255));
        btnDeleteAnimes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeleteAnimes.setForeground(new java.awt.Color(102, 0, 102));
        btnDeleteAnimes.setText("Delete Animes");
        btnDeleteAnimes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAnimesActionPerformed(evt);
            }
        });
        pnlDeleteAnime.add(btnDeleteAnimes, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        btnExitDelete.setBackground(new java.awt.Color(204, 153, 255));
        btnExitDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExitDelete.setForeground(new java.awt.Color(153, 0, 153));
        btnExitDelete.setText("Exit");
        btnExitDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitDeleteActionPerformed(evt);
            }
        });
        pnlDeleteAnime.add(btnExitDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 130, -1, -1));

        lblBackgroundDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aniverse/resources/deleteBack.jpg"))); // NOI18N
        lblBackgroundDelete.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 2, true));
        pnlDeleteAnime.add(lblBackgroundDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 170));

        pnlMainScreen.add(pnlDeleteAnime, new org.netbeans.lib.awtextra.AbsoluteConstraints(483, 200, 400, 170));

        pnlAddAnimes.setBackground(new java.awt.Color(102, 102, 102));
        pnlAddAnimes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        pnlAddAnimes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setBackground(new java.awt.Color(51, 0, 102));
        jButton2.setForeground(new java.awt.Color(204, 204, 255));
        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        pnlAddAnimes.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(727, 355, -1, -1));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 0, 51));
        jLabel13.setText("Title");
        pnlAddAnimes.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, -1, -1));
        pnlAddAnimes.add(txtFldTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 295, 30));

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 0, 51));
        jLabel15.setText("Genere");
        pnlAddAnimes.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, -1, -1));

        txtFldGenere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldGenereActionPerformed(evt);
            }
        });
        pnlAddAnimes.add(txtFldGenere, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, 295, 30));

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 0, 51));
        jLabel16.setText("Type");
        pnlAddAnimes.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, -1, -1));

        comboxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TV", "Movie", "OVA", "ONA", "Specials" }));
        comboxType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboxTypeActionPerformed(evt);
            }
        });
        pnlAddAnimes.add(comboxType, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 295, 30));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 0, 51));
        jLabel17.setText("Episodes");
        pnlAddAnimes.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 130, -1, -1));
        pnlAddAnimes.add(spinEpisodes, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 150, 295, 30));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 0, 51));
        jLabel18.setText("Status");
        pnlAddAnimes.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, -1, -1));

        comboxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ongoing", "Upcoming", "Completed" }));
        comboxStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboxStatusActionPerformed(evt);
            }
        });
        pnlAddAnimes.add(comboxStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, 295, 36));

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 0, 51));
        jLabel19.setText("Studio");
        pnlAddAnimes.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 200, -1, -1));
        pnlAddAnimes.add(txtFldStudio, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, 295, 30));

        comboxRating.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "G", "PG", "PG-13", "R" }));
        comboxRating.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboxRatingActionPerformed(evt);
            }
        });
        pnlAddAnimes.add(comboxRating, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, 295, 32));

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 0, 51));
        jLabel20.setText("Rating");
        pnlAddAnimes.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, -1, -1));

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 0, 51));
        jLabel21.setText("Score (0-10)");
        pnlAddAnimes.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 270, -1, -1));
        pnlAddAnimes.add(spinScore, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 290, 296, 30));

        btnAdd.setBackground(new java.awt.Color(51, 0, 102));
        btnAdd.setForeground(new java.awt.Color(204, 204, 255));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        pnlAddAnimes.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 355, -1, -1));

        btnUpdate.setBackground(new java.awt.Color(51, 0, 102));
        btnUpdate.setForeground(new java.awt.Color(204, 204, 255));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        pnlAddAnimes.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 355, -1, -1));

        lblChangable.setBackground(new java.awt.Color(204, 204, 255));
        lblChangable.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        lblChangable.setForeground(new java.awt.Color(204, 204, 255));
        lblChangable.setText("Add Animes");
        pnlAddAnimes.add(lblChangable, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, 180, 30));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aniverse/resources/addback.jpg"))); // NOI18N
        jLabel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 0, 204), 2, true));
        pnlAddAnimes.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 400));

        pnlMainScreen.add(pnlAddAnimes, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 880, 400));

        btnSignOut.setBackground(new java.awt.Color(153, 102, 255));
        btnSignOut.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        btnSignOut.setForeground(new java.awt.Color(255, 255, 255));
        btnSignOut.setText("Sign Out");
        btnSignOut.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 2, true));
        btnSignOut.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSignOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignOutActionPerformed(evt);
            }
        });
        pnlMainScreen.add(btnSignOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 40, 90, 50));

        lblMainLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aniverse/resources/luffylogo.png"))); // NOI18N
        pnlMainScreen.add(lblMainLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 90, 80));

        jlblSmallBanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aniverse/resources/bannerbackk.jpg"))); // NOI18N
        pnlMainScreen.add(jlblSmallBanner, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 110));

        jtabAnimeList.setBackground(new java.awt.Color(255, 255, 255));
        jtabAnimeList.setPreferredSize(new java.awt.Dimension(1366, 768));

        pnlHome.setBackground(new java.awt.Color(153, 0, 204));
        pnlHome.setPreferredSize(new java.awt.Dimension(1366, 768));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aniverse/resources/finalone.png"))); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(1366, 400));

        jPanel1.setBackground(new java.awt.Color(204, 0, 204));
        jPanel1.setForeground(new java.awt.Color(204, 102, 255));

        jLabel25.setFont(new java.awt.Font("Snap ITC", 0, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 0, 102));
        jLabel25.setText("Trending Now");

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aniverse/resources/onepiece.jpg"))); // NOI18N

        jLabel27.setBackground(new java.awt.Color(255, 204, 255));
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aniverse/resources/bluelock.jpg"))); // NOI18N

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aniverse/resources/dandadan.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel25))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(58, 58, 58)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel25)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap(176, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 51, 255));

        jLabel28.setFont(new java.awt.Font("Stencil", 1, 36)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(102, 0, 102));
        jLabel28.setText("Your Dashboaard");

        jLabel30.setFont(new java.awt.Font("Sylfaen", 1, 20)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 0, 51));
        jLabel30.setText("Total Anime Entries :");

        jLabel31.setFont(new java.awt.Font("Sylfaen", 1, 20)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 0, 51));
        jLabel31.setText("Total Hours of anime watched :");

        jLabel32.setFont(new java.awt.Font("Sylfaen", 1, 20)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(51, 0, 51));
        jLabel32.setText("Completed Animes :");

        jLabel33.setFont(new java.awt.Font("Sylfaen", 1, 20)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(51, 0, 51));
        jLabel33.setText("Average score :");

        jLabel34.setFont(new java.awt.Font("Sylfaen", 1, 20)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(51, 0, 51));
        jLabel34.setText("Total Episodes :");

        jLabel35.setFont(new java.awt.Font("Sylfaen", 1, 20)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(51, 0, 51));
        jLabel35.setText("Ongoing Animes :");

        lblAnimeEntries.setBackground(new java.awt.Color(153, 153, 153));
        lblAnimeEntries.setFont(new java.awt.Font("Sylfaen", 1, 20)); // NOI18N
        lblAnimeEntries.setForeground(new java.awt.Color(255, 255, 255));
        lblAnimeEntries.setText("number");

        lblTotalHoursWatched.setBackground(new java.awt.Color(153, 153, 153));
        lblTotalHoursWatched.setFont(new java.awt.Font("Sylfaen", 1, 20)); // NOI18N
        lblTotalHoursWatched.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalHoursWatched.setText("jLabel36");

        lblTotalEpisodes.setBackground(new java.awt.Color(153, 153, 153));
        lblTotalEpisodes.setFont(new java.awt.Font("Sylfaen", 1, 20)); // NOI18N
        lblTotalEpisodes.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalEpisodes.setText("jLabel36");

        lblCompletedAnimes.setBackground(new java.awt.Color(153, 153, 153));
        lblCompletedAnimes.setFont(new java.awt.Font("Sylfaen", 1, 20)); // NOI18N
        lblCompletedAnimes.setForeground(new java.awt.Color(255, 255, 255));
        lblCompletedAnimes.setText("jLabel36");

        lblOngoingAnimes.setBackground(new java.awt.Color(153, 153, 153));
        lblOngoingAnimes.setFont(new java.awt.Font("Sylfaen", 1, 20)); // NOI18N
        lblOngoingAnimes.setForeground(new java.awt.Color(255, 255, 255));
        lblOngoingAnimes.setText("jLabel36");

        lblAverageScore.setBackground(new java.awt.Color(153, 153, 153));
        lblAverageScore.setFont(new java.awt.Font("Sylfaen", 1, 20)); // NOI18N
        lblAverageScore.setForeground(new java.awt.Color(255, 255, 255));
        lblAverageScore.setText("jLabel36");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel30)
                            .addComponent(jLabel32)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAnimeEntries, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTotalEpisodes, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTotalHoursWatched, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCompletedAnimes, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblOngoingAnimes, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblAverageScore, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel28)
                        .addGap(32, 32, 32)))
                .addGap(50, 50, 50))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel28)
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel33))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblAnimeEntries)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalHoursWatched)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalEpisodes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCompletedAnimes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOngoingAnimes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAverageScore)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlHomeLayout = new javax.swing.GroupLayout(pnlHome);
        pnlHome.setLayout(pnlHomeLayout);
        pnlHomeLayout.setHorizontalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 1366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHomeLayout.setVerticalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jtabAnimeList.addTab("Home", pnlHome);

        pnlAnimeList.setBackground(new java.awt.Color(27, 43, 100));
        pnlAnimeList.setOpaque(false);
        pnlAnimeList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlAnimeTable.setBackground(new java.awt.Color(153, 0, 204));

        jscrListTable.setBackground(new java.awt.Color(204, 0, 0));
        jscrListTable.setBorder(null);

        jtblAnimeList.setBackground(new java.awt.Color(153, 153, 255));
        jtblAnimeList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Genere", "Type", "Episodes", "Status", "Studio", "Rating", "Score"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblAnimeList.setOpaque(false);
        jscrListTable.setViewportView(jtblAnimeList);
        if (jtblAnimeList.getColumnModel().getColumnCount() > 0) {
            jtblAnimeList.getColumnModel().getColumn(0).setResizable(false);
            jtblAnimeList.getColumnModel().getColumn(0).setPreferredWidth(200);
            jtblAnimeList.getColumnModel().getColumn(1).setResizable(false);
            jtblAnimeList.getColumnModel().getColumn(1).setPreferredWidth(200);
            jtblAnimeList.getColumnModel().getColumn(2).setResizable(false);
            jtblAnimeList.getColumnModel().getColumn(3).setResizable(false);
            jtblAnimeList.getColumnModel().getColumn(4).setResizable(false);
            jtblAnimeList.getColumnModel().getColumn(5).setResizable(false);
            jtblAnimeList.getColumnModel().getColumn(6).setResizable(false);
            jtblAnimeList.getColumnModel().getColumn(7).setResizable(false);
        }

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 204, 255));
        jLabel12.setText("My Anime List");

        btnUpdateAnime.setBackground(new java.awt.Color(153, 0, 153));
        btnUpdateAnime.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateAnime.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateAnime.setText("Update Animes");
        btnUpdateAnime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateAnimeActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(153, 0, 153));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete Animes");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAddAnimes.setBackground(new java.awt.Color(153, 0, 153));
        btnAddAnimes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddAnimes.setForeground(new java.awt.Color(255, 255, 255));
        btnAddAnimes.setText("Add Animes");
        btnAddAnimes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAnimesActionPerformed(evt);
            }
        });

        comBoxSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sort", "Title", "Score", "Episodes" }));
        comBoxSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comBoxSortActionPerformed(evt);
            }
        });

        btnAcsDsc.setText("↓");
        btnAcsDsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcsDscActionPerformed(evt);
            }
        });

        pnlSearch.setOpaque(false);
        pnlSearch.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCrossSearch.setBackground(new java.awt.Color(255, 102, 102));
        lblCrossSearch.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblCrossSearch.setForeground(new java.awt.Color(255, 102, 102));
        lblCrossSearch.setText("X");
        pnlSearch.add(lblCrossSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 7, 20, -1));
        lblCrossSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Call the method to show the original table (cancel the search)
                loadAnimeListToTable(animeList);
                txtFldSearchAnime.setText("");
            }
        });
        pnlSearch.add(txtFldSearchAnime, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 8, 230, 25));

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pnlSearch.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 8, -1, 25));

        javax.swing.GroupLayout pnlAnimeTableLayout = new javax.swing.GroupLayout(pnlAnimeTable);
        pnlAnimeTable.setLayout(pnlAnimeTableLayout);
        pnlAnimeTableLayout.setHorizontalGroup(
            pnlAnimeTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAnimeTableLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlAnimeTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAnimeTableLayout.createSequentialGroup()
                        .addGap(567, 567, 567)
                        .addComponent(jLabel12))
                    .addGroup(pnlAnimeTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlAnimeTableLayout.createSequentialGroup()
                            .addComponent(btnAddAnimes)
                            .addGap(478, 478, 478)
                            .addComponent(btnUpdateAnime)
                            .addGap(527, 527, 527)
                            .addComponent(btnDelete))
                        .addGroup(pnlAnimeTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlAnimeTableLayout.createSequentialGroup()
                                .addComponent(comBoxSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAcsDsc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jscrListTable, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1328, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAnimeTableLayout.setVerticalGroup(
            pnlAnimeTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAnimeTableLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel12)
                .addGap(10, 10, 10)
                .addGroup(pnlAnimeTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlAnimeTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comBoxSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAcsDsc))
                    .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jscrListTable, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAnimeTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdateAnime)
                    .addComponent(btnDelete)
                    .addComponent(btnAddAnimes))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pnlAnimeList.add(pnlAnimeTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 410));

        jtabAnimeList.addTab("Anime List", pnlAnimeList);

        pnlMainScreen.add(jtabAnimeList, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1370, 768));

        lblBackgroundFooter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aniverse/resources/anotherr.jpg"))); // NOI18N
        pnlMainScreen.add(lblBackgroundFooter, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 1370, 280));

        lblBackgroundImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aniverse/resources/gradient background.jpg"))); // NOI18N
        pnlMainScreen.add(lblBackgroundImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 750));

        pnlLoadingScreen.setMinimumSize(new java.awt.Dimension(1336, 768));
        pnlLoadingScreen.setPreferredSize(new java.awt.Dimension(1336, 768));
        pnlLoadingScreen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lol.setBackground(new java.awt.Color(102, 102, 102));
        pnlLoadingScreen.add(lol, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 340, 850, 25));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aniverse/resources/loadingback.jpg"))); // NOI18N
        pnlLoadingScreen.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1380, 768));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1366, 768));

        pnlLoginScreen.setBackground(new java.awt.Color(204, 204, 204));
        pnlLoginScreen.setMinimumSize(new java.awt.Dimension(1336, 768));
        pnlLoginScreen.setPreferredSize(new java.awt.Dimension(1336, 768));
        pnlLoginScreen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnllogin.setBackground(new java.awt.Color(0, 102, 153));
        pnllogin.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        lblBigSignIn.setFont(new java.awt.Font("Segoe UI", 1, 29)); // NOI18N
        lblBigSignIn.setForeground(new java.awt.Color(204, 204, 255));
        lblBigSignIn.setText("Sign in to continue ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Universe");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("<html>Dive into a world of endless anime possibilities.Track your<br>" +
            " favorites, discover new series, and connect with fellow anime<br>" +
            "enthusiasts in one immersive platform.</html>");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("100k Users");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("500k+ anime");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Welcome to Your Anime  ");

        txtFldUsername.setBackground(new java.awt.Color(54, 93, 142));
        txtFldUsername.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtFldUsername.setForeground(new java.awt.Color(255, 255, 255));
        txtFldUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFldUsername.setText("username");
        txtFldUsername.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFldUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldUsernameActionPerformed(evt);
            }
        });

        lblPassword.setForeground(new java.awt.Color(255, 255, 255));
        lblPassword.setText("Password");

        lblUsername.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername.setText("Username");

        chBoxRemember.setForeground(new java.awt.Color(255, 255, 255));
        chBoxRemember.setText("remember me");
        chBoxRemember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chBoxRememberActionPerformed(evt);
            }
        });

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Forgot password?");

        btnLogIn.setBackground(new java.awt.Color(0, 39, 89));
        btnLogIn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogIn.setForeground(new java.awt.Color(255, 255, 255));
        btnLogIn.setText("Log In");
        btnLogIn.setBorder(null);
        btnLogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogInActionPerformed(evt);
            }
        });

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aniverse/resources/luffylogo.png"))); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(80, 80));

        pwdFldPassword.setBackground(new java.awt.Color(54, 93, 142));
        pwdFldPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        pwdFldPassword.setForeground(new java.awt.Color(18, 12, 71));
        pwdFldPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pwdFldPassword.setText("password");
        pwdFldPassword.setActionCommand("<Not Set>");
        pwdFldPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pwdFldPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pwdFldPasswordActionPerformed(evt);
            }
        });

        pnlSignInError.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        pnlSignInError.setForeground(new java.awt.Color(255, 51, 51));

        jLabel2.setFont(new java.awt.Font("Snap ITC", 1, 28)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 255));
        jLabel2.setText("Aniverse");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 204, 255));
        jLabel9.setText("Your anime universe");

        javax.swing.JPanel pnllogin = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Create a vertical gradient from top to bottom
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(21, 39, 63), 0, getHeight(), new Color(51, 86, 137));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

            }
        };

        javax.swing.GroupLayout pnlloginLayout = new javax.swing.GroupLayout(pnllogin);
        pnllogin.setLayout(pnlloginLayout);
        pnlloginLayout.setHorizontalGroup(
            pnlloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlloginLayout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addGroup(pnlloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlloginLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(58, 58, 58)
                        .addComponent(jLabel7))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addGroup(pnlloginLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel9))))
                .addGap(16, 16, 16)
                .addGroup(pnlloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLogIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPassword)
                            .addComponent(txtFldUsername)
                            .addGroup(pnlloginLayout.createSequentialGroup()
                                .addComponent(chBoxRemember)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11))
                            .addComponent(pwdFldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUsername))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlloginLayout.createSequentialGroup()
                            .addComponent(lblBigSignIn)
                            .addGap(49, 49, 49)))
                    .addComponent(pnlSignInError, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61))
        );
        pnlloginLayout.setVerticalGroup(
            pnlloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlloginLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(lblBigSignIn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSignInError, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pwdFldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chBoxRemember)
                    .addComponent(jLabel11))
                .addGap(16, 16, 16)
                .addComponent(btnLogIn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
            .addGroup(pnlloginLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnllogin.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(10, 10, 40),2, true)); // The 'true' makes the border rounded

        pnlLoginScreen.add(pnllogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 149, 940, 430));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/aniverse/resources/final.gif"))); // NOI18N
        pnlLoginScreen.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlLoginScreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlLoginScreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSignOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignOutActionPerformed
        pwdFldPassword.setText("");
        txtFldUsername.setText("");
        loadScreen("LoginScreen"); // Load the main screen        // TODO add your handling code here:
    }//GEN-LAST:event_btnSignOutActionPerformed

    private void pwdFldPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pwdFldPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pwdFldPasswordActionPerformed

    private void btnLogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogInActionPerformed

        String username = txtFldUsername.getText();
        String password = new String(pwdFldPassword.getPassword());

        // Check if username or password is empty
        if (username.isEmpty() || password.isEmpty()) {
            pnlSignInError.setText("Please enter your username and password.");
        } else if (!username.equals("username") || !password.equals("password")) {
            pnlSignInError.setText("Username and password are incorrect. Try again!");
        } else {
            pnlSignInError.setText("");
            loadScreen("LoadingScreen");
            startLoadingBar();
        }
    }//GEN-LAST:event_btnLogInActionPerformed

    private void chBoxRememberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chBoxRememberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chBoxRememberActionPerformed

    private void txtFldUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldUsernameActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        boolean isValid = true;
        boolean exists = false;
        int existingIndex = -1;

        // Validate Title
        if (!ValidationUtil.isValidTitle(txtFldTitle.getText())) {
            txtFldTitle.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Title. Please enter a valid title.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            txtFldTitle.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        // Validate Genre
        if (!ValidationUtil.isValidGenre(txtFldGenere.getText())) {
            txtFldGenere.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Genre. Please enter valid genres separated by commas.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            txtFldGenere.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        // Validate Type
        if (!ValidationUtil.isValidType((String) comboxType.getSelectedItem())) {
            comboxType.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Type. Select from TV, Movie, OVA, ONA, or Special.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            comboxType.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        // Validate Status
        if (!ValidationUtil.isValidStatus((String) comboxStatus.getSelectedItem())) {
            comboxStatus.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Status. Select from Ongoing, Completed, or Upcoming.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            comboxStatus.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        // Validate Rating
        if (!ValidationUtil.isValidRating((String) comboxRating.getSelectedItem())) {
            comboxRating.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Rating. Select from G, PG, PG-13, or R.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            comboxRating.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        // Validate Episodes
        int episodes = (int) spinEpisodes.getValue();
        if (!ValidationUtil.isValidEpisodes(episodes)) {
            spinEpisodes.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Episodes. Must be greater than 0.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            spinEpisodes.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        // Validate Studio
        if (!ValidationUtil.isValidStudio(txtFldStudio.getText())) {
            txtFldStudio.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Studio. Studio name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            txtFldStudio.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        // Validate Score
        int score = (int) spinScore.getValue();
        if (!ValidationUtil.isValidScore(score)) {
            spinScore.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Score. Must be between 0 and 10.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            spinScore.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        if (isValid) {
            // Add Anime to List
            AnimeModel updatedAnime = new AnimeModel(
                    txtFldTitle.getText().trim(),
                    txtFldGenere.getText().trim(),
                    (String) comboxType.getSelectedItem(),
                    episodes,
                    (String) comboxStatus.getSelectedItem(),
                    txtFldStudio.getText().trim(),
                    (String) comboxRating.getSelectedItem(),
                    score
            );

            for (int i = 0; i < animeList.size(); i++) {
                if (animeList.get(i).getTitle().equalsIgnoreCase(updatedAnime.getTitle())) {
                    exists = true;
                    existingIndex = i;
                    break;
                }
            }

            if (exists) {
                // Update the anime in the list
                animeList.set(existingIndex, updatedAnime);
                loadAnimeListToTable(animeList);
                clearAnimeForm();
                showDialogBox("Anime updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                showDialogBox("No anime found with the provided title to update.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        boolean isValid = true;

        // Validate Title
        if (!ValidationUtil.isValidTitle(txtFldTitle.getText())) {
            txtFldTitle.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Title. Please enter a valid title.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            txtFldTitle.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        // Validate Genre
        if (!ValidationUtil.isValidGenre(txtFldGenere.getText())) {
            txtFldGenere.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Genre. Please enter valid genres separated by commas.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            txtFldGenere.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        // Validate Type
        if (!ValidationUtil.isValidType((String) comboxType.getSelectedItem())) {
            comboxType.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Type. Select from TV, Movie, OVA, ONA, or Special.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            comboxType.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        // Validate Status
        if (!ValidationUtil.isValidStatus((String) comboxStatus.getSelectedItem())) {
            comboxStatus.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Status. Select from Ongoing, Completed, or Upcoming.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            comboxStatus.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        // Validate Rating
        if (!ValidationUtil.isValidRating((String) comboxRating.getSelectedItem())) {
            comboxRating.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Rating. Select from G, PG, PG-13, or R.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            comboxRating.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        // Validate Episodes
        int episodes = (int) spinEpisodes.getValue();
        if (!ValidationUtil.isValidEpisodes(episodes)) {
            spinEpisodes.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Episodes. Must be greater than 0.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            spinEpisodes.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        // Validate Studio
        if (!ValidationUtil.isValidStudio(txtFldStudio.getText())) {
            txtFldStudio.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Studio. Studio name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            txtFldStudio.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        // Validate Score
        int score = (int) spinScore.getValue();
        if (!ValidationUtil.isValidScore(score)) {
            spinScore.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Score. Must be between 0 and 10.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            spinScore.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        if (isValid) {
            // Add Anime to List
            AnimeModel newAnime = new AnimeModel(
                    txtFldTitle.getText().trim(),
                    txtFldGenere.getText().trim(),
                    (String) comboxType.getSelectedItem(),
                    episodes,
                    (String) comboxStatus.getSelectedItem(),
                    txtFldStudio.getText().trim(),
                    (String) comboxRating.getSelectedItem(),
                    score
            );

            if (!checkDuplicateAnime(newAnime)) {
                animeList.add(newAnime);
                loadAnimeListToTable(animeList);
                clearAnimeForm();
                showDialogBox("Anime added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                showDialogBox("Duplicate Title! Anime already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void comboxRatingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboxRatingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboxRatingActionPerformed

    private void comboxStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboxStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboxStatusActionPerformed

    private void comboxTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboxTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboxTypeActionPerformed

    private void txtFldGenereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldGenereActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldGenereActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        btnUpdateAnime.setVisible(true);
        pnlAddAnimes.setVisible(false);
        jtblAnimeList.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnDeleteAnimesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAnimesActionPerformed
        // TODO add your handling code here:
        boolean isValid = true;

        // Validate Title
        if (!ValidationUtil.isValidTitle(txtFldTitleDelete.getText())) {
            txtFldTitleDelete.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            showDialogBox("Invalid Title. Please enter a valid title.", "Error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else {
            txtFldTitleDelete.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }
        if (isValid) {
            // Search and Delete Anime
            boolean animeDeleted = false;
            for (int i = 0; i < animeList.size(); i++) {
                if (animeList.get(i).getTitle().equalsIgnoreCase(txtFldTitleDelete.getText().trim())) {
                    animeList.remove(i);
                    animeDeleted = true;
                    loadAnimeListToTable(animeList);
                    clearAnimeForm(); // Clear the delete form as well
                    showDialogBox("Anime deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
            if (!animeDeleted) {
                showDialogBox("Anime with the given title does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        updateMetrics();
    }//GEN-LAST:event_btnDeleteAnimesActionPerformed

    private void btnExitDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitDeleteActionPerformed

// TODO add your handling code here:    
        pnlDeleteAnime.setVisible(false);
        jtblAnimeList.setVisible(true);

    }//GEN-LAST:event_btnExitDeleteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        MergeSort mergeSort = new MergeSort();
        List<AnimeModel> sortedList = mergeSort.sort(animeList);
        BinarySearch search = new BinarySearch();
        AnimeModel searchedData = search.searchByTitle(txtFldSearchAnime.getText().trim(), sortedList, 0, sortedList.size());
        if (searchedData != null) {
            System.out.println(searchedData.getTitle());

            // Display the result in the table
            LinkedList<AnimeModel> resultList = new LinkedList<>();
            resultList.add(searchedData);
            loadAnimeListToTable(resultList);
        } else {

            showDialogBox("No match found for the given title.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAcsDscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcsDscActionPerformed
        // TODO add your handling code here:
        isAscending = !isAscending;

        // Update the button text based on the sorting order
        if (isAscending) {
            btnAcsDsc.setText("↓");
        } else {
            btnAcsDsc.setText("↑");
        }
        String selectedOption = (String) comBoxSort.getSelectedItem();
        if (selectedOption != null) {
            comBoxSortActionPerformed(evt);
        }

    }//GEN-LAST:event_btnAcsDscActionPerformed

    private void comBoxSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comBoxSortActionPerformed
        // TODO add your handling code here:
        String selectedOption = (String) comBoxSort.getSelectedItem();

        if (selectedOption != null) {
            switch (selectedOption) {
                case "Title": // Use MergeSort
                    MergeSort mergeSort = new MergeSort();
                    System.out.println("title");
                    List<AnimeModel> sortedList = mergeSort.sort(animeList);

                    if (!isAscending) {
                        LinkedList<AnimeModel> reversedList = new LinkedList<>();
                        for (AnimeModel anime : sortedList) {
                            reversedList.addFirst(anime); // Add each element to the front
                        }
                        sortedList = reversedList;
                    }
                    loadAnimeListToTable(sortedList);
                    break;

                case "Episodes": // Use SelectionSort
                    SelectionSort selectionSort = new SelectionSort();
                    System.out.println("title");
                    List<AnimeModel> sortedList1 = selectionSort.sortByEpisodes(animeList, isAscending);
                    loadAnimeListToTable(sortedList1);
                    break;

                case "Score": // Use InsertionSort
                    InsertionSort insertionSort = new InsertionSort();
                    System.out.println("title");
                    List<AnimeModel> sortedList2 = insertionSort.sortByScore(animeList, isAscending);
                    loadAnimeListToTable(sortedList2);
                    break;

                default:
                    break;
            }

        }
    }//GEN-LAST:event_comBoxSortActionPerformed

    private void btnAddAnimesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAnimesActionPerformed
        btnUpdateAnime.setVisible(false);
        clearAnimeForm();
        pnlAddAnimes.setVisible(true);
        lblChangable.setText("Add Animes");
        btnUpdate.setVisible(false);
        btnAdd.setVisible(true);
        jtblAnimeList.setVisible(false);

    }//GEN-LAST:event_btnAddAnimesActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        pnlDeleteAnime.setVisible(true);
        jtblAnimeList.setVisible(false);// TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateAnimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateAnimeActionPerformed
        btnUpdateAnime.setVisible(false);
        pnlAddAnimes.setVisible(true);
        lblChangable.setText("Update Animes");
        btnUpdate.setVisible(true);
        btnAdd.setVisible(false);
        jtblAnimeList.setVisible(false);
    }//GEN-LAST:event_btnUpdateAnimeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AnimeHouse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnimeHouse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnimeHouse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnimeHouse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnimeHouse().setVisible(true);
            }
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcsDsc;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddAnimes;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteAnimes;
    private javax.swing.JButton btnExitDelete;
    private javax.swing.JButton btnLogIn;
    private javax.swing.JButton btnSignOut;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdateAnime;
    private javax.swing.JCheckBox chBoxRemember;
    private javax.swing.JComboBox<String> comBoxSort;
    private javax.swing.JComboBox<String> comboxRating;
    private javax.swing.JComboBox<String> comboxStatus;
    private javax.swing.JComboBox<String> comboxType;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel jlblSmallBanner;
    private javax.swing.JScrollPane jscrListTable;
    private javax.swing.JTabbedPane jtabAnimeList;
    private javax.swing.JTable jtblAnimeList;
    private javax.swing.JLabel lblAnimeEntries;
    private javax.swing.JLabel lblAverageScore;
    private javax.swing.JLabel lblBackgroundDelete;
    private javax.swing.JLabel lblBackgroundFooter;
    private javax.swing.JLabel lblBackgroundImage;
    private javax.swing.JLabel lblBigSignIn;
    private javax.swing.JLabel lblChangable;
    private javax.swing.JLabel lblCompletedAnimes;
    private javax.swing.JLabel lblCrossSearch;
    private javax.swing.JLabel lblDeleteAnime;
    private javax.swing.JLabel lblMainLogo;
    private javax.swing.JLabel lblOngoingAnimes;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblSubTitle;
    private javax.swing.JLabel lblTotalEpisodes;
    private javax.swing.JLabel lblTotalHoursWatched;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JProgressBar lol;
    private javax.swing.JPanel pnlAddAnimes;
    private javax.swing.JPanel pnlAnimeList;
    private javax.swing.JPanel pnlAnimeTable;
    private javax.swing.JPanel pnlDeleteAnime;
    private javax.swing.JPanel pnlHome;
    private javax.swing.JPanel pnlLoadingScreen;
    private javax.swing.JPanel pnlLoginScreen;
    private javax.swing.JPanel pnlMainScreen;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JLabel pnlSignInError;
    private javax.swing.JPanel pnllogin;
    private javax.swing.JPasswordField pwdFldPassword;
    private javax.swing.JSpinner spinEpisodes;
    private javax.swing.JSpinner spinScore;
    private javax.swing.JTextField txtFldGenere;
    private javax.swing.JTextField txtFldSearchAnime;
    private javax.swing.JTextField txtFldStudio;
    private javax.swing.JTextField txtFldTitle;
    private javax.swing.JTextField txtFldTitleDelete;
    private javax.swing.JTextField txtFldUsername;
    // End of variables declaration//GEN-END:variables
}
