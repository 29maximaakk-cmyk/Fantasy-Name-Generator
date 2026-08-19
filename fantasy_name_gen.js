// fantasy_name_gen.js
#!/usr/bin/env node
const fs = require('fs');
const { program } = require('commander');

const RACES = {
    elf: {
        male: ['Ae', 'El', 'Ael', 'An', 'Ar', 'Bel', 'Cal', 'Celeb', 'Eär', 'Eld', 'Elr', 'Fin', 'Gil', 'Glor', 'Hald', 'Isil', 'Leg', 'Lind', 'Lúth', 'Maed', 'Mag', 'Mir', 'Nim', 'Oro', 'Rían', 'Saur', 'Sil', 'Tel', 'Thing', 'Tuor', 'Vor', 'Aran', 'Beleg', 'Beren', 'Círd'],
        female: ['Aew', 'An', 'Ar', 'Ared', 'Arwen', 'Cal', 'Cala', 'Celeb', 'El', 'Eär', 'El', 'Elen', 'Elr', 'Eow', 'Eä', 'Findu', 'Galad', 'Gald', 'Gil', 'Glor', 'Idril', 'Lúth', 'Míri', 'Nim', 'Sil', 'Tinú', 'Ung', 'Van', 'Varda', 'Yav', 'Zira']
    },
    orc: {
        male: ['Az', 'Bash', 'Borg', 'Burz', 'Dush', 'Ghâsh', 'Ghol', 'Ghor', 'Goth', 'Grak', 'Grish', 'Grom', 'Gul', 'Karg', 'Lug', 'Maug', 'Mog', 'Mok', 'Nazg', 'Rak', 'Rat', 'Shag', 'Skull', 'Snaga', 'Tark', 'Thrak', 'Ugl', 'Uruk', 'Zog'],
        female: ['Ag', 'As', 'Bagh', 'Borga', 'Dusha', 'Ghrá', 'Ghâsh', 'Ghola', 'Ghora', 'Gotha', 'Grak', 'Grisha', 'Groma', 'Gula', 'Karga', 'Luga', 'Mauga', 'Moga', 'Moka', 'Nazga', 'Raka', 'Rata', 'Shaga', 'Skulla', 'Snaga', 'Tarka', 'Thraka', 'Ugla', 'Uruka', 'Zoga']
    },
    human: {
        male: ['Ad', 'Al', 'Ald', 'Ar', 'Aric', 'Bael', 'Bald', 'Bri', 'Ced', 'Cor', 'Dael', 'Dar', 'Dun', 'Ead', 'Erik', 'Far', 'Gar', 'Grim', 'Hald', 'Har', 'Ing', 'Jar', 'Kael', 'Leif', 'Lor', 'Mar', 'Ned', 'Nor', 'Osw', 'Ragn', 'Ran', 'Rolf', 'Sig', 'Sven', 'Theo', 'Thor', 'Ulf', 'Val', 'Vid', 'Wulf'],
        female: ['Ad', 'Al', 'Alda', 'Ari', 'Asta', 'Bael', 'Bald', 'Bri', 'Ceda', 'Cora', 'Dael', 'Dara', 'Duna', 'Eada', 'Erika', 'Fara', 'Gara', 'Grima', 'Halda', 'Hara', 'Inga', 'Jara', 'Kaela', 'Leifa', 'Lora', 'Mara', 'Neda', 'Nora', 'Os', 'Ragna', 'Rana', 'Rolfa', 'Siga', 'Svena', 'Thea', 'Thora', 'Ulf', 'Vala', 'Vida', 'Wulf']
    },
    dragon: {
        male: ['Alar', 'Aur', 'Bel', 'Brin', 'Chron', 'Drak', 'Ember', 'Faf', 'Flam', 'Glaed', 'Gor', 'Hrath', 'Ign', 'Ith', 'Kael', 'Kor', 'Lor', 'Mal', 'Mor', 'Nad', 'Or', 'Pyros', 'Rath', 'Rogn', 'Saph', 'Sha', 'Smaug', 'Tar', 'Thorn', 'Ulf', 'Val', 'Vir', 'Wyr', 'Xar', 'Zar'],
        female: ['Alara', 'Aura', 'Bela', 'Brina', 'Chrona', 'Draka', 'Embera', 'Fafa', 'Flama', 'Glaeda', 'Gora', 'Hratha', 'Igna', 'Itha', 'Kaela', 'Kora', 'Lora', 'Mala', 'Mora', 'Nada', 'Ora', 'Pyra', 'Ratha', 'Rogna', 'Sapha', 'Shara', 'Smauga', 'Tara', 'Thorna', 'Ulfa', 'Vala', 'Vira', 'Wyra', 'Xara', 'Zara']
    }
};
const FALLBACK = ['Ael', 'Bel', 'Cal', 'Dal', 'El', 'Far', 'Gar', 'Hald', 'Ian', 'Jar', 'Kael', 'Lor', 'Mar', 'Nor', 'Or', 'Par', 'Quen', 'Ral', 'Sar', 'Tal', 'Val', 'Wen', 'Xan', 'Yor', 'Zan'];

function generateName(race, gender, minLen, maxLen) {
    let pool = [];
    let r = race;
    let g = gender;
    if (r === 'any') {
        const keys = Object.keys(RACES);
        r = keys[Math.floor(Math.random() * keys.length)];
    }
    if (g === 'any') {
        g = Math.random() < 0.5 ? 'male' : 'female';
    }
    if (RACES[r] && RACES[r][g]) {
        pool = RACES[r][g];
    } else {
        pool = FALLBACK;
    }
    while (true) {
        const numParts = Math.floor(Math.random() * 3) + 1;
        let parts = [];
        for (let i = 0; i < numParts; i++) {
            parts.push(pool[Math.floor(Math.random() * pool.length)]);
        }
        let name = parts.join('');
        if (name.length < minLen || name.length > maxLen) continue;
        return name.charAt(0).toUpperCase() + name.slice(1);
    }
}

program
    .option('-n, --count <n>', 'Number of names', parseInt, 1)
    .option('-g, --gender <g>', 'Gender: male, female, any', 'any')
    .option('-r, --race <r>', 'Race: elf, orc, human, dragon, any', 'any')
    .option('-o, --output <file>', 'Output file')
    .option('--min <n>', 'Minimum length', parseInt, 2)
    .option('--max <n>', 'Maximum length', parseInt, 12)
    .parse(process.argv);

const opts = program.opts();

const names = [];
for (let i = 0; i < opts.count; i++) {
    names.push(generateName(opts.race, opts.gender, opts.min, opts.max));
}

if (opts.output) {
    fs.writeFileSync(opts.output, names.join('\n') + '\n');
    console.log(`✅ Saved ${names.length} names to ${opts.output}`);
} else {
    console.log(`\n🧙 Fantasy Name Generator (Race: ${opts.race}, Gender: ${opts.gender})`);
    names.forEach((name, i) => console.log(`${i+1}. ${name}`));
}
