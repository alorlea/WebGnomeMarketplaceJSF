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



/**
 *
 * @author fer
 */
@Entity
public class Gnome implements Serializable, IGnome
{
   private static final long serialVersionUID = 1L;
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Id
   private String name;
   private int stock;
   private float price;


   public Gnome()
   {
   }


   public Gnome(String name, int stock, float price)
   {
      this.name = name;
      this.stock = stock;
      this.price = price;
   }

   
   
   

   @Override
   public String getName()
   {
      return name;
   }


   public void setName(String name)
   {
      this.name = name;
   }


   @Override
   public float getPrice()
   {
      return price;
   }


   public void setPrice(float price)
   {
      this.price = price;
   }


   @Override
   public int getStock()
   {
      return stock;
   }


   public void setStock(int stock)
   {
      this.stock = stock;
   }


   @Override
   public int hashCode()
   {
      return name.hashCode();
   }


   @Override
   public boolean equals(Object object)
   {
      if (!(object instanceof Gnome))
      {
         return false;
      }
      Gnome other = (Gnome) object;
      if (name.equals(other.getName()))
      {
         return true;
      }
      return false;
   }


   @Override
   public String toString()
   {
      return "SubProject1.model.Gnome[ name=" + name + " ]";
   }
   
}
