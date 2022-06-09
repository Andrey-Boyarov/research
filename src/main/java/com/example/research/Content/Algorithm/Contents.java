package com.example.research.Content.Algorithm;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class Contents {
    private Set<ConConnect> connections;

    public Set<ConConnect> get(){
        return connections;
    }

    public ConConnect get(Set<String> name){
        return connections.stream()
                .filter(c -> c.getName().equals(name))
                .collect(Collectors.toList()).get(0);
    }

    public Contents add(ConConnect connect){
        connections.add(connect);
        return this;
    }

    public Contents merge(Set<String> name1, Set<String> name2){
        ConConnect c1 = connections.stream().filter(c -> c.getName().equals(name1)).collect(Collectors.toList()).get(0);
        ConConnect c2 = connections.stream().filter(c -> c.getName().equals(name2)).collect(Collectors.toList()).get(0);
        Set<String> names = new HashSet<>();
        names.addAll(c1.getName());
        names.addAll(c2.getName());
        ConConnect res = new ConConnect(c1.getValue() + c2.getValue(), names);
        connections.remove(c1);
        connections.remove(c2);
        connections.add(res);
        return this;
    }
}
