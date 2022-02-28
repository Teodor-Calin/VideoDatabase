package entertainment;


import fileio.SerialInputData;
import java.util.ArrayList;


public final class Serial extends Video {
    private int nrSeasons;
    private ArrayList<Season> seasons;

    public Serial(final SerialInputData serialInput) {
        super(serialInput.getTitle(), serialInput.getYear(), serialInput.getCast(),
                serialInput.getGenres());
        this.nrSeasons = serialInput.getNumberSeason();
        this.seasons = serialInput.getSeasons();
    }

    public int getNrSeasons() {
        return nrSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    /**
     * @return the average rating of the serial
     */
    public double getRating() {
        double rating = 0;
        for (Season i : this.seasons) {
            if (i.getRatings().size() != 0) {
                double r = 0;
                for (double j : i.getRatings()) {
                    r += j;
                }
                r /= i.getRatings().size();
                rating += r;
            }
        }
        if (rating == 0) {
            return 0;
        }
        rating /= this.nrSeasons;
        return rating;
    }

    /**
     * @return the total duration of the serial
     */
    public int getDuration() {
        int d = 0;
        for (Season i : this.getSeasons()) {
            d += i.getDuration();
        }
        return d;
    }


}
