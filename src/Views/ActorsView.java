package Views;

import Models.Entities.Actor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Collection;

public class ActorsView extends JFrame {
    private JPanel mainPanel;
    private JList filmList;
    private JTextField tf_surname;
    private JTextField tf_name;
    private JTextField tf_patronymic;
    private JSpinner spin_age;
    private JComboBox cb_nationality;
    private JLabel label_surname;
    private JLabel label_name;
    private JLabel label_patronymic;
    private JLabel label_age;
    private JLabel label_nationality;

    public ActorsView() {

    };

    public ActorsView(Collection<Actor> actors) {

        super("Actors");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        filmList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                var obj = filmList.getSelectedValue();
                Actor actor = Actor.class.cast(obj);

                tf_surname.setText(actor.getSurname());
                tf_name.setText(actor.getName());
                tf_patronymic.setText(actor.getPatronymic());
                spin_age.setValue(actor.getAge());
                cb_nationality.setSelectedItem(actor.getNationality());
            }
        });

        String[] countries = {"Russia", "USA", "Great Britain", "China", "France", "Germany"};
        DefaultComboBoxModel cbModel = new DefaultComboBoxModel<String>(countries);
        cb_nationality.setModel(cbModel);

        DefaultListModel<Actor> dlm = new DefaultListModel<Actor>();

        for (Actor actor : actors)
            dlm.add(0, actor);

        filmList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        filmList.setModel(dlm);

        setContentPane(mainPanel);
        setSize(600, 400);
        setVisible(true);

    }

}
