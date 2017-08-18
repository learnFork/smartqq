package org.jcker.web.controller;

import org.jcker.web.MessageService;
import org.jcker.smartqq.client.SmartQQClient;
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

   static{
        try {
            new SmartQQClient(new MessageService());
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
    }

    @RequestMapping("/login")
    public void login(HttpServletResponse response) {
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
