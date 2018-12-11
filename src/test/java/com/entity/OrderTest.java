package com.entity;

import com.entity.gift.FruitBasket;
import com.entity.gift.Gift;
import com.entity.gift.StuffedAnimal;
import com.exceptions.IllegalOrderSizeException;
import com.exceptions.InvalidDeliveryDateException;
import com.service.AccountService;
import com.service.OrderService;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class OrderTest {


    @Test
    public void createOrder() {


        try{
            AccountService accountService = new AccountService();
            OrderService orderService = new OrderService();

            Account account = accountService.create("CreateOrder", "Lviv", "er@er.ua");

            assertNotNull(account.getId());
            assertEquals(account.getId()+"CreateOrder", account.getAccountNumber());
            assertEquals("CreateOrder", account.getCompanyName());
            assertEquals("Lviv", account.getAddress());
            assertEquals("er@er.ua", account.getEmail());

            orderService.setAccount(account);
            String orderId = orderService.create("Lviv", "Volodymyr", "11/30/2018",
                    "qwer@qwer.ua", "Hello world", 100);

            Order order = orderService.getById(orderId);

            assertNotNull(order);
            assertEquals("Lviv", order.getAddress());
            assertEquals("Volodymyr", order.getConsumerName());
            assertEquals("11/30/2018", order.getDeliveryDate());
            assertEquals("qwer@qwer.ua", order.getEmail());
            assertEquals("Hello world", order.getMessage());
            assertEquals(100.0, order.getPrice());
            assertEquals(orderId, order.getId());



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void editOrder() {
        try {
            AccountService accountService = new AccountService();
            OrderService orderService = new OrderService();

            Account account = accountService.create("EditOrder", "Lviv", "er@er.ua");

            assertNotNull(account.getId());
            assertEquals(account.getId()+"EditOrder", account.getAccountNumber());
            assertEquals("EditOrder", account.getCompanyName());
            assertEquals("Lviv", account.getAddress());
            assertEquals("er@er.ua", account.getEmail());

            orderService.setAccount(account);
            String orderId = orderService.create("Lviv", "Volodymyr", "11/30/2018",
                    "qwer@qwer.ua", "Hello world", 100);


            Order order = orderService.getById(orderId);
            order.setMessage("Chnaged msg");
            order.setAddress("London");

            String ordId = orderService.edit(orderId, order);
            Order cOrder = orderService.getById(ordId);

            assertNotNull(order);
            assertEquals("London", cOrder.getAddress());
            assertEquals("Volodymyr", cOrder.getConsumerName());
            assertEquals("11/30/2018", cOrder.getDeliveryDate());
            assertEquals("qwer@qwer.ua", cOrder.getEmail());
            assertEquals("Chnaged msg", cOrder.getMessage());
            assertEquals(100.0, cOrder.getPrice());
            assertEquals(ordId, cOrder.getId());

        } catch (Exception e) {
             e.printStackTrace();
    }
    }

    @Test
    public void addEditRemoveGift() {
        try {
            AccountService accountService = new AccountService();
            OrderService orderService = new OrderService();

            Account account = accountService.create("EditOrder", "Lviv", "er@er.ua");

            assertNotNull(account.getId());
            assertEquals(account.getId()+"EditOrder", account.getAccountNumber());
            assertEquals("EditOrder", account.getCompanyName());
            assertEquals("Lviv", account.getAddress());
            assertEquals("er@er.ua", account.getEmail());

            orderService.setAccount(account);
            String orderId = orderService.create("Lviv", "Volodymyr", "11/30/2018",
                    "qwer@qwer.ua", "Hello world", 100);


            FruitBasket gift = new FruitBasket();
            gift.setSize("S");

            orderService.addGift(orderId, gift);

            assertEquals(119.99, orderService.getById(orderId).getPrice());

            StuffedAnimal anGift = new StuffedAnimal();
            anGift.setSize("L");
            orderService.addGift(orderId, anGift);
            assertEquals(163.97, orderService.getById(orderId).getPrice());

            accountService.save(account);

            orderService.removeGift(orderId, anGift);
            assertEquals(119.99, orderService.getById(orderId).getPrice());

            //edit
            orderService.removeGift(orderId, gift);
            orderService.addGift(orderId, anGift);
            assertEquals(143.98000000000002, orderService.getById(orderId).getPrice());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addGift() {
        try {
            AccountService accountService = new AccountService();
            OrderService orderService = new OrderService();

            Account account = accountService.create("AddGift", "Lviv", "er@er.ua");

            assertNotNull(account.getId());
            assertEquals(account.getId()+"AddGift", account.getAccountNumber());
            assertEquals("AddGift", account.getCompanyName());
            assertEquals("Lviv", account.getAddress());
            assertEquals("er@er.ua", account.getEmail());

            orderService.setAccount(account);
            String orderId = orderService.create("Lviv", "Volodymyr", "11/30/2018",
                    "qwer@qwer.ua", "Hello world", 100);


            StuffedAnimal anGift = new StuffedAnimal();
            anGift.setSize("L");

            orderService.addGift(orderId, anGift);
            assertEquals(143.98000000000002, orderService.getById(orderId).getPrice());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void assignWrongGiftSize() {
        try {
            AccountService accountService = new AccountService();
            OrderService orderService = new OrderService();

            Account account = accountService.create("assignWrongGiftSize", "Lviv", "er@er.ua");

            assertNotNull(account.getId());
            assertEquals(account.getId()+"assignWrongGiftSize", account.getAccountNumber());
            assertEquals("assignWrongGiftSize", account.getCompanyName());
            assertEquals("Lviv", account.getAddress());
            assertEquals("er@er.ua", account.getEmail());

            orderService.setAccount(account);
            String orderId = orderService.create("Lviv", "Volodymyr", "11/30/2018",
                    "qwer@qwer.ua", "Hello world", 100);


            StuffedAnimal anGift = new StuffedAnimal();
            anGift.setSize("O");

            assertTrue(false);



        } catch (IllegalOrderSizeException e) {
            assertTrue(true);
        } catch (Exception e1) {
            assertTrue(false);
        }
    }

    @Test
    public void assignLongOrderMessage() {
        try {
            AccountService accountService = new AccountService();
            OrderService orderService = new OrderService();

            Account account = accountService.create("assignWrongGiftSize", "Lviv", "er@er.ua");

            assertNotNull(account.getId());
            assertEquals(account.getId()+"assignWrongGiftSize", account.getAccountNumber());
            assertEquals("assignWrongGiftSize", account.getCompanyName());
            assertEquals("Lviv", account.getAddress());
            assertEquals("er@er.ua", account.getEmail());

            orderService.setAccount(account);
            String orderId = orderService.create("Lviv", "Volodymyr", "11/30/2018",
                    "qwer@qwer.ua", "Hello world message longer than 20 character", 100);


            assertTrue(false);



        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (Exception e1) {
            assertTrue(false);
        }
    }

    @Test
    public void incorectDeliveryDate() {
        try {
            AccountService accountService = new AccountService();
            OrderService orderService = new OrderService();

            Account account = accountService.create("assignWrongGiftSize", "Lviv", "er@er.ua");

            assertNotNull(account.getId());
            assertEquals(account.getId()+"assignWrongGiftSize", account.getAccountNumber());
            assertEquals("assignWrongGiftSize", account.getCompanyName());
            assertEquals("Lviv", account.getAddress());
            assertEquals("er@er.ua", account.getEmail());

            orderService.setAccount(account);
            String orderId = orderService.create("Lviv", "Volodymyr", "11/15/2018",
                    "qwer@qwer.ua", "Hello world message", 100);


            assertTrue(false);



        } catch (InvalidDeliveryDateException e1) {
            assertTrue(true);
        }
        catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void editGift() {
        try {
            AccountService accountService = new AccountService();
            OrderService orderService = new OrderService();

            Account account = accountService.create("EditGift", "Lviv", "er@er.ua");

            assertNotNull(account.getId());
            assertEquals(account.getId()+"EditGift", account.getAccountNumber());
            assertEquals("EditGift", account.getCompanyName());
            assertEquals("Lviv", account.getAddress());
            assertEquals("er@er.ua", account.getEmail());

            orderService.setAccount(account);
            String orderId = orderService.create("Lviv", "Volodymyr", "11/30/2018",
                    "qwer@qwer.ua", "Hello world", 100);


            FruitBasket gift = new FruitBasket();
            gift.setSize("S");

            orderService.addGift(orderId, gift);

            assertEquals(119.99, orderService.getById(orderId).getPrice());

            StuffedAnimal anGift = new StuffedAnimal();
            anGift.setSize("L");

            orderService.removeGift(orderId, gift);
            orderService.addGift(orderId, anGift);
            assertEquals(143.98000000000002, orderService.getById(orderId).getPrice());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeGift() {
        try {
            AccountService accountService = new AccountService();
            OrderService orderService = new OrderService();

            Account account = accountService.create("RemoveGift", "Lviv", "er@er.ua");

            assertNotNull(account.getId());
            assertEquals(account.getId()+"RemoveGift", account.getAccountNumber());
            assertEquals("RemoveGift", account.getCompanyName());
            assertEquals("Lviv", account.getAddress());
            assertEquals("er@er.ua", account.getEmail());

            orderService.setAccount(account);
            String orderId = orderService.create("Lviv", "Volodymyr", "11/30/2018",
                    "qwer@qwer.ua", "Hello world", 100);


            FruitBasket gift = new FruitBasket();
            gift.setSize("S");

            orderService.addGift(orderId, gift);
            assertEquals(119.99, orderService.getById(orderId).getPrice());

            orderService.removeGift(orderId, gift);
            assertEquals(100.0, orderService.getById(orderId).getPrice());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cancelOrder() {
        try {
            AccountService accountService = new AccountService();
            OrderService orderService = new OrderService();

            Account account = accountService.create("CancelOrder", "Lviv", "er@er.ua");

            assertNotNull(account.getId());
            assertEquals(account.getId()+"CancelOrder", account.getAccountNumber());
            assertEquals("CancelOrder", account.getCompanyName());
            assertEquals("Lviv", account.getAddress());
            assertEquals("er@er.ua", account.getEmail());

            orderService.setAccount(account);
            String orderId = orderService.create("Lviv", "Volodymyr", "11/30/2018",
                    "qwer@qwer.ua", "Hello world", 100);



            Boolean wasCanceled = orderService.cancel(orderId);
            Order order = orderService.getById(orderId);

            assertNull(order);
            assertTrue(wasCanceled);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
