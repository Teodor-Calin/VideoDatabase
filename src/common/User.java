package common;

import entertainment.Genre;
import entertainment.Movie;
import entertainment.Season;
import entertainment.Serial;
import entertainment.Video;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.Utils.genreToString;
import static utils.Utils.stringToGenre;

public final class User {
    private String username;
    private String subscriptionType;
    private Map<String, Integer> history;
    private ArrayList<String> favoriteMovies;
    private ArrayList<String> ratedMovies;
    private ArrayList<String> ratedSerials;
    private ArrayList<Season> ratedSeasons;

    public String getUsername() {
        return username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public ArrayList<String> getRatedMovies() {
        return ratedMovies;
    }

    public ArrayList<String> getRatedSerials() {
        return ratedSerials;
    }

    public ArrayList<Season> getRatedSeasons() {
        return ratedSeasons;
    }

    public User(final UserInputData userInput) {
        this.username = userInput.getUsername();
        this.subscriptionType = userInput.getSubscriptionType();
        this.history = userInput.getHistory();
        this.favoriteMovies = userInput.getFavoriteMovies();
        ratedMovies = new ArrayList<>();
        ratedSerials = new ArrayList<>();
        ratedSeasons = new ArrayList<>();
    }

    /**
     *
     * @param movieArrayList
     * @param serialArrayList
     * @return te first video that hasn't been seen by the user
     */
    public String standardRecommendation(final ArrayList<Video> movieArrayList,
                                         final ArrayList<Video> serialArrayList) {
        for (Video i : movieArrayList) {
            if (!this.history.containsKey(i.getTitle())) {
                return "StandardRecommendation result: " + i.getTitle();
            }
        }
        for (Video i : serialArrayList) {
            if (!this.history.containsKey(i.getTitle())) {
                return "StandardRecommendation result: " + i.getTitle();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    /**
     *
     * @param movieArrayList
     * @param serialArrayList
     * @return the best video that hasn't been seen by the user
     */
    public String bestUnseenRecommendation(final ArrayList<Video> movieArrayList,
                                           final ArrayList<Video> serialArrayList) {
        List<Video> list = new ArrayList<>();
        for (Video i : movieArrayList) {
            if (!this.history.containsKey(i.getTitle())) {
                list.add(i);
            }
        }
        for (Video i : serialArrayList) {
            if (!this.history.containsKey(i.getTitle())) {
                list.add(i);
            }
        }

        if (list.size() == 0) {
            return "BestRatedUnseenRecommendation cannot be applied!";
        }

        list.sort(new Comparator<>() {
            @Override
            public int compare(final Video o1, final Video o2) {
                return Double.compare(o2.getRating(), o1.getRating());
            }
        });

        return "BestRatedUnseenRecommendation result: " + list.get(0).getTitle();
    }

    /**
     *
     * @param movieArrayList
     * @param serialArrayList
     * @return the video with most favourite appearances that hasn't been seen by the user
     */
    public String favoriteRecommendation(final ArrayList<Video> movieArrayList,
                                         final ArrayList<Video> serialArrayList) {
        if (this.subscriptionType.equals("BASIC")) {
            return "FavoriteRecommendation cannot be applied!";
        }

        ArrayList<Video> list = new ArrayList<>();
        for (Video i : movieArrayList) {
            if (!this.history.containsKey(i.getTitle())) {
                list.add(i);
            }
        }
        for (Video i : serialArrayList) {
            if (!this.history.containsKey(i.getTitle())) {
                list.add(i);
            }
        }

        if (list.size() == 0) {
            return "FavoriteRecommendation cannot be applied!";
        }

        list.sort(new Comparator<>() {
            @Override
            public int compare(final Video o1, final Video o2) {
                return (o2.getNrFavoriteAppearances() - o1.getNrFavoriteAppearances());
            }
        });

        return "FavoriteRecommendation result: " + list.get(0).getTitle();
    }

    /**
     *
     * @param genre
     * @param movieArrayList
     * @param serialArrayList
     * @return all the unseen videos by the user, of a given genre
     */
    public String searchRecommendation(final String genre, final ArrayList<Video> movieArrayList,
                                       final ArrayList<Video> serialArrayList) {
        if (this.subscriptionType.equals("BASIC")) {
            return "SearchRecommendation cannot be applied!";
        }

        String message = "SearchRecommendation result: [";
        List<Video> list = new ArrayList<>();
        for (Video i : movieArrayList) {
            if (!this.history.containsKey(i.getTitle()) && i.getGenres().contains(genre)) {
                list.add(i);
            }
        }
        for (Video i : serialArrayList) {
            if (!this.history.containsKey(i.getTitle()) && i.getGenres().contains(genre)) {
                list.add(i);
            }
        }

        if (list.size() == 0) {
            return "SearchRecommendation cannot be applied!";
        }

        list.sort(new Comparator<>() {
            @Override
            public int compare(final Video o1, final Video o2) {
                if (o1.getRating() - o2.getRating() == 0) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
                return Double.compare(o1.getRating(), o2.getRating());
            }
        });

        for (int i = 0; i < list.size() - 1; i++) {
            message += list.get(i).getTitle() + ", ";
        }
        message += list.get(list.size() - 1).getTitle();

        message += "]";
        return message;
    }

    /**
     *
     * @param movieArrayList
     * @param serialArrayList
     * @param genreMap
     * @return a string meaning the title of the first video of the most
     * popular genre, not seen by the user
     */
    public String popularRecommendation(final ArrayList<Video> movieArrayList,
                                        final ArrayList<Video> serialArrayList,
                                        final HashMap<Genre, Integer> genreMap) {
        if (this.subscriptionType.equals("BASIC")) {
            return "PopularRecommendation cannot be applied!";
        }

        List<Genre> genreList = new ArrayList<>();
        for (Genre genre : Genre.values()) {
            genreList.add(genre);
        }

        genreList.sort(new Comparator<>() {
            @Override
            public int compare(final Genre o1, final Genre o2) {
                return (genreMap.get(o2) - genreMap.get(o1));
            }
        });

        for (Genre genre: genreList) {
            for (Video video : movieArrayList) {
                if (!this.history.containsKey(video.getTitle())) {
                    if (video.getGenres().contains(genreToString(genre))) {
                        return "PopularRecommendation result: " + video.getTitle();
                    }
                }
            }
            for (Video video : serialArrayList) {
                if (!this.history.containsKey(video.getTitle())) {
                    if (video.getGenres().contains(genreToString(genre))) {
                        return "PopularRecommendation result: " + video.getTitle();
                    }
                }
            }
        }
        return "PopularRecommendation cannot be applied!";
    }

    /**
     * Adds the video to the history of the user and increments the nrViews variable of the movie
     * @param title
     * @param videoMap
     * @param genreMap
     * @return a string, including how many times the user has seen the video
     */
    public String viewVideo(final String title, final HashMap<String, Video> videoMap,
                            final HashMap<Genre, Integer> genreMap) {
        if (this.history.get(title) == null) {
            this.history.put(title, 1);
            videoMap.get(title).addToNrViews(1);
        } else {
            this.history.put(title, this.history.get(title) + 1);
            videoMap.get(title).addToNrViews(1);
        }
        for (String genreWord : videoMap.get(title).getGenres()) {
            genreMap.put(stringToGenre(genreWord), genreMap.get(stringToGenre(genreWord)) + 1);
        }
        return "success -> " + title + " was viewed with total views of "
               + this.history.get(title);
    }

    /**
     * Adds the video to the favourites list of the user and increments
     * the nrFavorites variable of the movie object
     * @param title
     * @param videoMap
     * @return a string, saying if the process has been done successfully
     */
    public String addToFavorites(final String title, final HashMap<String, Video> videoMap) {
        if (this.history.get(title) != null) {
            for (String i : this.favoriteMovies) {
                if (i.equals(title)) {
                    return "error -> " + title + " is already in favourite list";
                }
            }
            this.favoriteMovies.add(title);
            videoMap.get(title).incrementNrFavoriteAppearances();
            return "success -> " + title + " was added as favourite";
        }
        return "error -> " + title + " is not seen";
    }


    /**
     * Adds the movie to the ratedMovies list of the user and adds the grade
     * to the ratings list of the movie
     * @param title
     * @param movieMap
     * @param grade
     * @return a string, saying if the process has been done successfully
     */
    public String rateMovie(final String title, final HashMap<String, Video> movieMap,
                            final double grade) {
        if (this.history.get(title) != null) {
            for (String i : this.ratedMovies) {
                if (i.equals(title)) {
                    return "error -> " + title + " has been already rated";
                }
            }
            this.ratedMovies.add(title);
            ((Movie) (movieMap.get(title))).getRatings().add(grade);
            return "success -> " + title + " was rated with " + grade + " by " + this.username;
        }
        return "error -> " + title + " is not seen";
    }


    /**
     * Adds the season to the ratedSeasons list of the user and adds the grade
     * to the ratings list of the season
     * @param title
     * @param serialMap
     * @param grade
     * @param season
     * @return a string, saying if the process has been done successfully
     */
    public String rateSerial(final String title, final HashMap<String, Video> serialMap,
                             final double grade, final int season) {
        if (this.history.get(title) != null) {
            for (Season i : this.ratedSeasons) {
                if (i.equals(((Serial) serialMap.get(title)).getSeasons().get(season - 1))) {
                    return "error -> " + title + " has been already rated";
                }
            }
            this.ratedSeasons.add(((Serial) serialMap.get(title)).getSeasons().get(season - 1));
            ((Serial) serialMap.get(title)).getSeasons().get(season - 1).getRatings().add(grade);
            return "success -> " + title + " was rated with " + grade + " by " + this.username;
        }
        return "error -> " + title + " is not seen";
    }

    /**
     *
     * @param query
     * @param userArrayList
     * @return a string containing the names of the users with the biggst number of ratings given
     */
    public static String mostActiveUsers(final Action query, final ArrayList<User> userArrayList) {
        String message = "Query result: [";
        List<User> list = new ArrayList<>();

        for (User i : userArrayList) {
            if (i.ratedSeasons.size() + i.ratedMovies.size() != 0) {
                list.add(i);
            }
        }

        list.sort(new Comparator<>() {
            @Override
            public int compare(final User o1, final User o2) {
                if (o1.ratedSeasons.size() + o1.ratedMovies.size()
                        - o2.ratedSeasons.size() - o2.ratedMovies.size() == 0) {
                    return o1.username.compareTo(o2.username);
                }
                return (o1.ratedSeasons.size() + o1.ratedMovies.size()
                        - o2.ratedSeasons.size() - o2.ratedMovies.size());
            }
        });

        if (list.size() == 0) {
            return "Query result: []";
        }

        int nr = Math.min(list.size(), query.getNumber());

        if (query.getSortType().equals("asc")) {
            for (int i = 0; i < nr - 1; i++) {
                message += list.get(i).username + ", ";
            }
            message += list.get(nr - 1).username;
        } else {
            for (int i = list.size() - 1; i >= list.size() - nr + 1; i--) {
                message += list.get(i).username + ", ";
            }
            message += list.get(list.size() - nr).username;
        }
        message += "]";
        return message;
    }



}
