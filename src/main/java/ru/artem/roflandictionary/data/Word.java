package ru.artem.roflandictionary.data;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
public class Word {

    @Id
    private String value;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> definition;

    //todo
    private String transcription;

    private String extra;

    public void addDefinition(String definition){
        this.definition.add(definition);
    }
}
