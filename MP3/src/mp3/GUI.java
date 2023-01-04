
package mp3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class GUI extends JFrame {

    private static JFrame frame = new JFrame();

    private String songTitle = "";
    private String currentSong = "";
    private double setVolume = 0.1;
    ArrayList<Song> library = new ArrayList<Song>();

    final JFXPanel fxPanel = new JFXPanel();
    static MediaPlayer[] media = new MediaPlayer[1];

    private static JPanel images = new JPanel();
    private static JLabel songName = new JLabel("Chúc bạn nghe nhạc vui vẻ");

    static JLabel lbtime;
    static JSlider song_duration;
    static JSlider volume;
    double totalLength = 0, currentLength = 0;


    public GUI() {
        //Get the library.
        library = MP3.returnLibrary();

        frame.setTitle("MP3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panel_top = new JPanel();
        JPanel panel_bot = new JPanel();
        //Functionalities
        JComboBox<?> songList = new JComboBox<Object>(makeMusicList());
        songList.setSelectedIndex(0);
        ListenForSelection Lsong = new ListenForSelection();
        songList.addActionListener(Lsong);
        //panel.add(songList);

        //Buttons
        JButton Play = new JButton("PLAY");
        ListenForControl Pcontrols = new ListenForControl("Play");
        Play.addActionListener(Pcontrols);
        //panel.add(Play);
        JButton Pause = new JButton("Pause");
        ListenForControl P2controls = new ListenForControl("Pause");
        Pause.addActionListener(P2controls);
        //panel.add(Pause);
        JButton Next = new JButton("Next");
        ListenForControl Ncontrols = new ListenForControl("Next");
        Next.addActionListener(Ncontrols);
        //panel.add(Next);
        JButton Back = new JButton("Back");
        ListenForControl Bcontrols = new ListenForControl("Back");
        Back.addActionListener(Bcontrols);
        //panel.add(Back);

        panel_top.add(songList);
        panel_top.add(Play);
        panel_top.add(Pause);
        panel_top.add(Next);
        panel_top.add(Back);

        panel.add(panel_top, BorderLayout.NORTH);
        //Song Duration
        int start_index = 0;
        int end_index = 100;
        int current_index = 0;

        lbtime = new JLabel(getDisplayDuration(), SwingConstants.HORIZONTAL);
        song_duration = new JSlider(JSlider.HORIZONTAL, start_index, end_index, current_index);

//        JSlider song_duration = new JSlider(JSlider.HORIZONTAL, start_index, end_index, current_index);
//
//        ListenForDuration lForDuration = new ListenForDuration();
//        song_duration.addMouseListener(lForDuration);
        //Song volume
        int Lo_Volume = 0;
        int Hi_Volume = 100;
        int Curr_Volume = 100;    //initial frames per second

        volume = new JSlider(JSlider.HORIZONTAL, Lo_Volume, Hi_Volume, Curr_Volume);

        //ListenForVolume lVolume = new ListenForVolume();
        //volume.addMouseListener(lVolume);
        volume.setMajorTickSpacing(10);
        volume.setMinorTickSpacing(1);
        volume.setPaintTicks(true);
        volume.setPaintLabels(true);

        //panel.add(volume);
        panel_bot.add(song_duration);
        panel_bot.add(lbtime);
        panel_bot.add(volume);
        panel.add(panel_bot, BorderLayout.SOUTH);
        Font f1 = new Font(Font.SERIF, Font.PLAIN, 35);
        songName.setFont(f1);
        //panel.add(songName);

        JPanel other = new JPanel();
        other.add(images);
        panel.setPreferredSize(new Dimension(500, 150));
        other.setPreferredSize(new Dimension(500, 300));
        mainPanel.add(panel, BorderLayout.NORTH);
        songName.setBorder(new EmptyBorder(30, 80, 30, 30));
        mainPanel.add(songName, BorderLayout.CENTER);
        mainPanel.add(other, BorderLayout.SOUTH);
        //this.add(panel);
        frame.add(mainPanel);
        frame.setVisible(true);

    }

    private void changeTitle(String cSong) {
        //find song.
        if (media[0] != null) {
            media[0].pause();
        }
        String songeTitle = "";
        boolean isCurrentSongPlaying = false;
        for (Song i : library) {

            if (i.getTitle().equals(cSong)) {
                //songTitle
                if (cSong.equals(songTitle)) {
                    isCurrentSongPlaying = true;
                } else {
                    currentSong = i.getPath().toString();
                    String[] arr = currentSong.split("\\\\");
                    songeTitle = arr[arr.length - 1].split(".mp3")[0];
                    songTitle = cSong;
                }
            }
        }
        if (!isCurrentSongPlaying) {
            this.setTitle("JPlayer: " + songTitle);
            changeSelection();
            playCurrentSong(0);

            songName.setText(songeTitle);

            frame.repaint();
        } else {
            System.out.println("Same song");
            playCurrentSong(1);
        }
    }

    private void changeSelection() {

    }

    private void playCurrentSong(int n) {
        if (n == 1) {
            media[0].play();
            displayTime();
        } else {
            Media hit = new Media(new File(currentSong).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            media[0] = mediaPlayer;
            media[0].play();
            displayTime();
        }
    }

    private String[] makeMusicList() {
        String s = "";
        for (Song i : library) {
            s += i.getTitle() + "%";
        }
        String[] musicList = s.split("%");
        return musicList;
    }

    private String getSong(String songName) {
        for (Song song : library) {
            if (song.getTitle().equals(songName)) {
                return song.getPath();
            }
        }
        return "-1";
    }

    private int getCurrentSongIndex() {
        int index = 0;
        for (Song i : library) {
            if (i.getPath().equals(currentSong)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private class ListenForSelection implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            // TODO Auto-generated method stub
            JComboBox<?> g = (JComboBox<?>) event.getSource();
            String selectedSong = (String) g.getSelectedItem();
            String songToPlay = getSong(selectedSong);
            changeTitle(selectedSong);
            System.out.println("Changed song to: " + selectedSong);
        }

    }

    private class ListenForControl implements ActionListener {

        String currentAction;

        public ListenForControl(String string) {
            currentAction = string;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub

            switch (currentAction) {
                case "Play": {
                    //Play song or Resume
                    System.out.println("Playing Song");
                    //If There is no current song then pick first song
                    if (currentSong.equals("")) {
                        currentSong = library.get(0).getPath();
                        changeTitle(library.get(0).getTitle());
                    } else {
                        //else resume song
                        System.out.println("resuming");
                        media[0].play();
                    }
                    displayTime();
                    break;
                }
                case "Pause": {
                    //Pause Song
                    media[0].pause();
                    break;
                }
                case "Next": {
                    //forward song, in library
                    int currentIndex = getCurrentSongIndex();
                    if (currentIndex == library.size() - 1) {
                        currentSong = library.get(0).getPath();
                        changeTitle(library.get(0).getTitle());
                        frame.repaint();

                    } else {
                        currentSong = library.get(currentIndex + 1).getPath();
                        changeTitle(library.get(currentIndex + 1).getTitle());
                        frame.repaint();
                    }
                    break;
                }
                case "Back": {
                    //play previous song.
                    int currentIndex = getCurrentSongIndex();
                    if (currentIndex == 0) {
                        currentSong = library.get(library.size() - 1).getPath();
                        changeTitle(library.get(library.size() - 1).getTitle());
                        frame.repaint();
                    } else {
                        currentSong = library.get(currentIndex - 1).getPath();
                        changeTitle(library.get(currentIndex - 1).getTitle());
                        frame.repaint();
                    }
                    break;
                }
            }
        }

    }

    
    public void displayTime() {
        media[0].currentTimeProperty().addListener(ov -> {
            if (!song_duration.getValueIsAdjusting()) {
                currentLength = media[0].getCurrentTime().toMillis();
                totalLength = media[0].getTotalDuration().toMillis();

                song_duration.setValue((int) ((100 / totalLength) * currentLength));
                lbtime.setText(getDisplayDuration());
            }

        });
        song_duration.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (song_duration.getValueIsAdjusting()) {
                    JSlider cb = (JSlider) e.getSource();
                    double sliderValue = cb.getValue();
                    sliderValue/=100;
                    double current_time = media[0].getCurrentTime().toSeconds();
                    double end_time = media[0].getStopTime().toSeconds();
                    System.out.println(current_time + " - " + end_time);
                    media[0].seek(media[0].getStopTime().seconds((sliderValue) * end_time));
                   
                    currentLength = media[0].getCurrentTime().toMillis();
                    totalLength = media[0].getTotalDuration().toMillis();
                    lbtime.setText(getDisplayDuration());
                }else{
                    currentLength = media[0].getCurrentTime().toMillis();
                    totalLength = media[0].getTotalDuration().toMillis();
                    lbtime.setText(getDisplayDuration());
                }
            }

        });
        volume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider cb = (JSlider) e.getSource();
                double sliderValue = cb.getValue();
                if (cb.getValueIsAdjusting()) {
                    int fps = (int) cb.getValue();
                    System.out.println("Volume is: " + sliderValue);
                    setVolume = (sliderValue % 100) / 100;
                    System.out.println(setVolume);
                    media[0].setVolume(setVolume);
                }

            }
        });
    }

    public String getDisplayDuration() {
        return getTimeString(currentLength) + "/" + getTimeString(totalLength);
    }

    public static String getTimeString(double millis) {
        millis /= 1000;
        String s = formatTime(millis % 60);
        millis /= 60;
        String m = formatTime(millis % 60);
        millis /= 60;
        String h = formatTime(millis % 24);
        return h + ":" + m + ":" + s;
    }

    public static String formatTime(double time) {
        int t = (int) time;
        if (t > 9) {
            return String.valueOf(t);
        }
        return "0" + t;
    }
}
