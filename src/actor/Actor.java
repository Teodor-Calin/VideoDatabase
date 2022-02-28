package actor;

import common.Action;
import entertainment.Video;
import fileio.ActorInputData;

import common.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static utils.Utils.stringToAwards;

public final class Actor {
    private String name;
    private String careerDescription;
    private ArrayList<String> filmography;
    private Map<ActorsAwards, Integer> awards;

    public String getName() {
        return name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public Actor(final ActorInputData inputActor) {
        this.name = inputActor.getName();
        this.careerDescription = inputActor.getCareerDescription();
        this.filmography = inputActor.getFilmography();
        this.awards = inputActor.getAwards();
    }


    /**
     *
     * @return the number of awards of an actor
     */
    public int getNrAwards() {
        int nr = 0;
        for (ActorsAwards i : ActorsAwards.values()) {
            if (this.awards.get(i) != null) {
                nr += this.awards.get(i);
            }
        }
        return nr;
    }

    /**
     *
     * @param videoMap - the video HashMap where keys are the titles of the videos
     * @return the rating of an actor, which is the average rating of the videos where he played
     */
    public double getActorRating(final HashMap<String, Video> videoMap) {
        double rating = 0;
        int nr = 0;
        for (String i : this.filmography) {
            if (videoMap.get(i) != null && videoMap.get(i).getRating() != 0) {
                rating += videoMap.get(i).getRating();
                nr++;
            }
        }
        if (nr == 0) {
            return 0;
        }
        rating /= nr;
        return rating;
    }

    /**
     *
     * @param query
     * @param actorArrayList
     * @param videoMap
     * @return the list of the best/worst rated actors, based on the movies they played in
     */
    public static String averageActors(final Action query, final ArrayList<Actor> actorArrayList,
                                       final HashMap<String, Video> videoMap) {
        String message = "Query result: [";
        ArrayList<Actor> list = new ArrayList<>();

        for (Actor i : actorArrayList) {
            if (i.getActorRating(videoMap) != 0) {
                list.add(i);
            }
        }

        list.sort(new Comparator<>() {
            @Override
            public int compare(final Actor o1, final Actor o2) {
                if (o1.getActorRating(videoMap) - o2.getActorRating(videoMap) == 0) {
                    return o1.name.compareTo(o2.name);
                }
                return  Double.compare(o1.getActorRating(videoMap),
                                         o2.getActorRating(videoMap));
            }
        });

        return printedList(list, query.getSortType(), query.getNumber());
    }

    /**
     *
     * @param query
     * @param actorArrayList
     * @return the list of actors with the most/least awards
     */
    public static String awardsActors(final Action query, final ArrayList<Actor> actorArrayList) {
        String message = "Query result: [";
        ArrayList<Actor> list = new ArrayList<>();

        for (Actor i : actorArrayList) {
            int ok = 1;
            for (String award : query.getFilters().get(Constants.THREE)) {
                if (i.awards.get(stringToAwards(award)) == null) {
                    ok = 0;
                    break;
                }
            }
            if (ok == 1) {
                list.add(i);
            }
        }

        list.sort(new Comparator<>() {
            @Override
            public int compare(final Actor o1, final Actor o2) {
                if (o1.getNrAwards() - o2.getNrAwards() == 0) {
                    return o1.name.compareTo(o2.name);
                }
                return o1.getNrAwards() - o2.getNrAwards();
            }
        });

        return printedList(list, query.getSortType(), query.getNumber());
    }

    /**
     *
     * @param query
     * @param actorArrayList
     * @return the list of actors after a filter on their description text
     */
    public static String filterDescriptionActors(final Action query,
                                                 final  ArrayList<Actor> actorArrayList) {
        String message = "Query result: [";
        ArrayList<Actor> list = new ArrayList<>();

        for (Actor i : actorArrayList) {
            int ok = 1;
            String[] text = i.careerDescription.toLowerCase().split("\\W");
            for (String word : query.getFilters().get(2)) {
                if (!Arrays.asList(text).contains(word)) {
                    ok = 0;
                    break;
                }
            }
            if (ok == 1) {
                list.add(i);
            }
        }

        list.sort(new Comparator<>() {
            @Override
            public int compare(final Actor o1, final Actor o2) {
                return o1.name.compareTo(o2.name);
            }
        });

        return printedList(list, query.getSortType(), query.getNumber());
    }

    private static String printedList(final ArrayList<Actor> list, final String sortType,
                                      final int nr) {
        if (list.size() == 0) {
            return "Query result: []";
        }

        StringBuilder message = new StringBuilder();
        message.append("Query result: [");

        int number;
        if (nr > list.size()) {
            number = list.size();
        } else {
            number = nr;
        }

        if (sortType.equals("asc")) {
            for (int i = 0; i < number - 1; i++) {
                message.append(list.get(i).name + ", ");
            }
            message.append(list.get(number - 1).name);
        } else {
            for (int i = list.size() - 1; i >= list.size() - number + 1; i--) {
                message.append(list.get(i).name + ", ");
            }
            message.append(list.get(list.size() - number).name);
        }
        message.append("]");
        return message.toString();
    }

}


