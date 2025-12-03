package projectpractice.tlearnapp.dto.responses;

public record WordResponse(String word,
                              String transcription,
                              String translation,
                              String partOfSpeech,
                              Long category) {
}
