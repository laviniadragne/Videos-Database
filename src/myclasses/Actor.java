package myclasses;

import actor.ActorsAwards;
import entertainment.Season;
import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.regex.Pattern;

public final class Actor {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;

    private ArrayList<String> serials;

    private ArrayList<String> movies;

    private double rating;

    private int indexDataBase;

    private int numberAwards;

    public Actor(final String name, final String careerDescription,
                          final ArrayList<String> filmography,
                          final Map<ActorsAwards, Integer> awards, int indexDataBase) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.serials = new ArrayList<>();
        this.movies = new ArrayList<>();
        this.rating = 0;
        this.indexDataBase = indexDataBase;
        this.numberAwards = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public ArrayList<String> getSerials() {
        return serials;
    }

    public void setSerials(ArrayList<String> serials) {
        this.serials = serials;
    }

    public ArrayList<String> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<String> movies) {
        this.movies = movies;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getIndexDataBase() {
        return indexDataBase;
    }

    public void setIndexDataBase(int indexDataBase) {
        this.indexDataBase = indexDataBase;
    }

    public int getNumberAwards() {
        return numberAwards;
    }

    public void setNumberAwards(int numberAwards) {
        this.numberAwards = numberAwards;
    }

    public void calculateRating(List<Movie> myMovies, List<Serial> mySerials){

        double sumMovies = 0;
        int nrMovies = 0;
        for(String movie : movies) {
            for(Movie myMovie : myMovies) {
                if (myMovie.getTitle().equals(movie)) {
                    double ratingMovie = myMovie.sumRatings();
                    if (ratingMovie != 0) {
                        sumMovies += ratingMovie;
                        nrMovies++;
                    }
                }
            }
        }


        double sumSerial = 0;
        int nrSerial = 0;
        for (String serial : serials) {
            for (Serial mySerial : mySerials) {
                if (mySerial.getTitle().equals(serial)) {
                    // pentru fiecare sezon voi avea un hashmap - serialMap
                    double ratingSerial = mySerial.sumRatings();
                    if(ratingSerial != 0) {
                        sumSerial += ratingSerial;
                        nrSerial++;
                    }
                }
            }
        }

        if (nrMovies + nrSerial != 0) {
            this.rating = (double) (sumMovies + sumSerial) / (nrMovies + nrSerial);
        }
    }


    // sortez filmele si serialele pe liste separate
    public void filterShow(ArrayList<String> filmography, List<MovieInputData> moviesInput, List<SerialInputData> serialsInput) {
        for (String videos : filmography) {
            for(MovieInputData inputM : moviesInput)
                if (inputM.getTitle().equals(videos)) {
                    this.movies.add(videos);
                    break;
            }
            for(SerialInputData inputS : serialsInput)
                if (inputS.getTitle().equals(videos)) {
                    this.serials.add(videos);
                    break;
                }
        }
    }

    public String filterDescription(List<List<String>> filters) {

        int found = 1;
        // caut in lista de cuvinte
        List <String> filter = filters.get(2);
        // caut in lista de liste fiecare cuvant
            if (filter != null) {
                for (String word : filter) {
                    // daca nu gasesc un cuvant dau break;
                    if (word != null) {
                        word = " " + word + " ";
                        if (!Pattern.compile(Pattern.quote(word), Pattern.CASE_INSENSITIVE).matcher(getCareerDescription()).find()) {
                            found = 0;
                            break;
                        }
                    }
                }
            }
        // daca toate cuvintele au fost gasite
        if (found == 1) {
            return getName();
        }
        return null;
    }

//    public String awards (List<String> awards) {
//        boolean found = true;
//        for (String award : awards) {
//            // trebuie sa-i trimit ca parametru un ActorsAwards, nu un string
//
//            // TO DO
//            Integer numAwards = getAwards().get(award);
//            // premiul respectiv nu este castigat de acel actor
//            if(numAwards == null) {
//                found = false;
//                break;
//            }
//        }
//        if (found == true) {
//            return getName();
//        }
//        else {
//            return null;
//        }
//    }

    // metoda calculeaza numarul total de premii din cele de la input
    // primite de un actor si returneaza 0 in cazul in care
    // actorul nu are toate premiile
    public int awards (List<String> awards) {
        int noAwards = awards.size();
        // parcurg map-ul de ActorAwards
        for (Map.Entry<ActorsAwards,Integer> entry : getAwards().entrySet()) {
            for (String award : awards) {
                // am gasit unul din premiile din lista actorului in lista
                // de input
                if (entry.getKey().toString().equals(award)) {
                    noAwards--;
                }
            }
        }

        // am gasit toate premiile
        if (noAwards == 0) {
            numberAwards = totalAwards();
            return  numberAwards;
        }
        else {
            return 0;
        }
    }

    // calculeaza numarul total de premii ale unui actor
    public int totalAwards () {
        int total = 0;
        for (Map.Entry<ActorsAwards,Integer> entry : this.getAwards().entrySet()) {
            if (entry.getValue() != 0) {
                total += entry.getValue();
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}