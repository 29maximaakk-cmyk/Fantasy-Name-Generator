# fantasy_name_gen.php
#!/usr/bin/env php
<?php
$races = [
    'elf' => [
        'male' => ['Ae', 'El', 'Ael', 'An', 'Ar', 'Bel', 'Cal', 'Celeb', 'Eär', 'Eld', 'Elr', 'Fin', 'Gil', 'Glor', 'Hald', 'Isil', 'Leg', 'Lind', 'Lúth', 'Maed', 'Mag', 'Mir', 'Nim', 'Oro', 'Rían', 'Saur', 'Sil', 'Tel', 'Thing', 'Tuor', 'Vor', 'Aran', 'Beleg', 'Beren', 'Círd'],
        'female' => ['Aew', 'An', 'Ar', 'Ared', 'Arwen', 'Cal', 'Cala', 'Celeb', 'El', 'Eär', 'El', 'Elen', 'Elr', 'Eow', 'Eä', 'Findu', 'Galad', 'Gald', 'Gil', 'Glor', 'Idril', 'Lúth', 'Míri', 'Nim', 'Sil', 'Tinú', 'Ung', 'Van', 'Varda', 'Yav', 'Zira']
    ],
    'orc' => [
        'male' => ['Az', 'Bash', 'Borg', 'Burz', 'Dush', 'Ghâsh', 'Ghol', 'Ghor', 'Goth', 'Grak', 'Grish', 'Grom', 'Gul', 'Karg', 'Lug', 'Maug', 'Mog', 'Mok', 'Nazg', 'Rak', 'Rat', 'Shag', 'Skull', 'Snaga', 'Tark', 'Thrak', 'Ugl', 'Uruk', 'Zog'],
        'female' => ['Ag', 'As', 'Bagh', 'Borga', 'Dusha', 'Ghrá', 'Ghâsh', 'Ghola', 'Ghora', 'Gotha', 'Grak', 'Grisha', 'Groma', 'Gula', 'Karga', 'Luga', 'Mauga', 'Moga', 'Moka', 'Nazga', 'Raka', 'Rata', 'Shaga', 'Skulla', 'Snaga', 'Tarka', 'Thraka', 'Ugla', 'Uruka', 'Zoga']
    ],
    'human' => [
        'male' => ['Ad', 'Al', 'Ald', 'Ar', 'Aric', 'Bael', 'Bald', 'Bri', 'Ced', 'Cor', 'Dael', 'Dar', 'Dun', 'Ead', 'Erik', 'Far', 'Gar', 'Grim', 'Hald', 'Har', 'Ing', 'Jar', 'Kael', 'Leif', 'Lor', 'Mar', 'Ned', 'Nor', 'Osw', 'Ragn', 'Ran', 'Rolf', 'Sig', 'Sven', 'Theo', 'Thor', 'Ulf', 'Val', 'Vid', 'Wulf'],
        'female' => ['Ad', 'Al', 'Alda', 'Ari', 'Asta', 'Bael', 'Bald', 'Bri', 'Ceda', 'Cora', 'Dael', 'Dara', 'Duna', 'Eada', 'Erika', 'Fara', 'Gara', 'Grima', 'Halda', 'Hara', 'Inga', 'Jara', 'Kaela', 'Leifa', 'Lora', 'Mara', 'Neda', 'Nora', 'Os', 'Ragna', 'Rana', 'Rolfa', 'Siga', 'Svena', 'Thea', 'Thora', 'Ulf', 'Vala', 'Vida', 'Wulf']
    ],
    'dragon' => [
        'male' => ['Alar', 'Aur', 'Bel', 'Brin', 'Chron', 'Drak', 'Ember', 'Faf', 'Flam', 'Glaed', 'Gor', 'Hrath', 'Ign', 'Ith', 'Kael', 'Kor', 'Lor', 'Mal', 'Mor', 'Nad', 'Or', 'Pyros', 'Rath', 'Rogn', 'Saph', 'Sha', 'Smaug', 'Tar', 'Thorn', 'Ulf', 'Val', 'Vir', 'Wyr', 'Xar', 'Zar'],
        'female' => ['Alara', 'Aura', 'Bela', 'Brina', 'Chrona', 'Draka', 'Embera', 'Fafa', 'Flama', 'Glaeda', 'Gora', 'Hratha', 'Igna', 'Itha', 'Kaela', 'Kora', 'Lora', 'Mala', 'Mora', 'Nada', 'Ora', 'Pyra', 'Ratha', 'Rogna', 'Sapha', 'Shara', 'Smauga', 'Tara', 'Thorna', 'Ulfa', 'Vala', 'Vira', 'Wyra', 'Xara', 'Zara']
    ]
];
$fallback = ['Ael', 'Bel', 'Cal', 'Dal', 'El', 'Far', 'Gar', 'Hald', 'Ian', 'Jar', 'Kael', 'Lor', 'Mar', 'Nor', 'Or', 'Par', 'Quen', 'Ral', 'Sar', 'Tal', 'Val', 'Wen', 'Xan', 'Yor', 'Zan'];

function generateName($race, $gender, $minLen, $maxLen) {
    global $races, $fallback;
    $pool = [];
    if ($race == 'any') {
        $race = array_rand($races);
    }
    if ($gender == 'any') {
        $gender = rand(0,1) ? 'male' : 'female';
    }
    if (isset($races[$race]) && isset($races[$race][$gender])) {
        $pool = $races[$race][$gender];
    } else {
        $pool = $fallback;
    }
    while (true) {
        $numParts = rand(1, 3);
        $parts = [];
        for ($i=0; $i<$numParts; $i++) {
            $parts[] = $pool[array_rand($pool)];
        }
        $name = implode('', $parts);
        if (strlen($name) < $minLen || strlen($name) > $maxLen) continue;
        return ucfirst($name);
    }
}

$opts = getopt("n:g:r:o:", ["count:", "gender:", "race:", "output:", "min:", "max:"]);
$count = isset($opts['n']) ? (int)$opts['n'] : (isset($opts['count']) ? (int)$opts['count'] : 1);
$gender = $opts['g'] ?? $opts['gender'] ?? 'any';
$race = $opts['r'] ?? $opts['race'] ?? 'any';
$output = $opts['o'] ?? $opts['output'] ?? null;
$minLen = isset($opts['min']) ? (int)$opts['min'] : 2;
$maxLen = isset($opts['max']) ? (int)$opts['max'] : 12;

$names = [];
for ($i=0; $i<$count; $i++) {
    $names[] = generateName($race, $gender, $minLen, $maxLen);
}

if ($output) {
    file_put_contents($output, implode("\n", $names) . "\n");
    echo "✅ Saved " . count($names) . " names to $output\n";
} else {
    echo "\n🧙 Fantasy Name Generator (Race: $race, Gender: $gender)\n";
    foreach ($names as $idx => $name) {
        echo ($idx+1) . ". $name\n";
    }
}
?>
