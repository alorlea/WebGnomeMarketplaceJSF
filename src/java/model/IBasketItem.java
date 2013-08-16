/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


/**
 *
 * @author fer
 */
public interface IBasketItem
{
   public Account getOwner();
   public Gnome getGnome();
   public int getAmount();
   public float getSubTotal();
}
