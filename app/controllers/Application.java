package controllers;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import constants.AppConstant;
import exceptions.VideoMungerException;
import handlers.AccountHandler;
import handlers.CohortHandler;
import handlers.VideoHandler;
import message.Message;
import org.apache.commons.io.FileUtils;
import play.Play;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


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
        Message msg = AccountHandler.authenticate(json.get(AppConstant.USERNAME).getAsString(), json.get(AppConstant.PASSWORD).getAsString());
        if (msg == Message.ACCOUNT_NOT_FOUND) {
            return unauthorized(msg.toString());
        } else if (msg == Message.WRONG_PASSWORD) {
            return unauthorized(msg.toString());
        }
        response().setCookie("VideoMunger", json.get("username").getAsString());
        return ok("authenticated!");
    }

    public static Result videoUpload() {


        String upload_base_dir = Play.application().configuration().getString(AppConstant.UPLOAD_PATH);
        String video_upload_path = upload_base_dir;

        Map<String, String[]> multipartFormData = request().body().asMultipartFormData().asFormUrlEncoded();
        Map<String, String> params = new HashMap<>();

        for (String key: multipartFormData.keySet()) {
            params.put(key, multipartFormData.get(key)[0]);
        }

        String cohort_id    = params.get(AppConstant.COHORT_ID);
        String video_name   = params.get(AppConstant.VIDEO_NAME);

        final Http.MultipartFormData formData = request().body().asMultipartFormData();
        final Http.MultipartFormData.FilePart filePart = formData.getFiles().get(0);

        if (filePart.getFilename().indexOf(".avi") != filePart.getFilename().length()-4 || !filePart.getContentType().equals("video/avi")) {
            System.out.println("File extension doesn't match the required format");
            return badRequest("File format not AVI");
        }


        File file = filePart.getFile();

        video_upload_path = video_upload_path +"/" +cohort_id + "/"+video_name+".avi";
        params.put(AppConstant.VIDEO_FILE_PATH, video_upload_path);

        try {
//            FileUtils.moveFile(file, new File("/Users/johntower/Desktop/uploaddir/"+filePart.getFilename()));
            FileUtils.copyFile(file, new File(video_upload_path));
            VideoHandler.uploadVideo(params);

        } catch (IOException ioe) {
            System.out.println("Problem operating on filesystem: "+ioe.getMessage());
            return internalServerError(ioe.getMessage());
        } catch (VideoMungerException e) {
            return badRequest(e.getMessage());
        } catch (Exception e) {
            return internalServerError(e.getMessage());
        }

        return ok();

    }

    public static Result createCohort () {

        JsonObject json = (JsonObject) new JsonParser().parse(request().body().asJson().toString());

        Map<String, String> params = new HashMap<String, String>();

        params.put(AppConstant.COHORT_NAME, json.get(AppConstant.COHORT_NAME).getAsString());
        params.put(AppConstant.CREATED_BY_USER, json.get(AppConstant.CREATED_BY_USER).getAsString());
        params.put(AppConstant.EXPERIMENT_ID,json.get(AppConstant.EXPERIMENT_ID).getAsString());
        params.put(AppConstant.NO_OF_GROUPS, json.get(AppConstant.NO_OF_GROUPS).getAsString());
        params.put(AppConstant.NO_OF_VIALS,json.get(AppConstant.NO_OF_VIALS).getAsString());

        Long cohort_id = null;

        try {
            cohort_id = CohortHandler.createCohort(params);

        } catch (VideoMungerException e) {
            return forbidden(e.getMessage());
        }

        return ok("{\"cohort_id\":\""+cohort_id.toString()+"\"}");
    }

    public static Result retrieveCohortNames(String cohort_name_str) {

        System.out.println("retrieve cohort: "+cohort_name_str);
        try {
            return ok(CohortHandler.retrieveCohorts(cohort_name_str));
        } catch (VideoMungerException e) {
            return badRequest(e.getMessage());
        }
        catch (Exception e ) {
            return badRequest(e.getMessage());
        }
    }

//    public static void main(String[] args) {
//        try {
//            System.out.println(CohortHandler.retrieveCohorts("test"));
//        } catch (VideoMungerException e) {
//            e.printStackTrace();
//        }
//
//    }
//    public static void main(String[] args) throws IOException, InterruptedException {
//
//        System.out.println("testing");
//
//
//
//        String [] cmd = {"/Users/johntower/Videomunger/app/controllers/FluorescoreCMD", "/Users/johntower/Videomunger/app/controllers/AviFileChunk1_View0.avi", "25",
//                "/Users/johntower/Videomunger/app/controllers/AviFileChunk1_View1.avi", "25", "n", "-1"};
//
//        ProcessBuilder builder = new ProcessBuilder(cmd);
//
//        Map<String,String> environ = builder.environment();
//        final Process process = builder.start();
//        InputStream is = process.getInputStream();
//        InputStreamReader isr =  new InputStreamReader(is);
//        BufferedReader br = new BufferedReader(isr);
//
//        String line;
//        while ((line=br.readLine())!=null) {
//
//            System.out.println(line);
//        }
//        System.out.println("Program terminated");
//
//    }
//        try {
//            Runtime.getRuntime().exec(cmd);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


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