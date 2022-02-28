package entertainment;

import common.Action;

import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.String.valueOf;

public abstract class Video {
    protected final String title;
    protected final int year;
    protected final ArrayList<String> cast;
    protected final ArrayList<String> genres;
    protected int nrFavoriteAppearances;
    protected int nrViews;

    public Video(final String title, final int year, final ArrayList<String> cast,
                 final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        nrViews = 0;
        nrFavoriteAppearances = 0;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    public final int getNrFavoriteAppearances() {
        return nrFavoriteAppearances;
    }

    public final int getNrViews() {
        return nrViews;
    }

    public abstract int getDuration();

    /**
     * Increments the value of the nrFavoriteAppearances of a video
     */
    public void incrementNrFavoriteAppearances() {
        nrFavoriteAppearances++;
    }

    /**
     * Adds value of x to the value of the nrViews field of the video
     * @param x
     */
    public void addToNrViews(final int x) {
        nrViews += x;
    }

    /**
     * @return the average rating of the video
     */
    public abstract double getRating();


    /**
     * @param query
     * @param movieArrayList
     * @return a String containing the titles of the longest movies/serials
     */
    public static String longestVideos(final Action query, final ArrayList<Video> movieArrayList) {
        String message = "Query result: [";
        ArrayList<Video> list = new ArrayList<>();
        for (Video i : movieArrayList) {
            i.addToList(query, list);
        }

        list.sort(new Comparator<>() {
            @Override
            public int compare(final Video o1, final Video o2) {
                if (o1.getDuration() - o2.getDuration() == 0) {
                    return o1.title.compareTo(o2.title);
                }
                return o1.getDuration() - o2.getDuration();
            }
        });

        return printedList(list, query.getSortType(), query.getNumber());
    }

    /**
     * @param query
     * @param videoArrayList
     * @return a String containing the titles of the movies/serials
     * with the most favorite appearances
     */
    public static String favoriteVideos(final Action query, final ArrayList<Video> videoArrayList) {
        String message = "Query result: [";
        ArrayList<Video> list = new ArrayList<>();
        for (Video i : videoArrayList) {
            if (i.nrFavoriteAppearances != 0) {
                i.addToList(query, list);
            }
        }

        list.sort(new Comparator<>() {
            @Override
            public int compare(final Video o1, final Video o2) {
                if (o1.nrFavoriteAppearances - o2.nrFavoriteAppearances == 0) {
                    return o1.title.compareTo(o2.title);
                }
                return o1.nrFavoriteAppearances - o2.nrFavoriteAppearances;
            }
        });

        return printedList(list, query.getSortType(), query.getNumber());
    }

    /**
     * @param query
     * @param videoArrayList
     * @return a String containing the titles of the best rated movies/serials
     */
    public static String bestRatedVideos(final Action query,
                                         final ArrayList<Video> videoArrayList) {
        String message = "Query result: [";
        ArrayList<Video> list = new ArrayList<>();
        for (Video i : videoArrayList) {
            if (i.getRating() != 0) {
                i.addToList(query, list);
            }
        }

        list.sort(new Comparator<>() {
            @Override
            public int compare(final Video o1, final Video o2) {
                return Double.compare(o1.getRating(), o2.getRating());
            }
        });

        return printedList(list, query.getSortType(), query.getNumber());
    }

    /**
     * @param query
     * @param videoArrayList
     * @return a String containing the titles of the most viewed movies/serials
     */
    public static String mostViewedVideos(final Action query,
                                          final ArrayList<Video> videoArrayList) {
        ArrayList<Video> list = new ArrayList<>();
        for (Video i : videoArrayList) {
            if (i.nrViews != 0) {
                i.addToList(query, list);
            }
        }

        list.sort(new Comparator<>() {
            @Override
            public int compare(final Video o1, final Video o2) {
                if (o1.nrViews - o2.nrViews == 0) {
                    return o1.title.compareTo(o2.title);
                }
                return (o1.nrViews - o2.nrViews);
            }
        });

        return printedList(list, query.getSortType(), query.getNumber());
    }


    private static String printedList(final ArrayList<Video> list, final String sortType,
                                      final int nr) {
        if (list.size() == 0) {
            return "Query result: []";
        }

        StringBuilder message = new StringBuilder();
        message.append("Query result: [");

        int number = Math.min(list.size(), nr);

        if (sortType.equals("asc")) {
            for (int i = 0; i < number - 1; i++) {
                message.append(list.get(i).title + ", ");
            }
            message.append(list.get(number - 1).title);
        } else {
            for (int i = list.size() - 1; i >= list.size() - number + 1; i--) {
                message.append(list.get(i).title + ", ");
            }
            message.append(list.get(list.size() - number).title);
        }
        message.append("]");
        return message.toString();
    }

    private void addToList(final Action query, final ArrayList<Video> list) {
        if (valueOf(this.year).equals(query.getFilters().get(0).get(0))
                || query.getFilters().get(0).get(0) == null) {
            if (query.getFilters().get(1).get(0) == null) {
                list.add(this);
            } else {
                for (String j : this.genres) {
                    if (j.equals(query.getFilters().get(1).get(0))) {
                        list.add(this);
                    }
                }
            }
        }
    }

}
