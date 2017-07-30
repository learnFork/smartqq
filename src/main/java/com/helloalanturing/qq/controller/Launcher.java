package com.helloalanturing.qq.controller;

import com.helloalanturing.qq.MessageService;
import com.scienjus.smartqq.client.SmartQQClient;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * Created by Alan Turing on 2017/7/3.
 * s
 */
@Controller
public class Launcher {
    public static final Logger LOGGER = Logger.getLogger(Launcher.class);

    @RequestMapping("/start")
    public void start() {
        new Thread(
                new Runnable(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (final Exception e) {
                            LOGGER.log(Level.ERROR, e.getMessage());
                        }
                        System.out.println("Launcher.run");
                        new SmartQQClient(new MessageService());
                    }
                }
        ).start();
    }

    @RequestMapping("/scan")
    private void scan(HttpServletResponse response){
        OutputStream output = null;
        try {
            final String filePath = new File("../qrcode.png").getCanonicalPath();
            final byte[] data = IOUtils.toByteArray(new FileInputStream(filePath));

            output = response.getOutputStream();
            IOUtils.write(data, output);
            output.flush();
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(output);
        }
    }
}
