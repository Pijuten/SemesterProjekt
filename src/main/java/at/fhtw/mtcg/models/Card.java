package at.fhtw.mtcg.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class Card {
    @JsonAlias({"Id"})
    private String id;
    @JsonAlias({"Name"})
    private String name;
    @JsonAlias({"Damage"})
    private float damage;
    public Card(String id, String name, float damage){

        this.id = id;
        this.name = name;
        this.damage = damage;
    }
}
