import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ElectionTurn
{
    private final List<Candidate> candidates;
    private List<Vote> votes;
    public ElectionTurn(List<Candidate> candidates) {
        this.candidates = new ArrayList<>(candidates);
    }

    public List<Candidate> getCandidates() {
        return new ArrayList<>(candidates);
    }

    public void populate(String filePath) throws IOException
    {
        this.votes = Files.lines(Paths.get(filePath))
                .skip(1)
                .map(line->Vote.fromCsvLine(line, this.candidates))
                .collect(Collectors.toList());
    }
}
