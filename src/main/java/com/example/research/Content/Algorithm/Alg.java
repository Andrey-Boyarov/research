package com.example.research.Content.Algorithm;

import com.example.research.Content.Validation.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Alg {

    //two signs after dot only

    public Set<FormalConcept> getContextSet(Context context){
        Set<String> subs = new HashSet<>(context.getOwners());
        Set<Set<String>> props = context.getQualities().stream().map(q -> {
            Set<String> name = new HashSet<>();
            name.add(q.getName());
            return name;
        }).collect(Collectors.toSet());
        Set<FormalConcept> result = new HashSet<>();
        List<VolConnect> counter = getCounter(subs);
        do {
            Set<VolConnect> connects = counter.stream().filter(c -> !eq(c.getValue(), 0d)).collect(Collectors.toSet());
            Volume vol = new Volume(connects);
            FormalConcept fc = getFormalConcept(vol, subs, props);
            if (fc != null) result.add(fc);
        } while (increaseCounter(counter));
        return result;
    }

    public FormalConcept getFormalConcept(Volume volume, Set<String> subs, Set<Set<String>> props){
        Contents contents = new Contents();
        props.forEach(prop -> {
            contents.add(new ConConnect(aA(volume, subs, prop), prop));
        });
        Volume newVolume = new Volume();
        subs.forEach(sub -> {
            newVolume.add(new VolConnect(aB(contents, sub, props), sub));
        });
        if (volume.equals(newVolume))
        return new FormalConcept(volume, contents);
        return null;
    }

    public Double aLuk(Double a, Double b){        // ->
        return Double.min(1, 1 - a + b);
    }

    public Double aMaxim(Double a, Double b){
        return Double.max(0, a + b - 1);
    }

    public Double aI(Context context, String sub, Set<String> prop){
        return context.get(sub, prop.stream().reduce((s1, s2)->s1).orElse(""));//todo String -> Set<String> -> ArList
    }

    public Double aA(Volume vol, Set<String> subs, Set<String> prop){
        return subs.stream()
                .map(sub -> {
                    VolConnect connect = vol.get(sub);
                    if (connect == null) return 0d;
                    else return connect.getValue();
                })
                .reduce(this::aMaxim)
                .orElse(null);
    }

    public Double aB(Contents con, String sub, Set<Set<String>> props){
        return props.stream()
                .map(prop -> {
                    ConConnect connect = con.get(prop);
                    if (connect == null) return 0d;
                    else return connect.getValue();
                })
                .reduce(this::aMaxim)
                .orElse(null);
    }

    private Double aAInternal(Context context, Volume vol, String sub, Set<String> prop){
        return aLuk(vol.get(sub).getValue(), aI(context, sub, prop));
    }

    private Double aBInternal(Context context, Contents con, String sub, Set<String> prop){
        return aLuk(con.get(prop).getValue(), aI(context, sub, prop));
    }

    public static boolean eq(Double a, Double b){ return a - b < 0.01; }

    public List<VolConnect> getCounter(Set<String> subs){
        List<VolConnect> counter = new ArrayList<>();
        subs.forEach(sub -> counter.add(new VolConnect(0d, sub)));
        return counter;
    }

    public boolean increaseCounter(List<VolConnect> counter){
        int i = 0;
        while (true){
            counter.get(i).setValue(counter.get(i).getValue() + 0.01);
            if (counter.get(i).getValue() > 1d) {
                counter.get(i).setValue(0d);
                i++;
            } else {
                break;
            }
            if (i == counter.size()) return false;
        }
        return true;
    }
}
