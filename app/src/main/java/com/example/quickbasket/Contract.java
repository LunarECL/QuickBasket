package com.example.quickbasket;

public interface Contract {
    public interface Model{
        public boolean customerExists(String username);
        public boolean ownerExists(String username);
        public boolean customerPasswordMatches(String username, String password);
        public boolean ownerPasswordMatches(String username, String password);
    }

    public interface View{
        public String getUsername();
        public String getPassword();
        public void displayMessage(String message);

    }

    public interface Presenter{
        public void checkCustomerUsername();
        public void checkOwnerUsername();
        public void checkCustomerPassword();
        public void checkOwnerPassword();
    }
}
