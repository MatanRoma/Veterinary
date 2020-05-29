package Controller;

import Model.Medicine;
import Model.MedicineManager;
import Model.TablesModels.MedicineTableModel;
import View.MedicinesView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class MedicineController {
    private MedicineManager model;
    private MedicinesView view;

    public MedicineController(MedicinesView view,MedicineManager model) {
        this.model = model;
        this.view = view;
        view.getTablePanel().setMedicineData(model.getMedicinesAndQuantity());
        view.addMedicineToInventory(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getView().validateFields()) {
                    if (!model.findMedicineId(view.getId())) {
                        Medicine medicine = new Medicine();
                        medicine.setId(view.getId());
                        medicine.setName(view.getName());
                        medicine.setType(view.getType());
                        model.addMedicine(medicine, Integer.parseInt(view.getQuantity()));
                        view.getTablePanel().setMedicineData(model.getMedicinesAndQuantity());
                        view.getTablePanel().refresh();
                        JOptionPane.showMessageDialog(view, medicine.getName() + " Added");
                    } else JOptionPane.showMessageDialog(view, "Medicine Already Exist!");
                } else JOptionPane.showMessageDialog(view, "Fill All Fields!");
            }
        });

        view.deleteMedicineFromInventory(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getTablePanel().getTable().getSelectedRow() != -1) {
                    int row = view.getTablePanel().getTable().getSelectedRow();
                    String id = (String) view.getTablePanel().getTable().getValueAt(row, 0);
                    for (Medicine medicine : model.getMedicinesAndQuantity().keySet()) {
                        if (medicine.getId().equals(id)) {
                            model.removeMedicine(medicine);
                            break;
                        }
                        view.getTablePanel().setMedicineData(model.getMedicinesAndQuantity());
                        view.getTablePanel().refresh();
                    }
                }
            }
        });

        view.updateMedicineInInventory(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getTablePanel().getTable().getSelectedRow() != -1) {
                    for (Medicine medicine : model.getMedicinesAndQuantity().keySet()) {
                        if (medicine.getId().equals(view.getId())) {
                            if (view.getView().validateFields()) {
                                medicine.setId(view.getId());
                                medicine.setName(view.getName());
                                medicine.setType(view.getType());
                                model.addMedicine(medicine, Integer.parseInt(view.getQuantity()));
                                view.getTablePanel().setMedicineData(model.getMedicinesAndQuantity());
                                view.getTablePanel().refresh();
                                JOptionPane.showMessageDialog(view, "Updated!");
                                break;
                            } else JOptionPane.showMessageDialog(view, "Fill All Fields!");
                        }
                    }
                }

            }
        });

        view.addSelectedRowListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MedicineTableModel model = (MedicineTableModel) view.getTablePanel().getTable().getModel();
                int selectedRowIndex = view.getTablePanel().getTable().getSelectedRow();
                view.setId(model.getValueAt(selectedRowIndex, 0).toString());
                view.setName(model.getValueAt(selectedRowIndex, 1).toString());
                view.setType(model.getValueAt(selectedRowIndex, 2).toString());
                view.setQuantity(model.getValueAt(selectedRowIndex, 3).toString());
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

}

