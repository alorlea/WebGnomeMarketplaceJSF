WebGnomeMarketplaceJSF
======================

This is the code for a web based marketplace in JSF for a shop handling plastic gnomes. User can register, update the cart and admins can add and delete new types of gnomes. The web design is very simple and the application was done following the MVC pattern. We put into practice all the things we learned in the course (JPA, JSF, java entities). This work was done by me and my colleague Fernando Garcia Sanjuan for the course in KTH.

If you are getting problems to making the project run, you need to check the persistence unit file that the project has.

By default it is pointing to a database connector that we configured for our local machine, if you do not change it; it will try to look for it and crash.

What you need to do, in the case that you are using netbeans and running a glassfish server is to:
-Go to the persistence.xml file in the web application 
-change the database connector labeled to "jdbc/APGMarketplace1" to another connector you have

If you have glassfish, you can select a default connection called "jdbc/sample" indicate the username and password to connect to the DB and should work.

Once you have it running, you can create a user account easily and to access the admin section use "Admin" as user and password "admin"
