package entertainment;


import fileio.MovieInputData;
import java.util.ArrayList;


public final class Movie extends Video {
    private ArrayList<Double> ratings;
    private int duration;

    public Movie(final MovieInputData movieInput) {
        super(movieInput.getTitle(), movieInput.getYear(), movieInput.getCast(),
                movieInput.getGenres());
        this.duration = movieInput.getDuration();
        this.ratings = new ArrayList<>();
    }

    public ArrayList<Double> getRatings() {
        return ratings;
    }

    public int getDuration() {
        return duration;
    }

    /**
     * @return the average rating of the movie
     */
    public double getRating() {
        double r = 0;
        for (double i : this.ratings) {
            r += i;
        }

        if (r == 0) {
           return 0;
        }
        return r / this.ratings.size();
    }


}
