package android.alcode.com.material.models;

/**
 * Created by Hamzeh on 4/6/2016.
 */
public class UserInfo {
    String name, email, phoneNumber, titleLocation, imageProfileUrl, imageBackgroundUrl, companyName, birthDate, gender, statment, isOnline, job;

    UserLocation userLocation;


    public UserInfo(String name, String email, String phoneNumber, String titleLocation, String imageProfileUrl, String imageBackgroundUrl, String companyName, String birthDate, String gender, String statment, String isOnline, String job, UserLocation userLocation) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.titleLocation = titleLocation;
        this.imageProfileUrl = imageProfileUrl;
        this.imageBackgroundUrl = imageBackgroundUrl;
        this.companyName = companyName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.statment = statment;
        this.isOnline = isOnline;
        this.job = job;
        this.userLocation = userLocation;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTitleLocation() {
        return titleLocation;
    }

    public void setTitleLocation(String titleLocation) {
        this.titleLocation = titleLocation;
    }

    public String getImageProfileUrl() {
        return imageProfileUrl;
    }

    public void setImageProfileUrl(String imageProfileUrl) {
        this.imageProfileUrl = imageProfileUrl;
    }

    public String getImageBackgroundUrl() {
        return imageBackgroundUrl;
    }

    public void setImageBackgroundUrl(String imageBackgroundUrl) {
        this.imageBackgroundUrl = imageBackgroundUrl;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatment() {
        return statment;
    }

    public void setStatment(String statment) {
        this.statment = statment;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public UserLocation getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(UserLocation userLocation) {
        this.userLocation = userLocation;
    }
}
