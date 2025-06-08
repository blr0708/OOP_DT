import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Path.*;

public class Election
{
    private List<Candidate> candidates;
    private ElectionTurn firstTurn;
    private ElectionTurn secondTurn;
    //Empty constructor
    // Ten wiersz tworzy prawdziwy, choć pusty, obiekt listy w pamięci
    // i przypisuje do niego odwołanie do pola this.candidates.
    public Election() {
        this.candidates = new ArrayList<>();
        this.firstTurn = null;
        this.secondTurn = null;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public ElectionTurn getFirstTurn() {
        return firstTurn;
    }

    public ElectionTurn getSecondTurn() {
        return secondTurn;
    }

    //metodа, która zwraca nową listę będącą kopią oryginalnej listy candidates
    public List<Candidate> copyCandidates()
    {
        return new ArrayList<>(candidates);
    }

    public void populateCandidates(String filePath) throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        this.candidates.clear(); // Wyczyścimy to w przypadku ponownego połączenia
        for(String line : lines){
            if(!line.trim().isEmpty())
            {
                this.candidates.add(new Candidate(line.trim()));
            }
        }
    }
    public void populate(String candidatesPath,String firstTurnPath, String secondTurnPath)
    {
        try {
            this.populateCandidates(candidatesPath);
            System.out.println("Kandydaci zostali pomyślnie załadowani. Ilość: " + this.candidates.size());

            this.firstTurn = new ElectionTurn(this.candidates);
            System.out.println("Объект первого тура создан.");
            this.firstTurn.populate(firstTurnPath);
            System.out.println("Данные первого тура успешно загружены.");

        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu plika kandydat: " + e.getMessage());
        }
    }

}
