package Main;

import java.time.LocalDate;

public class Entry {

    int serialNum;
    String title;
    String name;
    String email;
    String suggestions;
    LocalDate checkInDate;

    public Entry(int serialNum) {
        this.serialNum = serialNum;
        this.title = "";
        this.name = "";
        this.email = "";
        this.suggestions = "";
        this.checkInDate = LocalDate.now();
    }

    public int getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", checkInDate.toString(), title, name, email, suggestions);
    }
}
