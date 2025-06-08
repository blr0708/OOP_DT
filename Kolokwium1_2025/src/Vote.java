import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vote
{
    private final Map<Candidate, Integer> votesForCandidate;
    private final List<String> location;
    private Long totalVotesCache = null;

    public Vote(Map<Candidate, Integer> votesForCandidate, List<String> location)
    {
        this.votesForCandidate = new HashMap<>(votesForCandidate);
        this.location = new ArrayList<>(location);
    }

    public Map<Candidate, Integer> getVotesForCandidate()
    {
        return new HashMap<>(this.votesForCandidate);
    }

    public List<String> getLocation() {
        return new ArrayList<>(this.location);
    }

    public static Vote fromCsvLine(String scvLine, List<Candidate> allCandidates)
    {
        String[] parts = scvLine.split(",");
        List<String> location = List.of(parts[2], parts[1],parts[0]);
        Map<Candidate, Integer> votesMap = new HashMap<>();

        for(int i = 3; i< parts.length; i++)
        {
            int candidateIndex = i-3;
            if(candidateIndex<allCandidates.size())
            {
                Candidate candidate = allCandidates.get(candidateIndex);
                int voteCount = Integer.parseInt(parts[i]);
                votesMap.put(candidate, voteCount);
            }
        }
        return new Vote (votesMap, location);
    }
    public static Vote summarize(List<Vote> votesList)
    {
        Map<Candidate, Integer> summaryVotes = new HashMap<>();
        for(Vote vote : votesList)
        {
            for (Map.Entry<Candidate, Integer> entry : vote.votesForCandidate.entrySet())
            {
                summaryVotes.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }
        }
        return new Vote(summaryVotes, new ArrayList<>());
    }
    public int votes(Candidate candidate)
    {
        return this.votesForCandidate.getOrDefault(candidate, 0);
    }

    public double percentage(Candidate candidate) {
        // Просто просим готовую сумму
        long totalVotes = getTotalVotes();

        if (totalVotes == 0) return 0.0;

        int candidateVotes = votes(candidate);
        return (double) candidateVotes / totalVotes * 100.0;
    }

    public long getTotalVotes() {
        // Проверяем "блокнот"
        if (totalVotesCache == null) {
            // Он пуст! Нужно считать.
            System.out.println("СЧИТАЮ СУММУ ВПЕРВЫЕ..."); // Добавим для наглядности
            long sum = 0;
            for (int v : this.votesForCandidate.values()) {
                sum += v;
            }
            // Записываем результат в "блокнот"
            this.totalVotesCache = sum;
        }

        // Возвращаем значение из "блокнота"
        return totalVotesCache;
    }


    @Override // Эта аннотация говорит, что мы переопределяем стандартный метод
    public String toString() {
        // 1. Создаем пустой "строитель строк"
        StringBuilder reportBuilder = new StringBuilder();

        // 2. Добавляем заголовок
        reportBuilder.append("--- Результаты голосования ---\n");

        // 3. Проходим по всем записям в нашей карте голосов
        for (Map.Entry<Candidate, Integer> entry : this.votesForCandidate.entrySet()) {

            // Для каждой записи получаем кандидата и его голоса
            Candidate candidate = entry.getKey();
            int votes = entry.getValue();

            // Считаем его процент (используя наш метод из 9-го шага)
            double percent = this.percentage(candidate);

            // 4. Формируем красивую строку для одного кандидата и добавляем её к отчету
            // String.format - удобный способ форматирования строк
            String candidateLine = String.format("%s: %d голосов (%.2f %%)\n",
                    candidate.name(),
                    votes,
                    percent);
            reportBuilder.append(candidateLine);
        }

        // 5. Когда цикл закончился, возвращаем всё, что мы "настроили"
        return reportBuilder.toString();
    }

}

