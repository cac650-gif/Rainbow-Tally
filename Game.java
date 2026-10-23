
public class Game {
    private Team[] teams;

    public Game(Team[] teams) {
        this.teams = teams;
    }

    public Team getLeader() {
        Team leader = teams[0];

        for (int i = 1; i < teams.length; i++) {
            if (teams[i].getScore() > leader.getScore()) {
                leader = teams[i];
            }
        }

        return leader;
    }

    public boolean isTie() {
        int highestScore = teams[0].getScore();
        int teamsWithHighestScore = 0;

        for (int i = 0; i < teams.length; i++) {
            if (teams[i].getScore() > highestScore) {
                highestScore = teams[i].getScore();
                teamsWithHighestScore = 1;
            } else if (teams[i].getScore() == highestScore) {
                teamsWithHighestScore++;
            }
        }

        return teamsWithHighestScore > 1;
    }
}
