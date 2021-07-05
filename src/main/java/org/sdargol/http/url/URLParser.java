package org.sdargol.http.url;

import com.sun.net.httpserver.HttpExchange;
import org.sdargol.controllers.annotation.Mapping;
import org.sdargol.controllers.core.ControllerManager;
import org.sdargol.controllers.core.IController;
import org.sdargol.controllers.core.request.BaseRequestEntity;
import org.sdargol.controllers.core.request.RequestEntity;
import org.sdargol.controllers.method.HTTPMethod;
import org.sdargol.utils.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class URLParser {
    private static final Logger LOGGER = Log.getLogger(URLParser.class.getName());
    private static final ControllerManager CONTROLLER_MANAGER;

    static {
        CONTROLLER_MANAGER = new ControllerManager();
        boolean bInit = CONTROLLER_MANAGER.init();

        if(bInit){
            LOGGER.info("ControllerManager success load");
        }else{
            LOGGER.info("ControllerManager error load");
        }
    }

    //public void parse(String url, HTTPMethod httpMethod) {
    public void run(HttpExchange exchange) {
        LOGGER.info("[START PARSE URL]: " + Thread.currentThread().getName());
        String url = exchange.getRequestURI().getPath();
        HTTPMethod httpMethod = parseHTTPMethod(exchange.getRequestMethod());

        IController controller = CONTROLLER_MANAGER.getController(url);

        Class<? extends IController> clss = controller.getClass();
        Method[] methods = clss.getDeclaredMethods();

        String response = null;

        //Если совпадает url и httpMethod
        for (Method method : methods) {
            if (method.isAnnotationPresent(Mapping.class)) {
                Mapping mapping = method.getDeclaredAnnotation(Mapping.class);
                if (mapping.url().equals(url) && mapping.httpMethod().equals(httpMethod)) {
                    try {
                        response = (String) method.invoke(controller, new BaseRequestEntity(exchange));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        if (response != null) {
            LOGGER.info("[RESPONSE]: " + response);
            return;
        }

        LOGGER.info("[NEXT SEARCH]: " + Thread.currentThread().getName());

        for (Method method : methods) {
            if (method.isAnnotationPresent(Mapping.class)) {
                Mapping mapping = method.getDeclaredAnnotation(Mapping.class);
                if(check(url, mapping.url()) && mapping.httpMethod().equals(httpMethod)){
                    System.out.println("Ура, мы нашли подходящий метод: " + method.getName());
                    try {
                        response = (String) method.invoke(controller, new RequestEntity(exchange, parseUrlVariable(url,mapping.url())));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        LOGGER.info("[RESPONSE AFTER FULL SEARCH]: " +
                response + " " + Thread.currentThread().getName());
    }

    private boolean check(String urlRequest, String urlMethod){
        //получаем первое вхождение { в шаблон
        int start = urlMethod.indexOf("{");

        //если не нашли {, то сравниваем строки
        if(start == - 1){
            return urlRequest.equals(urlMethod);
        }

        //если в начале URL шаблона есть / то удаляем
        if(urlMethod.startsWith("/")){
            urlMethod = urlMethod.substring(1);
        }

        //получить подстроку до первого вхождения {
        String urlSubstringMethod = urlMethod.substring(0,start - 1);

        /*if(urlSubstringMethod.startsWith("/")){
            urlSubstringMethod = urlSubstringMethod.substring(1);
        }*/

        //получаем массив из пути /api/v1/cards/ => [api, v1, cards]
        String[] arrUrlMethod = urlSubstringMethod.split("/");

        //удаляем / из начала URL запроса
        if(urlRequest.startsWith("/")){
            urlRequest = urlRequest.substring(1);
        }

        //получаем массив из URL запроса
        String[] arrUrlRequest = urlRequest.split("/");

        //если длина массива из содержимого URL шаблона не совпадает с длиной массива URL запроса
        if(urlMethod.split("/").length != arrUrlRequest.length){
            return false;
        }

        //если вдруг длина массива шаблона без {...} оказалась больше длины массива URL запроса
        if(arrUrlMethod.length > arrUrlRequest.length){
            return false;
        }

        //все элементы URL шаблона до {...} должны совпасть с подобными из URL запроса иначе false
        for (int i = 0; i < arrUrlMethod.length; i++) {
            if(!arrUrlMethod[i].equals(arrUrlRequest[i])){
                return false;
            }
        }

        return true;
    }

    //при использовании данного метода стоит учитывать, что URL запроса и URL шаблона совпадают
    private Map<String, String> parseUrlVariable(String urlRequest, String urlMethod){
        //получаем первое вхождение { в шаблон
        int start = urlMethod.indexOf("{") - 1;

        //если в начале URL шаблона есть / то удаляем
        if(urlMethod.startsWith("/")){
            urlMethod = urlMethod.substring(1);
        }

        //получить подстроку после первого вхождения {
        String urlSubstringMethod = urlMethod.substring(start);

        //получаем массив наименований URL шаблона
        String[] arrUrlMethod = urlSubstringMethod.split("/");

        for (int i = 0; i < arrUrlMethod.length; i++) {
            arrUrlMethod[i] = arrUrlMethod[i].replaceAll("[{}]", "");
        }

        //удаляем / из начала URL запроса
        if(urlRequest.startsWith("/")){
            urlRequest = urlRequest.substring(1);
        }

        //безопасно, т.к мы уже выполнили проверку и уверены, что URL шаблона и URL запроса совпадают
        //нам просто нужна та часть, с которой начинается "динамическая часть" URL запроса
        //но можно добавить try catch
        urlRequest = urlRequest.substring(start);

        //получаем массив из URL запроса
        String[] arrUrlRequest = urlRequest.split("/");

        if(arrUrlMethod.length != arrUrlRequest.length){
            System.out.println("Можно кинуть исключение");
        }

        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < arrUrlMethod.length; i++) {
            map.put(arrUrlMethod[i], arrUrlRequest[i]);
        }

        return map;
    }

    private HTTPMethod parseHTTPMethod(String method){
        HTTPMethod httpMethod;

        switch(method){
            case "GET" : httpMethod = HTTPMethod.GET;
                break;
            case "POST" : httpMethod = HTTPMethod.POST;
                break;
            default: httpMethod = null;
                break;
        }

        return httpMethod;
    }
}
