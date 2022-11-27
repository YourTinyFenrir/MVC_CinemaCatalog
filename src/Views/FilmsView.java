package Views;

import Models.Entities.Actor;
import Models.Entities.Film;
import Models.Entities.FilmDirector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Collection;

public class FilmsView extends JFrame {
    private JPanel mainPanel;
    private JList filmList;
    private JSpinner spin_duration;
    private JTextField tf_title;
    private JComboBox cb_filmDirector;
    private JLabel label_title;
    private JLabel label_duration;
    private JLabel label_filmDirector;

    public FilmsView() {

    }

    public FilmsView(Collection<Film> films, Collection<FilmDirector> filmDirectors) {

        super("Films");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        filmList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                var obj = filmList.getSelectedValue();
                Film film = Film.class.cast(obj);

                tf_title.setText(film.getTitle());
                spin_duration.setValue(film.getDuration());
                cb_filmDirector.setSelectedItem(film.getFilmDirector());
            }
        });

        DefaultComboBoxModel cbModel = new DefaultComboBoxModel<FilmDirector>();
        cbModel.addAll(filmDirectors);
        cb_filmDirector.setModel(cbModel);

        DefaultListModel<Film> dlm = new DefaultListModel<Film>();

        for (Film film : films)
            dlm.add(0, film);

        filmList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        filmList.setModel(dlm);

        setContentPane(mainPanel);
        setSize(600, 400);
        setVisible(true);
    }
}
