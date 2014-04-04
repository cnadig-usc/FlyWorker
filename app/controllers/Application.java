package controllers;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import message.Message;
import models.AccountHandler;
import org.apache.commons.io.FileUtils;
import play.Play;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.io.File;
import java.io.IOException;



public class Application extends Controller {
    public static Result index(String any) {
        //Account ac = new Account("chaitanya nadig", "chai.nadig","mypassword");
        //Ebean.save(ac);
        return ok(index.render());
    }
    public static Result tryPost() {
        return ok (Play.application().path().toString());
    }

    public static Result loadPartial(String any) throws IOException {

        File file = Play.application().getFile("public/app/partials/"+any+".html");
        String fileStr = FileUtils.readFileToString(file);
        if (file.exists())
            return ok(fileStr).as("text/html");
            //return ok(Scala.io.Source.fromFile(file.getCanonicalFile()).mkString).as("text/html");
        else
            return notFound();

    }

    public static Result authenticate() {

        JsonObject json = (JsonObject) new JsonParser().parse(request().body().asJson().toString());
        Message msg = AccountHandler.authenticate(json.get("username").getAsString(), json.get("password").getAsString());
        if (msg == Message.ACCOUNT_NOT_FOUND) {
            return unauthorized(msg.toString());
        } else if (msg == Message.WRONG_PASSWORD) {
            return unauthorized(msg.toString());
        }
        return ok("authenticated!");
    }



//    def getURI(any: String): String = any match {
//        case "main" => "/public/html/main.html"
//        case "detail" => "/public/html/detail.html"
//        case "testPartial" => "/public/html/testPartial.html"
//        case _ => "error"
//    }
//
//    /** load an HTML page from public/html */
//    def loadPublicHTML(any: String) = Action {
//        val projectRoot = Play.application().path()
//        var file = new File(projectRoot + getURI(any))
//        if (file.exists())
//            Ok(scala.io.Source.fromFile(file.getCanonicalPath()).mkString).as("text/html");
//        else
//            NotFound
//    }

//    File jsonFile = Play.application().getFile("public/app/phones/"+phoneId);
//    String json = FileUtils.readFileToString(jsonFile);
}