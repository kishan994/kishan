package trainedge.autoprofiler.model;

/**
 * Created by kishan on 23-09-2017.
 */

public class ProfileModel {
    String location;
    String profileName;
    public ProfileModel() {
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }



    public ProfileModel(String location, String profileName) {
        this.location = location;
        this.profileName = profileName;
    }

}
