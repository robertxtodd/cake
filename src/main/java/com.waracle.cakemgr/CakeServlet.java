package com.waracle.cakemgr;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@SpringBootApplication
@Controller
public class CakeServlet {

    public static final String SOURCE_DATA_URL = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";

    public static void main(String[] args) {
        SpringApplication.run(CakeServlet.class, args);
    }

    @PreDestroy
    public void destroy() {
        HibernateUtil.shutdown();
        System.out.println("destroy");
    }


    @PostConstruct
    public void init() throws ServletException, MalformedURLException {

        System.out.println("init started");
        System.out.println("downloading cake json");

        ObjectMapper mapper = new ObjectMapper();
        try {
            CakeEntity[] obj = mapper.readValue(new URL(SOURCE_DATA_URL), CakeEntity[].class);

            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                for(CakeEntity cakeEntity : obj) {
                    session.persist(cakeEntity);
                    System.out.println("adding cake entity "+cakeEntity);
                }

                session.getTransaction().commit();
            } catch (ConstraintViolationException ex) {

            }
            session.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("init finished");
    }



    @RequestMapping(value = {"/","/cakes"}, method = RequestMethod.GET)
    protected ModelAndView handleBrowserRequest (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return doDisplay();
    }

    @RequestMapping(value = "/cakes", consumes ="application/json",produces = "application/json")
    @ResponseBody
    protected String handleJSONRequest (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<CakeEntity> list = session.createCriteria(CakeEntity.class).list();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(list);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    protected ModelAndView handleAddBrowserRequest(@RequestParam("title") String title, @RequestParam("desc") String desc,
                                 @RequestParam("image") String image) throws ServletException, IOException {
        return doAdd(title, desc, image);
    }

    @RequestMapping(value = "/add", consumes ="application/json",produces = "application/json")
    protected ModelAndView handleAddJSONRequest(@RequestParam("title") String title, @RequestParam("desc") String desc,
                                 @RequestParam("image") String image) throws ServletException, IOException {
        return doAdd(title, desc, image);
    }


    private ModelAndView doDisplay() throws ServletException, MalformedURLException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<CakeEntity> cakeList = session.createCriteria(CakeEntity.class).list();
        session.close();

        ModelAndView model = new ModelAndView("mainPage");
        model.addObject("cakeList", cakeList);

        return model;
    }

    private ModelAndView doAdd(String title, String desc, String image){
        CakeEntity cakeEntity = new CakeEntity(title, desc, image);
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(cakeEntity);
            System.out.println("adding cake entity "+cakeEntity);
            session.getTransaction().commit();
        } catch (ConstraintViolationException ex) {

        }
        List<CakeEntity> cakeList = session.createCriteria(CakeEntity.class).list();
        session.close();

        ModelAndView model = new ModelAndView("mainPage");
        model.addObject("cakeList", cakeList);
        return model;
    }

}
