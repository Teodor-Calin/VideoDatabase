package common;

import fileio.ActionInputData;
import java.util.List;

public final class Action {
    private int actionId;
    private String actionType;
    private String type;
    private String username;
    private String objectType;
    private String sortType;
    private String criteria;
    private String title;
    private String genre;
    private int number;
    private double grade;
    private int seasonNumber;
    private List<List<String>> filters;

    public int getActionId() {
        return actionId;
    }

    public String getActionType() {
        return actionType;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getSortType() {
        return sortType;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getNumber() {
        return number;
    }

    public double getGrade() {
        return grade;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public List<List<String>> getFilters() {
        return filters;
    }

    public Action(final ActionInputData actionInput) {
        this.actionId = actionInput.getActionId();
        this.actionType = actionInput.getActionType();
        this.type = actionInput.getType();
        this.username = actionInput.getUsername();
        this.objectType = actionInput.getObjectType();
        this.sortType = actionInput.getSortType();
        this.criteria = actionInput.getCriteria();
        this.title = actionInput.getTitle();
        this.genre = actionInput.getGenre();
        this.number = actionInput.getNumber();
        this.grade = actionInput.getGrade();
        this.seasonNumber = actionInput.getSeasonNumber();
        this.filters = actionInput.getFilters();
    }

}
