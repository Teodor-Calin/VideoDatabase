package main;

import actor.Actor;
import common.Action;
import common.User;
import entertainment.Genre;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import common.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static actor.Actor.averageActors;
import static actor.Actor.awardsActors;
import static actor.Actor.filterDescriptionActors;
import static common.User.mostActiveUsers;
import static entertainment.Video.bestRatedVideos;
import static entertainment.Video.favoriteVideos;
import static entertainment.Video.longestVideos;
import static entertainment.Video.mostViewedVideos;
import static utils.Utils.stringToGenre;

public final class Implement {

    private Implement() { }

    private static void writeToArray(final int id, final String message,
                                     final JSONArray arrayResult,
                                     final Writer fileWriter) throws IOException {
        JSONObject object = fileWriter.writeFile(id, "", message);
        arrayResult.add(object);
    }

    /**
     * The actual start of the program
     * @param input
     * @param arrayResult
     * @param fileWriter
     * @throws IOException
     */
    public static void start(final Input input, final JSONArray arrayResult,
                             final Writer fileWriter) throws IOException {
        String message = null;

        //ACTORS
        ArrayList<Actor> actorArrayList = new ArrayList<>();
        for (ActorInputData i : input.getActors()) {
            actorArrayList.add(new Actor(i));
        }

        // VIDEO
        HashMap<String, Video> videoMap = new HashMap<>();

        //GENRE
        HashMap<Genre, Integer> genreMap = new HashMap<>();
        for (Genre i : Genre.values()) {
            genreMap.put(i, 0);
        }

        // MOVIES
        ArrayList<Video> movieArrayList = new ArrayList<>();
        for (MovieInputData i : input.getMovies()) {
            movieArrayList.add(new Movie(i));
        }
        HashMap<String, Video> movieMap = new HashMap<>();
        for (Video i : movieArrayList) {
            movieMap.put(i.getTitle(), i);
            videoMap.put(i.getTitle(), i);
        }

        // SERIALE
        ArrayList<Video> serialArrayList = new ArrayList<>();
        for (SerialInputData i : input.getSerials()) {
            serialArrayList.add(new Serial(i));
        }
        HashMap<String, Video> serialMap = new HashMap<>();
        for (Video i : serialArrayList) {
            serialMap.put(i.getTitle(), i);
            videoMap.put(i.getTitle(), i);
        }

        // USERI
        ArrayList<User> userArrayList = new ArrayList<>();
        for (UserInputData i : input.getUsers()) {
            userArrayList.add(new User(i));
        }
        Map<String, User> userMap = new HashMap<>();
        for (User i : userArrayList) {
            userMap.put(i.getUsername(), i);
            for (Map.Entry j : i.getHistory().entrySet()) {
                String key = (String) (j.getKey());
                videoMap.get(key).addToNrViews(i.getHistory().get(key));
                int aux;
                for (String genreWord : videoMap.get(key).getGenres()) {
                    aux = i.getHistory().get(key) + genreMap.get(stringToGenre(genreWord));
                    genreMap.put(stringToGenre(genreWord), aux);
                }
            }
            for (String j : i.getFavoriteMovies()) {
                videoMap.get(j).incrementNrFavoriteAppearances();
            }
        }

        // ACTIUNI
        ArrayList<Action> actionArrayList = new ArrayList<>();
        for (ActionInputData i : input.getCommands()) {
            actionArrayList.add(new Action(i));
        }

        for (Action i : actionArrayList) {
            switch (i.getActionType()) {
                case Constants.COMMAND:
                    switch (i.getType()) {
                        case Constants.FAVORITE:
                            message = userMap.get(i.getUsername()).addToFavorites(i.getTitle(),
                                    videoMap);
                            break;
                        case Constants.VIEW:
                            message = userMap.get(i.getUsername()).viewVideo(i.getTitle(),
                                    videoMap, genreMap);
                            break;
                        case Constants.RATING:
                            if (i.getSeasonNumber() == 0) {
                                message = userMap.get(i.getUsername()).rateMovie(i.getTitle(),
                                        movieMap, i.getGrade());
                            } else {
                                message = userMap.get(i.getUsername()).rateSerial(i.getTitle(),
                                        serialMap, i.getGrade(),
                                        i.getSeasonNumber());
                            }
                            break;
                        default :
                    }
                    break;
                case Constants.QUERY:
                    switch (i.getObjectType()) {
                        case Constants.MOVIES:
                            switch (i.getCriteria()) {
                                case Constants.LONGEST -> {
                                    message = longestVideos(i, movieArrayList);
                                }
                                case Constants.FAVORITE -> {
                                    message = favoriteVideos(i, movieArrayList);
                                }
                                case Constants.RATINGS -> {
                                    message = bestRatedVideos(i, movieArrayList);
                                }
                                case Constants.MOST_VIEWED -> {
                                    message = mostViewedVideos(i, movieArrayList);
                                }
                                default -> { }
                            }
                            break;
                        case Constants.SHOWS:
                            switch (i.getCriteria()) {
                                case "most_viewed" -> {
                                    message = mostViewedVideos(i, serialArrayList);
                                }
                                case "favorite" -> {
                                    message = favoriteVideos(i, serialArrayList);
                                }
                                case "longest" -> {
                                    message = longestVideos(i, serialArrayList);
                                }
                                case "ratings" -> {
                                    message = bestRatedVideos(i, serialArrayList);
                                }
                                default -> { }
                            }
                        case Constants.USERS:
                            if (i.getCriteria() .equals(Constants.NUM_RATINGS)) {
                                message = mostActiveUsers(i, userArrayList);
                                break;
                            }
                        case Constants.ACTORS:
                            switch (i.getCriteria()) {
                                case Constants.AVERAGE -> {
                                    message = averageActors(i, actorArrayList, videoMap);
                                }
                                case Constants.AWARDS -> {
                                    message = awardsActors(i, actorArrayList);
                                }
                                case Constants.FILTER_DESCRIPTIONS -> {
                                    message = filterDescriptionActors(i, actorArrayList);
                                }
                                default -> { }
                            }
                            break;
                        default:
                    }
                    break;
                case Constants.RECOMMENDATION:
                    switch (i.getType()) {
                        case Constants.STANDARD -> {
                            message = userMap.get(i.getUsername()).
                                    standardRecommendation(movieArrayList, serialArrayList);
                        }
                        case Constants.BEST_UNSEEN -> {
                            message = userMap.get(i.getUsername()).
                                    bestUnseenRecommendation(movieArrayList, serialArrayList);
                        }
                        case Constants.FAVORITE -> {
                            message = userMap.get(i.getUsername()).
                                    favoriteRecommendation(movieArrayList, serialArrayList);
                        }
                        case Constants.SEARCH -> {
                            message = userMap.get(i.getUsername()).
                                    searchRecommendation(i.getGenre(), movieArrayList,
                                            serialArrayList);
                        }
                        case Constants.POPULAR -> {
                            message = userMap.get(i.getUsername()).
                                    popularRecommendation(movieArrayList, serialArrayList,
                                            genreMap);
                        }
                        default -> { }
                    }
                    break;
                default:
            }
            writeToArray(i.getActionId(), message, arrayResult, fileWriter);
        }
    }
}
