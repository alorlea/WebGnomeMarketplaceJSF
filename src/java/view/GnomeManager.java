/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import model.Gnome;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import model.BasketItem;


/**
 *
 * @author Fernando Garcia Sanjuan, <fgs@kth.se>, <fdosanjuan@gmail.com>
 */
@Named(value = "gnomeManager")
@ConversationScoped
public class GnomeManager
   implements Serializable
{
   private static final long serialVersionUID = 16247164405L;
   @EJB
   private Controller controller;
   private List<Gnome> gnomes;
   private Gnome selectedGnome = null;
   private HtmlDataTable table;
   private String selectedName = "";
   private int selectedIndex;
   private Integer selectedUnits = 0;
   private boolean availableMessages = false;
   private String message;
   @Inject
   private Conversation conversation;
   
   
   
    public boolean getAvailableMessages()
   {
      return availableMessages;
   }


   public String getMessage()
   {
      return message;
   }


   public List<Gnome> getGnomes()
   {
      gnomes = controller.obtainGnomeList();
      return gnomes;
   }
   
   
   public HtmlDataTable getTable()
   {
      return table;
   }


   public void setTable(HtmlDataTable table)
   {
      this.table = table;
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
      selectedGnome = (Gnome)table.getRowData();
      this.selectedName = selectedGnome.getName();
   }
   
   
   public void addToBasket(String username)
   {
      if (selectedUnits <= 0)
      {
         showMessage("You must select a positive integer of units to buy.");
         return;
      }
      
      if (selectedGnome == null)
      {
         showMessage("You must select a gnome first.");
         return;
      }
      
      selectedGnome = controller.updateGnomeStock(selectedName, -selectedUnits);
      if (selectedGnome == null)
      {
         showMessage("You cannot buy more units than available.");
         return;
      }
      
      BasketItem existingBasketItem = controller.findBasketItem(username, selectedName);
      if (existingBasketItem == null)
         controller.createBasketItem(username, selectedName, selectedUnits);
      else
      {
         existingBasketItem.setAmount(existingBasketItem.getAmount() + selectedUnits);
         controller.updateBasketItem(existingBasketItem);
      }
      
      gnomes.set(selectedIndex, selectedGnome);
      
      showMessage("The gnomes have been added to your basket.");
      
      selectedGnome = null;
      selectedUnits = 0;
      selectedName = "";
   }


    private void showMessage(String msg)
   {
      availableMessages = true;
      message = msg;
   }
}
