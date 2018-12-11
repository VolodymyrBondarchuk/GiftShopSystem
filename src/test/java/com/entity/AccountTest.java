package com.entity;

import com.entity.gift.FruitBasket;
import com.service.AccountService;
import com.service.OrderService;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class AccountTest {


    @Test
    public void createAccount() {


        try{
            AccountService accountService = new AccountService();
            Account account = accountService.create("CreateAccount", "Lviv", "er@er.ua");
            assertNotNull(account.getId());
            assertEquals(account.getId()+"CreateAccount", account.getAccountNumber());
            assertEquals("CreateAccount", account.getCompanyName());
            assertEquals("Lviv", account.getAddress());
            assertEquals("er@er.ua", account.getEmail());

            System.out.println(account.getId());
            System.out.println(account.getCompanyName());
            System.out.println(account.getAddress());
            System.out.println(account.getEmail());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void accountSave() {
        try {
            AccountService accountService = new AccountService();
            Account account = accountService.create("SaveAccount", "London", "ey@ey.ua");

            accountService.save(account);

            File file = new File("C:\\oop-storage\\"+account.getAccountNumber()+".txt");
            assertTrue(file.exists() && !file.isDirectory());

        } catch (Exception e) {
             e.printStackTrace();
    }
    }

    @Test
    public void accountOpen() {
        try {
            AccountService accountService = new AccountService();
            OrderService orderService = new OrderService();
            Account account = accountService.create("OpenAccount", "London", "ey@ey.ua");



            String accountNumber = account.getAccountNumber();

            /*orderService.setAccount(account);
            String orderId = orderService.create("Lviv", "Volodymyr", "11/30/2018",
                    "qwer@qwer.ua", "Hello world", 100);


            FruitBasket gift = new FruitBasket();
            gift.setSize("S");

            orderService.addGift(orderId, gift);*/

            accountService.save(account);

            Account loaded = accountService.open(accountNumber);

            assertNotNull(account.getId());
            assertEquals(account.getId()+"OpenAccount", account.getAccountNumber());
            assertEquals("OpenAccount", account.getCompanyName());
            assertEquals("London", account.getAddress());
            assertEquals("ey@ey.ua", account.getEmail());

            //assertEquals("Hello world", account.getOrders().get(0).getMessage());



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void closeAccount() {
        try {
            AccountService accountService = new AccountService();
            Account account = accountService.create("CloseAccount", "London", "ey@ey.ua");

            String accountNumber = account.getAccountNumber();
            accountService.close(account);

            File file = new File("C:\\oop-storage\\"+accountNumber+".txt");
            assertTrue(file.exists() && !file.isDirectory());

            assertNull(account.getId());
            assertNull(account.getAccountNumber());
            assertNull( account.getCompanyName());
            assertNull( account.getAddress());
            assertNull(account.getEmail());



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteAccount() {
        try {
            AccountService accountService = new AccountService();
            Account account = accountService.create("DeleteAccount", "London", "ey@ey.ua");

            String accountNumber = account.getAccountNumber();

            accountService.save(account);

            accountService.delete(accountNumber);

            File file = new File("C:\\oop-storage\\"+accountNumber+".txt");

            assertFalse(file.exists());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
