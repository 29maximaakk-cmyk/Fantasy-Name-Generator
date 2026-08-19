// fantasy_name_gen.go
package main

import (
	"flag"
	"fmt"
	"math/rand"
	"os"
	"strings"
	"time"
)

var races = map[string]map[string][]string{
	"elf": {
		"male":   {"Ae", "El", "Ael", "An", "Ar", "Bel", "Cal", "Celeb", "Eär", "Eld", "Elr", "Fin", "Gil", "Glor", "Hald", "Isil", "Leg", "Lind", "Lúth", "Maed", "Mag", "Mir", "Nim", "Oro", "Rían", "Saur", "Sil", "Tel", "Thing", "Tuor", "Vor", "Aran", "Beleg", "Beren", "Círd"},
		"female": {"Aew", "An", "Ar", "Ared", "Arwen", "Cal", "Cala", "Celeb", "El", "Eär", "El", "Elen", "Elr", "Eow", "Eä", "Findu", "Galad", "Gald", "Gil", "Glor", "Idril", "Lúth", "Míri", "Nim", "Sil", "Tinú", "Ung", "Van", "Varda", "Yav", "Zira"},
	},
	"orc": {
		"male":   {"Az", "Bash", "Borg", "Burz", "Dush", "Ghâsh", "Ghol", "Ghor", "Goth", "Grak", "Grish", "Grom", "Gul", "Karg", "Lug", "Maug", "Mog", "Mok", "Nazg", "Rak", "Rat", "Shag", "Skull", "Snaga", "Tark", "Thrak", "Ugl", "Uruk", "Zog"},
		"female": {"Ag", "As", "Bagh", "Borga", "Dusha", "Ghrá", "Ghâsh", "Ghola", "Ghora", "Gotha", "Grak", "Grisha", "Groma", "Gula", "Karga", "Luga", "Mauga", "Moga", "Moka", "Nazga", "Raka", "Rata", "Shaga", "Skulla", "Snaga", "Tarka", "Thraka", "Ugla", "Uruka", "Zoga"},
	},
	"human": {
		"male":   {"Ad", "Al", "Ald", "Ar", "Aric", "Bael", "Bald", "Bri", "Ced", "Cor", "Dael", "Dar", "Dun", "Ead", "Erik", "Far", "Gar", "Grim", "Hald", "Har", "Ing", "Jar", "Kael", "Leif", "Lor", "Mar", "Ned", "Nor", "Osw", "Ragn", "Ran", "Rolf", "Sig", "Sven", "Theo", "Thor", "Ulf", "Val", "Vid", "Wulf"},
		"female": {"Ad", "Al", "Alda", "Ari", "Asta", "Bael", "Bald", "Bri", "Ceda", "Cora", "Dael", "Dara", "Duna", "Eada", "Erika", "Fara", "Gara", "Grima", "Halda", "Hara", "Inga", "Jara", "Kaela", "Leifa", "Lora", "Mara", "Neda", "Nora", "Os", "Ragna", "Rana", "Rolfa", "Siga", "Svena", "Thea", "Thora", "Ulf", "Vala", "Vida", "Wulf"},
	},
	"dragon": {
		"male":   {"Alar", "Aur", "Bel", "Brin", "Chron", "Drak", "Ember", "Faf", "Flam", "Glaed", "Gor", "Hrath", "Ign", "Ith", "Kael", "Kor", "Lor", "Mal", "Mor", "Nad", "Or", "Pyros", "Rath", "Rogn", "Saph", "Sha", "Smaug", "Tar", "Thorn", "Ulf", "Val", "Vir", "Wyr", "Xar", "Zar"},
		"female": {"Alara", "Aura", "Bela", "Brina", "Chrona", "Draka", "Embera", "Fafa", "Flama", "Glaeda", "Gora", "Hratha", "Igna", "Itha", "Kaela", "Kora", "Lora", "Mala", "Mora", "Nada", "Ora", "Pyra", "Ratha", "Rogna", "Sapha", "Shara", "Smauga", "Tara", "Thorna", "Ulfa", "Vala", "Vira", "Wyra", "Xara", "Zara"},
	},
}

var fallback = []string{"Ael", "Bel", "Cal", "Dal", "El", "Far", "Gar", "Hald", "Ian", "Jar", "Kael", "Lor", "Mar", "Nor", "Or", "Par", "Quen", "Ral", "Sar", "Tal", "Val", "Wen", "Xan", "Yor", "Zan"}

func generateName(race, gender string, minLen, maxLen int) string {
	var pool []string
	if race == "any" {
		keys := make([]string, 0, len(races))
		for k := range races {
			keys = append(keys, k)
		}
		race = keys[rand.Intn(len(keys))]
	}
	if gender == "any" {
		if rand.Intn(2) == 0 {
			gender = "male"
		} else {
			gender = "female"
		}
	}
	if r, ok := races[race]; ok {
		if g, ok := r[gender]; ok {
			pool = g
		}
	}
	if len(pool) == 0 {
		pool = fallback
	}
	for {
		numParts := rand.Intn(3) + 1
		var parts []string
		for i := 0; i < numParts; i++ {
			parts = append(parts, pool[rand.Intn(len(pool))])
		}
		name := strings.Join(parts, "")
		if len(name) < minLen || len(name) > maxLen {
			continue
		}
		return strings.ToUpper(name[:1]) + name[1:]
	}
}

func main() {
	var (
		count  = flag.Int("n", 1, "Number of names")
		gender = flag.String("g", "any", "Gender: male, female, any")
		race   = flag.String("r", "any", "Race: elf, orc, human, dragon, any")
		output = flag.String("o", "", "Output file")
		minLen = flag.Int("min", 2, "Minimum length")
		maxLen = flag.Int("max", 12, "Maximum length")
	)
	flag.Parse()
	rand.Seed(time.Now().UnixNano())

	names := make([]string, *count)
	for i := 0; i < *count; i++ {
		names[i] = generateName(*race, *gender, *minLen, *maxLen)
	}

	if *output != "" {
		f, err := os.Create(*output)
		if err != nil {
			fmt.Fprintf(os.Stderr, "Error creating file: %v\n", err)
			os.Exit(1)
		}
		defer f.Close()
		for _, name := range names {
			f.WriteString(name + "\n")
		}
		fmt.Printf("✅ Saved %d names to %s\n", len(names), *output)
	} else {
		fmt.Printf("\n🧙 Fantasy Name Generator (Race: %s, Gender: %s)\n", *race, *gender)
		for i, name := range names {
			fmt.Printf("%d. %s\n", i+1, name)
		}
	}
}
