package com.helloalanturing.qq.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Alan Turing on 2017/7/16.
 * load config sources
 */
@Service
public class CommandService {
    public static List<String> INTEREST_TAGS = new CopyOnWriteArrayList<>();
    static {
        ResourceBundle CFG = ResourceBundle.getBundle("interests");
        INTEREST_TAGS = Arrays.asList(CFG.getString("qq.interests.tags").split("\\|"));
        System.out.println("INTEREST_TAGS = " + INTEREST_TAGS);
    }

    public static void main(String[] args) {
        System.out.println(CommandService.INTEREST_TAGS);
    }
}
