package com.service;

import com.entity.Account;
import org.apache.commons.io.FileUtils;

import java.io.*;

public class AccountService {

    private static final String path = "C:\\oop-storage\\";
    private String idCount = "id-count.txt";


    public Account create(String companyName, String address, String email) throws IOException {

        Account account = new Account();

        account.setCompanyName(companyName);
        account.setAddress(address);
        account.setEmail(email);

        String id = generateIdNumber();

        account.setId(id);
        account.setAccountNumber(id+companyName);

        return account;
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

    public void save(Account account) throws IOException {
        FileOutputStream f = new FileOutputStream(new File(path + account.getAccountNumber()+".txt"));
        ObjectOutputStream o = new ObjectOutputStream(f);

        o.writeObject(account);

        o.close();
        f.close();

    }

    public Account open(String accountNumber) throws IOException, ClassNotFoundException {

        Account account = new Account();
        FileInputStream fi = new FileInputStream(new File(path+accountNumber+".txt"));
        ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            Account pr1 = (Account) oi.readObject();

            oi.close();
            fi.close();

        return account;
    }

    public void close(Account account) throws IOException {
        save(account);
        account.setId(null);
        account.setAccountNumber(null);
        account.setCompanyName(null);
        account.setAddress(null);
        account.setEmail(null);
    }

    public void delete(String accountNumber) throws IOException {
        File file = new File(path+accountNumber+".txt");
        System.out.println(file.getAbsolutePath());
        FileUtils.forceDelete(file);
    }

    public void delete( Account account) throws IOException {
        File file = new File(path+account.getAccountNumber()+".txt");
        System.out.println(file.getAbsolutePath());
        FileUtils.forceDelete(file);

        account.setId(null);
        account.setAccountNumber(null);
        account.setCompanyName(null);
        account.setAddress(null);
        account.setEmail(null);
    }

}
