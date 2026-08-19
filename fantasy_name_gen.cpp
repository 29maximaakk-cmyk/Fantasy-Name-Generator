// fantasy_name_gen.cpp
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <map>
#include <random>
#include <ctime>
#include <cctype>
#include <getopt.h>

using namespace std;

map<string, map<string, vector<string>>> races = {
    {"elf", {
        {"male", {"Ae","El","Ael","An","Ar","Bel","Cal","Celeb","Eär","Eld","Elr","Fin","Gil","Glor","Hald","Isil","Leg","Lind","Lúth","Maed","Mag","Mir","Nim","Oro","Rían","Saur","Sil","Tel","Thing","Tuor","Vor","Aran","Beleg","Beren","Círd"}},
        {"female", {"Aew","An","Ar","Ared","Arwen","Cal","Cala","Celeb","El","Eär","El","Elen","Elr","Eow","Eä","Findu","Galad","Gald","Gil","Glor","Idril","Lúth","Míri","Nim","Sil","Tinú","Ung","Van","Varda","Yav","Zira"}}
    }},
    {"orc", {
        {"male", {"Az","Bash","Borg","Burz","Dush","Ghâsh","Ghol","Ghor","Goth","Grak","Grish","Grom","Gul","Karg","Lug","Maug","Mog","Mok","Nazg","Rak","Rat","Shag","Skull","Snaga","Tark","Thrak","Ugl","Uruk","Zog"}},
        {"female", {"Ag","As","Bagh","Borga","Dusha","Ghrá","Ghâsh","Ghola","Ghora","Gotha","Grak","Grisha","Groma","Gula","Karga","Luga","Mauga","Moga","Moka","Nazga","Raka","Rata","Shaga","Skulla","Snaga","Tarka","Thraka","Ugla","Uruka","Zoga"}}
    }},
    {"human", {
        {"male", {"Ad","Al","Ald","Ar","Aric","Bael","Bald","Bri","Ced","Cor","Dael","Dar","Dun","Ead","Erik","Far","Gar","Grim","Hald","Har","Ing","Jar","Kael","Leif","Lor","Mar","Ned","Nor","Osw","Ragn","Ran","Rolf","Sig","Sven","Theo","Thor","Ulf","Val","Vid","Wulf"}},
        {"female", {"Ad","Al","Alda","Ari","Asta","Bael","Bald","Bri","Ceda","Cora","Dael","Dara","Duna","Eada","Erika","Fara","Gara","Grima","Halda","Hara","Inga","Jara","Kaela","Leifa","Lora","Mara","Neda","Nora","Os","Ragna","Rana","Rolfa","Siga","Svena","Thea","Thora","Ulf","Vala","Vida","Wulf"}}
    }},
    {"dragon", {
        {"male", {"Alar","Aur","Bel","Brin","Chron","Drak","Ember","Faf","Flam","Glaed","Gor","Hrath","Ign","Ith","Kael","Kor","Lor","Mal","Mor","Nad","Or","Pyros","Rath","Rogn","Saph","Sha","Smaug","Tar","Thorn","Ulf","Val","Vir","Wyr","Xar","Zar"}},
        {"female", {"Alara","Aura","Bela","Brina","Chrona","Draka","Embera","Fafa","Flama","Glaeda","Gora","Hratha","Igna","Itha","Kaela","Kora","Lora","Mala","Mora","Nada","Ora","Pyra","Ratha","Rogna","Sapha","Shara","Smauga","Tara","Thorna","Ulfa","Vala","Vira","Wyra","Xara","Zara"}}
    }}
};
vector<string> fallback = {"Ael","Bel","Cal","Dal","El","Far","Gar","Hald","Ian","Jar","Kael","Lor","Mar","Nor","Or","Par","Quen","Ral","Sar","Tal","Val","Wen","Xan","Yor","Zan"};

string generateName(const string& race, const string& gender, int minLen, int maxLen) {
    static mt19937 rng(time(nullptr));
    string r = race;
    string g = gender;
    if (r == "any") {
        auto it = races.begin();
        advance(it, uniform_int_distribution<int>(0, races.size()-1)(rng));
        r = it->first;
    }
    if (g == "any") {
        g = uniform_int_distribution<int>(0,1)(rng) ? "male" : "female";
    }
    vector<string> pool;
    if (races.count(r) && races[r].count(g)) {
        pool = races[r][g];
    } else {
        pool = fallback;
    }
    while (true) {
        int numParts = uniform_int_distribution<int>(1,3)(rng);
        string name;
        for (int i = 0; i < numParts; i++) {
            name += pool[uniform_int_distribution<int>(0, pool.size()-1)(rng)];
        }
        name[0] = toupper(name[0]);
        if (name.length() >= (size_t)minLen && name.length() <= (size_t)maxLen) {
            return name;
        }
    }
}

int main(int argc, char* argv[]) {
    int count = 1;
    string gender = "any", race = "any", output;
    int minLen = 2, maxLen = 12;

    static struct option long_options[] = {
        {"count", required_argument, 0, 'n'},
        {"gender", required_argument, 0, 'g'},
        {"race", required_argument, 0, 'r'},
        {"output", required_argument, 0, 'o'},
        {"min", required_argument, 0, 'm'},
        {"max", required_argument, 0, 'M'},
        {0,0,0,0}
    };
    int opt;
    while ((opt = getopt_long(argc, argv, "n:g:r:o:m:M:", long_options, nullptr)) != -1) {
        switch (opt) {
            case 'n': count = stoi(optarg); break;
            case 'g': gender = optarg; break;
            case 'r': race = optarg; break;
            case 'o': output = optarg; break;
            case 'm': minLen = stoi(optarg); break;
            case 'M': maxLen = stoi(optarg); break;
            default: 
                cerr << "Usage: fantasy_name_gen -n <count> -g <gender> -r <race> -o <file> -m <min> -M <max>\n";
                return 1;
        }
    }

    vector<string> names;
    for (int i = 0; i < count; i++) {
        names.push_back(generateName(race, gender, minLen, maxLen));
    }

    if (!output.empty()) {
        ofstream f(output);
        for (const auto& name : names) f << name << "\n";
        f.close();
        cout << "✅ Saved " << names.size() << " names to " << output << "\n";
    } else {
        cout << "\n🧙 Fantasy Name Generator (Race: " << race << ", Gender: " << gender << ")\n";
        for (size_t i = 0; i < names.size(); i++) {
            cout << (i+1) << ". " << names[i] << "\n";
        }
    }
    return 0;
}
