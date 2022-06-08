package com.example.research.Content.Algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public class FormalConcept {
    private Set<GalConnect> connects;

    public Set<GalConnect> get(){
        return connects;
    }

    public FormalConcept add(GalConnect connect){
        connects.add(connect);
        return this;
    }

    public FormalConcept merge(Set<String> name1, Set<String> name2){
        GalConnect c1 = connects.stream().filter(c -> c.getName().equals(name1)).collect(Collectors.toList()).get(0);
        GalConnect c2 = connects.stream().filter(c -> c.getName().equals(name2)).collect(Collectors.toList()).get(0);
        Set<String> names = new HashSet<>();
        names.addAll(c1.getName());
        names.addAll(c2.getName());
        GalConnect res = new GalConnect(c1.getValue() + c2.getValue(), names);
        connects.remove(c1);
        connects.remove(c2);
        connects.add(res);
        return this;
    }
}
