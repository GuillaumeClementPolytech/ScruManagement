package gui.main;

import business.system.Project;
import business.system.User;

public interface MainController {

    void setProject(Project project);

    void setUser(User user);

    void setHomeController(HomeController homeController);

    void init(Object param);

    // TODO : add comment handle ?

}
