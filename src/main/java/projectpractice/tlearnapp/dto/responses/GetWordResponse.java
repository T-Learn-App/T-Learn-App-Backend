package projectpractice.tlearnapp.dto.responses;

public record GetWordResponse(String word,
                              String transcription,
                              String translation,
                              String partOfSpeech) {
}
