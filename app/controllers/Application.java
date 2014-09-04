package controllers;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import message.Message;
import models.handlers.AccountHandler;
import org.apache.commons.io.FileUtils;
import play.Play;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.io.IOException;



public class Application extends Controller {
    public static Result index(String any) throws IOException {
        //Account ac = new Account("chaitanya nadig", "chai.nadig","mypassword");
        //Ebean.save(ac);
//        return ok(index.render());


        File file = Play.application().getFile("public/mainindex.html");
        String fileStr = FileUtils.readFileToString(file);
        if (file.exists())
            return ok(fileStr).as("text/html");
            //return ok(Scala.io.Source.fromFile(file.getCanonicalFile()).mkString).as("text/html");
        else
            return notFound();

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
        response().setCookie("VideoMunger", json.get("username").getAsString());
        return ok("authenticated!");
    }

    public static Result videoUpload() {
//        try {
//            File file = request().body().asRaw().asFile();
//        } catch ( NullPointerException e ) {
//
//            return ok ("File upload failed");
//        }
//        return ok("File uploaded");
        final Http.MultipartFormData formData = request().body().asMultipartFormData();
        // here I have formData=null, in case of a huge file
        final Http.MultipartFormData.FilePart filePart = formData.getFiles().get(0);

        System.out.println(filePart.getFilename());
        System.out.println(filePart.getContentType());
        System.out.println(filePart.getFilename().indexOf(".avi"));
        System.out.println(filePart.getFilename().length());



        if (filePart.getFilename().indexOf(".avi") != filePart.getFilename().length()-4 || !filePart.getContentType().equals("video/x-msvideo")) {
            System.out.println("File extension doesn't match the required format");
            return badRequest("File format not AVI");
        }


        System.out.println(filePart.getFile().length());
        File file = filePart.getFile();

        System.out.println(file.getAbsoluteFile());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getName());
        System.out.println(file.getParent());

        try {
            FileUtils.moveFile(file, new File("/Users/johntower/Desktop/uploaddir/"+filePart.getFilename()));
        } catch (IOException ioe) {
            System.out.println("Problem operating on filesystem: "+ioe.getMessage());
        }

        return ok();

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