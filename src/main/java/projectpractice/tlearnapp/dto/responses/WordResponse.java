package projectpractice.tlearnapp.dto;

public record WordResponse(String word,
                              String transcription,
                              String translation,
                              String partOfSpeech,
                              String categoryName) {
}
