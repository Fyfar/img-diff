package com.spring.controllers;

import com.modules.DiffSearcher;
import com.modules.Drawer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Controller
public class MainController {
    private static final Logger LOG = Logger.getLogger(MainController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private DiffSearcher diffSearcher;

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "getDiff", method = RequestMethod.POST, produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getDiff(@RequestParam final MultipartFile firstImg, @RequestParam final MultipartFile secondImg) throws IOException {
        String firstPath = saveFile(firstImg);
        String secondPath = saveFile(secondImg);

        if (firstPath.isEmpty() || secondPath.isEmpty()) {
            LOG.error("Image path is empty: " + firstPath + ", " + secondPath);
        }

        diffSearcher.loadImages(firstPath, secondPath);

        return Files.readAllBytes(Paths.get(Drawer.OUTPUT_IMAGE_NAME));
    }

    private String saveFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String uploadsDir = "/uploads/";
                String realPathToUploads = request.getServletContext().getRealPath(uploadsDir);

                LOG.info("realPathtoUploads = " + realPathToUploads);


                String originalName = file.getOriginalFilename();
                String filePath = realPathToUploads + File.separator + originalName;
                File destination = new File(filePath);
                file.transferTo(destination);

                return destination.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }
}