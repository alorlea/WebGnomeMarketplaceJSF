package view;

import controller.Controller;
import java.security.NoSuchAlgorithmException;
import model.IAccount;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;
import model.Account;


/**
 *
 * @author alberto
 */
@Named(value = "accountManager")
@ConversationScoped
public class AccountManager
   implements Serializable
{
   private static final long serialVersionUID = 16247164405L;
   @EJB
   private Controller controller;
   private Account currentAccount;
   private String username;
   private String password;
   List<Account> accounts;
   private HtmlDataTable table;
   @Inject
   private Conversation conversation;
   private int selectedIndex;
   private Account selectedUser;
   private boolean availableMessages = false;
   private String message;


   private void startConversation()
   {
      if (conversation.isTransient())
      {
         conversation.begin();
      }
   }


   private void stopConversation()
   {
      if (!conversation.isTransient())
      {
         conversation.end();
      }
   }


   
   public boolean getAvailableMessages()
   {
      return availableMessages;
   }


   public String getMessage()
   {
      return message;
   }


   public List<Account> getAccounts()
   {
      accounts = controller.obtainAccountList();
      
      Account admin = null;
      for (Account a : accounts)
      {
         if (a.getUsername().equals("Admin"))
         {
            admin = a;
            break;
         }
      }
      
      if (admin != null)
         accounts.remove(admin);
      
      return accounts;
   }
   
   public HtmlDataTable getTable()
   {
      return table;
   }


   public void setTable(HtmlDataTable table)
   {
      this.table = table;
   }
   
   


   /**
    * Method for setting the username parameter of the Account Manager
    * bean
    * @param username 
    */
   public void setUsername(String username)
   {
      this.username = username;
   }


   /**
    * Method for getting the username parameter of the Account Manager
    */
   public String getUsername()
   {
      return username;
   }


   /**
    * Method for setting the password parameter of the Account Manager
    * bean
    * @param password 
    */
   public void setPassword(String password)
   {
      this.password = password;
   }


   /**
    * Method for getting the password parameter of the Account Manager
    */
   public String getPassword()
   {
      return password;
   }



   public IAccount getCurrentAccount()
   {
      return currentAccount;
   }


   public void register()
   {
      startConversation();
      
      if (username.isEmpty())
      {
         showMessage("You cannot be registered if you do not provide a user name.");
      }
      else if (username.equals("Admin") || controller.alreadyRegistered(username))
      {
         showMessage("This user name is already taken. Choose another one.");
      }
      else if (password.isEmpty())
      {
         showMessage("You must choose a password.");
      }
      else
      {
         controller.createAccount(username, getMD5sum(password));
         showMessage("You have been successfully registered. Now you can log in.");
      }
   }


   public String login()
   {      
      String nextPage = "welcome";

      startConversation();

      addAdmin();

      Account account = controller.findAccount(username);
      
      if (account == null)
      {
         showMessage("You are not registered in the system.");
      }
      else if (account.isBanned())
      {
         showMessage("You have been banned. You cannot enter into the system.");
      }
      else if (account.getPassword().equals(getMD5sum(password)) == false)
      {
         showMessage("The password is incorrect.");
      }
      else
      {
         if (account.getUsername().equals("Admin"))
         {
            nextPage = "administration";
         }
         else
         {
            nextPage = ("marketplace");
         }

         currentAccount = account;
         availableMessages = false;
      }

      return nextPage;
   }


   public String logout()
   {
      currentAccount = null;
      password = null;
      username = null;
      stopConversation();
      startConversation();
      return "welcome";
   }


   
   
   public void banOrAllowUser()
   {
      selectedIndex = table.getRowIndex();
      selectedUser = (Account) table.getRowData();
      
      boolean value = !selectedUser.isBanned();
      selectedUser.setBanned(value);
      controller.updateAccount(selectedUser);
   }


   private void addAdmin()
   {
      Account adminAcc = controller.findAccount("Admin");
      if (adminAcc == null)
         controller.createAccount("Admin", getMD5sum("admin"));
   }


   


   private String getMD5sum(String str)
   {
      try
      {
         MessageDigest algorithm = MessageDigest.getInstance("MD5");
         algorithm.reset();
         algorithm.update(str.getBytes());
         byte[] aux = algorithm.digest();
         
         return (new String(aux));
      }
      catch (NoSuchAlgorithmException ex)
      {
         // Never reached because the algorithm "MD5" is correct.
         return null;
      }

   }
   
   private void showMessage(String msg)
   {
      availableMessages = true;
      message = msg;
   }
}
