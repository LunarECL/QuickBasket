package com.example.quickbasket;

import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Mock
    LoginCustomerPage viewCustomer;

    @Mock
    LoginStoreOwnerPage viewOwner;

    @Mock
    MyModel model;

    @Test
    public void testPresenter(){
        when(viewCustomer.getUsername()).thenReturn("leah");
        when(model.customerExists("leah")).thenReturn(true);

        MyPresenter presenter = new MyPresenter(model, viewCustomer);
        presenter.checkCustomerUsername();

        verify(viewCustomer).displayMessage("user found");
    }

    @Test
    public void testPresenter1(){
        when(viewCustomer.getUsername()).thenReturn("trisha");
        when(model.customerExists("trisha")).thenReturn(false);
        MyPresenter presenter = new MyPresenter(model, viewCustomer);
        presenter.checkCustomerUsername();
        verify(viewCustomer).displayMessage("user not found");
    }

    @Test
    public void testPresenter2(){
        when(viewCustomer.getUsername()).thenReturn("");
        MyPresenter presenter = new MyPresenter(model, viewCustomer);
        presenter.checkCustomerUsername();
        verify(viewCustomer).displayMessage("Username cannot be empty");
    }

    @Test
    public void testPresenter3(){
        when(viewCustomer.getUsername()).thenReturn("ryan");
        when(viewCustomer.getPassword()).thenReturn("");
        MyPresenter presenter = new MyPresenter(model, viewCustomer);
        presenter.checkCustomerPassword();
        verify(viewCustomer).displayMessage2("Password cannot be empty");
    }

    @Test
    public void testPresenter4(){
        when(viewCustomer.getUsername()).thenReturn("ryan");
        when(viewCustomer.getPassword()).thenReturn("123");
        when(model.customerPasswordMatches("ryan", "123")).thenReturn(false);
        MyPresenter presenter = new MyPresenter(model, viewCustomer);
        presenter.checkCustomerPassword();
        verify(viewCustomer).displayMessage2("Wrong password");
    }

    @Test
    public void testPresenter5(){
        when(viewOwner.getUsername()).thenReturn("harry");
        when(model.ownerExists("harry")).thenReturn(true);

        MyPresenter presenter = new MyPresenter(model, viewOwner);
        presenter.checkOwnerUsername();

        verify(viewOwner).displayMessage("user found");
    }

    @Test
    public void testPresenter6(){
        when(viewOwner.getUsername()).thenReturn("harry");
        when(model.ownerExists("harry")).thenReturn(false);

        MyPresenter presenter = new MyPresenter(model, viewOwner);
        presenter.checkOwnerUsername();

        verify(viewOwner).displayMessage("user not found");
    }

    @Test
    public void testPresenter7(){
        when(viewOwner.getUsername()).thenReturn("");

        MyPresenter presenter = new MyPresenter(model, viewOwner);
        presenter.checkOwnerUsername();

        verify(viewOwner).displayMessage("Username cannot be empty");
    }

    @Test
    public void testPresenter8(){
        when(viewOwner.getUsername()).thenReturn("dean");
        when(viewOwner.getPassword()).thenReturn("");
        MyPresenter presenter = new MyPresenter(model, viewOwner);
        presenter.checkOwnerPassword();
        verify(viewOwner).displayMessage2("Password cannot be empty");
    }

    @Test
    public void testPresenter9(){
        when(viewOwner.getUsername()).thenReturn("dean");
        when(viewOwner.getPassword()).thenReturn("123");
        when(model.ownerPasswordMatches("dean", "123")).thenReturn(false);
        MyPresenter presenter = new MyPresenter(model, viewOwner);
        presenter.checkOwnerPassword();
        verify(viewOwner).displayMessage2("Wrong password");
    }
}