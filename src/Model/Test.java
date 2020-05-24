package Model;

import Controller.AppointmentsController;
import Controller.MedicineController;
import Controller.PersonsController;
import Exceptions.PersonAlreadyExistException;
import View.Dialogs.LoginDialog;
import View.VeterinaryMainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws PersonAlreadyExistException {
        VeterinaryMainView frame = new VeterinaryMainView("Veti");
        frame.setVisible(false);
        MedicineManager medicineManager = MedicineManager.singletonMedicineManager();
        PersonManager personManager = PersonManager.singletonPersonManager();
        AppointmentManager appointmentManager = AppointmentManager.singletonAppointmentManager();

        CustomersAppointmentsModelView model = new CustomersAppointmentsModelView(personManager, appointmentManager);

        PersonsController controller = new PersonsController(frame.getPersonsView(), personManager);
        MedicineController medicineController = new MedicineController(frame.getMedicineView(), medicineManager);
        AppointmentsController appointmentsController = new AppointmentsController(frame.getAppointmentsView(), model);

        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        char [] pass = {'1','3','2','4'};
        LoginDialog loginDialog = new LoginDialog("Login");
        loginDialog.addSignInListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(loginDialog.getVet_rb().isSelected()){
                    if(Arrays.equals(loginDialog.getPassword_pf().getPassword(), pass) &&loginDialog.getUsername_tf().getText().equals("custo")){
                        JOptionPane.showMessageDialog(loginDialog,"Success!");
                        frame.setLocationRelativeTo(null);
                        loginDialog.dispose();
                        frame.setVisible(true);
                    }else JOptionPane.showMessageDialog(loginDialog,"Wrong Username or Password");
                }
            }
        });
    }
}
