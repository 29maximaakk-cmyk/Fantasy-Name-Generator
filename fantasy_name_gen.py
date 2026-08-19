# fantasy_name_gen.py
import sys
import random
import argparse
import string

# Syllable parts for each race and gender
RACES = {
    'elf': {
        'male': ['Ae', 'El', 'Ael', 'An', 'Ar', 'Bel', 'Cal', 'Celeb', 'Eär', 'Eld', 'Elr', 'Fin', 'Gil', 'Glor', 'Hald', 'Isil', 'Leg', 'Lind', 'Lúth', 'Maed', 'Mag', 'Mir', 'Nim', 'Oro', 'Rían', 'Saur', 'Sil', 'Tel', 'Thing', 'Tuor', 'Vor', 'Aran', 'Beleg', 'Beren', 'Círd'],
        'female': ['Aew', 'An', 'Ar', 'Ared', 'Arwen', 'Cal', 'Cala', 'Celeb', 'El', 'Eär', 'El', 'Elen', 'Elr', 'Eow', 'Eä', 'Findu', 'Galad', 'Gald', 'Gil', 'Glor', 'Idril', 'Lúth', 'Míri', 'Nim', 'Sil', 'Tinú', 'Ung', 'Van', 'Varda', 'Yav', 'Zira']
    },
    'orc': {
        'male': ['Az', 'Bash', 'Borg', 'Burz', 'Dush', 'Ghâsh', 'Ghol', 'Ghor', 'Goth', 'Grak', 'Grish', 'Grom', 'Gul', 'Karg', 'Lug', 'Maug', 'Mog', 'Mok', 'Nazg', 'Rak', 'Rat', 'Shag', 'Skull', 'Snaga', 'Tark', 'Thrak', 'Ugl', 'Uruk', 'Zog'],
        'female': ['Ag', 'As', 'Bagh', 'Borga', 'Dusha', 'Ghrá', 'Ghâsh', 'Ghola', 'Ghora', 'Gotha', 'Grak', 'Grisha', 'Groma', 'Gula', 'Karga', 'Luga', 'Mauga', 'Moga', 'Moka', 'Nazga', 'Raka', 'Rata', 'Shaga', 'Skulla', 'Snaga', 'Tarka', 'Thraka', 'Ugla', 'Uruka', 'Zoga']
    },
    'human': {
        'male': ['Ad', 'Al', 'Ald', 'Ar', 'Aric', 'Bael', 'Bald', 'Bri', 'Ced', 'Cor', 'Dael', 'Dar', 'Dun', 'Ead', 'Erik', 'Far', 'Gar', 'Grim', 'Hald', 'Har', 'Ing', 'Jar', 'Kael', 'Leif', 'Lor', 'Mar', 'Ned', 'Nor', 'Osw', 'Ragn', 'Ran', 'Rolf', 'Sig', 'Sven', 'Theo', 'Thor', 'Ulf', 'Val', 'Vid', 'Wulf'],
        'female': ['Ad', 'Al', 'Alda', 'Ari', 'Asta', 'Bael', 'Bald', 'Bri', 'Ceda', 'Cora', 'Dael', 'Dara', 'Duna', 'Eada', 'Erika', 'Fara', 'Gara', 'Grima', 'Halda', 'Hara', 'Inga', 'Jara', 'Kaela', 'Leifa', 'Lora', 'Mara', 'Neda', 'Nora', 'Os', 'Ragna', 'Rana', 'Rolfa', 'Siga', 'Svena', 'Thea', 'Thora', 'Ulf', 'Vala', 'Vida', 'Wulf']
    },
    'dragon': {
        'male': ['Alar', 'Aur', 'Bel', 'Brin', 'Chron', 'Drak', 'Ember', 'Faf', 'Flam', 'Glaed', 'Gor', 'Hrath', 'Ign', 'Ith', 'Kael', 'Kor', 'Lor', 'Mal', 'Mor', 'Nad', 'Or', 'Pyros', 'Rath', 'Rogn', 'Saph', 'Sha', 'Smaug', 'Tar', 'Thorn', 'Ulf', 'Val', 'Vir', 'Wyr', 'Xar', 'Zar'],
        'female': ['Alara', 'Aura', 'Bela', 'Brina', 'Chrona', 'Draka', 'Embera', 'Fafa', 'Flama', 'Glaeda', 'Gora', 'Hratha', 'Igna', 'Itha', 'Kaela', 'Kora', 'Lora', 'Mala', 'Mora', 'Nada', 'Ora', 'Pyra', 'Ratha', 'Rogna', 'Sapha', 'Shara', 'Smauga', 'Tara', 'Thorna', 'Ulfa', 'Vala', 'Vira', 'Wyra', 'Xara', 'Zara']
    }
}
# Fallback if race/gender not found
FALLBACK = ['Ael', 'Bel', 'Cal', 'Dal', 'El', 'Far', 'Gar', 'Hald', 'Ian', 'Jar', 'Kael', 'Lor', 'Mar', 'Nor', 'Or', 'Par', 'Quen', 'Ral', 'Sar', 'Tal', 'Val', 'Wen', 'Xan', 'Yor', 'Zan']

def generate_name(race='any', gender='any', min_len=2, max_len=12):
    # Determine pool
    pool = []
    if race == 'any':
        race = random.choice(list(RACES.keys()))
    if gender == 'any':
        gender = random.choice(['male', 'female'])
    if race in RACES and gender in RACES[race]:
        pool = RACES[race][gender]
    else:
        pool = FALLBACK
    # Build name from 1-3 parts
    name = ''
    while len(name) < min_len or len(name) > max_len:
        num_parts = random.randint(1, 3)
        parts = [random.choice(pool) for _ in range(num_parts)]
        name = ''.join(parts)
        # Capitalize first letter
        name = name.capitalize()
        # If too short or too long, retry
        if len(name) < min_len or len(name) > max_len:
            name = ''
    return name

def main():
    parser = argparse.ArgumentParser(description='Fantasy Name Generator')
    parser.add_argument('-n', '--count', type=int, default=1, help='Number of names')
    parser.add_argument('-g', '--gender', choices=['male', 'female', 'any'], default='any')
    parser.add_argument('-r', '--race', choices=['elf', 'orc', 'human', 'dragon', 'any'], default='any')
    parser.add_argument('-o', '--output', help='Output file')
    parser.add_argument('--min', type=int, default=2, help='Minimum length')
    parser.add_argument('--max', type=int, default=12, help='Maximum length')
    args = parser.parse_args()

    names = []
    for _ in range(args.count):
        names.append(generate_name(args.race, args.gender, args.min, args.max))

    if args.output:
        with open(args.output, 'w') as f:
            for name in names:
                f.write(name + '\n')
        print(f"✅ Saved {len(names)} names to {args.output}")
    else:
        print(f"\n🧙 Fantasy Name Generator (Race: {args.race}, Gender: {args.gender})")
        for i, name in enumerate(names, 1):
            print(f"{i}. {name}")

if __name__ == '__main__':
    main()
