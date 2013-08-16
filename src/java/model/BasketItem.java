/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 *
 * @author Fernando Garcia Sanjuan, <fgs@kth.se>, <fdosanjuan@gmail.com>
 */
@Entity
public class BasketItem
   implements Serializable, IBasketItem
{
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Integer id;
   @ManyToOne
   private Account owner;
   @ManyToOne
   private Gnome gnome;
   private int amount;
   private float subTotal;


   public BasketItem()
   {
   }


   public BasketItem(Account owner, Gnome gnome, int amount)
   {
      this.owner = owner;
      this.gnome = gnome;
      this.amount = amount;
      calculateSubTotal();
   }
   
   
   public Integer getId()
   {
      return id;
   }


   public void setId(Integer id)
   {
      this.id = id;
   }


   @Override
   public int hashCode()
   {
      int hash = 0;
      hash += (id != null ? id.hashCode() : 0);
      return hash;
   }


   @Override
   public boolean equals(Object object)
   {
      if (!(object instanceof BasketItem))
      {
         return false;
      }
      BasketItem other = (BasketItem) object;
      if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
      {
         return false;
      }
      return true;
   }


   @Override
   public String toString()
   {
      return "model.BasketItem[ id=" + id + " ]";
   }


   @Override
   public Account getOwner()
   {
      return owner;
   }


   @Override
   public Gnome getGnome()
   {
      return gnome;
   }


   @Override
   public int getAmount()
   {
      return amount;
   }


   @Override
   public float getSubTotal()
   {
      return subTotal;
   }


   public void setAmount(int amount)
   {
      this.amount = amount;
      
      calculateSubTotal();
   }


//   public void setSubTotal(float subTotal)
//   {
//      this.subTotal = subTotal;
//   }


   public void calculateSubTotal()
   {
      subTotal = amount * gnome.getPrice();
   }


}
