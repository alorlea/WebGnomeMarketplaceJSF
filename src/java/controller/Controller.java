package controller;

import model.Account;
import model.Gnome;
import model.IAccount;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.BasketItem;


/**
 *
 * @author Alberto Lorente Leal <albll@kth.se>, <a.lorenteleal@gmail.com
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
@LocalBean
public class Controller
{
   @PersistenceContext(unitName = "SubProject1PU")
   private EntityManager em;


   public boolean alreadyRegistered(String username)
   {
      IAccount found = em.find(Account.class, username);
      if (found == null)
      {
         return false;
      }
      else
      {
         return true;
      }
   }


   public void createAccount(String username, String password)
   {
      Account newAcct = new Account(username, password);
      em.persist(newAcct);
   }


   public Account findAccount(String username)
   {
      try
      {
         Account newAccount = em.find(Account.class, username);
         return newAccount;
      }
      catch (Exception e)
      {
         return null;
      }
   }


   public Gnome findGnome(String gnomename)
   {
      try
      {
         Gnome ret = em.find(Gnome.class, gnomename);
         return ret;
      }
      catch (Exception e)
      {
         return null;
      }
   }


   public List<Gnome> obtainGnomeList()
   {
      Query query = em.createQuery("SELECT g FROM Gnome g");
      List<Gnome> ret = query.getResultList();

      if (ret == null)
      {
         return new ArrayList<Gnome>();
      }
      else
      {
         return ret;
      }
   }


   public void createBasketItem(String username, String gnomename, int units)
   {
      Account acc = findAccount(username);
      Gnome g = findGnome(gnomename);

      BasketItem bi = new BasketItem(acc, g, units);
      em.persist(bi);
   }


   public Gnome updateGnomeStock(String gnomename, int selectedUnits)
   {
      Gnome g = findGnome(gnomename);
      int newUnits = g.getStock() + selectedUnits;

      if (newUnits < 0)
      {
         return null;
      }
      else
      {
         g.setStock(newUnits);
         em.merge(g);
         return g;
      }
   }


   public BasketItem findBasketItem(String username, String gnomename)
   {
      Query result = em.createQuery("SELECT x FROM BasketItem x WHERE x.owner.username = '" + username + "' and x.gnome.name = '" + gnomename + "'");
      try
      {
         BasketItem ret = (BasketItem) result.getSingleResult();
         return ret;
      }
      catch (NoResultException e)
      {
         return null;
      }
   }


   public void updateBasketItem(BasketItem bi)
   {
      em.merge(bi);
   }


   public List<BasketItem> obtainBasketItemList(String username)
   {
      Query query = em.createQuery("SELECT x FROM BasketItem x WHERE x.owner.username = '" + username + "'");
      List<BasketItem> ret = query.getResultList();

      if (ret == null)
      {
         return new ArrayList<BasketItem>();
      }
      else
      {
         return ret;
      }
   }


   public void deleteItemsFromDataBase(String username)
   {
      Query query = em.createQuery("DELETE FROM BasketItem x WHERE x.owner.username = '" + username + "'");
      query.executeUpdate();
   }


   public BasketItem updateBasketItemAmount(String username, String gnomename, int selectedUnits)
   {
      BasketItem i = findBasketItem(username, gnomename);
      int newUnits = i.getAmount() + selectedUnits;

      if (newUnits < 0)
      {
         return null;
      }
      else
      {
         i.setAmount(newUnits);
         em.merge(i);
         return i;
      }
   }


   public List<Account> obtainAccountList()
   {
      Query query = em.createQuery("SELECT x FROM Account x");
      List<Account> ret = query.getResultList();

      if (ret == null)
      {
         return new ArrayList<Account>();
      }
      else
      {
         return ret;
      }
   }


   public void updateAccount(Account acc)
   {
      em.merge(acc);
   }


   public void updateGnome(Gnome g)
   {
      em.merge(g);

      Query q = em.createQuery("SELECT x FROM BasketItem x WHERE x.gnome.name = '" + g.getName() + "'");
      List<BasketItem> bItems = q.getResultList();
      for (BasketItem i : bItems)
      {
         i.calculateSubTotal();
      }
   }


   public Gnome createGnome(String name, float price, int units)
   {
      Gnome newGnome = new Gnome(name, units, price);
      em.persist(newGnome);

      return newGnome;
   }


   public void removeGnome(Gnome g)
   {
      Query q = em.createQuery("SELECT x FROM BasketItem x WHERE x.gnome.name = '" + g.getName() + "'");
      List<BasketItem> bItems = q.getResultList();
      for (BasketItem i : bItems)
      {
         em.remove(i);
      }

      q = em.createQuery("DELETE FROM Gnome x WHERE x.name = '" + g.getName() + "'");
      q.executeUpdate();
   }
}
