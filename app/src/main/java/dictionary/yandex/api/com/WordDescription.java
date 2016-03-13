package dictionary.yandex.api.com;

import java.util.ArrayList;

public class WordDescription {

    private ArrayList<Rank> ranking = new ArrayList<>();

    public void addRank(final Rank translation) {
        ranking.add(translation);
    }

    public ArrayList<Rank> getRanking() {
        return ranking;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < ranking.size(); i++) {
            stringBuffer.append(ranking.get(i) + "<br /><br />");
        }
        return stringBuffer.toString();
    }
}

class Rank {
    private String text;
    private String partOfSpeech;
    private String transcription;
    private ArrayList<Translation> translations = new ArrayList<>();

    public void setText(final String text) {
        this.text = text;
    }

    public void setPartOfSpeech(final String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public void setTranscription(final String transcription) {
        this.transcription = transcription;
    }

    public String getText() {
        return text;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getTranscription() {
        return transcription;
    }

    public void addTranslation(final Translation translation) {
        translations.add(translation);
    }

    public ArrayList<Translation> getTranslation() {
        return translations;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<font color=\"black\">" + text + "</font> ");
        if (transcription != null) {
            stringBuffer.append("[" + transcription + "]");
        }
        if (partOfSpeech != null) {
            stringBuffer.append(" <font color=\"green\">" + partOfSpeech + "</font>");
        }

        for (int i = 0; i < translations.size(); i++) {
            stringBuffer.append("<br />" + (i + 1) + " " + translations.get(i));
        }
        return stringBuffer.toString();
    }
}

class Translation {
    private String text;
    private String partOfSpeech;
    private String gen;
    private ArrayList<Synonym> synonyms = new ArrayList<>();
    private ArrayList<Meaning> meanings = new ArrayList<>();
    private ArrayList<Example> examples = new ArrayList<>();

    public void setText(final String text) {
        this.text = text;
    }

    public void setPartOfSpeech(final String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public void setGen(final String gen) {
        this.gen = gen;
    }

    public void addSynonym(final Synonym synonym) {
        synonyms.add(synonym);
    }

    public void addMeaning(final Meaning meaning) {
        meanings.add(meaning);
    }

    public void addExample(final Example example) {
        examples.add(example);
    }

    public String getText() {
        return text;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getGen() {
        return gen;
    }

    public ArrayList<Synonym> getSynonyms() {
        return synonyms;
    }

    public ArrayList<Meaning> getMeanings() {
        return meanings;
    }

    public ArrayList<Example> getExamples() {
        return examples;
    }
    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<font color=\"blue\">" + text + "</font>");
        if (gen != null) {
            stringBuffer.append(" " + gen);
        }

        for (int i = 0; i < synonyms.size(); i++) {
            stringBuffer.append(", " + synonyms.get(i));
        }

        if (meanings.size() > 0)
            stringBuffer.append("<br />&nbsp;&nbsp;&nbsp;&nbsp;<font color=\"red\">(");
        for (int i = 0; i < meanings.size(); i++) {
            stringBuffer.append(meanings.get(i) + (i != meanings.size() - 1 ? ", " : ""));
        }
        if (meanings.size() > 0)
            stringBuffer.append(")</font>");

        if (examples.size() > 0) {
            stringBuffer.append("<br />");
        }
        for (int i = 0; i < examples.size(); i++) {
            stringBuffer.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + examples.get(i) + (i != examples.size() - 1 ? "<br />" : ""));
        }

        return stringBuffer.toString();
    }
}

class Synonym {
    private String text;
    private String partOfSpeech;
    private String gen;

    public void setText(final String text) {
        this.text = text;
    }

    public void setPartOfSpeech(final String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public void setGen(final String gen) {
        this.gen = gen;
    }

    public String getText() {
        return text;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getGen() {
        return gen;
    }

    @Override
    public String toString() {
        return "<font color=\"blue\">" + text  + "</font>" + (gen != null ? " " + gen : "");
    }
}

class Meaning {
    private String text;

    public void setText(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

}

class Example {
    private String text;
    private ArrayList<String> translations = new ArrayList<>();

    public void setText(final String text) {
        this.text = text;
    }

    public void addTranslate(final String translate) {
        translations.add(translate);
    }

    public String getText() {
        return text;
    }

    public ArrayList<String> getTranlations() {
        return translations;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(text + " - ");
        for (int i = 0; i < translations.size(); i++) {
            stringBuffer.append(translations.get(i) + (i != translations.size() - 1 ? ", " : ""));
        }
        return stringBuffer.toString();
    }
}
