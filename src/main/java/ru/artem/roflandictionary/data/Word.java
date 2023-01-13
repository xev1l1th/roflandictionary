package ru.artem.roflandictionary.data;

import lombok.Data;
import lombok.ToString;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.Set;

@Entity
@Data
@ToString
public class Word {

    @Id
    private String value;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> definition;

    //todo
    private String transcription;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> extra;

    public void addDefinition(String definition){
        this.definition.add(definition);
    }
}
