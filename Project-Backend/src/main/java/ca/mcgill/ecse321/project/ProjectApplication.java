package ca.mcgill.ecse321.project;

import ca.mcgill.ecse321.project.model.Session;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Time;

@RestController
@SpringBootApplication
public class ProjectApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProjectApplication.class, args);
  }

  @RequestMapping("/")
  public String greeting(){
    try{
      Session session = new Session();
      session.setTime(new Time(100));
      JavaEmail.NotifyTutor("ypoulmarck@gmail.com", session);
    }catch (Exception e){ return "Fail" + e.getMessage() + e.getStackTrace();}
    return "Pass";
  }

}
