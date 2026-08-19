// FantasyNameGen.cs
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

class FantasyNameGen
{
    static Dictionary<string, Dictionary<string, List<string>>> races = new Dictionary<string, Dictionary<string, List<string>>>()
    {
        ["elf"] = new Dictionary<string, List<string>>()
        {
            ["male"] = new List<string>{"Ae","El","Ael","An","Ar","Bel","Cal","Celeb","Eär","Eld","Elr","Fin","Gil","Glor","Hald","Isil","Leg","Lind","Lúth","Maed","Mag","Mir","Nim","Oro","Rían","Saur","Sil","Tel","Thing","Tuor","Vor","Aran","Beleg","Beren","Círd"},
            ["female"] = new List<string>{"Aew","An","Ar","Ared","Arwen","Cal","Cala","Celeb","El","Eär","El","Elen","Elr","Eow","Eä","Findu","Galad","Gald","Gil","Glor","Idril","Lúth","Míri","Nim","Sil","Tinú","Ung","Van","Varda","Yav","Zira"}
        },
        ["orc"] = new Dictionary<string, List<string>>()
        {
            ["male"] = new List<string>{"Az","Bash","Borg","Burz","Dush","Ghâsh","Ghol","Ghor","Goth","Grak","Grish","Grom","Gul","Karg","Lug","Maug","Mog","Mok","Nazg","Rak","Rat","Shag","Skull","Snaga","Tark","Thrak","Ugl","Uruk","Zog"},
            ["female"] = new List<string>{"Ag","As","Bagh","Borga","Dusha","Ghrá","Ghâsh","Ghola","Ghora","Gotha","Grak","Grisha","Groma","Gula","Karga","Luga","Mauga","Moga","Moka","Nazga","Raka","Rata","Shaga","Skulla","Snaga","Tarka","Thraka","Ugla","Uruka","Zoga"}
        },
        ["human"] = new Dictionary<string, List<string>>()
        {
            ["male"] = new List<string>{"Ad","Al","Ald","Ar","Aric","Bael","Bald","Bri","Ced","Cor","Dael","Dar","Dun","Ead","Erik","Far","Gar","Grim","Hald","Har","Ing","Jar","Kael","Leif","Lor","Mar","Ned","Nor","Osw","Ragn","Ran","Rolf","Sig","Sven","Theo","Thor","Ulf","Val","Vid","Wulf"},
            ["female"] = new List<string>{"Ad","Al","Alda","Ari","Asta","Bael","Bald","Bri","Ceda","Cora","Dael","Dara","Duna","Eada","Erika","Fara","Gara","Grima","Halda","Hara","Inga","Jara","Kaela","Leifa","Lora","Mara","Neda","Nora","Os","Ragna","Rana","Rolfa","Siga","Svena","Thea","Thora","Ulf","Vala","Vida","Wulf"}
        },
        ["dragon"] = new Dictionary<string, List<string>>()
        {
            ["male"] = new List<string>{"Alar","Aur","Bel","Brin","Chron","Drak","Ember","Faf","Flam","Glaed","Gor","Hrath","Ign","Ith","Kael","Kor","Lor","Mal","Mor","Nad","Or","Pyros","Rath","Rogn","Saph","Sha","Smaug","Tar","Thorn","Ulf","Val","Vir","Wyr","Xar","Zar"},
            ["female"] = new List<string>{"Alara","Aura","Bela","Brina","Chrona","Draka","Embera","Fafa","Flama","Glaeda","Gora","Hratha","Igna","Itha","Kaela","Kora","Lora","Mala","Mora","Nada","Ora","Pyra","Ratha","Rogna","Sapha","Shara","Smauga","Tara","Thorna","Ulfa","Vala","Vira","Wyra","Xara","Zara"}
        }
    };
    static List<string> fallback = new List<string>{"Ael","Bel","Cal","Dal","El","Far","Gar","Hald","Ian","Jar","Kael","Lor","Mar","Nor","Or","Par","Quen","Ral","Sar","Tal","Val","Wen","Xan","Yor","Zan"};

    static string GenerateName(string race, string gender, int minLen, int maxLen)
    {
        var rand = new Random();
        string r = race == "any" ? races.Keys.ElementAt(rand.Next(races.Count)) : race;
        string g = gender == "any" ? (rand.Next(2) == 0 ? "male" : "female") : gender;
        List<string> pool;
        if (races.ContainsKey(r) && races[r].ContainsKey(g))
            pool = races[r][g];
        else
            pool = fallback;
        while (true)
        {
            int numParts = rand.Next(1, 4);
            var parts = new List<string>();
            for (int i = 0; i < numParts; i++)
                parts.Add(pool[rand.Next(pool.Count)]);
            string name = string.Concat(parts);
            name = char.ToUpper(name[0]) + name.Substring(1);
            if (name.Length >= minLen && name.Length <= maxLen)
                return name;
        }
    }

    static void Main(string[] args)
    {
        var parsed = ParseArgs(args);
        int count = int.Parse(parsed.GetValueOrDefault("count", parsed.GetValueOrDefault("n", "1")));
        string gender = parsed.GetValueOrDefault("gender", parsed.GetValueOrDefault("g", "any"));
        string race = parsed.GetValueOrDefault("race", parsed.GetValueOrDefault("r", "any"));
        string output = parsed.GetValueOrDefault("output");
        int minLen = int.Parse(parsed.GetValueOrDefault("min", "2"));
        int maxLen = int.Parse(parsed.GetValueOrDefault("max", "12"));

        var names = new List<string>();
        for (int i = 0; i < count; i++)
            names.Add(GenerateName(race, gender, minLen, maxLen));

        if (output != null)
        {
            File.WriteAllLines(output, names);
            Console.WriteLine($"✅ Saved {names.Count} names to {output}");
        }
        else
        {
            Console.WriteLine($"\n🧙 Fantasy Name Generator (Race: {race}, Gender: {gender})");
            for (int i = 0; i < names.Count; i++)
                Console.WriteLine($"{i+1}. {names[i]}");
        }
    }

    static Dictionary<string, string> ParseArgs(string[] args)
    {
        var dict = new Dictionary<string, string>();
        for (int i = 0; i < args.Length; i++)
        {
            if (args[i].StartsWith("--"))
            {
                string key = args[i].Substring(2);
                if (i + 1 < args.Length && !args[i+1].StartsWith("--"))
                    dict[key] = args[++i];
                else
                    dict[key] = "";
            }
        }
        return dict;
    }
}
