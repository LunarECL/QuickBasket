package com.example.quickbasket;

public interface Contract {
    public interface Model{
        public boolean userExists(String username);
    }

    public interface View{
        public String getUsername();
        public void displayMessage(String message);

    }

    public interface Presenter{
        public void checkUsername();
    }
}
