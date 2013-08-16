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
import model.BasketItem;
import model.IBasketItem;


/**
 *
 * @author fer
 */
@Named(value = "basketManager")
@ConversationScoped
public class BasketManager
   implements Serializable
{
   private static final long serialVersionUID = 16247164405L;
   @EJB
   private Controller controller;
   private List<BasketItem> items;
   private BasketItem selectedItem;
   private HtmlDataTable table;
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


   public List<BasketItem> obtainItems(String username)
   {
      items = controller.obtainBasketItemList(username);
      return items;
   }


   public HtmlDataTable getTable()
   {
      return table;
   }


   public void setTable(HtmlDataTable table)
   {
      this.table = table;
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


   public void selectItem()
   {
      selectedIndex = table.getRowIndex();
      selectedItem = (BasketItem)table.getRowData();
   }
   
   
   public BasketItem getSelectedItem()
   {
      return selectedItem;
   }
   
   
   public float getTotal()
   {
      float total = 0;
      for (IBasketItem i : items)
      {
         total += i.getSubTotal();
      }
      return total;
   }
   
   
   public void pay(String username)
   {
      controller.deleteItemsFromDataBase(username);
      items.clear();
      
      showMessage("You have completed your purchase.");
      
      selectedItem = null;
      selectedUnits = 0;
   }

   public void removeUnits(String username)
   {
      if (selectedUnits <= 0)
      {
         showMessage("You must select a positive integer number of units to remove.");
         return;
      }
      
      if (selectedItem == null)
      {
         showMessage("You must select an item to remove.");
         return;
      }

      selectedItem = controller.updateBasketItemAmount(username, selectedItem.getGnome().getName(), -selectedUnits);
      if (selectedItem == null)
      {
         showMessage("You cannot remove more units than you have.");
         return;
      }
      items.set(selectedIndex, selectedItem);

      controller.updateGnomeStock(selectedItem.getGnome().getName(), selectedUnits);
      showMessage("You have successfully removed some items.");
   }
   
   
   private void showMessage(String msg)
   {
      availableMessages = true;
      message = msg;
   }
}
