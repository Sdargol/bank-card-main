package org.sdargol.controllers;

import org.sdargol.controllers.annotation.Mapping;
import org.sdargol.controllers.annotation.RestController;
import org.sdargol.controllers.core.IController;
import org.sdargol.controllers.core.request.BaseRequestEntity;
import org.sdargol.controllers.core.request.RequestEntity;
import org.sdargol.controllers.method.HTTPMethod;

@RestController(url= "/api/v1/cards")
public class CardController implements IController {

    @Mapping(url = "/api/v1/cards")
    public String getCards(BaseRequestEntity requestEntity){
        return "[{\"id\":0, \"number\":0000 0000 0000 0000}, {\"id\":1, \"number\":1111 1111 1111 1111}]";
    }

    @Mapping(url = "/api/v1/cards/{id}")
    public String getCardByID(RequestEntity requestEntity){
        return "Мой id: " + requestEntity.getUrlVariable().get("id");
    }

    @Mapping(url = "/api/v1/cards/{id}/{test}")
    public String test(RequestEntity requestEntity){
        return "Мой id: " + requestEntity.getUrlVariable().get("id") +
                " и test: " + requestEntity.getUrlVariable().get("test");
    }

    @Mapping(url = "/api/v1/cards", httpMethod = HTTPMethod.POST)
    public String createCard(BaseRequestEntity requestEntity){
        return "Card created";
    }

    @Mapping(url = "/api/v1/cards/add/{id}/{test}")
    public String testHello(RequestEntity requestEntity){
        return "Здравствуйте, а вы знаете, что мой id: " +
                requestEntity.getUrlVariable().get("id") +
                " и test: " + requestEntity.getUrlVariable().get("test");
    }
}
