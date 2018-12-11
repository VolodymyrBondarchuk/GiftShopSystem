package com.service;

import com.entity.Account;
import com.entity.Order;
import com.entity.gift.FruitBasket;
import com.entity.gift.Gift;
import com.entity.gift.StuffedAnimal;
import com.entity.gift.SweetsBasket;
import com.entity.gift.fruit.Fruit;
import com.entity.gift.fruit.Orange;
import com.exceptions.IllegalModifyOrderException;
import com.exceptions.InvalidDeliveryDateException;
import org.apache.commons.lang.time.DateUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderService {


    private Account account;

    private static final String path = "C:\\oop-storage\\";
    private String idCount = "order-count.txt";

    //create order
    public String create(String address, String clientName, String deliveryDate,
                       String email, String message, double price) throws IOException, ParseException {

        Date today = new Date();
        Date delivDate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(deliveryDate);

        if(delivDate.before(today)){
            throw new InvalidDeliveryDateException();
        }
        Order order = new Order();

        order.setId(generateIdNumber());
        order.setAddress(address);
        order.setConsumerName(clientName);
        order.setDeliveryDate(deliveryDate);
        order.setEmail(email);
        order.setMessage(message);
        order.setPrice(price);

        account.addOrder(order);

        return order.getId();

    }

    private String generateIdNumber() throws IOException {
        File file = new File(path+idCount);

        if(file.exists() && !file.isDirectory()) {
            String prevId = readIdFromFile();
            Integer id = Integer.valueOf(prevId);

            ++id;

            writeId2File(id);

            return id.toString();

        } else {
            new File(path).mkdir();
            writeId2File(1);
            return readIdFromFile();
        }

    }

    private void writeId2File(Integer id) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(path + idCount, "UTF-8");
        writer.println(id);
        writer.close();
    }


    private String readIdFromFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path+idCount));
        String line = "";
        try {

            line = br.readLine();

        } finally {
            br.close();
        }

        return line;
    }

    //edit order
    public String edit(String orderId, Order newOrder) {
        checkOrderExpiration(orderId);
        if(account !=null) {
            List<Order> orders = account.getOrders();

            for(int i=0; i<orders.size(); i++) {
                if(orders.get(i).getId().equals(orderId)) {
                    orders.remove(i);
                    orders.add(newOrder);
                    return newOrder.getId();

                }
            }
        }

        return orderId;
    }

    public String edit(Order oldOrder, Order newOrder) {

        checkOrderExpiration(oldOrder);

        if(account !=null) {
            List<Order> orders = account.getOrders();

            for(int i=0; i<orders.size(); i++) {
                if(orders.get(i).getId().equals(oldOrder.getId())) {
                    orders.remove(i);
                    orders.add(newOrder);
                    return newOrder.getId();

                }
            }
        }

        return oldOrder.getId();
    }

    public List<Order> getAll() {
        if(account !=null) {
            return account.getOrders();
        }

        return Collections.emptyList();
    }

    public Order getById(String orderId) {
        if(account !=null) {
            List<Order> orders = account.getOrders();

            for(int i=0; i<orders.size(); i++) {
                if(orders.get(i).getId().equals(orderId)) {

                    return orders.get(i);

                }
            }
        }

        return null;
    }

    //cancel order
    public boolean cancel(Order order) {

        checkOrderExpiration(order);
        if(account !=null) {
            List<Order> orders = account.getOrders();

            for(int i=0; i<orders.size(); i++) {
                if(orders.get(i).getId().equals(order.getId())) {
                    orders.remove(i);
                    return true;

                }
            }
        }

        return false;
    }

    private void checkOrderExpiration(String orderId) {
        Order order = getById(orderId);
        if(order.isHasSent()) {
            throw new IllegalModifyOrderException();
        }
    }

    private void checkOrderExpiration(Order order) {
        if(order.isHasSent()) {
            throw new IllegalModifyOrderException();
        }
    }

    public boolean cancel(String orderId) {
        checkOrderExpiration(orderId);
        if(account !=null) {
            List<Order> orders = account.getOrders();

            for(int i=0; i<orders.size(); i++) {
                if(orders.get(i).getId().equals(orderId)) {
                    orders.remove(i);
                    return true;

                }
            }
        }

        return false;
    }

    //add gift to order
    public int addGift(String orderId, FruitBasket gift) {

        checkOrderExpiration(orderId);

        Order order = getById(orderId);
        Double giftFee = gift.getSize().fee();

        if(gift.getFruits()!=null && gift.getFruits().contains(new Orange())){
            giftFee += 5.99;
        }

        gift.setPrice(giftFee);

        //price calculates inside method
        return order.addGift(gift);
    }

    public void sendOrder(String orderId){
       Order order = getById(orderId);
       order.setHasSent(true);
    }

    public int addGift(String orderId, StuffedAnimal gift) {

        checkOrderExpiration(orderId);

        Order order = getById(orderId);
        Double giftFee = gift.getSize().fee();

        giftFee += 3.99;

        gift.setPrice(giftFee);

        //price calculates inside method
        return order.addGift(gift);
    }

    public void addGift(String orderId, SweetsBasket gift) {
        Order order = getById(orderId);
        Double giftFee = gift.getSize().fee();

        gift.setPrice(giftFee);

        //price calculates inside method
        order.addGift(gift);
    }

    //remove gift
    public void removeGift(String orderId, Gift gift){
        checkOrderExpiration(orderId);

        Order order = getById(orderId);
        order.getGifts().remove(gift);
        order.setPrice(order.getPrice()-gift.getPrice());
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
