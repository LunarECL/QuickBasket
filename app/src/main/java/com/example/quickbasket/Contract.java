package com.example.quickbasket;

public interface Contract {
    public interface Mode{
        public boolean userExists(String username);
    }

    public interface View{
        //public String getName();
        public String getUsername();
        public String getPassword();

    }

    public interface Presenter{
        public void checkUsername();
    }
}
