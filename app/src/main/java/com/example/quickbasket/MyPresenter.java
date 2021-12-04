package com.example.quickbasket;

public class MyPresenter implements Contract.Presenter{
    private Contract.Model model;
    private Contract.View view;

    public MyPresenter(Contract.Model model, Contract.View view){
        this.model = model;
        this.view = view;
    }

    public void checkCustomerUsername(){
        String username = view.getUsername();
        if(username.equals(""))
            view.displayMessage("Username cannot be empty");
        else if(model.customerExists(username))
            view.displayMessage("user found");
        else
            view.displayMessage("user not found");
    }

    public void checkOwnerUsername(){
        String username = view.getUsername();
        if(username.equals(""))
            view.displayMessage("Username cannot be empty");
        else if(model.ownerExists(username))
            view.displayMessage("user found");
        else
            view.displayMessage("user not found");
    }

    public void checkCustomerPassword(){
        String username = view.getUsername();
        String password = view.getPassword();
        if (password.equals(""))
            view.displayMessage2("Password cannot be empty");
        else if (!model.customerPasswordMatches(username, password))
            view.displayMessage2("Wrong password");
    }

    public void checkOwnerPassword(){
        String username = view.getUsername();
        String password = view.getPassword();
        if (password.equals(""))
            view.displayMessage2("Password cannot be empty");
        else if (!model.ownerPasswordMatches(username, password))
            view.displayMessage2("Wrong password");
    }
}
