package com.example.research.Content.Algorithm;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Volume {
    private Set<VolConnect> connections;

    public Set<VolConnect> get(){
        return connections;
    }
    
    public VolConnect get(String name){
        return connections.stream()
                .filter(c -> c.getName().equals(name))
                .collect(Collectors.toList()).get(0);
    }

    public Volume add(VolConnect connect){
        connections.add(connect);
        return this;
    }
}
