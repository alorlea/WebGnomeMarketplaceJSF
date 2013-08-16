/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Alberto Lorente Leal <albll@kth.se>, <a.lorenteleal@gmail.com
 */
@Entity
public class Account implements Serializable, IAccount {
    private static final long serialVersionUID = 1L;
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Id   
    private String username;
    private String password;
    private boolean banned = false;
   @OneToMany(mappedBy = "owner")
   private List<BasketItem> basketItems;

    public Account(){
        
    }
    
    public Account(String username, String password){
        this.username= username;
        this.password= password;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if (username.equals(other.getUsername()))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "SubProject1.model.Account[ Username" + username + " Password:"+password +" ]";
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
    
    public boolean isBanned()
    {
       return banned;
    }
    
    public void setBanned(boolean value)
    {
       banned = value;
    }
}
