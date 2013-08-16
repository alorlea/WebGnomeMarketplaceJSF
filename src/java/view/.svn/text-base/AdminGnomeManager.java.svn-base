/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import model.Gnome;


/**
 *
 * @author fer
 */
@Named(value = "adminGnomeManager")
@ConversationScoped
public class AdminGnomeManager
   implements Serializable
{
   private static final long serialVersionUID = 16247164405L;
   @EJB
   private Controller controller;
   private List<Gnome> gnomes;
   private Gnome selectedGnome;
   private HtmlDataTable table;
   private String selectedName;
   private int selectedIndex;
   private Integer selectedUnits = 0;
   private Float selectedPrice = 0f;
   private boolean availableMessages = false;
   private String message;
   @Inject
   private Conversation conversation;


   public List<Gnome> getGnomes()
   {
      gnomes = controller.obtainGnomeList();
      return gnomes;
   }


   public void setSelectedPrice(Float selectedPrice)
   {
      if (selectedPrice == null)
         this.selectedPrice = 0f;
      else
         this.selectedPrice = selectedPrice;
   }


   public Float getSelectedPrice()
   {
      return selectedPrice;
   }


   public HtmlDataTable getTable()
   {
      return table;
   }


   public void setTable(HtmlDataTable table)
   {
      this.table = table;
   }


   public boolean getAvailableMessages()
   {
      return availableMessages;
   }


   public String getMessage()
   {
      return message;
   }
   
   
   


   public String getSelectedName()
   {
      return selectedName;
   }


   public void setSelectedName(String selectedName)
   {
      this.selectedName = selectedName;
   }


   public Integer getSelectedUnits()
   {
      return selectedUnits;
   }


   public void setSelectedUnits(Integer selectedUnits)
   {
      if (selectedUnits == null)
         this.selectedUnits = 0;
      else
         this.selectedUnits = selectedUnits;
   }


   public void selectGnome()
   {
      selectedIndex = table.getRowIndex();
      selectedGnome = (Gnome) table.getRowData();
      this.selectedName = selectedGnome.getName();
      selectedPrice = selectedGnome.getPrice();
      selectedUnits = selectedGnome.getStock();
      
      availableMessages = false;
   }


   public void modifyGnome()
   {
      if (selectedGnome == null)
      {
         showMessage("You must select a gnome to modify.");
         return;
      }
      
      if (selectedPrice < 0)
      {
         showMessage("The price cannot be negative.");
         return;
      }
      
      if (selectedUnits < 0)
      {
         showMessage("The selected units must be a positive integer number.");
         return;
      }
      
      selectedGnome.setPrice(selectedPrice);
      selectedGnome.setStock(selectedUnits);
      controller.updateGnome(selectedGnome);
      
      showMessage("Gnome modified.");
   }


   public void addGnome()
   {
      if (selectedName.isEmpty())
      {
         showMessage("The gnome must have a name.");
         return;
      }
      
      if (selectedPrice < 0)
      {
         showMessage("The price cannot be negative.");
         return;
      }

      if (selectedUnits < 0)
      {
         showMessage("The selected units must be a positive integer number.");
         return;
      }
      
      Gnome g = controller.findGnome(selectedName);
      if (g != null)
      {
         showMessage("This gnome already exists.");
         return;
      }
      
      g = controller.createGnome(selectedName, selectedPrice, selectedUnits);
      gnomes.add(g);
      
      selectedGnome = g;
      
      showMessage("Gnome added.");
   }


   public void removeGnome()
   {
      if (selectedGnome == null)
      {
         showMessage("You must select a gnome to remove.");
         return;
      }
      
      controller.removeGnome(selectedGnome);
      gnomes.remove(selectedGnome);
      selectedGnome = null;
      selectedName = "";
      selectedPrice = 0f;
      selectedUnits = 0;
      
      showMessage("Gnome removed.");
   }


   private void showMessage(String msg)
   {
      availableMessages = true;
      message = msg;
   }
}
