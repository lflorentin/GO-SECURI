/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Guiform;

/**
 *
 * @author florentinlucas
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class CheckBoxList extends JList { 

   public CheckBoxList() {
      super();

      setModel(new DefaultListModel());
      setCellRenderer(new CheckboxCellRenderer());

      addMouseListener(new MouseAdapter() {
                          @Override
                          public void mousePressed(MouseEvent e) {
                             int index = locationToIndex(e.getPoint());

                             if (index != -1) {
                                Object obj = getModel().getElementAt(index);
                                if (obj instanceof JCheckBox) {
                                   JCheckBox checkbox = (JCheckBox) obj;

                                   checkbox.setSelected(!checkbox.isSelected());
                                   repaint();
                                }
                             }
                          }
                       }

      );

      setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   }

   @SuppressWarnings("unchecked")
   public int[] getCheckedIndexes() {
      java.util.List list = new java.util.ArrayList();
      DefaultListModel dlm = (DefaultListModel) getModel();
      for (int i = 0; i < dlm.size(); ++i) {
         Object obj = getModel().getElementAt(i);
         if (obj instanceof JCheckBox) {
            JCheckBox checkbox = (JCheckBox) obj;
            if (checkbox.isSelected()) {
               list.add(new Integer(i));
            }
         }
      }

      int[] indexes = new int[list.size()];

      for (int i = 0; i < list.size(); ++i) {
         indexes[i] = ((Integer) list.get(i)).intValue();
      }

      return indexes;
   }

   @SuppressWarnings("unchecked")
   public java.util.List getCheckedItems() {
      java.util.List list = new java.util.ArrayList();
      DefaultListModel dlm = (DefaultListModel) getModel();
      for (int i = 0; i < dlm.size(); ++i) {
         Object obj = getModel().getElementAt(i);
         if (obj instanceof JCheckBox) {
            JCheckBox checkbox = (JCheckBox) obj;
            if (checkbox.isSelected()) {
               list.add(checkbox);
            }
         }
      }
      return list;
   }
}

    
    

