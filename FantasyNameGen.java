// FantasyNameGen.java
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class FantasyNameGen {
    private static final Map<String, Map<String, List<String>>> RACES = new HashMap<>();
    private static final List<String> FALLBACK = Arrays.asList(
        "Ael", "Bel", "Cal", "Dal", "El", "Far", "Gar", "Hald", "Ian", "Jar",
        "Kael", "Lor", "Mar", "Nor", "Or", "Par", "Quen", "Ral", "Sar", "Tal",
        "Val", "Wen", "Xan", "Yor", "Zan"
    );
    static {
        RACES.put("elf", Map.of(
            "male", Arrays.asList("Ae", "El", "Ael", "An", "Ar", "Bel", "Cal", "Celeb", "Eär", "Eld", "Elr", "Fin", "Gil", "Glor", "Hald", "Isil", "Leg", "Lind", "Lúth", "Maed", "Mag", "Mir", "Nim", "Oro", "Rían", "Saur", "Sil", "Tel", "Thing", "Tuor", "Vor", "Aran", "Beleg", "Beren", "Círd"),
            "female", Arrays.asList("Aew", "An", "Ar", "Ared", "Arwen", "Cal", "Cala", "Celeb", "El", "Eär", "El", "Elen", "Elr", "Eow", "Eä", "Findu", "Galad", "Gald", "Gil", "Glor", "Idril", "Lúth", "Míri", "Nim", "Sil", "Tinú", "Ung", "Van", "Varda", "Yav", "Zira")
        ));
        RACES.put("orc", Map.of(
            "male", Arrays.asList("Az", "Bash", "Borg", "Burz", "Dush", "Ghâsh", "Ghol", "Ghor", "Goth", "Grak", "Grish", "Grom", "Gul", "Karg", "Lug", "Maug", "Mog", "Mok", "Nazg", "Rak", "Rat", "Shag", "Skull", "Snaga", "Tark", "Thrak", "Ugl", "Uruk", "Zog"),
            "female", Arrays.asList("Ag", "As", "Bagh", "Borga", "Dusha", "Ghrá", "Ghâsh", "Ghola", "Ghora", "Gotha", "Grak", "Grisha", "Groma", "Gula", "Karga", "Luga", "Mauga", "Moga", "Moka", "Nazga", "Raka", "Rata", "Shaga", "Skulla", "Snaga", "Tarka", "Thraka", "Ugla", "Uruka", "Zoga")
        ));
        RACES.put("human", Map.of(
            "male", Arrays.asList("Ad", "Al", "Ald", "Ar", "Aric", "Bael", "Bald", "Bri", "Ced", "Cor", "Dael", "Dar", "Dun", "Ead", "Erik", "Far", "Gar", "Grim", "Hald", "Har", "Ing", "Jar", "Kael", "Leif", "Lor", "Mar", "Ned", "Nor", "Osw", "Ragn", "Ran", "Rolf", "Sig", "Sven", "Theo", "Thor", "Ulf", "Val", "Vid", "Wulf"),
            "female", Arrays.asList("Ad", "Al", "Alda", "Ari", "Asta", "Bael", "Bald", "Bri", "Ceda", "Cora", "Dael", "Dara", "Duna", "Eada", "Erika", "Fara", "Gara", "Grima", "Halda", "Hara", "Inga", "Jara", "Kaela", "Leifa", "Lora", "Mara", "Neda", "Nora", "Os", "Ragna", "Rana", "Rolfa", "Siga", "Svena", "Thea", "Thora", "Ulf", "Vala", "Vida", "Wulf")
        ));
        RACES.put("dragon", Map.of(
            "male", Arrays.asList("Alar", "Aur", "Bel", "Brin", "Chron", "Drak", "Ember", "Faf", "Flam", "Glaed", "Gor", "Hrath", "Ign", "Ith", "Kael", "Kor", "Lor", "Mal", "Mor", "Nad", "Or", "Pyros", "Rath", "Rogn", "Saph", "Sha", "Smaug", "Tar", "Thorn", "Ulf", "Val", "Vir", "Wyr", "Xar", "Zar"),
            "female", Arrays.asList("Alara", "Aura", "Bela", "Brina", "Chrona", "Draka", "Embera", "Fafa", "Flama", "Glaeda", "Gora", "Hratha", "Igna", "Itha", "Kaela", "Kora", "Lora", "Mala", "Mora", "Nada", "Ora", "Pyra", "Ratha", "Rogna", "Sapha", "Shara", "Smauga", "Tara", "Thorna", "Ulfa", "Vala", "Vira", "Wyra", "Xara", "Zara")
        ));
    }

    private static String generateName(String race, String gender, int minLen, int maxLen) {
        List<String> pool = new ArrayList<>();
        String r = race.equals("any") ? (String) RACES.keySet().toArray()[new Random().nextInt(RACES.size())] : race;
        String g = gender.equals("any") ? (new Random().nextBoolean() ? "male" : "female") : gender;
        if (RACES.containsKey(r) && RACES.get(r).containsKey(g)) {
            pool = RACES.get(r).get(g);
        } else {
            pool = FALLBACK;
        }
        Random rand = new Random();
        while (true) {
            int numParts = rand.nextInt(3) + 1;
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < numParts; i++) {
                name.append(pool.get(rand.nextInt(pool.size())));
            }
            String result = name.toString();
            result = Character.toUpperCase(result.charAt(0)) + result.substring(1);
            if (result.length() >= minLen && result.length() <= maxLen) {
                return result;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                String key = args[i].substring(2);
                if (i+1 < args.length && !args[i+1].startsWith("--")) {
                    params.put(key, args[++i]);
                } else {
                    params.put(key, "");
                }
            }
        }
        int count = Integer.parseInt(params.getOrDefault("count", params.getOrDefault("n", "1")));
        String gender = params.getOrDefault("gender", params.getOrDefault("g", "any"));
        String race = params.getOrDefault("race", params.getOrDefault("r", "any"));
        String output = params.get("output");
        int minLen = Integer.parseInt(params.getOrDefault("min", "2"));
        int maxLen = Integer.parseInt(params.getOrDefault("max", "12"));

        List<String> names = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            names.add(generateName(race, gender, minLen, maxLen));
        }

        if (output != null) {
            Files.write(Paths.get(output), names);
            System.out.printf("✅ Saved %d names to %s%n", names.size(), output);
        } else {
            System.out.printf("\n🧙 Fantasy Name Generator (Race: %s, Gender: %s)%n", race, gender);
            for (int i = 0; i < names.size(); i++) {
                System.out.printf("%d. %s%n", i+1, names.get(i));
            }
        }
    }
}
